package com.java.lms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.java.lms.curso.iterator.PlanLecciones;
import com.java.lms.curso.model.PaqueteCurso;
import com.java.lms.curso.proxy.AccesoDenegadoException;
import com.java.lms.curso.proxy.ContenidoCursoServicio;
import com.java.lms.curso.service.InscripcionService;
import com.java.lms.model.Usuario;
import com.java.lms.repository.NotificacionRepository;

import jakarta.servlet.http.HttpSession;

@Controller
public class EstudianteController {
	
	@Autowired
	private InscripcionService inscripcionService;
	
	@Autowired
	private ContenidoCursoServicio contenidoServicio;
	
	@Autowired
	private NotificacionRepository notificacionRepository;

	@GetMapping("/estudiante/cursos")
	public String cursosDisponibles(HttpSession session, Model model) {
		Usuario estudiante = (Usuario) session.getAttribute("usuarioActual");
		model.addAttribute("cursos", inscripcionService.cursosDisponibles());
		model.addAttribute("inscritosIds", inscripcionService.idsDeCursosInscritos(estudiante.getId()));
		return "cursos-disponibles";
	}

	@PostMapping("/estudiante/cursos/{id}/inscribirse")
	public String inscribirse(@PathVariable String id, HttpSession session, RedirectAttributes redirectAttributes) {
		Usuario estudiante = (Usuario) session.getAttribute("usuarioActual");
		try {
			inscripcionService.inscribir(estudiante, id);
			redirectAttributes.addFlashAttribute("mensaje", "Te inscribiste correctamente");
		} catch (IllegalStateException | IllegalArgumentException e) {
			redirectAttributes.addFlashAttribute("error", e.getMessage());
		}
		return "redirect:/estudiante/cursos";
	}

	@GetMapping("/estudiante/mis-cursos")
	public String misCursos(HttpSession session, Model model) {
		Usuario estudiante = (Usuario) session.getAttribute("usuarioActual");
		model.addAttribute("cursos", inscripcionService.cursosDe(estudiante.getId()));
		return "mis-cursos-estudiante";
	}
	
	@GetMapping("/estudiante/cursos/{id}/contenido")
	public String verContenido(@PathVariable String id, HttpSession session,
			Model model, RedirectAttributes redirectAttributes) {
		Usuario estudiante = (Usuario) session.getAttribute("usuarioActual");
		try {
			PaqueteCurso curso = contenidoServicio.verContenido(estudiante, id);
			if (curso == null) {
				return "redirect:/estudiante/mis-cursos";
			}
			model.addAttribute("curso", curso);
			model.addAttribute("editable", false);
			model.addAttribute("plan", PlanLecciones.de(curso.getContenido().getEstructura()));
			return "contenido-curso";
		} catch (AccesoDenegadoException e) {
			redirectAttributes.addFlashAttribute("error", e.getMessage());
			return "redirect:/estudiante/mis-cursos";
		}
	}
	
	@GetMapping("/estudiante/notificaciones")
	public String notificaciones(HttpSession session, Model model) {
		Usuario estudiante = (Usuario) session.getAttribute("usuarioActual");
		
		model.addAttribute("nuevasIds", notificacionRepository.idsNoLeidas(estudiante.getId()));
		model.addAttribute("notificaciones", notificacionRepository.de(estudiante.getId()));
		notificacionRepository.marcarTodasLeidas(estudiante.getId());
		return "notificaciones";
	}
}
