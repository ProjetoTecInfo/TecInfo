package br.com.tecinfo.boundary.jsf;

import br.com.tecinfo.controller.GerenciadorDeClientes;
import br.com.tecinfo.controller.GerenciadorDeClientes.FiltroCliente;
import br.com.tecinfo.entity.Cliente;
import br.com.tecinfo.entity.Endereco;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Model;
import javax.inject.Inject;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SessionScoped
@Model
public class ClientesMBean implements Serializable {

    private static final long serialVersionUID = 1L;

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

    @Inject
    private GerenciadorDeClientes g;

    @Inject
    private ViewManager viewManager;

    private FiltroCliente filtro = new FiltroCliente();

    public FiltroCliente getFiltro() {
        if (filtro == null) {
            filtro = new FiltroCliente();
        }
        return filtro;
    }

    public void setFiltro(FiltroCliente filtro) {

        this.filtro = filtro;
    }

    private List<Cliente> clientesEncontrados = new ArrayList<>();

    public void procurar() {
        this.clientesEncontrados = this.g.filtrarPor(this.filtro);
    }


    public List<Cliente> getClientesEncontrados() {
        if (this.clientesEncontrados == null)
            this.clientesEncontrados = new ArrayList<>();
        return clientesEncontrados;
    }

    public List<Map<String, Serializable>> getFiltrosSuportados() {
        return FILTROS;
    }

    private Cliente cliente;
    private String email;
    private Endereco endereco;

    public Endereco getEndereco() {
        if (endereco == null)
            endereco = new Endereco();
        return endereco;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void novoCliente() {
        this.cliente = new Cliente();
//        this.viewManager.irParaPagina("/clientes/add.xhtml", false);
    }

    public void adicionarCliente() {
        this.g.criar(this.cliente);
        this.g.salvar();
        this.cliente = this.g.getCorrente();
    }


    public void cancelarEdicao() {
        atualizarCliente();
        this.email = null;
        this.endereco = null;
    }

    public void atualizarCliente() {
        this.g.atualizar();
        this.email = null;
        this.endereco=null;
        this.cliente = this.g.getCorrente();
    }

    public void salvarCliente() {
        this.g.salvar();
        this.abrirCliente(this.cliente.getCodigo());
    }

    public void removerCliente() {
        this.g.removerCorrente();
        this.g.salvar();
        this.viewManager.exibirMensagens(null,
                this.viewManager.facesMessageBuilder().asInfo().summary(
                        String.format("Cliente %s removido com sucesso!", this.cliente)).build());
        this.cliente = null;
        this.procurar();
    }

    public void abrirCliente(Long codigo) {
        this.cliente = this.g.getCliente(codigo);
    }

    public void adicionarEmail() {
        this.cliente.adicionarEmail(this.email);
        this.email = null;
    }

    public void removerEmail(String email) {
        this.cliente.removeEmail(email);
        this.email = email;
    }

    public void adicionarEndereco(){
        this.cliente.adicionarEndereco(this.endereco);
        this.endereco=null;
    }

    public void removerEndereco(Endereco endereco){
        this.cliente.removerEndereco(endereco);
        this.endereco=endereco;
    }
}
