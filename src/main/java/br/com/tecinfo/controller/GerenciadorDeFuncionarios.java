package br.com.tecinfo.controller;

import br.com.tecinfo.entity.Funcionario;

import java.io.Serializable;
import java.util.List;

public interface GerenciadorDeFuncionarios extends Serializable {

    List<Funcionario> listarTodos();

    public Funcionario getFuncionario(Long codigo);

    public Funcionario getCorrente();

    public void criar(Funcionario funcionario);

    public void salvar();

    public void atualizar();

    public void remover(Long codigo);

    public void removerCorrente();

    public void fechar();

}
