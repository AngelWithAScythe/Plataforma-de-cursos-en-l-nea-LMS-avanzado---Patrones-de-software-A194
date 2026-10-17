package com.java.lms.curso.factory;

import org.springframework.stereotype.Component;

import com.java.lms.curso.model.Certificado;
import com.java.lms.curso.model.CertificadoCertificacion;
import com.java.lms.curso.model.Contenido;
import com.java.lms.curso.model.ContenidoCertificacion;
import com.java.lms.curso.model.Evaluacion;
import com.java.lms.curso.model.EvaluacionCertificacion;
import com.java.lms.curso.model.TipoCurso;

@Component
public class CursoCertificacionFactory extends CursoFactory{
	
	@Override
	public TipoCurso getTipo() {
		return TipoCurso.CERTIFICACION;
	}

	@Override
	public Contenido crearContenido(String titulo, String descripcion) {
		return new ContenidoCertificacion(titulo, descripcion);
	}

	@Override
	public Evaluacion crearEvaluacion(String nombre, int notaMinimaAprobacion) {
		return new EvaluacionCertificacion(nombre, notaMinimaAprobacion);
	}

	@Override
	public Certificado crearCertificado(String nombreCurso, String codigoValidacion) {
		return new CertificadoCertificacion(nombreCurso, codigoValidacion);
	}
}
