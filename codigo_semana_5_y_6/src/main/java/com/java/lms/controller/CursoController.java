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

import com.java.lms.curso.model.CertificadoImpreso;
import com.java.lms.curso.model.PaqueteCurso;
import com.java.lms.curso.model.TipoCurso;
import com.java.lms.curso.service.CertificadoService;
import com.java.lms.curso.service.CursoService;
import com.java.lms.model.Usuario;
import com.java.lms.repository.CertificadoRepository;
import com.java.lms.repository.CursoRepository;

import jakarta.servlet.http.HttpSession;

@Controller
public class CursoController {
	
	@Autowired
	private CursoService cursoService;
	
	@Autowired
	private CursoRepository cursoRepository;
	
	@Autowired
	private CertificadoService certificadoService;
	
	@Autowired
	private CertificadoRepository certificadoRepository;

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
	
	@GetMapping("/instructor/cursos/{id}/certificado")
	public String formularioCertificado(@PathVariable String id, Model model) {
	    PaqueteCurso curso = cursoRepository.findById(id);
	    if (curso == null) {
	        return "redirect:/instructor/mis-cursos";
	    }
	    model.addAttribute("curso", curso);
	    return "emitir-certificado";
	}

	@PostMapping("/instructor/cursos/{id}/certificado")
	public String emitirCertificado(@PathVariable String id,
			@RequestParam String nombreEstudiante,
			@RequestParam int nota,
			RedirectAttributes redirectAttributes) {

		try {
			CertificadoImpreso impreso = certificadoService.emitirCertificado(id, nombreEstudiante, nota);
			redirectAttributes.addFlashAttribute("mensaje",
					"Certificado emitido para " + impreso.getNombreEstudiante() + " (nota: " + nota + ")");
		} catch (IllegalStateException e) {
			redirectAttributes.addFlashAttribute("error", e.getMessage());
		}

		return "redirect:/instructor/mis-cursos";
	}	
	
	@GetMapping("/instructor/cursos/{id}/certificados")
	public String verCertificados(@PathVariable String id, Model model) {
	    PaqueteCurso curso = cursoRepository.findById(id);
	    if (curso == null) {
	        return "redirect:/instructor/mis-cursos";
	    }
	    model.addAttribute("curso", curso);
	    model.addAttribute("certificados", certificadoRepository.findByCursoId(id));
	    return "certificados-curso";
	}
	
	@GetMapping("/instructor/cursos/{id}/plantilla-certificado")
	public String formularioPlantilla(@PathVariable String id, Model model) {
		PaqueteCurso curso = cursoRepository.findById(id);
		if (curso == null) {
			return "redirect:/instructor/mis-cursos";
		}
		model.addAttribute("curso", curso);
		return "plantilla-certificado";
	}

	@PostMapping("/instructor/cursos/{id}/plantilla-certificado")
	public String guardarPlantilla(@PathVariable String id,
			@RequestParam String firmaInstructor,
			@RequestParam(required = false) String logoUrl,
			@RequestParam(required = false) String textoLegal,
			RedirectAttributes redirectAttributes) {

		PaqueteCurso curso = cursoRepository.findById(id);
		if (curso == null) {
			return "redirect:/instructor/mis-cursos";
		}

		certificadoService.configurarPlantilla(curso, firmaInstructor, logoUrl, textoLegal);
		redirectAttributes.addFlashAttribute("mensaje", "Plantilla de certificado guardada");
		return "redirect:/instructor/mis-cursos";
	}
}
