package com.java.lms.curso.builder;

import com.java.lms.curso.model.Certificado;
import com.java.lms.curso.model.CertificadoImpreso;

public class CertificadoBuilder {
	
	private final Certificado certificado;
	private final String cursoId;
	private String nombreEstudiante;

	private Integer notaObtenida;
	private String firmaInstructor;
	private String logoUrl;
	private String textoLegal = "Este certificado no tiene validez como título profesional.";
	private String fechaEmision;
	
	public CertificadoBuilder(Certificado certificado,String cursoId, String nombreEstudiante) {
		this.cursoId = cursoId;
		this.certificado = certificado;
		this.nombreEstudiante = nombreEstudiante;
	}
	
	public CertificadoBuilder(Certificado certificado, String cursoId) {
		this(certificado, cursoId, null);
	}

	public CertificadoBuilder conNota(int nota) {
		this.notaObtenida = nota;
		return this;
	}

	public CertificadoBuilder conFirma(String firmaInstructor) {
		this.firmaInstructor = firmaInstructor;
		return this;
	}

	public CertificadoBuilder conLogo(String logoUrl) {
		this.logoUrl = logoUrl;
		return this;
	}

	public CertificadoBuilder conTextoLegal(String textoLegal) {
		this.textoLegal = textoLegal;
		return this;
	}

	public CertificadoBuilder conFechaEmision(String fechaEmision) {
		this.fechaEmision = fechaEmision;
		return this;
	}
	
	public CertificadoBuilder paraEstudiante(String nombreEstudiante) {
		this.nombreEstudiante = nombreEstudiante;
		return this;
	}

	public CertificadoImpreso build() {
		return new CertificadoImpreso(certificado, cursoId, nombreEstudiante, notaObtenida,
				firmaInstructor, logoUrl, textoLegal, fechaEmision);
	}
	
	public CertificadoBuilder clonar() {
	    CertificadoBuilder copia = new CertificadoBuilder(certificado, cursoId, nombreEstudiante);
	    copia.notaObtenida = notaObtenida;
	    copia.firmaInstructor = firmaInstructor;
	    copia.logoUrl = logoUrl;
	    copia.textoLegal = textoLegal;
	    copia.fechaEmision = fechaEmision;
	    return copia;
	}
}
