package com.java.lms.factory;

import com.java.lms.model.Rol;
import com.java.lms.model.Usuario;

public abstract class UsuarioFactory {

	protected abstract Usuario crearUsuarioEspecifico(String id, String nombre, String nickname, String contrasena);

	public abstract Rol getRol();

	public final Usuario registrarUsuario(String nombre, String nickname, String contrasena) {
		String id = java.util.UUID.randomUUID().toString();
		return crearUsuarioEspecifico(id, nombre, nickname, contrasena);
	}
}
