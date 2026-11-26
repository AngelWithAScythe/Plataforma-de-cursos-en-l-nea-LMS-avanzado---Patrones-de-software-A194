package com.java.lms.curso.proxy;

import java.util.List;

import com.java.lms.curso.model.Contenido;
import com.java.lms.curso.model.PaqueteCurso;
import com.java.lms.model.Usuario;

public interface ContenidoCursoServicio {
	
	PaqueteCurso verContenido(Usuario usuario, String cursoId);

	void agregarSubmodulo(Usuario usuario, String cursoId, String moduloId, String titulo);

	void agregarLeccion(Usuario usuario, String cursoId, String moduloId, String titulo, int duracionMinutos);
	
	String deshacer(Usuario usuario, String cursoId);
	
	void guardarPunto(Usuario usuario, String cursoId, String etiqueta);

	List<Contenido.Memento> verPuntos(Usuario usuario, String cursoId);

	String restaurarPunto(Usuario usuario, String cursoId, String puntoId);
}
