package com.java.lms.curso.model;

public abstract class Certificado {
	
	private String nombreCurso;

	private String codigoValidacion;

	public Certificado(String nombreCurso, String codigoValidacion) {
		this.nombreCurso = nombreCurso;
		this.codigoValidacion = codigoValidacion;
	}

	public abstract TipoCurso getTipo();

	public String getNombreCurso() {
		return nombreCurso;
	}

	public String getCodigoValidacion() {
		return codigoValidacion;
	}
}
