package interfaz_y_clasebase_subclases;
import java.util.List;

public class PreguntaApuestaRiesgo extends Pregunta {
    private List<String> opciones;
    private int indiceCorrecto;
    private double multiplicadorRecompensa;
    private int penalizacionVidas;

    public PreguntaApuestaRiesgo(int id, String enunciado, int puntajeBase, int idCategoria, List<String> opciones, int indiceCorrecto, double multiplicadorRecompensa, int penalizacionVidas) {
        super(id, enunciado, puntajeBase, idCategoria);
        this.opciones = opciones;
        this.indiceCorrecto = indiceCorrecto;
        this.multiplicadorRecompensa = multiplicadorRecompensa;
        this.penalizacionVidas = penalizacionVidas;
    }

    @Override
    public boolean evaluarRespuesta(Object respuesta) {
        if (respuesta instanceof Integer) {
            return (Integer) respuesta == indiceCorrecto;
        }
        return false;
    }

    @Override
    public int calcularPuntajeFinal(int tiempoSegundos) {
        return (int) (puntajeBase * multiplicadorRecompensa);
    }

    public List<String> getOpciones() { return opciones; }
    public int getPenalizacionVidas() { return penalizacionVidas; }
}