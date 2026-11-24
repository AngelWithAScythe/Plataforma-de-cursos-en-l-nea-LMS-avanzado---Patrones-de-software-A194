package com.java.lms.acceso;

import com.java.lms.model.Rol;

public class VerificadorZona extends ManejadorAcceso{
	
	private final String prefijo;
	private final Rol rolPermitido;

	public VerificadorZona(String prefijo, Rol rolPermitido) {
		this.prefijo = prefijo;
		this.rolPermitido = rolPermitido;
	}

	@Override
	protected ResultadoAcceso evaluar(SolicitudAcceso solicitud) {
		String uri = solicitud.getUri();
		boolean enLaZona = uri.equals(prefijo) || uri.startsWith(prefijo + "/");

		if (enLaZona && solicitud.getUsuario().getRol() != rolPermitido) {
			return ResultadoAcceso.redirigir("/home");
		}
		return null;
	}
}
