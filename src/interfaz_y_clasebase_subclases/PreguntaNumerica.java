package interfaz_y_clasebase_subclases;

public class PreguntaNumerica extends Pregunta {
    private double valorEsperado;
    private double margenTolerancia;

    public PreguntaNumerica(int id, String enunciado, int puntajeBase, int idCategoria, double valorEsperado, double margenTolerancia) {
        super(id, enunciado, puntajeBase, idCategoria);
        this.valorEsperado = valorEsperado;
        this.margenTolerancia = margenTolerancia;
    }

    @Override
    public boolean evaluarRespuesta(Object respuesta) {
        if (!(respuesta instanceof Double) && !(respuesta instanceof Integer)) return false;
        double valor = Double.parseDouble(respuesta.toString());
        return Math.abs(valor - valorEsperado) <= margenTolerancia;
    }

    @Override
    public int calcularPuntajeFinal(int tiempoSegundos) {
        return puntajeBase + 50; // Otorga extra por mayor dificultad matemática
    }

    public double getValorEsperado() { return valorEsperado; }
    public double getMargenTolerancia() { return margenTolerancia; }
}