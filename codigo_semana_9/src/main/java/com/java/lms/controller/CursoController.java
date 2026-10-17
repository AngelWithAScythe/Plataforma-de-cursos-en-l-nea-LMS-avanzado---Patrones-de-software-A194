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

import com.java.lms.curso.model.Leccion;
import com.java.lms.curso.model.Modulo;
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
	
	@GetMapping("/instructor/cursos/{cursoId}/contenido")
	public String verContenido(@PathVariable String cursoId, Model model) {
		PaqueteCurso curso = cursoRepository.findById(cursoId);
		if (curso == null) {
			return "redirect:/instructor/mis-cursos";
		}
		model.addAttribute("curso", curso);
		return "contenido-curso";
	}

	@PostMapping("/instructor/cursos/{cursoId}/contenido/{moduloId}/submodulo")
	public String agregarSubmodulo(@PathVariable String cursoId, @PathVariable String moduloId, @RequestParam String titulo) {
		PaqueteCurso curso = cursoRepository.findById(cursoId);
		if (curso != null) {
			Modulo destino = curso.getContenido().getEstructura().buscarModulo(moduloId);
			if (destino != null) {
				destino.agregar(new Modulo(titulo));
			}
		}
		return "redirect:/instructor/cursos/" + cursoId + "/contenido";
	}

	@PostMapping("/instructor/cursos/{cursoId}/contenido/{moduloId}/leccion")
	public String agregarLeccion(@PathVariable String cursoId, @PathVariable String moduloId,
			@RequestParam String titulo, @RequestParam int duracionMinutos) {
		PaqueteCurso curso = cursoRepository.findById(cursoId);
		if (curso != null) {
			Modulo destino = curso.getContenido().getEstructura().buscarModulo(moduloId);
			if (destino != null) {
				destino.agregar(new Leccion(titulo, duracionMinutos));
			}
		}
		return "redirect:/instructor/cursos/" + cursoId + "/contenido";
	}
	
}