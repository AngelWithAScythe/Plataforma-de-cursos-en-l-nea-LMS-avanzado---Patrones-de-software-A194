package com.java.lms.factory;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.java.lms.model.Rol;

@Component
public class UsuarioFactoryProvider {

	private final Map<Rol, UsuarioFactory> factories;

	public UsuarioFactoryProvider(List<UsuarioFactory> factoriesDisponibles) {
		this.factories = factoriesDisponibles.stream()
				.collect(Collectors.toMap(UsuarioFactory::getRol, f -> f));
	}

	public UsuarioFactory getFactory(Rol rol) {
		UsuarioFactory factory = factories.get(rol);
		if (factory == null) {
			throw new IllegalArgumentException("No existe una factory registrada para el rol: " + rol);
		}
		return factory;
	}
}
