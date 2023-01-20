package Compiler;

public class ErreurCompilation extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ErreurCompilation(CodesErr MessErr){
		super(MessErr.toString());
	}
}
	