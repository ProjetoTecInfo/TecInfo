package br.com.tecinfo.entity;

/**
 * Created by marruda on 20/06/2017.
 */
public abstract class SuportaValidacoes {

    protected boolean estahNulo(Object valor) {
        return valor == null;
    }

    protected boolean estahEmBranco(String valor) {
        if (estahNulo(valor))
            return true;
        return valor.trim().isEmpty();
    }


    protected void naoPodeEstarEmBranco(String mensagem, String valor) {
        if (estahEmBranco(valor)) {
            if (estahEmBranco(mensagem)) {
                throw new IllegalArgumentException("valor inválido");
            } else {
                throw new IllegalArgumentException(mensagem);
            }
        }
    }
}
