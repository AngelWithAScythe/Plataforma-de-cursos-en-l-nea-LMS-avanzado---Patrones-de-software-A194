package com.java.lms.curso.model;

public class CertificadoCertificacion extends Certificado{
	
	public CertificadoCertificacion(String nombreCurso, String codigoValidacion) {
		super(nombreCurso, codigoValidacion);
	}

	@Override
	public TipoCurso getTipo() {
		return TipoCurso.CERTIFICACION;
	}
}
