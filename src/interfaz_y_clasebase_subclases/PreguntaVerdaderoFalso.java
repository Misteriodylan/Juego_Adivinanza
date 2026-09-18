package interfaz_y_clasebase_subclases;

public class PreguntaVerdaderoFalso extends Pregunta {
    private boolean respuestaCorrecta;

    public PreguntaVerdaderoFalso(int id, String enunciado, int puntajeBase, int idCategoria, boolean respuestaCorrecta) {
        super(id, enunciado, puntajeBase, idCategoria);
        this.respuestaCorrecta = respuestaCorrecta;
    }

    @Override
    public boolean evaluarRespuesta(Object respuesta) {
        if (!(respuesta instanceof Boolean)) return false;
        return ((Boolean) respuesta) == respuestaCorrecta;
    }

    @Override
    public int calcularPuntajeFinal(int tiempoSegundos) {
        return puntajeBase;
    }

    public boolean isRespuestaCorrecta() { return respuestaCorrecta; }
}