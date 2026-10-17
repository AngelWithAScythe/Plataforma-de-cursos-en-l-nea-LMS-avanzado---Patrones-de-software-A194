package com.java.lms.curso.formato;

public abstract class FormateadorDecorator implements FormateadorCertificado{
	
	protected final FormateadorCertificado formateador;
	
	protected FormateadorDecorator(FormateadorCertificado formateador) {
		this.formateador = formateador;
	}
}
