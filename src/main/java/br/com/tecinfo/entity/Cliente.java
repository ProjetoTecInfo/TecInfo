package br.com.tecinfo.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "clientes")
@NamedQueries({@NamedQuery(name = Cliente.ListarTodos, query = "select c from Cliente c"),
        @NamedQuery(name = Cliente.PesquisarPorCodigo, query = "select c from Cliente c where c.codigo = ?1"),
        @NamedQuery(name = Cliente.PesquisarPorNome, query = "select c from Cliente c where lower (c.nome) like lower (  '%' || ?1 || '%') ")})
public class Cliente implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final String ListarTodos = "Cliente.listarTodos";
    public static final String PesquisarPorCodigo = "Cliente.pesquisarPorCodigo";
    public static final String PesquisarPorNome = "Cliente.pesquisarPorNome";

    @Id
    @GeneratedValue
    private Long codigo;

    private String nome;

    @ElementCollection
    @CollectionTable(name = "clientes_emails", joinColumns = @JoinColumn(name = "cliente"))
    @Column(name = "email")
    private List<String> emails = new ArrayList<>();

    public Cliente() {
        // requerido pelo JPA
    }

    public Cliente(Number codigo, String nome) {
        super();
        this.codigo = codigo.longValue();
        this.nome = nome;
    }

    public Long getCodigo() {
        return codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String[] getEmails() {
        return emails.toArray(new String[0]);
    }

    public Cliente adicionarEmail(String email) {
        if (email != null) {
            for (String e : this.emails) {
                if (e.equalsIgnoreCase(email))
                    return this;
            }
            this.emails.add(email.toLowerCase());
        }
        return this;
    }

    public Cliente removeTodosEmails() {
        this.emails.clear();
        return this;
    }

    public Cliente removeEmail(String email) {
        if (email != null) {
            Iterator<String> it = this.emails.iterator();
            while (it.hasNext()) {
                if (it.next().equalsIgnoreCase(email)) {
                    it.remove();
                }
            }
        }
        return this;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((codigo == null) ? 0 : codigo.hashCode());
        result = prime * result + ((nome == null) ? 0 : nome.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Cliente other = (Cliente) obj;
        if (codigo == null) {
            if (other.codigo != null)
                return false;
        } else if (!codigo.equals(other.codigo))
            return false;
        if (nome == null) {
            if (other.nome != null)
                return false;
        } else if (!nome.equals(other.nome))
            return false;
        return true;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        if (getCodigo() == null)
            sb.append(getNome());
        else {
            sb.append(String.format("%s - %s", getCodigo(), getNome()));
        }
        return sb.toString();
    }
}