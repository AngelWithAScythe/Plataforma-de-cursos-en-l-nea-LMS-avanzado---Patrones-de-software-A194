package com.java.lms.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.java.lms.curso.model.Inscripcion;

@Repository
public class InscripcionRepository {
	
	private final List<Inscripcion> inscripciones = new ArrayList<>();

	public void save(Inscripcion inscripcion) {
		inscripciones.add(inscripcion);
	}

	public boolean existe(String estudianteId, String cursoId) {
		return inscripciones.stream()
				.anyMatch(i -> i.getEstudianteId().equals(estudianteId) && i.getCursoId().equals(cursoId));
	}

	public List<Inscripcion> findByEstudiante(String estudianteId) {
		return inscripciones.stream()
				.filter(i -> i.getEstudianteId().equals(estudianteId))
				.collect(Collectors.toList());
	}
}
