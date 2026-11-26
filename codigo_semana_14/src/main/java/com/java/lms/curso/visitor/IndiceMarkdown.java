package com.java.lms.curso.visitor;

import com.java.lms.curso.model.ElementoContenido;
import com.java.lms.curso.model.Leccion;
import com.java.lms.curso.model.Modulo;

public class IndiceMarkdown implements VisitanteContenido{
	
	private final StringBuilder texto = new StringBuilder();
	private int nivel = 0; 

	public IndiceMarkdown(String tituloCurso) {
		texto.append("# ").append(tituloCurso).append("\n\n");
	}

	public String getResultado() {
		return texto.toString();
	}

	@Override
	public void visitarModulo(Modulo modulo) {
		if (nivel > 0) {
			int lecciones = modulo.contarLecciones();
			texto.append("  ".repeat(nivel - 1))
					.append("- **").append(modulo.getTitulo()).append("** (")
					.append(lecciones).append(lecciones == 1 ? " lección, " : " lecciones, ")
					.append(modulo.getDuracionMinutos()).append(" min)\n");
		}
		nivel++;
		for (ElementoContenido elemento : modulo.getElementos()) {
			elemento.aceptar(this);
		}
		nivel--;
	}

	@Override
	public void visitarLeccion(Leccion leccion) {
		texto.append("  ".repeat(Math.max(nivel - 1, 0)))
				.append("- ").append(leccion.getTitulo())
				.append(" (").append(leccion.getDuracionMinutos()).append(" min)\n");
	}
}
