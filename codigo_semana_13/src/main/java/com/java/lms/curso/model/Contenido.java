package com.java.lms.curso.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

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
	
	public Memento guardarPunto(String etiqueta) {
		String fecha = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
		return new Memento(etiqueta, fecha, estructura.copiar());
	}

	public void restaurar(Memento memento) {
		this.estructura = memento.estructura.copiar();
	}

	public static final class Memento {

		private final String id = UUID.randomUUID().toString();
		private final String etiqueta;
		private final String fecha;
		private final Modulo estructura;

		private Memento(String etiqueta, String fecha, Modulo estructura) {
			this.etiqueta = etiqueta;
			this.fecha = fecha;
			this.estructura = estructura;
		}

		public String getId() { return id; }
		public String getEtiqueta() { return etiqueta; }
		public String getFecha() { return fecha; }
	}
}
