package br.com.tecinfo.boundary.jsf;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Instance;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@SessionScoped
public class LoginBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	@RequestScoped
	private Instance<FacesContext> facesContext;

	private boolean logged=false;

	@Inject
	Credenciais credenciais;

	public String login() {
		if (credenciais.getUsuario().equals("admin")) {
			if (credenciais.getSenha().equals("admin")) {
				logged = true;
			} else {
				facesContext.get().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_WARN, "Falha de autenticação", "senha inválida"));
			}
		} else {
			facesContext.get().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_WARN, "Falha de autenticação", "usuario inválido"));
		}
		return logged ? "home.xhtml?faces-redirect=true" : null;
	}
	
	public String logout(){
		this.logged=false;
		return null;
	}

	public boolean isLogged() {
		return logged;
	}

}
