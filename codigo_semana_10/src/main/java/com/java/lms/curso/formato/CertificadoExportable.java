package com.java.lms.curso.formato;

import com.java.lms.curso.model.CertificadoImpreso;

public class CertificadoExportable {
	
	private final FormateadorCertificado formateador;

	public CertificadoExportable(FormateadorCertificado formateador) {
		this.formateador = formateador;
	}

	public byte[] exportar(CertificadoImpreso certificado) {
		return formateador.generar(certificado);
	}
}
