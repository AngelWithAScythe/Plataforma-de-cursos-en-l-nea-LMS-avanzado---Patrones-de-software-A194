package com.java.lms.curso.model;

public class Leccion implements ElementoContenido{
	
	private final String titulo;
	private final int duracionMinutos;

	public Leccion(String titulo, int duracionMinutos) {
		this.titulo = titulo;
		this.duracionMinutos = duracionMinutos;
	}

	@Override
	public String getTitulo() {
		return titulo;
	}

	@Override
	public int getDuracionMinutos() {
		return duracionMinutos;
	}

	@Override
	public int contarLecciones() {
		return 1;
	}
}
