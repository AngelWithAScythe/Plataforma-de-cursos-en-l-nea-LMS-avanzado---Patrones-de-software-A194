package com.java.lms.config;

import org.springframework.web.servlet.HandlerInterceptor;

import com.java.lms.model.Rol;
import com.java.lms.model.Usuario;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class SesionInterceptor implements HandlerInterceptor{
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
	    HttpSession session = request.getSession(false);
	    Usuario usuario = (session != null) ? (Usuario) session.getAttribute("usuarioActual") : null;

	    if (usuario == null) {
	        response.sendRedirect(request.getContextPath() + "/");
	        return false;
	    }

	    String uri = request.getRequestURI();

	    if (uri.startsWith("/admin") && usuario.getRol() != Rol.ADMINISTRADOR) {
	        response.sendRedirect(request.getContextPath() + "/home");
	        return false;
	    }
	    if (uri.startsWith("/instructor") && usuario.getRol() != Rol.INSTRUCTOR) {
	        response.sendRedirect(request.getContextPath() + "/home");
	        return false;
	    }
	    if (uri.startsWith("/estudiante") && usuario.getRol() != Rol.ESTUDIANTE) {
	        response.sendRedirect(request.getContextPath() + "/home");
	        return false;
	    }

	    return true;
	}
}
