package com.java.lms.factory;

import org.springframework.stereotype.Component;

import com.java.lms.model.Instructor;
import com.java.lms.model.Rol;
import com.java.lms.model.Usuario;

@Component
public class InstructorFactory extends UsuarioFactory {

	@Override
	protected Usuario crearUsuarioEspecifico(String id, String nombre, String nickname, String contrasena) {
		return new Instructor(id, nombre, nickname, contrasena);
	}

	@Override
	public Rol getRol() {
		return Rol.INSTRUCTOR;
	}
}
