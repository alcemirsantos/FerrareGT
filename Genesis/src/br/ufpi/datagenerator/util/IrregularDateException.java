package br.ufpi.datagenerator.util;

public class IrregularDateException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public IrregularDateException(String message) {
		super("era esperado o caractere" + message);
	}

}
