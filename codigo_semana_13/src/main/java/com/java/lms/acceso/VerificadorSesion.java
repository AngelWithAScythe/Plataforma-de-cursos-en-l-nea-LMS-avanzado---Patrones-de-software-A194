package com.java.lms.acceso;

public class VerificadorSesion extends ManejadorAcceso{
	
	@Override
	protected ResultadoAcceso evaluar(SolicitudAcceso solicitud) {
		return solicitud.getUsuario() == null ? ResultadoAcceso.redirigir("/") : null;
	}
}
