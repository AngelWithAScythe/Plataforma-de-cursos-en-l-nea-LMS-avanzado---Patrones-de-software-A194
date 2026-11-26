package com.java.lms.model;

public class Instructor extends Usuario {

	public Instructor(String id, String nombre, String nickname, String contrasena) {
		super(id, nombre, nickname, contrasena);
	}

	@Override
	public Rol getRol() {
		return Rol.INSTRUCTOR;
	}
}
