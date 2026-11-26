package com.java.lms.curso.model;

import com.java.lms.curso.visitor.VisitanteContenido;

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
	
	@Override
	public Leccion copiar() {
		return new Leccion(titulo, duracionMinutos);
	}
	
	@Override
	public void aceptar(VisitanteContenido visitante) {
		visitante.visitarLeccion(this);
	}
}
