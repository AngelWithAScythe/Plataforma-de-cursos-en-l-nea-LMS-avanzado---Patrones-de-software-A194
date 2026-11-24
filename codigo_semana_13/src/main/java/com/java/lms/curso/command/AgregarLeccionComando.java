package com.java.lms.curso.command;

import com.java.lms.curso.model.Leccion;
import com.java.lms.curso.model.Modulo;

public class AgregarLeccionComando implements ComandoContenido{
	
	private final Modulo destino;
	private final Leccion nueva;

	public AgregarLeccionComando(Modulo destino, String titulo, int duracionMinutos) {
		this.destino = destino;
		this.nueva = new Leccion(titulo, duracionMinutos);
	}

	@Override
	public void ejecutar() {
		destino.agregar(nueva);
	}

	@Override
	public void deshacer() {
		destino.eliminar(nueva);
	}

	@Override
	public String getDescripcion() {
		return "Agregar lección \"" + nueva.getTitulo() + "\"";
	}
}
