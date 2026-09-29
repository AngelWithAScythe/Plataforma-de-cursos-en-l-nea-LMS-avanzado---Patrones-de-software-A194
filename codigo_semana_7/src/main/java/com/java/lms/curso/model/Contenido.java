package com.java.lms.curso.model;

public abstract class Contenido {
	
	private String titulo;

	private String descripcion;

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
}
