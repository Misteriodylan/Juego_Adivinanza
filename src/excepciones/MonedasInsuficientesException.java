package excepciones;

public class MonedasInsuficientesException extends Exception {
	private static final long serialVersionUID = 1L;
    public MonedasInsuficientesException(String msg) 
    { 
    super(msg); 
    }
}