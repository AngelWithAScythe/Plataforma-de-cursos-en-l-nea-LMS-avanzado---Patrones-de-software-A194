package com.java.lms.curso.command;

public interface ComandoContenido {
	
	void ejecutar();

	void deshacer();

	String getDescripcion();
}
