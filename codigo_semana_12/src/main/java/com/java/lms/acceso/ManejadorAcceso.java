package com.java.lms.acceso;

public abstract class ManejadorAcceso {
	
	private ManejadorAcceso siguiente;

	public ManejadorAcceso setSiguiente(ManejadorAcceso siguiente) {
		this.siguiente = siguiente;
		return siguiente;
	}

	public final ResultadoAcceso manejar(SolicitudAcceso solicitud) {
		ResultadoAcceso rechazo = evaluar(solicitud);

		if (rechazo != null) {
			System.out.println("[Acceso] " + getClass().getSimpleName() + " rechazó " + solicitud.getUri());
			return rechazo;
		}
		if (siguiente != null) {
			return siguiente.manejar(solicitud);
		}
		return ResultadoAcceso.permitido(); 
	}

	protected abstract ResultadoAcceso evaluar(SolicitudAcceso solicitud);
}
