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
        HttpSession session = request.getSession(false); // false: no crear una nueva si no existe
        Usuario usuario = (session != null) ? (Usuario) session.getAttribute("usuarioActual") : null;

        if (usuario == null) {
            response.sendRedirect(request.getContextPath() + "/");
            return false; // corta la petición, no llega al controlador
        }

        // Rutas /admin/** requieren además rol ADMINISTRADOR
        if (request.getRequestURI().startsWith("/admin") && usuario.getRol() != Rol.ADMINISTRADOR) {
            response.sendRedirect(request.getContextPath() + "/home");
            return false;
        }

        return true; 
    }
}
