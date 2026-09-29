package com.java.lms.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.java.lms.curso.model.CertificadoImpreso;

@Repository
public class CertificadoRepository {
	
	private final List<CertificadoImpreso> certificadosEmitidos = new ArrayList<>();

	public void save(CertificadoImpreso certificado) {
		certificadosEmitidos.add(certificado);
	}

	public List<CertificadoImpreso> findAll() {
		return certificadosEmitidos;
	}

	public List<CertificadoImpreso> findByEstudiante(String nombreEstudiante) {
		return certificadosEmitidos.stream()
				.filter(c -> c.getNombreEstudiante().equalsIgnoreCase(nombreEstudiante))
				.collect(Collectors.toList());
	}
	
	public List<CertificadoImpreso> findByCursoId(String cursoId) {
	    return certificadosEmitidos.stream()
	            .filter(c -> c.getCursoId().equals(cursoId))
	            .collect(Collectors.toList());
	}
}
