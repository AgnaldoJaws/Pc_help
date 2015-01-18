package model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import model.Solucao;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-01-12T04:25:48")
@StaticMetamodel(Pagamento.class)
public class Pagamento_ { 

    public static volatile SingularAttribute<Pagamento, Solucao> codSol;
    public static volatile SingularAttribute<Pagamento, Integer> codPag;
    public static volatile SingularAttribute<Pagamento, String> numVezes;
    public static volatile SingularAttribute<Pagamento, String> nomeCartao;
    public static volatile SingularAttribute<Pagamento, String> formPag;

}