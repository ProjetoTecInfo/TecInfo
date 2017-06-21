package br.com.tecinfo.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "funcionarios")
@NamedQueries({
        @NamedQuery(name = Funcionario.ListarTodos, query = "select f from Funcionario f where f.usuario <> 'admin' ")
})
public class Funcionario extends SuportaValidacoes implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final String ListarTodos = "Funcionario.listarTodos";

    private static final String SENHA_PADRAO = "tecinfo";

    @Id
    @GeneratedValue
    private Long codigo;

    private String usuario;

    private String senha = SENHA_PADRAO;

    private String nome;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "funcionarios_papeis", joinColumns = @JoinColumn(name = "func"))
    @Column(name = "papel")
    private Set<String> papeis = new LinkedHashSet<>();

    public Funcionario adicionarPapel(String papel) {
        naoPodeEstarEmBranco("Papel informado está inválido", papel);
        this.papeis.add(papel.toUpperCase());
        return this;
    }

    public Funcionario removerPapel(String papel) {
        naoPodeEstarEmBranco("Papel informado está inválido", papel);
        this.papeis.remove(papel.toUpperCase());
        return this;
    }

    public Long getCodigo() {
        return codigo;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        naoPodeEstarEmBranco("Usuário inválido", usuario);
        this.usuario = usuario;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        naoPodeEstarEmBranco("Senha inválida", senha);
        this.nome = nome;
        this.senha = senha;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        naoPodeEstarEmBranco("Nome inválido", nome);
        this.nome = nome;
        if (estahEmBranco(this.usuario)) {
            redefinirUsuarioPadrao();
        }
    }

    public Funcionario redefinirUsuarioPadrao() {
        String[] nomes = this.getNome().replaceAll(" de ", " ").split(" ");
        this.usuario = "";
        for (int i = 0; i < (nomes.length - 1); i++) {
            if (!nomes[i].trim().isEmpty()) {
                this.usuario = (this.usuario + nomes[i].charAt(0)).toLowerCase();
            }
        }
        this.usuario = this.usuario.concat(nomes[nomes.length - 1]);
        this.usuario = this.usuario.toLowerCase();
        return this;
    }

    public String[] getPapeis() {
        return papeis.toArray(new String[0]);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((codigo == null) ? 0 : codigo.hashCode());
        result = prime * result + ((usuario == null) ? 0 : usuario.hashCode());
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
        Funcionario other = (Funcionario) obj;
        if (codigo == null) {
            if (other.codigo != null)
                return false;
        } else if (!codigo.equals(other.codigo))
            return false;
        if (usuario == null) {
            if (other.usuario != null)
                return false;
        } else if (!usuario.equals(other.usuario))
            return false;
        return true;
    }

    public boolean temAlgumDessesPapeis(String... papeis) {
        if (papeis == null)
            return false;
        for (String p : papeis) {
            if (temPapel(p))
                return true;
        }
        return false;
    }

    public boolean temTodosEssesPapeis(String... papeis) {
        if (papeis == null)
            return false;
        boolean verificou = false;
        for (String p : papeis) {
            verificou = true;
            if (!temPapel(p))
                return false;
        }
        return verificou ? true : false;
    }

    public boolean temPapel(String papel) {
        if (this.getCodigo() != null) {
            if ("admin".equalsIgnoreCase(this.usuario)) {
                return true;
            } else {
                for (String p : this.papeis) {
                    if (p.equalsIgnoreCase(papel))
                        return true;
                }
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return getNome() + "<" + getUsuario() + ">";
    }

    public static TypedQuery<Funcionario> pesquisarPorUsuario(EntityManager em, String usuario) {
        TypedQuery<Funcionario> query = em.createQuery(
                "select f from " + Funcionario.class.getSimpleName() + " f where f.usuario = ?0 ", Funcionario.class);
        query.setParameter(0, usuario);
        return query;
    }

}
