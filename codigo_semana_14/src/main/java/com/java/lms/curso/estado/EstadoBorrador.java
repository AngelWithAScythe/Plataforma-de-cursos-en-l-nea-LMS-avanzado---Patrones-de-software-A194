package com.java.lms.curso.estado;

public class EstadoBorrador implements EstadoCurso{
	
	@Override
	public String getNombre() { return "Borrador"; }

	@Override
	public boolean admiteInscripciones() { return false; }

	@Override
	public boolean permiteEdicion() { return true; }

	@Override
	public EstadoCurso publicar() { return new EstadoPublicado(); }

	@Override
	public EstadoCurso archivar() {
		throw new IllegalStateException("Un curso en borrador no se puede archivar; primero debe publicarse");
	}
}
