package com.java.lms.curso.estado;

public class EstadoArchivado implements EstadoCurso{
	
	@Override
	public String getNombre() { return "Archivado"; }

	@Override
	public boolean admiteInscripciones() { return false; }

	@Override
	public boolean permiteEdicion() { return false; }

	@Override
	public EstadoCurso publicar() { return new EstadoPublicado(); } 

	@Override
	public EstadoCurso archivar() {
		throw new IllegalStateException("El curso ya está archivado");
	}
}
