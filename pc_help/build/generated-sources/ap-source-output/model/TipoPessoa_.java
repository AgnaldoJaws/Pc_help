package model;

import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import model.Cliente;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-01-12T04:25:48")
@StaticMetamodel(TipoPessoa.class)
public class TipoPessoa_ { 

    public static volatile SingularAttribute<TipoPessoa, String> tipoPessoa;
    public static volatile SingularAttribute<TipoPessoa, Integer> idTipoPessoa;
    public static volatile CollectionAttribute<TipoPessoa, Cliente> clienteCollection;

}