package com.java.lms.curso.proxy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.java.lms.curso.command.AgregarLeccionComando;
import com.java.lms.curso.command.AgregarModuloComando;
import com.java.lms.curso.command.HistorialContenido;
import com.java.lms.curso.model.Modulo;
import com.java.lms.curso.model.PaqueteCurso;
import com.java.lms.model.Usuario;
import com.java.lms.repository.CursoRepository;

@Service("contenidoCursoReal")
public class ContenidoCursoServicioImpl implements ContenidoCursoServicio{
	
	@Autowired
	private CursoRepository cursoRepository;
	
	@Autowired
	private HistorialContenido historial;

	@Override
	public PaqueteCurso verContenido(Usuario usuario, String cursoId) {
		return cursoRepository.findById(cursoId);
	}

	@Override
	public void agregarSubmodulo(Usuario usuario, String cursoId, String moduloId, String titulo) {
		Modulo destino = buscarModulo(cursoId, moduloId);
		if (destino != null) {
			historial.ejecutar(cursoId, new AgregarModuloComando(destino, titulo));
		}
	}

	@Override
	public void agregarLeccion(Usuario usuario, String cursoId, String moduloId, String titulo, int duracionMinutos) {
		Modulo destino = buscarModulo(cursoId, moduloId);
		if (destino != null) {
			historial.ejecutar(cursoId, new AgregarLeccionComando(destino, titulo, duracionMinutos));
		}
	}
	
	@Override
	public String deshacer(Usuario usuario, String cursoId) {
		return historial.deshacer(cursoId);
	}

	private Modulo buscarModulo(String cursoId, String moduloId) {
		PaqueteCurso curso = cursoRepository.findById(cursoId);
		return curso == null ? null : curso.getContenido().getEstructura().buscarModulo(moduloId);
	}
}
