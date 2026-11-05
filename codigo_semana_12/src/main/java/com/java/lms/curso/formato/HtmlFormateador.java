package com.java.lms.curso.formato;

import java.nio.charset.StandardCharsets;

import org.springframework.stereotype.Component;

import com.java.lms.curso.model.CertificadoImpreso;

@Component
public class HtmlFormateador implements FormateadorCertificado {

	@Override
	public byte[] generar(CertificadoImpreso certificado) {
		String logoHtml = tieneLogo(certificado)
				? "<img src=\"" + certificado.getLogoUrl() + "\" style=\"height: 60px; margin-bottom: 20px;\">"
				: "";

		String html = """
				<!DOCTYPE html>
				<html>
				<head><meta charset="UTF-8"><title>Certificado</title></head>
				<body style="font-family: sans-serif; text-align: center; padding: 60px;">
					%s
					<h1>Certificado de finalización</h1>
					<p>Otorgado a: <strong>%s</strong></p>
					<p>Curso: %s</p>
					<p>Nota: %s</p>
					<p style="margin-top: 40px; font-size: 12px; color: #666;">%s - %s</p>
				</body>
				</html>
				""".formatted(
						logoHtml,
						certificado.getNombreEstudiante(),
						certificado.getCertificado().getNombreCurso(),
						certificado.getNotaObtenida(),
						certificado.getFirmaInstructor(),
						certificado.getFechaEmision());

		return html.getBytes(StandardCharsets.UTF_8);
	}

	private boolean tieneLogo(CertificadoImpreso certificado) {
		return certificado.getLogoUrl() != null && !certificado.getLogoUrl().isBlank();
	}
}