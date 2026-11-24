package com.java.lms.curso.model;

public class ContenidoCertificacion extends Contenido{
	
	public ContenidoCertificacion(String titulo, String descripcion) {
		super(titulo, descripcion);
	}

	@Override
	public TipoCurso getTipo() {
		return TipoCurso.CERTIFICACION;
	}
}
