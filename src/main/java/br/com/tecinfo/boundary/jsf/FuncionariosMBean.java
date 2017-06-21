package br.com.tecinfo.boundary.jsf;

import br.com.tecinfo.controller.GerenciadorDeFuncionarios;
import br.com.tecinfo.entity.Funcionario;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Model;
import javax.inject.Inject;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

@SessionScoped
@Model
public class FuncionariosMBean implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final Logger logger = LoggerFactory.getLogger(FuncionariosMBean.class);


    private final static Map<String,String> PAPEIS_SUPORTADOS_POR_LABEL;

    private final static Map<String,String> PAPEIS_SUPORTADOS_POR_VALOR;

    static {
        PAPEIS_SUPORTADOS_POR_LABEL=new LinkedHashMap<>();
        PAPEIS_SUPORTADOS_POR_LABEL.put("Administrador","ADMINISTRADOR");
        PAPEIS_SUPORTADOS_POR_LABEL.put("Atendente","ATENDENTE");
        PAPEIS_SUPORTADOS_POR_LABEL.put("Técnico","TECNICO");

        PAPEIS_SUPORTADOS_POR_VALOR=new LinkedHashMap<>();
        for(Entry<String,String>e:PAPEIS_SUPORTADOS_POR_LABEL.entrySet()){
            PAPEIS_SUPORTADOS_POR_VALOR.put(e.getValue(),e.getKey());
        }
    }

    @Inject
    private GerenciadorDeFuncionarios g;

    @Inject
    private ViewManager viewManager;

    private List<Funcionario> funcionariosEncontrados = new ArrayList<>();

    public void listar() {
        this.funcionariosEncontrados = this.g.listarTodos();
    }

    public String mostrarLabelDoPapel(String value){
        return PAPEIS_SUPORTADOS_POR_VALOR.get(value);
    }

    public List<Funcionario> getFuncionariosEncontrados() {
        if (this.funcionariosEncontrados == null)
            this.funcionariosEncontrados = new ArrayList<>();
        return funcionariosEncontrados;
    }

    public List<Map<String,String>> getPapeisSuportados(){
        List<Map<String,String>> result=new ArrayList<>();
        for(Entry<String,String> entry:PAPEIS_SUPORTADOS_POR_LABEL.entrySet()){
            Map<String,String> papel=new LinkedHashMap<>();
            papel.put("label",entry.getKey());
            papel.put("value",entry.getValue());
            result.add(papel);
        }
        return result;
    }

    private Funcionario funcionario;

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public void novoFuncionario() {
        this.funcionario = new Funcionario();
    }

    public void adicionarFuncionario() {
        this.g.criar(this.funcionario);
        this.g.salvar();
        this.funcionario = this.g.getCorrente();
    }


    public void cancelarEdicao() {
        atualizar();
    }

    public void atualizar() {
        this.g.atualizar();
        this.funcionario = this.g.getCorrente();
    }

    public void salvar() {
        this.g.salvar();
        this.abrir(this.funcionario.getCodigo());
    }

    public void remover() {
        this.g.removerCorrente();
        this.g.salvar();
        this.viewManager.exibirMensagens(null,
                this.viewManager.facesMessageBuilder().asInfo().summary(
                        String.format("Cliente %s removido com sucesso!", this.funcionario)).build());
        this.funcionario = null;
        this.listar();
    }

    public void abrir(Long codigo) {
        this.funcionario = this.g.getFuncionario(codigo);
    }

}
