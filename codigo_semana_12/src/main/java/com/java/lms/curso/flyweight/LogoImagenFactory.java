package com.java.lms.curso.flyweight;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;

@Component
public class LogoImagenFactory {
	
	private final Map<String, LogoImagen> logos = new ConcurrentHashMap<>();

	public LogoImagen obtener(String ruta) {
		if (ruta == null || ruta.isBlank()) {
			return null;
		}
		return logos.computeIfAbsent(ruta, this::cargar);
	}

	public int cantidadCargados() {
		return logos.size();
	}

	private LogoImagen cargar(String ruta) {
		String rutaClasspath = "/static" + (ruta.startsWith("/") ? ruta : "/" + ruta);

		try (InputStream in = getClass().getResourceAsStream(rutaClasspath)) {
			if (in == null) {
				System.err.println("No se encontró el logo en el classpath: " + rutaClasspath);
				return null;
			}
			System.out.println("[Flyweight] Cargando logo desde el classpath: " + ruta);
			return new LogoImagen(ruta, in.readAllBytes());

		} catch (IOException e) {
			System.err.println("No se pudo leer el logo " + ruta + ": " + e.getMessage());
			return null;
		}
	}
}
