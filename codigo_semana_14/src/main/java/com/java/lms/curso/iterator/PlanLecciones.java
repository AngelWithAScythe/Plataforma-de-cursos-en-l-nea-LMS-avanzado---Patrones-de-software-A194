package com.java.lms.curso.iterator;

import java.util.ArrayList;
import java.util.List;

import com.java.lms.curso.model.Leccion;
import com.java.lms.curso.model.Modulo;

public class PlanLecciones {
	
	public record Fila(int numero, String titulo, int duracionMinutos, int inicioMinuto) {}

	private PlanLecciones() {}

	public static List<Fila> de(Modulo raiz) {
		List<Fila> filas = new ArrayList<>();
		int numero = 1;
		int acumulado = 0;

		for (Leccion leccion : raiz) { 
			filas.add(new Fila(numero++, leccion.getTitulo(), leccion.getDuracionMinutos(), acumulado));
			acumulado += leccion.getDuracionMinutos();
		}
		return filas;
	}
}
