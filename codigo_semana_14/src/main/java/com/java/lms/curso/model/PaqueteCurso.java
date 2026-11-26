package com.java.lms.curso.model;

import com.java.lms.model.Usuario;
import com.java.lms.curso.estado.EstadoCurso;
import com.java.lms.curso.estado.EstadoBorrador;


public class PaqueteCurso {
	
	private final String id;
	
	private final Contenido contenido;

	private final Evaluacion evaluacion;

	private final Certificado certificado;
	
	private final Usuario instructor;
	
	private volatile EstadoCurso estado = new EstadoBorrador();

	public String getNombreEstado() { return estado.getNombre(); }

	public boolean admiteInscripciones() { return estado.admiteInscripciones(); }

	public boolean permiteEdicion() { return estado.permiteEdicion(); }

	public PaqueteCurso(String id, Contenido contenido, Evaluacion evaluacion, Certificado certificado, Usuario instructor) {
		this.id = id;
		this.contenido = contenido;
		this.evaluacion = evaluacion;
		this.certificado = certificado;
		this.instructor = instructor;
	}
	
	public void publicar() {
		EstadoCurso anterior = estado;
		estado = estado.publicar();
		System.out.println("[State] " + contenido.getTitulo() + ": " + anterior.getNombre() + " -> " + estado.getNombre());
	}

	public void archivar() {
		EstadoCurso anterior = estado;
		estado = estado.archivar();
		System.out.println("[State] " + contenido.getTitulo() + ": " + anterior.getNombre() + " -> " + estado.getNombre());
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
