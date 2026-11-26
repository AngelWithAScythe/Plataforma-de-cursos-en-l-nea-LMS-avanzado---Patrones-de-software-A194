package com.java.lms.curso.model;

import com.java.lms.curso.visitor.VisitanteContenido;

public interface ElementoContenido {
	
	String getTitulo();
	int getDuracionMinutos();
	int contarLecciones();
	ElementoContenido copiar();
	void aceptar(VisitanteContenido visitante);
}
