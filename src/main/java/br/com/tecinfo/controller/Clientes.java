package br.com.tecinfo.controller;

import java.io.Serializable;
import java.util.List;

import br.com.tecinfo.entity.Cliente;

public interface Clientes {

	public static class FiltroCliente implements Serializable {

		private static final long serialVersionUID = 1L;

		public static enum TipoDeFiltro {
			POR_CODIGO, POR_NOME;
		}

		private TipoDeFiltro tipoDeFiltro = TipoDeFiltro.POR_NOME;
		private Long codigoRef;
		private String nomeRef;

		public TipoDeFiltro getTipoDeFiltro() {
			return tipoDeFiltro;
		}

		public void setTipoDeFiltro(TipoDeFiltro tipoDeFiltro) {
			this.tipoDeFiltro = tipoDeFiltro;
		}

		public Long getCodigoRef() {
			return codigoRef;
		}

		public void setCodigoRef(Long codigoRef) {
			this.codigoRef = codigoRef;
		}

		public String getNomeRef() {
			return nomeRef;
		}

		public void setNomeRef(String nomeRef) {
			this.nomeRef = nomeRef;
		}

	}

	public List<Cliente> procurar(FiltroCliente filtro);

}
