package com.java.lms.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.java.lms.curso.iterator.PlanLecciones;
import com.java.lms.curso.model.PaqueteCurso;
import com.java.lms.curso.model.TipoCurso;
import com.java.lms.curso.proxy.AccesoDenegadoException;
import com.java.lms.curso.proxy.ContenidoCursoServicio;
import com.java.lms.curso.service.CursoService;
import com.java.lms.model.Usuario;
import com.java.lms.repository.CursoRepository;

import jakarta.servlet.http.HttpSession;

@Controller
public class CursoController {
	
	@Autowired
	private CursoService cursoService;
	
	@Autowired
	private CursoRepository cursoRepository;
	
	@Autowired
	private ContenidoCursoServicio contenidoServicio;

	@PostMapping("/instructor/cursos")
	public String crearCurso(@RequestParam TipoCurso tipo,
			@RequestParam String tituloContenido,
			@RequestParam String descripcionContenido,
			@RequestParam String nombreEvaluacion,
			@RequestParam int notaMinima,
			@RequestParam String nombreCurso,
			@RequestParam String codigoValidacion,
			RedirectAttributes redirectAttributes, HttpSession session) {
		
		Usuario instructor = (Usuario) session.getAttribute("usuarioActual");
		PaqueteCurso paquete = cursoService.crearPaqueteCurso(tipo, tituloContenido, descripcionContenido,
				nombreEvaluacion, notaMinima, nombreCurso, codigoValidacion, instructor);

		redirectAttributes.addFlashAttribute("mensaje",
				"Curso creado: " + paquete.getContenido().getTitulo() + " (" + tipo + ")");
		return "redirect:/home";
	}
	
	@GetMapping("/instructor/cursos")
	public String formularioCrearCurso(Model model) {
	    model.addAttribute("tipos", TipoCurso.values());
	    return "crear-curso";
	}
	
	@GetMapping("/instructor/mis-cursos")
	public String misCursos(HttpSession session, Model model) {
	    Usuario usuario = (Usuario) session.getAttribute("usuarioActual");
	    List<PaqueteCurso> cursos = cursoRepository.findByInstructor(usuario.getNickname());
	    model.addAttribute("cursos", cursos);
	    return "mis-cursos";
	}
	
	@GetMapping("/instructor/cursos/{cursoId}/contenido")
	public String verContenido(@PathVariable String cursoId, HttpSession session,
			Model model, RedirectAttributes redirectAttributes) {
		Usuario usuario = (Usuario) session.getAttribute("usuarioActual");
		try {
			PaqueteCurso curso = contenidoServicio.verContenido(usuario, cursoId);
			if (curso == null) {
				return "redirect:/instructor/mis-cursos";
			}
			model.addAttribute("curso", curso);
			model.addAttribute("editable", true);
			model.addAttribute("puntos", contenidoServicio.verPuntos(usuario, cursoId));
			model.addAttribute("plan", PlanLecciones.de(curso.getContenido().getEstructura()));
			return "contenido-curso";
		} catch (AccesoDenegadoException e) {
			redirectAttributes.addFlashAttribute("error", e.getMessage());
			return "redirect:/instructor/mis-cursos";
		}
	}

	@PostMapping("/instructor/cursos/{cursoId}/contenido/{moduloId}/submodulo")
	public String agregarSubmodulo(@PathVariable String cursoId, @PathVariable String moduloId,
			@RequestParam String titulo, HttpSession session, RedirectAttributes redirectAttributes) {
		Usuario usuario = (Usuario) session.getAttribute("usuarioActual");
		try {
			contenidoServicio.agregarSubmodulo(usuario, cursoId, moduloId, titulo);
		} catch (AccesoDenegadoException e) {
			redirectAttributes.addFlashAttribute("error", e.getMessage());
			return "redirect:/instructor/mis-cursos";
		}
		return "redirect:/instructor/cursos/" + cursoId + "/contenido";
	}

	@PostMapping("/instructor/cursos/{cursoId}/contenido/{moduloId}/leccion")
	public String agregarLeccion(@PathVariable String cursoId, @PathVariable String moduloId,
			@RequestParam String titulo, @RequestParam int duracionMinutos,
			HttpSession session, RedirectAttributes redirectAttributes) {
		Usuario usuario = (Usuario) session.getAttribute("usuarioActual");
		try {
			contenidoServicio.agregarLeccion(usuario, cursoId, moduloId, titulo, duracionMinutos);
		} catch (AccesoDenegadoException e) {
			redirectAttributes.addFlashAttribute("error", e.getMessage());
			return "redirect:/instructor/mis-cursos";
		}
		return "redirect:/instructor/cursos/" + cursoId + "/contenido";
	}
	
	@PostMapping("/instructor/cursos/{cursoId}/contenido/deshacer")
	public String deshacer(@PathVariable String cursoId, HttpSession session, RedirectAttributes redirectAttributes) {
		Usuario usuario = (Usuario) session.getAttribute("usuarioActual");
		try {
			String accion = contenidoServicio.deshacer(usuario, cursoId);
			if (accion == null) {
				redirectAttributes.addFlashAttribute("error", "No hay acciones para deshacer");
			} else {
				redirectAttributes.addFlashAttribute("mensaje", "Se deshizo: " + accion);
			}
		} catch (AccesoDenegadoException e) {
			redirectAttributes.addFlashAttribute("error", e.getMessage());
			return "redirect:/instructor/mis-cursos";
		}
		return "redirect:/instructor/cursos/" + cursoId + "/contenido";
	}
	
	@PostMapping("/instructor/cursos/{cursoId}/contenido/puntos")
	public String guardarPunto(@PathVariable String cursoId, @RequestParam String etiqueta,
			HttpSession session, RedirectAttributes redirectAttributes) {
		Usuario usuario = (Usuario) session.getAttribute("usuarioActual");
		try {
			contenidoServicio.guardarPunto(usuario, cursoId, etiqueta);
			redirectAttributes.addFlashAttribute("mensaje", "Punto de restauración guardado: " + etiqueta);
		} catch (AccesoDenegadoException e) {
			redirectAttributes.addFlashAttribute("error", e.getMessage());
			return "redirect:/instructor/mis-cursos";
		}
		return "redirect:/instructor/cursos/" + cursoId + "/contenido";
	}

	@PostMapping("/instructor/cursos/{cursoId}/contenido/puntos/{puntoId}/restaurar")
	public String restaurarPunto(@PathVariable String cursoId, @PathVariable String puntoId,
			HttpSession session, RedirectAttributes redirectAttributes) {
		Usuario usuario = (Usuario) session.getAttribute("usuarioActual");
		try {
			String etiqueta = contenidoServicio.restaurarPunto(usuario, cursoId, puntoId);
			if (etiqueta == null) {
				redirectAttributes.addFlashAttribute("error", "El punto de restauración no existe");
			} else {
				redirectAttributes.addFlashAttribute("mensaje", "Contenido restaurado al punto: " + etiqueta);
			}
		} catch (AccesoDenegadoException e) {
			redirectAttributes.addFlashAttribute("error", e.getMessage());
			return "redirect:/instructor/mis-cursos";
		}
		return "redirect:/instructor/cursos/" + cursoId + "/contenido";
	}
}