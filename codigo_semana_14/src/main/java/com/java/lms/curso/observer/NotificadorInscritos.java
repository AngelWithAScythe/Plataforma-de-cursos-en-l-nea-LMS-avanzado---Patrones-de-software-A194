package com.java.lms.curso.observer;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.java.lms.curso.model.Inscripcion;
import com.java.lms.curso.model.Notificacion;
import com.java.lms.repository.InscripcionRepository;
import com.java.lms.repository.NotificacionRepository;

@Component
public class NotificadorInscritos implements ObservadorCurso{
	
	@Autowired
	private InscripcionRepository inscripcionRepository;

	@Autowired
	private NotificacionRepository notificacionRepository;

	@Override
	public void actualizar(EventoCurso evento) {
		String fecha = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
		String mensaje = evento.tituloCurso() + " — " + evento.detalle();

		for (Inscripcion inscripcion : inscripcionRepository.findByCurso(evento.cursoId())) {
			notificacionRepository.agregar(inscripcion.getEstudianteId(),
					new Notificacion(UUID.randomUUID().toString(), evento.cursoId(), mensaje, fecha));
		}
	}
}
