package com.java.lms.curso.model;

public class ContenidoTecnico extends Contenido{
	
	public ContenidoTecnico(String titulo, String descripcion) {
		super(titulo, descripcion);
	}

	@Override
	public TipoCurso getTipo() {
		return TipoCurso.TECNICO;
	}
}
