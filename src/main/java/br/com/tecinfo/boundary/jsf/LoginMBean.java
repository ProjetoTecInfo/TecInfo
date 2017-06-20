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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Named
@SessionScoped
public class LoginMBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private static Logger logger= LoggerFactory.getLogger(LoginMBean.class);

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

    public void login() {
        logger.info("realizando a autencação do usuário {}...",usuario);
        this.funcionarioCorrente = autenticador.autenticar(usuario, senha);
        if (this.funcionarioCorrente == null) {
            logger.info("usuário {} é não pode ser autenticado: usuário ou senha inválidos",usuario);
            facesContext.get().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN, "Falha de autenticação", "usuário ou senha inválidos"));
        }
        logger.info("usuário {} autenticado com sucesso!",usuario);
    }

    public void logout() {

        logger.info("efetuando o logoff do usuário {}...",this.funcionarioCorrente.getUsuario());
        this.funcionarioCorrente = null;
    }

    public boolean isLogged() {
        return this.funcionarioCorrente != null;
    }

    @Produces
    public Funcionario getFuncionarioCorrente() {
        return funcionarioCorrente;
    }
}
