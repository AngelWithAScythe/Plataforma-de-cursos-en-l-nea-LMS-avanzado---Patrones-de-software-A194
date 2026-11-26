package com.java.lms.factory;

import org.springframework.stereotype.Component;

import com.java.lms.model.Administrador;
import com.java.lms.model.Rol;
import com.java.lms.model.Usuario;

@Component
public class AdministradorFactory extends UsuarioFactory {

	@Override
	protected Usuario crearUsuarioEspecifico(String id, String nombre, String nickname, String contrasena) {
		return new Administrador(id, nombre, nickname, contrasena);
	}

	@Override
	public Rol getRol() {
		return Rol.ADMINISTRADOR;
	}
}
