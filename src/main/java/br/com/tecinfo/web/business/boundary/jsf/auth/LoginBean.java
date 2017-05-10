package br.com.tecinfo.web.business.boundary.jsf.auth;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Produces;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.tecinfo.web.business.controller.auth.Authenticator;
import br.com.tecinfo.web.business.entities.auth.User;
import br.com.tecinfo.web.business.exception.BusinessException;
import br.com.tecinfo.web.business.exception.InfrastructureException;

@Named
@SessionScoped
public class LoginBean implements Serializable {
	private static final long serialVersionUID = 1L;

	private static final String SUCCESS_MESSAGE = "Welcome";

	private User currentUser;
	private boolean renderedLoggedIn = false;

	@Inject
	private Authenticator authenticator;

	@Inject
	private Credentials credentials;

	public String login() {
		try {
			currentUser = authenticator.authenticate(credentials.getUsername(),credentials.getPassword());
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(SUCCESS_MESSAGE));
			return "home.xhtml";
		} catch (BusinessException e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_WARN, e.getMessage(), e.getMessage()));
			return null; 
		} catch (InfrastructureException e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_WARN, e.getMessage(), e.getMessage()));
			return null;
		}
	}
	
	public boolean isRenderedLoggedIn() {
		if (currentUser != null) {
			return renderedLoggedIn;
		} else {
			return false;
		}
	}

	public void renderLoggedIn() {
		this.renderedLoggedIn = true;
	}

	@Produces
	@Named
	public User getCurrentUser() {
		return currentUser;
	}
}