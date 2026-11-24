package com.java.lms.curso.memento;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.java.lms.curso.model.Contenido;

@Component
public class PuntosRestauracion {
	
	private final Map<String, List<Contenido.Memento>> puntos = new HashMap<>();

	public synchronized void guardar(String cursoId, Contenido.Memento memento) {
		puntos.computeIfAbsent(cursoId, k -> new ArrayList<>()).add(memento);
		System.out.println("[Memento] Punto guardado: " + memento.getEtiqueta());
	}

	public synchronized List<Contenido.Memento> listar(String cursoId) {
		return new ArrayList<>(puntos.getOrDefault(cursoId, List.of()));
	}

	public synchronized Contenido.Memento obtener(String cursoId, String puntoId) {
		return puntos.getOrDefault(cursoId, List.of()).stream()
				.filter(m -> m.getId().equals(puntoId))
				.findFirst()
				.orElse(null);
	}
}
