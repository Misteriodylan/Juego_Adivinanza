package interfaz_y_clasebase_subclases;

public class PreguntaBonus extends Pregunta {
    private Pregunta preguntaBase;
    private double multiplicadorPuntos;
    private int penalizacionVidas;

    public PreguntaBonus(int id, String enunciado, int puntajeBase, int idCategoria, Pregunta preguntaBase, double multiplicadorPuntos, int penalizacionVidas) {
        super(id, enunciado, puntajeBase, idCategoria);
        this.preguntaBase = preguntaBase;
        this.multiplicadorPuntos = multiplicadorPuntos;
        this.penalizacionVidas = penalizacionVidas;
    }

    @Override
    public boolean evaluarRespuesta(Object respuesta) {
        return preguntaBase.evaluarRespuesta(respuesta);
    }

    @Override
    public int calcularPuntajeFinal(int tiempoSegundos) {
        int puntajeOriginal = preguntaBase.calcularPuntajeFinal(tiempoSegundos);
        return (int) (puntajeOriginal * multiplicadorPuntos);
    }

    public int getPenalizacionVidas() { return penalizacionVidas; }
}