package com.java.lms.curso.model;

public class Inscripcion {
	
	private final String id;
	private final String estudianteId;
	private final String cursoId;
	private final String fechaInscripcion;

	public Inscripcion(String id, String estudianteId, String cursoId, String fechaInscripcion) {
		this.id = id;
		this.estudianteId = estudianteId;
		this.cursoId = cursoId;
		this.fechaInscripcion = fechaInscripcion;
	}

	public String getId() { return id; }
	public String getEstudianteId() { return estudianteId; }
	public String getCursoId() { return cursoId; }
	public String getFechaInscripcion() { return fechaInscripcion; }
}
