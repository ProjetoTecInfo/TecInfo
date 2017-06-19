package br.com.tecinfo.boundary.jsf;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Model;
import javax.faces.application.ConfigurableNavigationHandler;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.inject.Inject;

@RequestScoped
@Model
public class ViewUtil {

	@Inject
	private FacesContext facesContext;

	@Inject
	@SessionScoped
	private LoginMBean loginBean;

	public void isAuthenticated(ComponentSystemEvent event) {

		if (!isLogged()) {
			irParaPagina("/login.xhtml", true);
		}
	}

	public boolean isLogged() {
		return this.loginBean.isLogged();
	}

	public void irParaPagina(String page, boolean redirect) {
		ConfigurableNavigationHandler nav = (ConfigurableNavigationHandler) facesContext.getApplication()
				.getNavigationHandler();
		nav.performNavigation(page + (redirect ? "?faces-redirect=true" : ""));
	}

	public boolean temPapel(String papel) {
		return loginBean.getFuncionarioCorrente().temPapel(papel);
	}

	public boolean temAlgumDessesPapeis(String... papeis) {
		return loginBean.getFuncionarioCorrente().temAlgumDessesPapeis(papeis);
	}

	public void exibirMensagens(String clientId, FacesMessage... messages) {
		for (FacesMessage message : messages) {
			facesContext.addMessage(clientId, message);
		}
	}
}
