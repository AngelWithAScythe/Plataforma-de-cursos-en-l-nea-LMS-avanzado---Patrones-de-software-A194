package com.java.lms.curso.factory;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.java.lms.curso.model.TipoCurso;

@Component
public class CursoFactoryProvider {
	
	private final Map<TipoCurso, CursoFactory> factories;
	
	public CursoFactoryProvider(List<CursoFactory> factoriesDisponibles) {
		this.factories = factoriesDisponibles.stream().collect(Collectors.toMap(CursoFactory::getTipo, f -> f));
	}
	
	public CursoFactory getFactory(TipoCurso tipo) {
		CursoFactory factory = factories.get(tipo);
		if(factory == null) {
			throw new IllegalArgumentException("No existe una CursoFactory registrada para el tipo: " + tipo);
		}
		
		return factory;
	}
}
