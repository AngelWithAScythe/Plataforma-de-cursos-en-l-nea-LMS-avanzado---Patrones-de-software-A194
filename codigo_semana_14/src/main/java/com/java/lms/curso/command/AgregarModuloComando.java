package com.java.lms.curso.command;

import com.java.lms.curso.model.Modulo;

public class AgregarModuloComando implements ComandoContenido{
	
	private final Modulo destino;
	private final Modulo nuevo;

	public AgregarModuloComando(Modulo destino, String titulo) {
		this.destino = destino;
		this.nuevo = new Modulo(titulo); 
	}

	@Override
	public void ejecutar() {
		destino.agregar(nuevo);
	}

	@Override
	public void deshacer() {
		destino.eliminar(nuevo);
	}

	@Override
	public String getDescripcion() {
		return "Agregar módulo \"" + nuevo.getTitulo() + "\"";
	}
}
