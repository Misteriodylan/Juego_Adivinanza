package service;

import Dao.PreguntaDAO;
import excepciones.*;
import interfaz.Evaluable;
import model.Pregunta;
import model.PreguntaOpcionMultiple;

import java.sql.SQLException;

public class JuegoService {
    private final PreguntaDAO dao = new PreguntaDAO();

    public int procesarTurno(Evaluable elemento, Object respuesta, int tiempoSegundos) 
            throws TiempoAgotadoException {
        if (tiempoSegundos > 20) {
            throw new TiempoAgotadoException("Se superó el tiempo máximo de respuesta (20s). Turno perdido.");
        }

        if (elemento.evaluarRespuesta(respuesta)) {
            return elemento.calcularPuntajeFinal(tiempoSegundos);
        }
        return 0;
    }

    public void comprarComodin(int monedasActuales, int costo) throws MonedasInsuficientesException {
        if (monedasActuales < costo) {
            throw new MonedasInsuficientesException("No tenés monedas suficientes" + monedasActuales + ") para comprar este comodín " + costo + ").");
        }
    }

    public void validarApuestaRiesgo(int vidas) throws VidasInsuficientesParaApuestaException {
        if (vidas <= 1) {
            throw new VidasInsuficientesParaApuestaException("Se requieren al menos 2 vidas para entrar en 'Doble o Nada'.");
        }
    }

    public void validarInicioPartida(int vidas, int idCategoria) 
            throws SinVidasDisponiblesException, CategoriaInvalidaException {
        if (vidas <= 0) {
            throw new SinVidasDisponiblesException("No podés jugar: Te quedaste sin vidas.");
        }
        if (idCategoria < 1 || idCategoria > 6) {
            throw new CategoriaInvalidaException("La categoría elegida (" + idCategoria + ") no existe en el juego.");
        }
    }

    public boolean registrarPreguntaEnBD(PreguntaOpcionMultiple p) throws SQLException {
        return dao.guardarOpcionMultiple(p);
    }

    public Pregunta buscarPreguntaPorId(int id) throws SQLException {
        return dao.obtenerPorId(id);
    }
}