package com.java.lms.curso.visitor;

import com.java.lms.curso.model.Leccion;
import com.java.lms.curso.model.Modulo;

public interface VisitanteContenido {
	
	void visitarLeccion(Leccion leccion);

	void visitarModulo(Modulo modulo);
}
