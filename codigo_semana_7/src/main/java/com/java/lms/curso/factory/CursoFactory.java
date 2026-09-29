package com.java.lms.curso.factory;

import com.java.lms.curso.model.Certificado;
import com.java.lms.curso.model.Contenido;
import com.java.lms.curso.model.Evaluacion;
import com.java.lms.curso.model.TipoCurso;

public abstract class CursoFactory {
	
	public abstract TipoCurso getTipo();
	
	public abstract Contenido crearContenido(String titulo, String descripcion);
	
	public abstract Evaluacion crearEvaluacion(String nombre, int notaMinimaAprobacion);
	
	public abstract Certificado crearCertificado(String nombreCurso, String codigoValidacion);
}
