package aip.assignment2.entity;

import aip.assignment2.utility.POSTTYPE;
import java.util.*;
import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
public class Post {
    private int id;
    private Account user;
    private POSTTYPE posttype;
    private String subject;
    private String content;
    private String email;
    private Date postdate;
    private boolean issticky;

    /**
     * @return the id
     */
    @Id
    @GeneratedValue
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the posttype
     */
    @NotNull
    @Column(name = "posttype")
    @Enumerated(EnumType.STRING)
    public POSTTYPE getPosttype() {
        return posttype;
    }

    /**
     * @param posttype the posttype to set
     */
    public void setPosttype(POSTTYPE posttype) {
        this.posttype = posttype;
    }

    /**
     * @return the subject
     */
    @NotNull(message = "Subject can not empty")
    @Size(min=5, message = "Subject should be at least 5 characters long")
    @Column(name = "subject")
    public String getSubject() {
        return subject;
    }

    /**
     * @param subject the subject to set
     */
    public void setSubject(String subject) {
        this.subject = subject;
    }

    /**
     * @return the content
     */
    @NotNull(message = "Content can not empty")
    @Size(min=15, message="Content should be at least 15 characters long")
    @Column(name = "content")
    @Lob
    //@("")
    public String getContent() {
        return content;
    }

    /**
     * @param content the content to set
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * @return the postdate
     */
    @Column(name = "postdate")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getPostdate() {
        return postdate;
    }

    /**
     * @param postdate the postdate to set
     */
    public void setPostdate(Date postdate) {
        this.postdate = postdate;
    }
    
    /**
     * @return the email
     */
    @Column(name="email")
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the issticky
     */
    @Column(name = "issticky")
    public boolean isIssticky() {
        return issticky;
    }

    /**
     * @param issticky the issticky to set
     */
    public void setIssticky(boolean issticky) {
        this.issticky = issticky;
    }

    /**
     * @return the user
     */
    @ManyToOne
    public Account getUser() {
        return user;
    }

    /**
     * @param user the user to set
     */
    public void setUser(Account user) {
        this.user = user;
    }
}
