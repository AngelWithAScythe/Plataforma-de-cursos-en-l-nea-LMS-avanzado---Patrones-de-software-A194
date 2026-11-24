package com.java.lms.curso.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import com.java.lms.curso.iterator.RecorridoLecciones;

public class Modulo implements ElementoContenido, Iterable<Leccion>{
	
	private final String id;
	private final String titulo;
	private final List<ElementoContenido> elementos = new ArrayList<>();

	private Modulo(String id, String titulo) {
		this.id = id;
		this.titulo = titulo;
	}

	public Modulo(String titulo) {
		this(UUID.randomUUID().toString(), titulo);
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
	
	@Override
	public Iterator<Leccion> iterator() {
		return new RecorridoLecciones(this);
	}
	
	@Override
	public Modulo copiar() {
		Modulo copia = new Modulo(this.id, this.titulo);
		for (ElementoContenido elemento : elementos) {
			copia.agregar(elemento.copiar());
		}
		return copia;
	}
}
