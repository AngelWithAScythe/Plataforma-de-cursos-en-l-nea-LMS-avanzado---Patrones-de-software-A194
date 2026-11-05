package com.java.lms.curso.flyweight;

public final class LogoImagen {
	
	private final String ruta;
	private final byte[] bytes;

	public LogoImagen(String ruta, byte[] bytes) {
		this.ruta = ruta;
		this.bytes = bytes;
	}

	public String getRuta() {
		return ruta;
	}

	public byte[] getBytes() {
		return bytes;
	}
}
