package excepciones;

public class SinVidasDisponiblesException extends Exception {
	private static final long serialVersionUID = 1L;
    public SinVidasDisponiblesException(String msg) 
    { 
    super(msg); 
    	}
}