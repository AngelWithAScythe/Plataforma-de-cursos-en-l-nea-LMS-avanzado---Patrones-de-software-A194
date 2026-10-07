package com.java.lms.curso.model;

public abstract class Contenido {
	
	private String titulo;

	private String descripcion;
	
	private Modulo estructura = new Modulo("Contenido del curso");

	public Contenido(String titulo, String descripcion) {
		this.titulo = titulo;
		this.descripcion = descripcion;
	}

	public abstract TipoCurso getTipo();

	public String getTitulo() {
		return titulo;
	}

	public String getDescripcion() {
		return descripcion;
	}
	
	public Modulo getEstructura() {
		return estructura;
	}
}
