package com.java.lms.curso.formato;

import com.java.lms.curso.model.CertificadoImpreso;

public interface FormateadorCertificado {
	
	byte[] generar(CertificadoImpreso certificado);
}
