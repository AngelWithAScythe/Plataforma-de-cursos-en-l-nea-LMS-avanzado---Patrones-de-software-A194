package com.java.lms.curso.observer;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.stereotype.Component;

@Component
public class RegistroActividadCurso implements ObservadorCurso{
	
	private final List<String> registro = new CopyOnWriteArrayList<>();

	@Override
	public void actualizar(EventoCurso evento) {
		String linea = evento.tituloCurso() + ": " + evento.detalle();
		registro.add(linea);
		System.out.println("[Actividad] " + linea);
	}

	public List<String> getRegistro() {
		return new ArrayList<>(registro);
	}
}
