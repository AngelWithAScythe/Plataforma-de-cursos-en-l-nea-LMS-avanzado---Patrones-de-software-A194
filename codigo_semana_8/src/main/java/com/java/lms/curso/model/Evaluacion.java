package com.java.lms.curso.model;

public abstract class Evaluacion {
	
	private String nombre;

	private int notaMinimaAprobacion;

	public Evaluacion(String nombre, int notaMinimaAprobacion) {
		this.nombre = nombre;
		this.notaMinimaAprobacion = notaMinimaAprobacion;
	}

	public abstract TipoCurso getTipo();

	public String getNombre() {
		return nombre;
	}

	public int getNotaMinimaAprobacion() {
		return notaMinimaAprobacion;
	}
}
