package aip.assignment2.entity;

import aip.assignment2.entity.Post;
import aip.assignment2.entity.Subscription;
import aip.assignment2.utility.GROUP;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-11-06T01:37:08")
@StaticMetamodel(Account.class)
public class Account_ { 

    public static volatile SingularAttribute<Account, String> lastName;
    public static volatile SingularAttribute<Account, String> firstName;
    public static volatile SingularAttribute<Account, String> password;
    public static volatile SingularAttribute<Account, String> cardtoken;
    public static volatile SingularAttribute<Account, String> gender;
    public static volatile SingularAttribute<Account, String> phonenumber;
    public static volatile SingularAttribute<Account, Integer> id;
    public static volatile ListAttribute<Account, Subscription> subscription;
    public static volatile SingularAttribute<Account, GROUP> groupname;
    public static volatile ListAttribute<Account, Post> posts;
    public static volatile SingularAttribute<Account, String> email;
    public static volatile SingularAttribute<Account, String> username;

}