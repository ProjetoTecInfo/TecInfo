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
public class ViewUtil implements ViewManager {

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

    @Override
    public boolean isLogged() {
        return this.loginBean.isLogged();
    }

    @Override
    public void irParaPagina(String page){
        irParaPagina(page,true);
    }

    public String navegar(Object page){
        return String.valueOf(page);
    }

    @Override
    public void irParaPagina(String page, boolean redirect) {
        ConfigurableNavigationHandler nav = (ConfigurableNavigationHandler) facesContext.getApplication()
                .getNavigationHandler();
        nav.performNavigation(page + (redirect ? "?faces-redirect=true" : ""));
    }

    @Override
    public boolean temPapel(String papel) {
        return loginBean.getFuncionarioCorrente().temPapel(papel);
    }

    @Override
    public boolean temAlgumDessesPapeis(String... papeis) {
        return loginBean.getFuncionarioCorrente().temAlgumDessesPapeis(papeis);
    }

    @Override
    public void exibirMensagens(String clientId, FacesMessage... messages) {
        for (FacesMessage message : messages) {
            facesContext.addMessage(clientId, message);
        }
    }

    @Override
    public FacesMessageBuilder facesMessageBuilder() {
        return FacesMessageBuilder.builder();
    }
}
