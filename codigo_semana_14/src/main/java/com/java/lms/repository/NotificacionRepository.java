package com.java.lms.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.java.lms.curso.model.Notificacion;

@Repository
public class NotificacionRepository {
	
	private final Map<String, List<Notificacion>> porEstudiante = new HashMap<>();

	public synchronized void agregar(String estudianteId, Notificacion notificacion) {
		porEstudiante.computeIfAbsent(estudianteId, k -> new ArrayList<>()).add(0, notificacion); 
	}

	public synchronized List<Notificacion> de(String estudianteId) {
		return new ArrayList<>(porEstudiante.getOrDefault(estudianteId, List.of()));
	}

	public synchronized long contarNoLeidas(String estudianteId) {
		return porEstudiante.getOrDefault(estudianteId, List.of()).stream().filter(n -> !n.isLeida()).count();
	}

	public synchronized Set<String> idsNoLeidas(String estudianteId) {
		return porEstudiante.getOrDefault(estudianteId, List.of()).stream()
				.filter(n -> !n.isLeida())
				.map(Notificacion::getId)
				.collect(Collectors.toSet());
	}

	public synchronized void marcarTodasLeidas(String estudianteId) {
		porEstudiante.getOrDefault(estudianteId, List.of()).forEach(Notificacion::marcarLeida);
	}
}
