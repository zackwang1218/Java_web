package aip.assignment2.entity;

import aip.assignment2.entity.Account;
import aip.assignment2.entity.Service;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-11-06T01:37:08")
@StaticMetamodel(Subscription.class)
public class Subscription_ { 

    public static volatile SingularAttribute<Subscription, Date> end_date;
    public static volatile SingularAttribute<Subscription, Service> service;
    public static volatile SingularAttribute<Subscription, Integer> id;
    public static volatile SingularAttribute<Subscription, Account> user;
    public static volatile SingularAttribute<Subscription, Boolean> isactivated;
    public static volatile SingularAttribute<Subscription, Date> start_date;

}