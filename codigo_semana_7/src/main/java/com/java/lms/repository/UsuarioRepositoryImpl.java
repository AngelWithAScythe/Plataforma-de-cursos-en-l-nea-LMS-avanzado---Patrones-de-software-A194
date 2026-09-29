package com.java.lms.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.java.lms.model.Administrador;
import com.java.lms.model.Estudiante;
import com.java.lms.model.Instructor;
import com.java.lms.model.Usuario;

@Repository
public class UsuarioRepositoryImpl implements UsuarioRepository{

	private final List<Usuario> bDSimulada = new ArrayList<>();

	public UsuarioRepositoryImpl() {
		bDSimulada.add(new Estudiante("1", "Sebastian", "kayden", "tigre2003"));
		bDSimulada.add(new Instructor("2", "Oscar", "darkwolf", "giron123"));
		bDSimulada.add(new Administrador("3", "German", "aguiladelmal", "admin123"));
	}

	@Override
	public Usuario findByNickName(String nickname) {
		return bDSimulada.stream().filter(u -> u.getNickname().equalsIgnoreCase(nickname)).findFirst().orElse(null);
	}

	@Override
	public void save(Usuario usuario) {
		bDSimulada.add(usuario);
	}
}
