package br.com.tecinfo.boundary.jsf;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import java.io.Serializable;

/**
 * Created by marruda on 19/06/2017.
 */
public interface ViewManager extends Serializable {


    boolean isLogged();

    void irParaPagina(String page);

    void irParaPagina(String page, boolean redirect);

    boolean temPapel(String papel);

    boolean temAlgumDessesPapeis(String... papeis);

    void exibirMensagens(String clientId, FacesMessage... messages);

    FacesMessageBuilder facesMessageBuilder();

    public static class FacesMessageBuilder {

        private Severity severity;
        private String summary;
        private String detail;

        static FacesMessageBuilder builder() {
            return new FacesMessageBuilder();
        }

        FacesMessageBuilder() {

        }

        public FacesMessageBuilder asInfo() {
            this.severity = FacesMessage.SEVERITY_INFO;
            return this;
        }

        public FacesMessageBuilder asError() {
            this.severity = FacesMessage.SEVERITY_ERROR;
            return this;
        }

        public FacesMessageBuilder asWarn() {
            this.severity = FacesMessage.SEVERITY_WARN;
            return this;
        }

        public FacesMessageBuilder asFatal() {
            this.severity = FacesMessage.SEVERITY_FATAL;
            return this;
        }


        public FacesMessageBuilder summary(String summary) {
            this.summary = summary;
            return this;
        }

        public FacesMessageBuilder detail(String detail) {
            this.detail = detail;
            return this;
        }

        public FacesMessage build() {
            FacesMessage fm = new FacesMessage(severity, summary, detail);
            return fm;
        }
    }
}
