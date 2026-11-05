package com.java.lms.curso.model;

public class CertificadoImpreso {
	
	private final Certificado certificado;
	private final String cursoId;
	private final String nombreEstudiante;
	private final Integer notaObtenida;
	private final String firmaInstructor;
	private final String logoUrl;
	private final String textoLegal;
	private final String fechaEmision;
	
	public CertificadoImpreso(Certificado certificado,String cursoId, String nombreEstudiante, Integer notaObtenida,
			String firmaInstructor, String logoUrl, String textoLegal, String fechaEmision) {
		this.certificado = certificado;
		this.cursoId = cursoId;
		this.nombreEstudiante = nombreEstudiante;
		this.notaObtenida = notaObtenida;
		this.firmaInstructor = firmaInstructor;
		this.logoUrl = logoUrl;
		this.textoLegal = textoLegal;
		this.fechaEmision = fechaEmision;
	}
	
	
	public String getCursoId() {return cursoId;}
	public Certificado getCertificado() { return certificado; }
	public String getNombreEstudiante() { return nombreEstudiante; }
	public Integer getNotaObtenida() { return notaObtenida; }
	public String getFirmaInstructor() { return firmaInstructor; }
	public String getLogoUrl() { return logoUrl; }
	public String getTextoLegal() { return textoLegal; }
	public String getFechaEmision() { return fechaEmision; }
}
