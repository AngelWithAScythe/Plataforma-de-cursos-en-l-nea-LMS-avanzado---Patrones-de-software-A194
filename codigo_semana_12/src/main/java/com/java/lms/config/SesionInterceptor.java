package com.java.lms.config;

import org.springframework.web.servlet.HandlerInterceptor;

import com.java.lms.acceso.ManejadorAcceso;
import com.java.lms.acceso.ResultadoAcceso;
import com.java.lms.acceso.SolicitudAcceso;
import com.java.lms.acceso.VerificadorSesion;
import com.java.lms.acceso.VerificadorZona;
import com.java.lms.model.Rol;
import com.java.lms.model.Usuario;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class SesionInterceptor implements HandlerInterceptor{
	
	private final ManejadorAcceso cadena;

	public SesionInterceptor() {
		ManejadorAcceso sesion = new VerificadorSesion();
		sesion.setSiguiente(new VerificadorZona("/admin", Rol.ADMINISTRADOR))
				.setSiguiente(new VerificadorZona("/instructor", Rol.INSTRUCTOR))
				.setSiguiente(new VerificadorZona("/estudiante", Rol.ESTUDIANTE));
		this.cadena = sesion;
	}
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		HttpSession session = request.getSession(false);
		Usuario usuario = (session != null) ? (Usuario) session.getAttribute("usuarioActual") : null;

		ResultadoAcceso resultado = cadena.manejar(new SolicitudAcceso(request.getRequestURI(), usuario));

		if (!resultado.isPermitido()) {
			response.sendRedirect(request.getContextPath() + resultado.getDestino());
			return false;
		}
		return true;
	}
}
