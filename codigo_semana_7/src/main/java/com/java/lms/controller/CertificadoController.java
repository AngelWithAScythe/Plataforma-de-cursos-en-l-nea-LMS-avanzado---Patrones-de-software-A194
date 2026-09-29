package com.java.lms.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.java.lms.curso.formato.CertificadoExportable;
import com.java.lms.curso.formato.FormateadorCertificado;
import com.java.lms.curso.model.CertificadoImpreso;
import com.java.lms.curso.model.PaqueteCurso;
import com.java.lms.curso.pdf.LogoDisponible;
import com.java.lms.curso.service.CertificadoService;
import com.java.lms.repository.CertificadoRepository;
import com.java.lms.repository.CursoRepository;

@Controller
public class CertificadoController {
	
	@Autowired
	private CursoRepository cursoRepository;
	
	@Autowired
	private CertificadoService certificadoService;
	
	@Autowired
	private CertificadoRepository certificadoRepository;
	
	@Autowired
	private Map<String, FormateadorCertificado> formateadores;
	
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
		model.addAttribute("logos", LogoDisponible.values());
		return "plantilla-certificado";
	}

	@PostMapping("/instructor/cursos/{id}/plantilla-certificado")
	public String guardarPlantilla(@PathVariable String id,
			@RequestParam String firmaInstructor,
			@RequestParam LogoDisponible logo,
			@RequestParam(required = false) String textoLegal,
			RedirectAttributes redirectAttributes) {

		PaqueteCurso curso = cursoRepository.findById(id);
		if (curso == null) {
			return "redirect:/instructor/mis-cursos";
		}

		certificadoService.configurarPlantilla(curso, firmaInstructor, logo.getRuta(), textoLegal);
		redirectAttributes.addFlashAttribute("mensaje", "Plantilla de certificado guardada");
		return "redirect:/instructor/mis-cursos";
	}
	
	@GetMapping("/instructor/certificados/{cursoId}/{estudiante}/exportar")
	public ResponseEntity<byte[]> exportarCertificado(@PathVariable String cursoId, @PathVariable String estudiante,
			@RequestParam(defaultValue = "pdf") String formato) {

		CertificadoImpreso certificado = certificadoRepository.findByCursoId(cursoId).stream()
				.filter(c -> c.getNombreEstudiante().equalsIgnoreCase(estudiante))
				.findFirst()
				.orElseThrow(() -> new IllegalArgumentException("Certificado no encontrado"));

		FormateadorCertificado formateador = formateadores.get(formato + "Formateador");
		if (formateador == null) {
			throw new IllegalArgumentException("Formato no soportado: " + formato);
		}

		byte[] contenido = new CertificadoExportable(formateador).exportar(certificado);

		if ("html".equals(formato)) {
			return ResponseEntity.ok().contentType(MediaType.TEXT_HTML).body(contenido);
		}

		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=certificado.pdf")
				.contentType(MediaType.APPLICATION_PDF)
				.body(contenido);
	}
}
