package model;

import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import model.Cliente;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-01-12T04:25:48")
@StaticMetamodel(Cidade.class)
public class Cidade_ { 

    public static volatile SingularAttribute<Cidade, String> nomeCidade;
    public static volatile SingularAttribute<Cidade, Integer> idCidade;
    public static volatile CollectionAttribute<Cidade, Cliente> clienteCollection;

}