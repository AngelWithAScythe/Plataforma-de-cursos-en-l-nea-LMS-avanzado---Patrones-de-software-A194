package com.java.lms.curso.observer;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.stereotype.Component;

@Component
public class PublicadorEventosCurso {

	private final List<ObservadorCurso> observadores = new CopyOnWriteArrayList<>();

	public PublicadorEventosCurso(List<ObservadorCurso> iniciales) {
		observadores.addAll(iniciales);
	}

	public void suscribir(ObservadorCurso observador) {
		observadores.add(observador);
	}

	public void cancelar(ObservadorCurso observador) {
		observadores.remove(observador);
	}

	public void notificar(EventoCurso evento) {
		System.out.println("[Observer] " + evento.tituloCurso() + " - " + evento.detalle() + " -> "
				+ observadores.size() + " observador(es)");
		for (ObservadorCurso observador : observadores) {
			try {
				observador.actualizar(evento);
			} catch (RuntimeException e) {
				System.err.println("[Observer] Falló " + observador.getClass().getSimpleName() + ": " + e.getMessage());
			}
		}
	}
}
