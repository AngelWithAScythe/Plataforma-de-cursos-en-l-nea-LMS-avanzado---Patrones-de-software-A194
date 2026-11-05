package com.java.lms.repository;

import com.java.lms.model.Usuario;


public interface UsuarioRepository {

	public Usuario findByNickName(String nickname);

	void save(Usuario usuario);
}
