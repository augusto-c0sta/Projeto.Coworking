package Exceptions;

public class CampoVazioException extends Exception{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CampoVazioException(String campo) {
        super("O campo \"" + campo + "\" não pode estar vazio!");
    }
}