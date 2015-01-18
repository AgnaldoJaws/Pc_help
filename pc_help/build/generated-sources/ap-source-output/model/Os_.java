package model;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import model.Cliente;
import model.Solucao;
import model.Status;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-01-12T04:25:48")
@StaticMetamodel(Os.class)
public class Os_ { 

    public static volatile SingularAttribute<Os, Status> idStatus;
    public static volatile SingularAttribute<Os, String> obs;
    public static volatile SingularAttribute<Os, Cliente> codCli;
    public static volatile CollectionAttribute<Os, Solucao> solucaoCollection;
    public static volatile SingularAttribute<Os, String> problema;
    public static volatile SingularAttribute<Os, Integer> codOs;
    public static volatile SingularAttribute<Os, Date> dataAbertura;

}