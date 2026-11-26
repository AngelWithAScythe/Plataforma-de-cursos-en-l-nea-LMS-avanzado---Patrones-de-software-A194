package com.java.lms.curso.model;

public class Notificacion {
	
	private final String id;
	private final String cursoId;
	private final String mensaje;
	private final String fecha;
	private boolean leida = false;

	public Notificacion(String id, String cursoId, String mensaje, String fecha) {
		this.id = id;
		this.cursoId = cursoId;
		this.mensaje = mensaje;
		this.fecha = fecha;
	}

	public void marcarLeida() { this.leida = true; }

	public String getId() { return id; }
	public String getCursoId() { return cursoId; }
	public String getMensaje() { return mensaje; }
	public String getFecha() { return fecha; }
	public boolean isLeida() { return leida; }
}
