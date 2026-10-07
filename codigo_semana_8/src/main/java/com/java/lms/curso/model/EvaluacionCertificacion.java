package com.java.lms.curso.model;

public class EvaluacionCertificacion extends Evaluacion{
	
	public EvaluacionCertificacion(String nombre, int notaMinimaAprobacion) {
		super(nombre, notaMinimaAprobacion);
	}

	@Override
	public TipoCurso getTipo() {
		return TipoCurso.CERTIFICACION;
	}
}
