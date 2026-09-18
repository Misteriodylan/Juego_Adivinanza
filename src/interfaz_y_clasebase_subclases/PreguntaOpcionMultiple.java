package interfaz_y_clasebase_subclases;

import java.util.List;

public class PreguntaOpcionMultiple extends Pregunta {
    private List<String> opciones;
    private int indiceCorrecto;

    public PreguntaOpcionMultiple(int id, String enunciado, int puntajeBase, int idCategoria, List<String> opciones, int indiceCorrecto) {
        super(id, enunciado, puntajeBase, idCategoria);
        this.opciones = opciones;
        this.indiceCorrecto = indiceCorrecto;
    }

    @Override
    public boolean evaluarRespuesta(Object respuesta) {
        if (!(respuesta instanceof Integer)) return false;
        int eleccion = (Integer) respuesta;
        return eleccion == indiceCorrecto;
    }

    @Override
    public int calcularPuntajeFinal(int tiempoSegundos) {
        return Math.max(10, puntajeBase - (tiempoSegundos * 2));
    }

    public List<String> getOpciones() { return opciones; }
    public int getIndiceCorrecto() { return indiceCorrecto; }
}