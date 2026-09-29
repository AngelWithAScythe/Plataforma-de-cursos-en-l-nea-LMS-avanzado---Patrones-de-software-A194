package com.java.lms.curso.model;

public class EvaluacionTecnica extends Evaluacion{

	public EvaluacionTecnica(String nombre, int notaMinimaAprobacion) {
		super(nombre, notaMinimaAprobacion);
	}

	@Override
	public TipoCurso getTipo() {
		return TipoCurso.TECNICO;
	}
}
