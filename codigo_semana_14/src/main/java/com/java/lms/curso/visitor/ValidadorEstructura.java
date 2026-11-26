package com.java.lms.curso.visitor;

import java.util.ArrayList;
import java.util.List;

import com.java.lms.curso.model.ElementoContenido;
import com.java.lms.curso.model.Leccion;
import com.java.lms.curso.model.Modulo;

public class ValidadorEstructura implements VisitanteContenido{
	
	private final List<String> problemas = new ArrayList<>();
	private int nivel = 0; 
	private int lecciones = 0;

	public static List<String> validar(Modulo raiz) {
		ValidadorEstructura validador = new ValidadorEstructura();
		raiz.aceptar(validador);
		if (validador.lecciones == 0) {
			validador.problemas.add("el curso todavía no tiene ninguna lección");
		}
		System.out.println("[Visitor] Validación de estructura: " + validador.problemas.size() + " problema(s)");
		return validador.problemas;
	}

	@Override
	public void visitarModulo(Modulo modulo) {
		if (nivel > 0 && modulo.getElementos().isEmpty()) {
			problemas.add("el módulo \"" + modulo.getTitulo() + "\" está vacío");
		}
		nivel++;
		for (ElementoContenido elemento : modulo.getElementos()) {
			elemento.aceptar(this);
		}
		nivel--;
	}

	@Override
	public void visitarLeccion(Leccion leccion) {
		lecciones++;
		if (leccion.getDuracionMinutos() <= 0) {
			problemas.add("la lección \"" + leccion.getTitulo() + "\" no tiene una duración válida");
		}
	}
}
