package com.java.lms.curso.model;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import com.java.lms.curso.formato.FormateadorCertificado;
import com.java.lms.curso.formato.FormateadorDecorator;

public class CodigoVerificacionDecorator extends FormateadorDecorator{
	
	public CodigoVerificacionDecorator(FormateadorCertificado formateador) {
		super(formateador);
	}

	@Override
	public byte[] generar(CertificadoImpreso certificado) {
		byte[] original = formateador.generar(certificado);
		String codigo = "Código de verificación: " + UUID.randomUUID().toString().substring(0, 8).toUpperCase();

		return esPdf(original) ? agregarCodigoPdf(original, codigo) : agregarCodigoHtml(original, codigo);
	}

	private boolean esPdf(byte[] contenido) {
		return contenido.length > 4 && contenido[0] == '%' && contenido[1] == 'P' && contenido[2] == 'D' && contenido[3] == 'F';
	}

	private byte[] agregarCodigoHtml(byte[] original, String codigo) {
		String html = new String(original, StandardCharsets.UTF_8);
		String conCodigo = html.replace("</body>", "<p style=\"font-size:10px;color:#999;\">" + codigo + "</p></body>");
		return conCodigo.getBytes(StandardCharsets.UTF_8);
	}

	private byte[] agregarCodigoPdf(byte[] original, String codigo) {
		try (PDDocument documento = PDDocument.load(original)) {
			for (PDPage pagina : documento.getPages()) {
				PDRectangle caja = pagina.getMediaBox();

				try (PDPageContentStream contenido = new PDPageContentStream(
						documento, pagina, PDPageContentStream.AppendMode.APPEND, true, true)) {

					contenido.beginText();
					contenido.setFont(PDType1Font.HELVETICA, 8);
					contenido.newLineAtOffset(caja.getLowerLeftX() + 20, caja.getLowerLeftY() + 20);
					contenido.showText(codigo);
					contenido.endText();
				}
			}

			ByteArrayOutputStream salida = new ByteArrayOutputStream();
			documento.save(salida);
			return salida.toByteArray();

		} catch (IOException e) {
			throw new RuntimeException("No se pudo agregar el código de verificación al PDF", e);
		}
	}
}
