package com.java.lms.factory;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.java.lms.model.Rol;

/**
 * Spring inyecta automáticamente TODAS las UsuarioFactory que existan como
 * @Component (Instructor, Estudiante, Administrador...). Este provider las
 * indexa por Rol para poder pedir "la factory de este rol" sin necesidad de
 * un switch/if en el controlador.
 *
 * Ventaja clave: si el día de mañana se agrega un nuevo tipo de usuario
 * (por ejemplo, un "Moderador"), solo hace falta crear su clase Usuario y su
 * ModeradorFactory. Ni este provider ni el controlador necesitan cambiar
 * (principio Abierto/Cerrado).
 */
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
