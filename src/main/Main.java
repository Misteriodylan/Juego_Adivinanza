package main;

import excepciones.*;
import model.*;
import service.JuegoService;

import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        JuegoService service = new JuegoService();

        System.out.println("=================================================");
        System.out.println("  TRIVIA QUEST   ");
        System.out.println("=================================================\n");

       
        int idPrueba = 1;
        PreguntaOpcionMultiple pBaseDatos = new PreguntaOpcionMultiple(
                idPrueba,
                "¿Qué película ganó el Oscar en 2020?",
                100,
                1,
                Arrays.asList("1917", "Parasite", "Joker", "Roma"),
                1
        );

        try {
            System.out.print("Guardando pregunta ID " + idPrueba + " en MySQL... ");
            service.registrarPreguntaEnBD(pBaseDatos);
            System.out.println("[OK]");

            System.out.print("Recuperando pregunta ID " + idPrueba + " desde MySQL... ");
            Pregunta recuperada = service.buscarPreguntaPorId(idPrueba);
            if (recuperada != null) {
                System.out.println("[ÉXITO]");
                System.out.println("   Enunciado desde BD: " + recuperada.getEnunciado());
            }
        } catch (Exception e) {
            System.out.println("\n[AVISO BD]: La pregunta ya existía en MySQL o hubo un problema de conexión.");
            System.out.println("            Detalle: " + e.getMessage());
        }

        System.out.println("\nPresioná Enter para iniciar el menú del juego...");
        scanner.nextLine();

        int vidas = 5;
        int puntos = 0;
        int monedas = 50;
        boolean partidaActiva = true;

        while (partidaActiva) {
            System.out.println("\n--- ESTADO ACTUAL ---");
            System.out.println("Vidas: " + vidas + " | Puntos: " + puntos + " | Monedas: $" + monedas);
            System.out.println("---------------------");
            System.out.println("1. Jugar Turno Normal");
            System.out.println("2. Comprar Comodín en Tienda (Costo: $100)");
            System.out.println("3. Jugar Turno 'Doble o Nada' (Apuesta)");
            System.out.println("4. Forzar Categoría Inválida (Prueba Excepción)");
            System.out.println("5. Salir del Juego");
            System.out.print("Seleccioná una opción: ");

            int opcion = scanner.nextInt();

            switch (opcion) {
                case 1:
                    Pregunta pTurno = null;
                    try {
                        pTurno = service.buscarPreguntaPorId(idPrueba);
                    } catch (Exception ignored) {}

                    if (pTurno == null) {
                        pTurno = pBaseDatos;
                    }

                    System.out.println("\n[CATEGORÍA: Películas]");
                    System.out.println(pTurno.getEnunciado());
                    System.out.println("0) 1917 | 1) Parasite | 2) Joker | 3) Roma");
                    System.out.print("Tu respuesta (0-3): ");
                    int resp1 = scanner.nextInt();

                    System.out.print("¿Cuántos segundos tardaste en responder?: ");
                    int tiempo1 = scanner.nextInt();

                    try {
                        int ganancia = service.procesarTurno(pTurno, resp1, tiempo1);
                        if (ganancia > 0) {
                            System.out.println("¡CORRECTO! Sumaste " + ganancia + " puntos y $20 monedas.");
                            puntos += ganancia;
                            monedas += 20;
                        } else {
                            System.out.println("¡INCORRECTO! Perdiste 1 vida.");
                            vidas--;
                        }
                    } catch (TiempoAgotadoException e) {
                        System.out.println("\n[excepcion capturada]: " + e.getMessage());
                        vidas--;
                    }
                    break;

                case 2:
                    System.out.println("\nIntentando comprar Comodín 'Bomba 50/50' ($100)...");
                    try {
                        service.comprarComodin(monedas, 100);
                        monedas -= 100;
                        System.out.println("¡Comodín comprado exitosamente!");
                    } catch (MonedasInsuficientesException e) {
                        System.out.println("\n[excepcion capturada]: " + e.getMessage());
                    }
                    break;

                case 3:
                    try {
                        service.validarApuestaRiesgo(vidas);
                        Pregunta pApuesta = new PreguntaApuestaRiesgo(
                                3, "¿En qué año fue el Maracanazo?", 200, 5,
                                Arrays.asList("1950", "1962", "1970", "1954"), 0, 2.0, 2
                        );

                        System.out.println("\n[turno especial: doble o nada]");
                        System.out.println(pApuesta.getEnunciado());
                        System.out.println("0) 1950 | 1) 1962 | 2) 1970 | 3) 1954");
                        System.out.print("Tu respuesta (0-3): ");
                        int respA = scanner.nextInt();

                        int gananciaA = service.procesarTurno(pApuesta, respA, 5);
                        if (gananciaA > 0) {
                            System.out.println("¡ACERTASTE LA APUESTA! Ganaste " + gananciaA + " puntos.");
                            puntos += gananciaA;
                        } else {
                            System.out.println("¡FALLASTE LA APUESTA! Perdiste 2 vidas.");
                            vidas -= 2;
                        }
                    } catch (VidasInsuficientesParaApuestaException | TiempoAgotadoException e) {
                        System.out.println("\n[excepcion capturada]: " + e.getMessage());
                    }
                    break;

                case 4:
                    System.out.println("\nSeleccionando categoría ID 99...");
                    try {
                        service.validarInicioPartida(vidas, 99);
                    } catch (CategoriaInvalidaException | SinVidasDisponiblesException e) {
                        System.out.println("\n[excepcion capturada]: " + e.getMessage());
                    }
                    break;

                case 5:
                    partidaActiva = false;
                    System.out.println("\nSaliendo del juego...");
                    break;

                default:
                    System.out.println("Opción no válida.");
            }

            if (vidas <= 0) {
                System.out.println("\n=============================================");
                System.out.println("¡GAME OVER! Te quedaste sin vidas.");
                System.out.println("Puntaje Final: " + puntos);
                System.out.println("=============================================");
                partidaActiva = false;
            }
        }

        scanner.close();
    }
}