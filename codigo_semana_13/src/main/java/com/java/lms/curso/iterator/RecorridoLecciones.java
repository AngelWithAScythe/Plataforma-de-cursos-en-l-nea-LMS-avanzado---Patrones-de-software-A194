package com.java.lms.curso.iterator;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;
import java.util.NoSuchElementException;

import com.java.lms.curso.model.ElementoContenido;
import com.java.lms.curso.model.Leccion;
import com.java.lms.curso.model.Modulo;

public class RecorridoLecciones implements Iterator<Leccion>{
	
	private final Deque<Iterator<ElementoContenido>> pila = new ArrayDeque<>();
	private Leccion siguiente;

	public RecorridoLecciones(Modulo raiz) {
		pila.push(raiz.getElementos().iterator());
		avanzar();
	}

	@Override
	public boolean hasNext() {
		return siguiente != null;
	}

	@Override
	public Leccion next() {
		if (siguiente == null) {
			throw new NoSuchElementException();
		}
		Leccion actual = siguiente;
		avanzar();
		return actual;
	}

	private void avanzar() {
		siguiente = null;
		while (!pila.isEmpty()) {
			Iterator<ElementoContenido> actual = pila.peek();
			if (!actual.hasNext()) {
				pila.pop();
				continue;
			}
			ElementoContenido elemento = actual.next();
			if (elemento instanceof Modulo modulo) {
				pila.push(modulo.getElementos().iterator());
			} else if (elemento instanceof Leccion leccion) {
				siguiente = leccion;
				return;
			}
		}
	}
}
