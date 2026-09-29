package com.java.lms.model;

public class Estudiante extends Usuario {

	public Estudiante(String id, String nombre, String nickname, String contrasena) {
		super(id, nombre, nickname, contrasena);
	}

	@Override
	public Rol getRol() {
		return Rol.ESTUDIANTE;
	}
}
