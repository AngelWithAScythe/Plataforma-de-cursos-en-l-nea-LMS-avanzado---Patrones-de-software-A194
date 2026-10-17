package com.java.lms.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.java.lms.curso.model.PaqueteCurso;

@Repository
public class CursoRepository {
	
	private final List<PaqueteCurso> cursosSimulados = new ArrayList<>();
	
	public void save(PaqueteCurso curso) {
		cursosSimulados.add(curso);
	}

	public List<PaqueteCurso> findAll() {
		return cursosSimulados;
	}
	
	public List<PaqueteCurso> findByInstructor(String nicknameInstructor) {
	    return cursosSimulados.stream()
	            .filter(c -> c.getInstructor().getNickname().equalsIgnoreCase(nicknameInstructor))
	            .collect(Collectors.toList());
	}
	
	public PaqueteCurso findById(String id) {
	    return cursosSimulados.stream()
	            .filter(c -> c.getId().equals(id))
	            .findFirst()
	            .orElse(null);
	}
}
