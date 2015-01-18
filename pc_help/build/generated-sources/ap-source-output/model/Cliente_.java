package model;

import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import model.Cidade;
import model.Maquina;
import model.Os;
import model.TipoPessoa;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-01-12T04:25:48")
@StaticMetamodel(Cliente.class)
public class Cliente_ { 

    public static volatile SingularAttribute<Cliente, String> estado;
    public static volatile SingularAttribute<Cliente, String> telefone;
    public static volatile SingularAttribute<Cliente, String> endereco;
    public static volatile SingularAttribute<Cliente, String> bairro;
    public static volatile CollectionAttribute<Cliente, Os> osCollection;
    public static volatile SingularAttribute<Cliente, Cidade> idCidade;
    public static volatile SingularAttribute<Cliente, String> cnpj;
    public static volatile SingularAttribute<Cliente, TipoPessoa> idTipoPessoa;
    public static volatile SingularAttribute<Cliente, String> cep;
    public static volatile SingularAttribute<Cliente, String> skype;
    public static volatile SingularAttribute<Cliente, Integer> codCli;
    public static volatile SingularAttribute<Cliente, String> complemento;
    public static volatile CollectionAttribute<Cliente, Maquina> maquinaCollection;
    public static volatile SingularAttribute<Cliente, String> nomeEmpresa;
    public static volatile SingularAttribute<Cliente, String> rg;
    public static volatile SingularAttribute<Cliente, String> logradouro;
    public static volatile SingularAttribute<Cliente, String> cpf;
    public static volatile SingularAttribute<Cliente, String> celular;
    public static volatile SingularAttribute<Cliente, String> nomeCliente;
    public static volatile SingularAttribute<Cliente, String> ie;
    public static volatile SingularAttribute<Cliente, String> razaoSocial;
    public static volatile SingularAttribute<Cliente, String> email;

}