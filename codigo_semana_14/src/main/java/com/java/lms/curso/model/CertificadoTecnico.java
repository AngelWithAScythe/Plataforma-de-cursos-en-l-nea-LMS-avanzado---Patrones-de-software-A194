package com.java.lms.curso.model;

public class CertificadoTecnico extends Certificado{
	
	public CertificadoTecnico(String nombreCurso, String codigoValidacion) {
		super(nombreCurso, codigoValidacion);
	}

	@Override
	public TipoCurso getTipo() {
		return TipoCurso.TECNICO;
	}
}
