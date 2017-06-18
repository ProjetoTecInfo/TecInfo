package br.com.tecinfo.boundary.jsf;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Model;

@Model
@RequestScoped
public class Credenciais implements Serializable {

	private static final long serialVersionUID = 1L;

	private String usuario;
	private String senha;

	public Credenciais() {
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

}
