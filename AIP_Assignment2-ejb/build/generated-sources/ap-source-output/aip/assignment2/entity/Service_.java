package aip.assignment2.entity;

import aip.assignment2.entity.Subscription;
import aip.assignment2.utility.SERVICETYPE;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-11-06T01:37:08")
@StaticMetamodel(Service.class)
public class Service_ { 

    public static volatile SingularAttribute<Service, SERVICETYPE> name;
    public static volatile SingularAttribute<Service, Integer> id;
    public static volatile ListAttribute<Service, Subscription> subscription;

}