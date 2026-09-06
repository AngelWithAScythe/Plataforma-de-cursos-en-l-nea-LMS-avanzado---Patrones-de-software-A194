package com.java.lms.model;

public class Usuario {

	private String id;

	private String nombre;

	private String nickname;

	private String contrasena;

	private Rol rol;

	public Usuario(String id, String nombre, String nickname, String contraseña, Rol rol) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.nickname = nickname;
		this.contrasena = contraseña;
		this.rol = rol;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getContrasena() {
		return contrasena;
	}

	public void setContrasena(String contraseña) {
		this.contrasena = contraseña;
	}

	public Rol getRol() {
		return rol;
	}

	public void setRol(Rol rol) {
		this.rol = rol;
	}


}
