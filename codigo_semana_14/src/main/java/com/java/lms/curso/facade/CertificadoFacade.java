package com.java.lms.curso.facade;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.java.lms.curso.formato.CertificadoExportable;
import com.java.lms.curso.formato.FormateadorCertificado;
import com.java.lms.curso.model.CertificadoImpreso;
import com.java.lms.curso.model.CodigoVerificacionDecorator;
import com.java.lms.curso.model.MarcaDeAguaDecorator;
import com.java.lms.curso.model.PaqueteCurso;
import com.java.lms.curso.service.CertificadoService;
import com.java.lms.repository.CertificadoRepository;
import com.java.lms.repository.CursoRepository;

@Component
public class CertificadoFacade {
	
	@Autowired
	private CursoRepository cursoRepository;

	@Autowired
	private CertificadoService certificadoService;

	@Autowired
	private CertificadoRepository certificadoRepository;

	@Autowired
	private Map<String, FormateadorCertificado> formateadores;

	public PaqueteCurso buscarCurso(String cursoId) {
		return cursoRepository.findById(cursoId);
	}

	public void configurarPlantilla(String cursoId, String firmaInstructor, String logoUrl, String textoLegal) {
		PaqueteCurso curso = cursoRepository.findById(cursoId);
		if (curso == null) {
			throw new IllegalArgumentException("El curso no existe");
		}
		certificadoService.configurarPlantilla(curso, firmaInstructor, logoUrl, textoLegal);
	}

	public CertificadoImpreso emitir(String cursoId, String nombreEstudiante, int nota) {
		return certificadoService.emitirCertificado(cursoId, nombreEstudiante, nota);
	}

	public List<CertificadoImpreso> listarPorCurso(String cursoId) {
		return certificadoRepository.findByCursoId(cursoId);
	}

	public byte[] exportar(String cursoId, String estudiante, String formato,
			boolean marcaAgua, boolean codigoVerificacion) {

		CertificadoImpreso certificado = certificadoRepository.findByCursoId(cursoId).stream()
				.filter(c -> c.getNombreEstudiante().equalsIgnoreCase(estudiante))
				.findFirst()
				.orElseThrow(() -> new IllegalArgumentException("Certificado no encontrado"));

		FormateadorCertificado formateador = formateadores.get(formato + "Formateador");
		if (formateador == null) {
			throw new IllegalArgumentException("Formato no soportado: " + formato);
		}

		if (marcaAgua) {
			formateador = new MarcaDeAguaDecorator(formateador, "COPIA");
		}
		if (codigoVerificacion) {
			formateador = new CodigoVerificacionDecorator(formateador);
		}

		return new CertificadoExportable(formateador).exportar(certificado);
	}
}
