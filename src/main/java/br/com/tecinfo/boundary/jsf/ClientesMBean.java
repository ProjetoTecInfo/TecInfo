package br.com.tecinfo.boundary.jsf;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Model;
import javax.faces.application.FacesMessage;
import javax.inject.Inject;

import org.hibernate.validator.constraints.Email;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.tecinfo.controller.GerenciadorDeClientes;
import br.com.tecinfo.entity.Cliente;

@SessionScoped
@Model
public class ClientesMBean implements Serializable {

	private static final String FILTRAR_POR_CODIGO = "Por código";

	private static final String FILTRAR_POR_NOME = "Por nome";

	public static final Logger logger = LoggerFactory.getLogger(ClientesMBean.class);

	private static final String[] FILTROS = new String[] { FILTRAR_POR_NOME, FILTRAR_POR_CODIGO };

	private static final long serialVersionUID = 1L;

	private String tipoFiltro = FILTROS[0];

	private Long codigoRef;

	private String nomeRef;

	private List<Cliente> clientesEncontrados;

	private Cliente cliente;

	private String novoEmail;

	@Inject
	private ViewUtil viewUtil;

	@Inject
	private GerenciadorDeClientes gerenciadoDeClientes;

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public List<Cliente> getClientesEncontrados() {
		if (clientesEncontrados == null)
			clientesEncontrados = new ArrayList<>();
		return clientesEncontrados;
	}

	public String[] getFiltrosSuportados() {
		return FILTROS;
	}

	public String getTipoFiltro() {
		return tipoFiltro;
	}

	public void setTipoFiltro(String tipoFiltro) {
		this.tipoFiltro = tipoFiltro;
	}

	public Long getCodigoRef() {
		return codigoRef;
	}

	public void setCodigoRef(Long codigoRef) {
		this.codigoRef = codigoRef;
	}

	public String getNomeRef() {
		return nomeRef;
	}

	public void setNomeRef(String nomeRef) {
		this.nomeRef = nomeRef;
	}

	public String getNovoEmail() {
		return novoEmail;
	}

	public void setNovoEmail(String novoEmail) {
		this.novoEmail = novoEmail;
	}

	public void adicionarEmail() {
		if (this.novoEmail != null && !this.novoEmail.isEmpty()) {
			this.cliente = gerenciadoDeClientes.adicionarEmailAoCliente(this.cliente, this.novoEmail);
			this.novoEmail = null;
		}
	}

	public void removerEmail(String email) {
		this.cliente = gerenciadoDeClientes.removerEmailAoCliente(this.cliente, email);
		this.novoEmail = email;
	}

	// actions

	public void pesquisar() {
		this.clientesEncontrados = new ArrayList<>();
		if (FILTRAR_POR_CODIGO.equals(this.tipoFiltro))
			if (this.codigoRef == null)
				this.clientesEncontrados = gerenciadoDeClientes.listarTodos();
			else
				this.clientesEncontrados = gerenciadoDeClientes.pesquisarPorCodigo(this.codigoRef);
		else if (FILTRAR_POR_NOME.equals(this.tipoFiltro))
			if (this.nomeRef == null || this.nomeRef.isEmpty())
				this.clientesEncontrados = gerenciadoDeClientes.listarTodos();
			else
				this.clientesEncontrados = gerenciadoDeClientes.pesquisarPorNome(this.nomeRef);

	}

	public void remover(Cliente cliente) {
		logger.info("remover o cliente {}", cliente);
		gerenciadoDeClientes.remover(cliente);
		pesquisar();
	}

	public void abrirPesquisa() {
		pesquisar();
		viewUtil.irParaPagina("/clientes/list.xhtml", true);
	}

	public void abrirCliente(Cliente cliente) {
		this.cliente = gerenciadoDeClientes.pegarClientePorCodigo(cliente.getCodigo());
		if (this.cliente == null) {
			viewUtil.exibirMensagens(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Abrir cliente",
					"Falha ao abrir o cliente solicitado"));

		} else {
			viewUtil.irParaPagina("/clientes/show.xhtml", true);
		}
	}

	public void novoCliente() {
		logger.info("adicionar novo cliente");
		this.cliente = new Cliente();
		viewUtil.irParaPagina("/clientes/add.xhtml", true);
	}

	public void editarCliente(Cliente cliente) {
		this.cliente = gerenciadoDeClientes.pegarClientePorCodigo(cliente.getCodigo());
		if (this.cliente == null) {
			viewUtil.exibirMensagens(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Abrir cliente",
					"Falha ao abrir o cliente solicitado"));
		} else {
			viewUtil.irParaPagina("/clientes/edit.xhtml", true);
		}
	}

	public void salvarCliente() {
		this.cliente = gerenciadoDeClientes.alterar(cliente);
		viewUtil.irParaPagina("/clientes/show.xhtml", true);
	}

	public void adicionarCliente() {
		this.cliente = gerenciadoDeClientes.adicionar(cliente);
		viewUtil.irParaPagina("/clientes/show.xhtml", true);
	}
}
