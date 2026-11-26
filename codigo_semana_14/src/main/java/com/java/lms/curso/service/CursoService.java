package com.java.lms.curso.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.java.lms.curso.factory.CursoFactory;
import com.java.lms.curso.factory.CursoFactoryProvider;
import com.java.lms.curso.model.Certificado;
import com.java.lms.curso.model.Contenido;
import com.java.lms.curso.model.Evaluacion;
import com.java.lms.curso.model.PaqueteCurso;
import com.java.lms.curso.model.TipoCurso;
import com.java.lms.curso.proxy.AccesoDenegadoException;
import com.java.lms.curso.visitor.ValidadorEstructura;
import com.java.lms.model.Usuario;
import com.java.lms.repository.CursoRepository;

@Service
public class CursoService {
	
	@Autowired
	private CursoFactoryProvider cursoFactoryProvider;
	
	@Autowired
    private CursoRepository cursoRepository;


	public PaqueteCurso crearPaqueteCurso(TipoCurso tipo,
			String tituloContenido, String descripcionContenido,
			String nombreEvaluacion, int notaMinima,
			String nombreCurso, String codigoValidacion, Usuario instructor) {
		
		CursoFactory factory = cursoFactoryProvider.getFactory(tipo); 
		Contenido contenido = factory.crearContenido(tituloContenido, descripcionContenido);
		Evaluacion evaluacion = factory.crearEvaluacion(nombreEvaluacion, notaMinima);
		Certificado certificado = factory.crearCertificado(nombreCurso, codigoValidacion);
		
		String id = java.util.UUID.randomUUID().toString();

		PaqueteCurso paquete = new PaqueteCurso(id, contenido, evaluacion, certificado, instructor);
		
		cursoRepository.save(paquete);
		
		return paquete;
	}
	
	public void publicar(Usuario usuario, String cursoId) {
		PaqueteCurso curso = obtenerPropio(usuario, cursoId);

		List<String> problemas = ValidadorEstructura.validar(curso.getContenido().getEstructura());
		if (!problemas.isEmpty()) {
			throw new IllegalStateException("No se puede publicar: " + String.join("; ", problemas));
		}

		curso.publicar();
	}

	public void archivar(Usuario usuario, String cursoId) {
		obtenerPropio(usuario, cursoId).archivar();
	}

	private PaqueteCurso obtenerPropio(Usuario usuario, String cursoId) {
		PaqueteCurso curso = cursoRepository.findById(cursoId);
		if (curso == null) {
			throw new IllegalArgumentException("El curso no existe");
		}
		if (!curso.getInstructor().getId().equals(usuario.getId())) {
			throw new AccesoDenegadoException("Solo el instructor del curso puede cambiar su estado");
		}
		return curso;
	}
}
