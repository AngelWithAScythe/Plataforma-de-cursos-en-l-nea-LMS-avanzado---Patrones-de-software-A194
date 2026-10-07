package com.java.lms.curso.factory;

import org.springframework.stereotype.Component;

import com.java.lms.curso.model.Certificado;
import com.java.lms.curso.model.CertificadoTecnico;
import com.java.lms.curso.model.Contenido;
import com.java.lms.curso.model.ContenidoTecnico;
import com.java.lms.curso.model.Evaluacion;
import com.java.lms.curso.model.EvaluacionTecnica;
import com.java.lms.curso.model.TipoCurso;

@Component
public class CursoTecnicoFactory extends CursoFactory{
	
	@Override
	public TipoCurso getTipo() {
		return TipoCurso.TECNICO;
	}

	@Override
	public Contenido crearContenido(String titulo, String descripcion) {
		return new ContenidoTecnico(titulo, descripcion);
	}

	@Override
	public Evaluacion crearEvaluacion(String nombre, int notaMinimaAprobacion) {
		return new EvaluacionTecnica(nombre, notaMinimaAprobacion);
	}

	@Override
	public Certificado crearCertificado(String nombreCurso, String codigoValidacion) {
		return new CertificadoTecnico(nombreCurso, codigoValidacion);
	}
}
