package com.java.lms.acceso;

public class ResultadoAcceso {
	
	private final boolean permitido;
	private final String destino;

	private ResultadoAcceso(boolean permitido, String destino) {
		this.permitido = permitido;
		this.destino = destino;
	}

	public static ResultadoAcceso permitido() {
		return new ResultadoAcceso(true, null);
	}

	public static ResultadoAcceso redirigir(String destino) {
		return new ResultadoAcceso(false, destino);
	}

	public boolean isPermitido() { return permitido; }
	public String getDestino() { return destino; }
}
