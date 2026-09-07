package com.java.lms.model;

public class Administrador extends Usuario {

	public Administrador(String id, String nombre, String nickname, String contrasena) {
		super(id, nombre, nickname, contrasena);
	}

	@Override
	public Rol getRol() {
		return Rol.ADMINISTRADOR;
	}

	// Aquí a futuro: métodos propios de un administrador,
	// por ejemplo gestionarUsuarios(), verReportes(), etc.
}
