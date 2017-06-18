package br.com.tecinfo.boundary.jsf;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Model;
import javax.faces.application.ConfigurableNavigationHandler;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.inject.Inject;

@RequestScoped
@Model
public class ViewSecurity {

	@Inject
	private FacesContext facesContext;

	@Inject
	@SessionScoped	
	private LoginBean loginBean;

	public void isAuthenticated(ComponentSystemEvent event) {

		if (!isLogged()) {

			ConfigurableNavigationHandler nav = (ConfigurableNavigationHandler) facesContext.getApplication()
					.getNavigationHandler();

			nav.performNavigation("login.xhtml?faces-redirect=true");

		}
	}

	public boolean isLogged() {
		return this.loginBean.isLogged();
	}
}
