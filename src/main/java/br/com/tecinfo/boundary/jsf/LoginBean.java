package br.com.tecinfo.boundary.jsf;

import br.com.tecinfo.controller.Authenticator;
import br.com.tecinfo.entities.User;
import java.io.Serializable;
import javax.ejb.EJBException;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Produces;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import org.primefaces.context.RequestContext;

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

    public void login(ActionEvent event) {
        RequestContext context = RequestContext.getCurrentInstance();
        FacesMessage message = null;
        boolean loggedIn = false;
        try {
            currentUser = authenticator.authenticate(credentials.getUsername(), credentials.getPassword());
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Welcome", currentUser.getUsername());
            loggedIn = true;
        } catch (EJBException e) {
            if (e.getCausedByException() instanceof NoResultException) {
                message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Loggin Error", "Invalid credentials");
            } else {
                message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Loggin Error", "Unexpected exception: " + e.getMessage());
            }
        }

        FacesContext.getCurrentInstance().addMessage(null, message);
        context.addCallbackParam("loggedIn", loggedIn);
    }

    public String ldogin() {
        try {
            currentUser = authenticator.authenticate(credentials.getUsername(), credentials.getPassword());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(SUCCESS_MESSAGE));
            return "home.xhtml";
        } catch (NoResultException e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN, e.getMessage(), e.getMessage()));
            return null;
        } catch (PersistenceException e) {
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
