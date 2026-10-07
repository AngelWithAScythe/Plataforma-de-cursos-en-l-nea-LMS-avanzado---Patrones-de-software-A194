package com.java.lms.curso.formato;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.java.lms.curso.model.CertificadoImpreso;
import com.java.lms.curso.pdf.ExportadorCertificado;

@Component
public class PdfFormateador implements FormateadorCertificado{
	
	@Autowired
	private ExportadorCertificado exportadorCertificado;

	@Override
	public byte[] generar(CertificadoImpreso certificado) {
		return exportadorCertificado.exportarAPdf(certificado);
	}
}
