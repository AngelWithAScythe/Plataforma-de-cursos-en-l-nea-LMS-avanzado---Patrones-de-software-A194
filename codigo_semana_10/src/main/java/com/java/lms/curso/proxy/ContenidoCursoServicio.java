package com.java.lms.curso.proxy;

import com.java.lms.curso.model.PaqueteCurso;
import com.java.lms.model.Usuario;

public interface ContenidoCursoServicio {
	
	PaqueteCurso verContenido(Usuario usuario, String cursoId);

	void agregarSubmodulo(Usuario usuario, String cursoId, String moduloId, String titulo);

	void agregarLeccion(Usuario usuario, String cursoId, String moduloId, String titulo, int duracionMinutos);
}
