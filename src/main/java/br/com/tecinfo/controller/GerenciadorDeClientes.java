package br.com.tecinfo.controller;

import br.com.tecinfo.entity.Cliente;

import java.io.Serializable;
import java.util.List;

public interface GerenciadorDeClientes extends Serializable {

    public static class FiltroCliente implements Serializable {

        private static final long serialVersionUID = 1L;

        public static enum TipoDeFiltro {
            POR_CODIGO {
                @Override
                List<Cliente> filtrar(GerenciadorDeClientes gerenciadorDeClientes, FiltroCliente filtroCliente) {
                    return gerenciadorDeClientes.pesquisarPorCodigo(filtroCliente.getCodigoRef());
                }
            }, POR_NOME {
                @Override
                List<Cliente> filtrar(GerenciadorDeClientes gerenciadorDeClientes, FiltroCliente filtroCliente) {
                    return gerenciadorDeClientes.pesquisarPorNome(filtroCliente.getNomeRef());
                }
            };

            abstract List<Cliente> filtrar(GerenciadorDeClientes gerenciadorDeClientes, FiltroCliente filtroCliente);
        }

        private TipoDeFiltro tipoDeFiltro = TipoDeFiltro.POR_NOME;
        private Long codigoRef;
        private String nomeRef;

        public TipoDeFiltro getTipoDeFiltro() {

            return tipoDeFiltro;
        }

        public void setTipoDeFiltro(TipoDeFiltro tipoDeFiltro) {

            this.tipoDeFiltro = tipoDeFiltro;
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

    }

    List<Cliente> filtrarPor(FiltroCliente fitro);

    List<Cliente> pesquisarPorCodigo(Long codigo);

    List<Cliente> pesquisarPorNome(String nome);

    List<Cliente> listarTodos();

    public Cliente getCliente(Long codigo);

    public Cliente getCorrente();

    public void criar(Cliente cliente);

    public void salvar();

    public void atualizar();

    public void remover(Long codigo);

    public void removerCorrente();

    public void fechar();

}
