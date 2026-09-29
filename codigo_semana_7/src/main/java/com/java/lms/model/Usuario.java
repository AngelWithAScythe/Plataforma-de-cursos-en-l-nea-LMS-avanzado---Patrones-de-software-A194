package com.java.lms.model;

public abstract class Usuario {

	private String id;

	private String nombre;

	private String nickname;

	private String contrasena;

	public Usuario(String id, String nombre, String nickname, String contrasena) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.nickname = nickname;
		this.contrasena = contrasena;
	}


	public abstract Rol getRol();

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

	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}

}
