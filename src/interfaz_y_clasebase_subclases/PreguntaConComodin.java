package interfaz_y_clasebase_subclases;
import java.util.List;

public class PreguntaConComodin extends Pregunta {
    private  String tipoComodinAplicado;
    private List<String> opcionesRestantes;
    private int indiceCorrecto;

    public PreguntaConComodin(int id, String enunciado, int puntajeBase, int idCategoria, String tipoComodinAplicado, List<String> opcionesRestantes, int indiceCorrecto) {
        super(id, enunciado, puntajeBase, idCategoria);
        this.tipoComodinAplicado = tipoComodinAplicado;
        this.opcionesRestantes = opcionesRestantes;
        this.indiceCorrecto = indiceCorrecto;
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
        return puntajeBase / 2; 
    }
    public String getTipoComodinAplicado() {
        return tipoComodinAplicado;
    }

    public List<String> getOpcionesRestantes() { return opcionesRestantes; }
}


