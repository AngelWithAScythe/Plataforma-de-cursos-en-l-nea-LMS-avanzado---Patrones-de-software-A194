package com.java.lms.curso.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Modulo implements ElementoContenido{
	
	private final String id = UUID.randomUUID().toString();
	private final String titulo;
	private final List<ElementoContenido> elementos = new ArrayList<>();

	public Modulo(String titulo) {
		this.titulo = titulo;
	}

	public void agregar(ElementoContenido elemento) {
		elementos.add(elemento);
	}

	public void eliminar(ElementoContenido elemento) {
		elementos.remove(elemento);
	}
	
	public String getId() {
		return id;
	}

	public List<ElementoContenido> getElementos() {
		return elementos;
	}
	
	public Modulo buscarModulo(String idBuscado) {
		if (this.id.equals(idBuscado)) {
			return this;
		}
		for (ElementoContenido e : elementos) {
			if (e instanceof Modulo m) {
				Modulo encontrado = m.buscarModulo(idBuscado);
				if (encontrado != null) {
					return encontrado;
				}
			}
		}
		return null;
	}

	@Override
	public String getTitulo() {
		return titulo;
	}

	@Override
	public int getDuracionMinutos() {
		return elementos.stream().mapToInt(ElementoContenido::getDuracionMinutos).sum();
	}

	@Override
	public int contarLecciones() {
		return elementos.stream().mapToInt(ElementoContenido::contarLecciones).sum();
	}
}
