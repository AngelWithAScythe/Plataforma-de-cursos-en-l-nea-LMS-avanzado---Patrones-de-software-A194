package com.java.lms.curso.command;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component
public class HistorialContenido {
	
	private final Map<String, Deque<ComandoContenido>> historiales = new HashMap<>();

	public synchronized void ejecutar(String cursoId, ComandoContenido comando) {
		comando.ejecutar();
		historiales.computeIfAbsent(cursoId, k -> new ArrayDeque<>()).push(comando);
		System.out.println("[Command] Ejecutado: " + comando.getDescripcion());
	}

	public synchronized String deshacer(String cursoId) {
		Deque<ComandoContenido> pila = historiales.get(cursoId);
		if (pila == null || pila.isEmpty()) {
			return null;
		}
		ComandoContenido comando = pila.pop();
		comando.deshacer();
		System.out.println("[Command] Deshecho: " + comando.getDescripcion());
		return comando.getDescripcion();
	}
}
