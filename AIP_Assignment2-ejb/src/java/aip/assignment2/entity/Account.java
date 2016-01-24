package aip.assignment2.entity;

import aip.assignment2.utility.GROUP;
import java.util.*;
import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
public class Account {
    private int id;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String Email;
    private String phonenumber;
    private String gender;
    private String cardtoken;
    private GROUP groupname;
    private List<Post> posts = new ArrayList<Post>();
    private List<Subscription> subscription = new ArrayList<Subscription>();
    
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
     * @return the username
     */
    @NotNull
    @Pattern(regexp="[a-zA-Z0-9_-]{3,20}", message = "No special chararcter not allowed except for '_' '-'")
    @Column(name = "username")
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the password
     */
    @NotNull
    @Size(min=3, message = "Please type no less than 3 character for password")
    @Column(name = "password")
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the firstName
     */
    @NotNull
    @Pattern(regexp="[a-zA-Z]+", message ="Only alphabetical character is allowed")
    @Column(name = "firstname")
    public String getFirstName() {
        return firstName;
    }

    /**
     * @param firstName the firstName to set
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * @return the lastName
     */
    @NotNull
    @Pattern(regexp="[a-zA-Z]+", message ="Only alphabetical character is allowed")
    @Column(name = "lastname")
    public String getLastName() {
        return lastName;
    }

    /**
     * @param lastName the lastName to set
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * @return the Email
     */
    @NotNull
    @Pattern(regexp="^[a-zA-Z0-9_]+@([a-zA-Z0-9_-]+\\.)+[a-zA-Z0-9]+$", message ="Not a proper email address format") 
    @Column(name = "email")
    public String getEmail() {
        return Email;
    }

    /**
     * @param Email the Email to set
     */
    public void setEmail(String Email) {
        this.Email = Email;
    }

    /**
     * @return the phonenumber
     */
    @Column(name = "phonenumber")
    public String getPhonenumber() {
        return phonenumber;
    }

    /**
     * @param phonenumber the phonenumber to set
     */
    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    /**
     * @return the gender
     */
    @Column(name = "gender")
    public String getGender() {
        return gender;
    }

    /**
     * @param gender the gender to set
     */
    public void setGender(String gender) {
        this.gender = gender;
    }

    /**
     * @return the cardtoken
     */
    @Column(name = "cardtoken")
    public String getCardtoken() {
        return cardtoken;
    }

    /**
     * @param cardtoken the cardtoken to set
     */
    public void setCardtoken(String cardtoken) {
        this.cardtoken = cardtoken;
    }
    
    /**
     * @return the groupname
     */
    @Column(name = "groupname")
    @Enumerated(EnumType.STRING)
    public GROUP getGroupname() {
        return groupname;
    }

    /**
     * @param groupname the groupname to set
     */
    public void setGroupname(GROUP groupname) {
        this.groupname = groupname;
    }

    /**
     * @return the posts
     */
    @OneToMany(mappedBy="user", orphanRemoval=true, cascade=CascadeType.ALL)
    public List<Post> getPosts() {
        return posts;
    }

    /**
     * @param posts the posts to set
     */
    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    /**
     * @return the subscription
     */
    @OneToMany(mappedBy="user", orphanRemoval=true, cascade=CascadeType.ALL)
    public List<Subscription> getSubscription() {
        return subscription;
    }

    /**
     * @param subscription the subscription to set
     */
    public void setSubscription(List<Subscription> subscription) {
        this.subscription = subscription;
    }

    
    
}
