package com.java.lms.curso.pdf;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.pdfbox.io.IOUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.springframework.stereotype.Component;

import com.java.lms.curso.model.CertificadoImpreso;

@Component
public class CertificadoAdapter implements ExportadorCertificado {

	@Override
	public byte[] exportarAPdf(CertificadoImpreso certificado) {
		try (PDDocument documento = new PDDocument()) {
			PDPage pagina = new PDPage();
			documento.addPage(pagina);

			try (PDPageContentStream contenido = new PDPageContentStream(documento, pagina)) {

				dibujarLogo(documento, contenido, certificado.getLogoUrl());

				escribirLinea(contenido, PDType1Font.HELVETICA_BOLD, 18, 700, "Certificado de finalización");
				escribirLinea(contenido, PDType1Font.HELVETICA, 12, 650, "Otorgado a: " + certificado.getNombreEstudiante());
				escribirLinea(contenido, PDType1Font.HELVETICA, 12, 630, "Curso: " + certificado.getCertificado().getNombreCurso());
				escribirLinea(contenido, PDType1Font.HELVETICA, 12, 610, "Nota: " + certificado.getNotaObtenida());
				escribirLinea(contenido, PDType1Font.HELVETICA, 10, 580,
						certificado.getFirmaInstructor() + " - " + certificado.getFechaEmision());
			}

			ByteArrayOutputStream salida = new ByteArrayOutputStream();
			documento.save(salida);
			return salida.toByteArray();

		} catch (IOException e) {
			throw new RuntimeException("No se pudo generar el PDF del certificado", e);
		}
	}

	private void escribirLinea(PDPageContentStream contenido, PDType1Font fuente, int tamano, int y, String texto) throws IOException {
		contenido.beginText();
		contenido.setFont(fuente, tamano);
		contenido.newLineAtOffset(80, y);
		contenido.showText(texto);
		contenido.endText();
	}

	private void dibujarLogo(PDDocument documento, PDPageContentStream contenido, String logoUrl) {
		if (logoUrl == null || logoUrl.isBlank()) {
			return;
		}

		String rutaClasspath = "/static" + (logoUrl.startsWith("/") ? logoUrl : "/" + logoUrl);

		try (InputStream in = getClass().getResourceAsStream(rutaClasspath)) {
			if (in == null) {
				System.err.println("No se encontró el logo en el classpath: " + rutaClasspath);
				return;
			}

			byte[] bytes = IOUtils.toByteArray(in);
			PDImageXObject imagen = PDImageXObject.createFromByteArray(documento, bytes, logoUrl);

			float alturaDeseada = 50;
			float anchoDeseado = alturaDeseada * imagen.getWidth() / imagen.getHeight();
			contenido.drawImage(imagen, 80, 730, anchoDeseado, alturaDeseada);

		} catch (IOException e) {
			System.err.println("No se pudo cargar el logo del certificado: " + e.getMessage());
		}
	}
}