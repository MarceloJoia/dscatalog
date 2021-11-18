package br.com.joiamarketing.dscatalog.services.exceptions;

public class DataBaseException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public DataBaseException(String msn) {
		super(msn);
	}
}
