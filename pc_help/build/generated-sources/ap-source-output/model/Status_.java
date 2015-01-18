package model;

import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import model.Os;
import model.Solucao;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-01-12T04:25:48")
@StaticMetamodel(Status.class)
public class Status_ { 

    public static volatile SingularAttribute<Status, Integer> idStatus;
    public static volatile CollectionAttribute<Status, Solucao> solucaoCollection;
    public static volatile CollectionAttribute<Status, Os> osCollection;
    public static volatile SingularAttribute<Status, String> status;

}