package br.com.tecinfo.boundary.jsf;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Instance;
import javax.enterprise.inject.Produces;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.tecinfo.controller.ServicoDeAutenticacao;
import br.com.tecinfo.entity.Funcionario;

@Named
@SessionScoped
public class LoginMBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	@RequestScoped
	private Instance<FacesContext> facesContext;

	private Funcionario funcionarioCorrente;

	private String usuario;
	private String senha;

	@Inject
	private ServicoDeAutenticacao autenticador;

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

	public String login() {
		this.funcionarioCorrente = autenticador.autenticar(usuario, senha);
		if (this.funcionarioCorrente == null) {
			facesContext.get().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_WARN, "Falha de autenticação", "usuario inválido"));
		}
		return isLogged() ? "home.xhtml?faces-redirect=true" : null;
	}

	public String logout() {
		this.funcionarioCorrente = null;
		return null;
	}

	public boolean isLogged() {
		return this.funcionarioCorrente != null;
	}

	@Produces
	public Funcionario getFuncionarioCorrente() {
		return funcionarioCorrente;
	}
}
