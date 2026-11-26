package com.java.lms.curso.pdf;

public enum LogoDisponible {
	
	SIN_LOGO(null),
	LMS_UTS("/img/LOGO-UTS.png"),
	LMS_CISCO("/img/Cisco-Emblema.png"),
	LMS_MIKROTIK("/img/Marca-Logo-Mikrotik.png");

	private final String ruta;

	LogoDisponible(String ruta) {
		this.ruta = ruta;
	}

	public String getRuta() {
		return ruta;
	}
}
