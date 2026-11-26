package com.java.lms.curso.estado;

public class EstadoPublicado implements EstadoCurso{
	
	@Override
	public String getNombre() { return "Publicado"; }

	@Override
	public boolean admiteInscripciones() { return true; }

	@Override
	public boolean permiteEdicion() { return true; }

	@Override
	public EstadoCurso publicar() {
		throw new IllegalStateException("El curso ya está publicado");
	}

	@Override
	public EstadoCurso archivar() { return new EstadoArchivado(); }
}
