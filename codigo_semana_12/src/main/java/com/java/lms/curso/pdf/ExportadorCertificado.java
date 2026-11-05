package com.java.lms.curso.pdf;

import com.java.lms.curso.model.CertificadoImpreso;

public interface ExportadorCertificado {
	
	byte[] exportarAPdf(CertificadoImpreso certificado);
}
