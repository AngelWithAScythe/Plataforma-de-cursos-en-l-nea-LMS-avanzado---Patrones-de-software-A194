package com.java.lms.factory;

import org.springframework.stereotype.Component;

import com.java.lms.model.Estudiante;
import com.java.lms.model.Rol;
import com.java.lms.model.Usuario;

@Component
public class EstudianteFactory extends UsuarioFactory {

	@Override
	protected Usuario crearUsuarioEspecifico(String id, String nombre, String nickname, String contrasena) {
		return new Estudiante(id, nombre, nickname, contrasena);
	}

	@Override
	public Rol getRol() {
		return Rol.ESTUDIANTE;
	}
}
