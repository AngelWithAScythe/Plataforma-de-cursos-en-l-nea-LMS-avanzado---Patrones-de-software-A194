package com.java.lms.curso.proxy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.java.lms.curso.model.PaqueteCurso;
import com.java.lms.curso.service.InscripcionService;
import com.java.lms.model.Rol;
import com.java.lms.model.Usuario;
import com.java.lms.repository.CursoRepository;

@Primary
@Service
public class ContenidoCursoProxy implements ContenidoCursoServicio{
	
	@Autowired
	@Qualifier("contenidoCursoReal")
	private ContenidoCursoServicio real;

	@Autowired
	private CursoRepository cursoRepository;

	@Autowired
	private InscripcionService inscripcionService;

	@Override
	public PaqueteCurso verContenido(Usuario usuario, String cursoId) {
		verificarAcceso(usuario, cursoId, false);
		return real.verContenido(usuario, cursoId);
	}

	@Override
	public void agregarSubmodulo(Usuario usuario, String cursoId, String moduloId, String titulo) {
		verificarAcceso(usuario, cursoId, true);
		real.agregarSubmodulo(usuario, cursoId, moduloId, titulo);
	}

	@Override
	public void agregarLeccion(Usuario usuario, String cursoId, String moduloId, String titulo, int duracionMinutos) {
		verificarAcceso(usuario, cursoId, true);
		real.agregarLeccion(usuario, cursoId, moduloId, titulo, duracionMinutos);
	}

	private void verificarAcceso(Usuario usuario, String cursoId, boolean modificacion) {
		PaqueteCurso curso = cursoRepository.findById(cursoId);
		if (curso == null) {
			return; // no hay nada que proteger; el servicio real resuelve el caso
		}

		if (curso.getInstructor().getId().equals(usuario.getId())) {
			return; // el instructor dueño puede ver y modificar
		}

		if (modificacion) {
			throw new AccesoDenegadoException("Solo el instructor del curso puede modificar su contenido");
		}

		boolean estudianteInscrito = usuario.getRol() == Rol.ESTUDIANTE
				&& inscripcionService.estaInscrito(usuario.getId(), cursoId);
		if (!estudianteInscrito) {
			throw new AccesoDenegadoException("No tienes acceso al contenido de este curso");
		}
	}
}
