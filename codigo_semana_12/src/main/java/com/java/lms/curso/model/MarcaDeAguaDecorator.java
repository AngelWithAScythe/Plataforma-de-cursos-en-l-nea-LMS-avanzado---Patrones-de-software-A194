package com.java.lms.curso.model;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.awt.Color;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.util.Matrix;

import com.java.lms.curso.formato.FormateadorCertificado;
import com.java.lms.curso.formato.FormateadorDecorator;

public class MarcaDeAguaDecorator extends FormateadorDecorator{
	
	private final String texto;

	public MarcaDeAguaDecorator(FormateadorCertificado formateador, String texto) {
		super(formateador);
		this.texto = texto;
	}

	@Override
	public byte[] generar(CertificadoImpreso certificado) {
		byte[] original = formateador.generar(certificado);
		return esPdf(original) ? agregarMarcaPdf(original) : agregarMarcaHtml(original);
	}

	private boolean esPdf(byte[] contenido) {
		return contenido.length > 4 && contenido[0] == '%' && contenido[1] == 'P' && contenido[2] == 'D' && contenido[3] == 'F';
	}

	private byte[] agregarMarcaHtml(byte[] original) {
		String html = new String(original, StandardCharsets.UTF_8);
		String marca = "<div style=\"position:fixed; top:40%%; left:0; right:0; text-align:center; "
				+ "transform:rotate(-30deg); font-size:48px; color:rgba(200,0,0,0.25); z-index:999;\">" + texto + "</div>";

		int idx = html.indexOf("<body");
		int fin = html.indexOf('>', idx) + 1;
		String conMarca = html.substring(0, fin) + marca + html.substring(fin);
		return conMarca.getBytes(StandardCharsets.UTF_8);
	}

	private byte[] agregarMarcaPdf(byte[] original) {
		try (PDDocument documento = PDDocument.load(original)) {
			for (PDPage pagina : documento.getPages()) {
				try (PDPageContentStream contenido = new PDPageContentStream(
						documento, pagina, PDPageContentStream.AppendMode.APPEND, true, true)) {

					contenido.setNonStrokingColor(new Color(220, 150, 150));
					contenido.beginText();
					contenido.setFont(PDType1Font.HELVETICA_BOLD, 50);
					contenido.setTextMatrix(Matrix.getRotateInstance(Math.toRadians(30), 100, 400));
					contenido.showText(texto);
					contenido.endText();
				}
			}

			ByteArrayOutputStream salida = new ByteArrayOutputStream();
			documento.save(salida);
			return salida.toByteArray();

		} catch (IOException e) {
			throw new RuntimeException("No se pudo agregar la marca de agua al PDF", e);
		}
	}
}
