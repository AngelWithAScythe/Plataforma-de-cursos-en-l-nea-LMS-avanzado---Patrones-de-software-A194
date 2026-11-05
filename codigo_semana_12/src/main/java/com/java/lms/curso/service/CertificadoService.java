package com.java.lms.curso.service;

import java.time.LocalDate;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.java.lms.curso.builder.CertificadoBuilder;
import com.java.lms.curso.model.CertificadoImpreso;
import com.java.lms.curso.model.PaqueteCurso;
import com.java.lms.repository.CertificadoRepository;

@Service
public class CertificadoService {
	
	@Autowired
    private CertificadoRepository certificadoRepository;
	
	private final Map<String, CertificadoBuilder> plantillas = new ConcurrentHashMap<>();
	
	public void configurarPlantilla(PaqueteCurso curso, String firmaInstructor, String logoUrl, String textoLegal) {
		CertificadoBuilder plantilla = new CertificadoBuilder(curso.getCertificado(), curso.getId())
				.conFirma(firmaInstructor)
				.conLogo(logoUrl);

		if (textoLegal != null && !textoLegal.isBlank()) {
			plantilla.conTextoLegal(textoLegal);
		}

		plantillas.put(curso.getId(), plantilla);
	}

	public boolean tienePlantilla(String cursoId) {
		return plantillas.containsKey(cursoId);
	}
	
	public CertificadoImpreso emitirCertificado(String cursoId, String nombreEstudiante, int nota) {
		CertificadoBuilder plantilla = plantillas.get(cursoId);
		if (plantilla == null) {
			throw new IllegalStateException("Este curso todavía no tiene una plantilla de certificado configurada");
		}

		CertificadoImpreso impreso = plantilla.clonar()
				.paraEstudiante(nombreEstudiante)
				.conNota(nota)
				.conFechaEmision(LocalDate.now().toString())
				.build();

		certificadoRepository.save(impreso);
		return impreso;
	}
}
