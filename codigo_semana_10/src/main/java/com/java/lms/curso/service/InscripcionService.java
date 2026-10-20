package com.java.lms.curso.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.java.lms.curso.model.Inscripcion;
import com.java.lms.curso.model.PaqueteCurso;
import com.java.lms.model.Usuario;
import com.java.lms.repository.CursoRepository;
import com.java.lms.repository.InscripcionRepository;

@Service
public class InscripcionService {
	
	@Autowired
	private InscripcionRepository inscripcionRepository;

	@Autowired
	private CursoRepository cursoRepository;

	public Inscripcion inscribir(Usuario estudiante, String cursoId) {
		if (cursoRepository.findById(cursoId) == null) {
			throw new IllegalArgumentException("El curso no existe");
		}
		if (inscripcionRepository.existe(estudiante.getId(), cursoId)) {
			throw new IllegalStateException("Ya estás inscrito en este curso");
		}

		Inscripcion inscripcion = new Inscripcion(UUID.randomUUID().toString(),
				estudiante.getId(), cursoId, LocalDate.now().toString());
		inscripcionRepository.save(inscripcion);
		return inscripcion;
	}

	public boolean estaInscrito(String estudianteId, String cursoId) {
		return inscripcionRepository.existe(estudianteId, cursoId);
	}

	public List<PaqueteCurso> cursosDisponibles() {
		return cursoRepository.findAll();
	}

	public List<PaqueteCurso> cursosDe(String estudianteId) {
		return inscripcionRepository.findByEstudiante(estudianteId).stream()
				.map(i -> cursoRepository.findById(i.getCursoId()))
				.filter(Objects::nonNull)
				.collect(Collectors.toList());
	}

	public Set<String> idsDeCursosInscritos(String estudianteId) {
		return inscripcionRepository.findByEstudiante(estudianteId).stream()
				.map(Inscripcion::getCursoId)
				.collect(Collectors.toSet());
	}
}
