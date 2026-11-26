package com.java.lms.curso.estado;

public interface EstadoCurso {
	
	String getNombre();

	boolean admiteInscripciones();

	boolean permiteEdicion();

	EstadoCurso publicar();

	EstadoCurso archivar();
}
