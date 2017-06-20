package br.com.tecinfo.entity;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;

@Embeddable
public class Endereco implements Serializable {

    private String logradouro;
    private String numero;
    private String cep;

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Endereco endereco = (Endereco) o;
        return Objects.equals(logradouro, endereco.logradouro) &&
                Objects.equals(numero, endereco.numero) &&
                Objects.equals(cep, endereco.cep);
    }

    @Override
    public int hashCode() {
        return Objects.hash(logradouro, numero, cep);
    }

    @Override
    public String toString() {
        return Optional.ofNullable(logradouro).orElse("") +
                (null != numero ? ", " + numero : "") +
                (null != cep ? ", CEP: " + cep : "");
    }
}
