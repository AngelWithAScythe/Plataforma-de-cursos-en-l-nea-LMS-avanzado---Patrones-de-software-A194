package com.java.lms.curso.model;

import com.java.lms.model.Usuario;

public class PaqueteCurso {
	
	private final String id;
	
	private final Contenido contenido;

	private final Evaluacion evaluacion;

	private final Certificado certificado;
	
	private final Usuario instructor;

	public PaqueteCurso(String id, Contenido contenido, Evaluacion evaluacion, Certificado certificado, Usuario instructor) {
		this.id = id;
		this.contenido = contenido;
		this.evaluacion = evaluacion;
		this.certificado = certificado;
		this.instructor = instructor;
	}
	
	
	public String getId() {
		return id;
	}

	public Contenido getContenido() {
		return contenido;
	}

	public Evaluacion getEvaluacion() {
		return evaluacion;
	}

	public Certificado getCertificado() {
		return certificado;
	}
	
	public Usuario getInstructor() {
		return instructor;
	}
}
