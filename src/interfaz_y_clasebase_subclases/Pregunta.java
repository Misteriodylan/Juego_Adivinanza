package interfaz_y_clasebase_subclases;

public abstract class Pregunta implements Evaluable {
    protected int id;
    protected String enunciado;
    protected int puntajeBase;
    protected int idCategoria;

    public Pregunta(int id, String enunciado, int puntajeBase, int idCategoria) {
        this.id = id;
        this.enunciado = enunciado;
        this.puntajeBase = puntajeBase;
        this.idCategoria = idCategoria;
    }

    public int getId() { return id; }
    public String getEnunciado() { return enunciado; }
    public int getPuntajeBase() { return puntajeBase; }
    public int getIdCategoria() { return idCategoria; }
}