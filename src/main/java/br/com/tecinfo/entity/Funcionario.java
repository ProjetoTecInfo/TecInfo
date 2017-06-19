package br.com.tecinfo.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.persistence.TypedQuery;

@Entity
@Table(name = "funcionarios")
public class Funcionario implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Long codigo;

	@Column(unique = true, nullable = false)
	private String usuario;

	@Column(nullable = false)
	private String senha;

	@Column(nullable = false)
	private String nome;

	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "funcionarios_papeis", joinColumns = @JoinColumn(name = "func"))
	@Column(name = "papel")
	private List<String> papeis = new ArrayList<>();

	public Long getCodigo() {
		return codigo;
	}

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

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public List<String> getPapeis() {
		return papeis;
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

	public static TypedQuery<Funcionario> pesquisarPorUsuario(EntityManager em, String usuario) {
		TypedQuery<Funcionario> query = em.createQuery(
				"select f from " + Funcionario.class.getSimpleName() + " f where f.usuario = ?0 ", Funcionario.class);
		query.setParameter(0, usuario);
		return query;
	}

}
