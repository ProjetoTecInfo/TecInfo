package br.com.tecinfo.boundary.jsf;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.tecinfo.controller.Clientes;
import br.com.tecinfo.controller.Clientes.FiltroCliente;
import br.com.tecinfo.entity.Cliente;

@RequestScoped
@Model
public class ClientesMBean implements Serializable {

	public static final Logger logger = LoggerFactory.getLogger(ClientesMBean.class);

	private static final List<Map<String, Serializable>> FILTROS;
	static {

		FILTROS = new ArrayList<>(2);
		HashMap<String, Serializable> map = new HashMap<>();
		FILTROS.add(map);
		map.put("descricao", "Por Codigo");
		map.put("valor", FiltroCliente.TipoDeFiltro.POR_CODIGO);

		FILTROS.add(map = new HashMap<>());
		map.put("descricao", "Por Nome");
		map.put("valor", FiltroCliente.TipoDeFiltro.POR_NOME);
	}

	private static final long serialVersionUID = 1L;

	@Inject
	private Clientes clientes;

	private FiltroCliente filtro = new FiltroCliente();

	private List<Cliente> clientesEncontrados;

	public void procurar() {
		this.clientesEncontrados = this.clientes.procurar(this.filtro);
	}

	public List<Cliente> getClientesEncontrados() {
		return clientesEncontrados;
	}

	@Produces
	@Model
	public List<Map<String, Serializable>> getFiltrosSuportados() {
		return FILTROS;
	}
}
