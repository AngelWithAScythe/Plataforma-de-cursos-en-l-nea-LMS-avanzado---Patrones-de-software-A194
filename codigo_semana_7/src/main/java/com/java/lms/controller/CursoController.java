package com.java.lms.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.java.lms.curso.model.PaqueteCurso;
import com.java.lms.curso.model.TipoCurso;
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
	
}