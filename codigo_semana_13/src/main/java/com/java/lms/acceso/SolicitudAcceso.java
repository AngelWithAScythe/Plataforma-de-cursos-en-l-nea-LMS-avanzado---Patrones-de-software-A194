package com.java.lms.acceso;

import com.java.lms.model.Usuario;

public class SolicitudAcceso {
	
	private final String uri;
	private final Usuario usuario; 

	public SolicitudAcceso(String uri, Usuario usuario) {
		this.uri = uri;
		this.usuario = usuario;
	}

	public String getUri() { return uri; }
	public Usuario getUsuario() { return usuario; }
}
