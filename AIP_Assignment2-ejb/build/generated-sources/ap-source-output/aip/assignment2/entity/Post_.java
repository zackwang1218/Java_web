package aip.assignment2.entity;

import aip.assignment2.entity.Account;
import aip.assignment2.utility.POSTTYPE;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-11-06T01:37:08")
@StaticMetamodel(Post.class)
public class Post_ { 

    public static volatile SingularAttribute<Post, String> subject;
    public static volatile SingularAttribute<Post, POSTTYPE> posttype;
    public static volatile SingularAttribute<Post, Date> postdate;
    public static volatile SingularAttribute<Post, Boolean> issticky;
    public static volatile SingularAttribute<Post, Integer> id;
    public static volatile SingularAttribute<Post, Account> user;
    public static volatile SingularAttribute<Post, String> content;
    public static volatile SingularAttribute<Post, String> email;

}