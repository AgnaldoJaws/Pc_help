package model;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import model.Os;
import model.Pagamento;
import model.Status;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-01-12T04:25:48")
@StaticMetamodel(Solucao.class)
public class Solucao_ { 

    public static volatile SingularAttribute<Solucao, Integer> codSol;
    public static volatile SingularAttribute<Solucao, String> tempGarantia;
    public static volatile SingularAttribute<Solucao, String> modelo;
    public static volatile SingularAttribute<Solucao, Status> idStatus;
    public static volatile SingularAttribute<Solucao, String> marca;
    public static volatile SingularAttribute<Solucao, String> solucao;
    public static volatile SingularAttribute<Solucao, Date> dataGarantia;
    public static volatile SingularAttribute<Solucao, String> pecaTrocada;
    public static volatile SingularAttribute<Solucao, Date> dataBertura;
    public static volatile SingularAttribute<Solucao, Os> codOs;
    public static volatile SingularAttribute<Solucao, String> hd;
    public static volatile CollectionAttribute<Solucao, Pagamento> pagamentoCollection;
    public static volatile SingularAttribute<Solucao, String> ram;

}