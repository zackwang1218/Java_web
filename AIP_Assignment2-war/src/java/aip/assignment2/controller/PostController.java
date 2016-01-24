package aip.assignment2.controller;

import aip.assignment2.domain.*;
import aip.assignment2.entity.Account;
import aip.assignment2.entity.Post;
import aip.assignment2.entity.Subscription;
import aip.assignment2.utility.POSTTYPE;
import aip.assignment2.utility.SERVICETYPE;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;

/**
 * Post controller manage all post related functionality, it acting as a
 * intermediate role to transfer data from data transfer object in the backing bean
 * and response to the call from user interface JSF page
 * @author Cayden
 */
@Named
@RequestScoped
public class PostController {

    /**
     * The current model used by JSF.
     */
    private Post post;
    private Account account;
    private List<Post> postList;
    private String showType;
    private String exportedRecord;

    @EJB
    private AccountFacade accountBean;
    @EJB
    private PostFacade postBean;
    @EJB
    private SubscriptionFacade subscriptionBean;

    @PostConstruct
    public void InitModel() {
        setAccount(new Account());
        post = new Post();
    }

    /**
     * Add new post record collected from user input into post table in databse
     * @return URL after creation of new post
     */
    public String addPost() {
        this.account = accountBean.getAccountByUserId(account.getId());
        if (this.post.getEmail().isEmpty()) {
            this.post.setEmail(this.getAccount().getEmail());
        }
        this.post.setPostdate(new Date());
        this.postBean.addPost(this.account, this.post);
        return "/post/postlist?faces-redirect=true";
    }

    /**
     * get array of post type in POSTTYPE enum
     * @return array of post type
     */
    public POSTTYPE[] getPostType() {
        return POSTTYPE.values();
    }

    /**
     * Check if current posting user have reached the maximum sticky post limit
     * @return ture if not reached the maximum sticky post limit or false if not 
     * reached limit
     * @throws DataStoreException throws when failed to get current logged in user 
     * information
     */
    public boolean isMaxiumStickyPost() throws DataStoreException {
        Account tempUser = getCurrentUserAccount();
        Subscription tempSubscription = subscriptionBean.getUserActivatedSubscription(tempUser);
        if (tempSubscription.getService().getName() == SERVICETYPE.FREE) {
            return false;
        } else if (tempSubscription.getService().getName() == SERVICETYPE.STANDARD) {
            //Maxium 5 sticky post avaliable for standard user
            if (postBean.getUserStickPostCount(tempUser) < 5) {
                return true;
            } else {
                return false;
            }
        } else if (tempSubscription.getService().getName() == SERVICETYPE.PREMIUM) {
            //Maxium 10 sticky post avaliable for premium user
            if (postBean.getUserStickPostCount(tempUser) < 10) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
    
    /**
     * Update post with new content
     */
    public void updatePost(){
        Account tempuser = accountBean.getAccountByUserId(this.account.getId());
        post.setUser(tempuser);
        post.setPostdate(new Date());
        postBean.updatePost(post);
    }
    
    /**
     * Check whether current logged in user is the author of the post
     * @return ture if it is author or false if not 
     */
    public boolean isCurrentAccountMatchPostAuthor(){
        String currentUsername = FacesContext.getCurrentInstance().getExternalContext().getRemoteUser();
        Account currentAccount = accountBean.getAccountByUsername(currentUsername);
        return currentAccount.getId() == this.account.getId();
    }
    
    /**
     * Remove current post
     * @return URL after removing post
     */
    public String removePost(){
        int id = this.post.getId();
        this.postBean.removePost(id);
        return "/faces/post/postlist.xhtml";
    }
    
    /**
     * Get current logged in user's information
     * @return current logged in user
     */
    public Account getCurrentUserAccount(){
        String currentUsername = FacesContext.getCurrentInstance().getExternalContext().getRemoteUser();
        account = accountBean.getAccountByUsername(currentUsername);
        return account;
    }

    /**
     * @return all posts stored in post table
     */
    public ArrayList<Post> getAllPosts() {
        return new ArrayList<>(postBean.getAllPost());
    }

    /**
     * @return all regular posts stored in post table
     */
    public ArrayList<Post> getAllRegularPosts() {
        return new ArrayList<>(postBean.getAllRegularPost());
    }

    /**
     * @return all sticky posts stored in post table
     */
    public ArrayList<Post> getAllStickyPosts() {
        return new ArrayList<>(postBean.getAllStickyPost());
    }

    /**
     * @return all rent in regular posts stored in post table
     */
    public ArrayList<Post> getAllRentinRegularPosts() {
        return new ArrayList<>(postBean.getAllRentinRegularPost());
    }

    /**
     * @return all rent in sticky posts stored in post table
     */
    public ArrayList<Post> getAllRentinStickyPosts() {
        return new ArrayList<>(postBean.getAllRentinStickyPost());
    }

    /**
     * @return all rent out regular posts stored in post table
     */
    public ArrayList<Post> getAllRentoutRegularPosts() {
        return new ArrayList<>(postBean.getAllRentoutRegularPost());
    }

    /**
     * @return all rent out sticky posts stored in post table
     */
    public ArrayList<Post> getAllRentoutStickyPosts() {
        return new ArrayList<>(postBean.getAllRentoutStickyPost());
    }

    /**
     * @return a list of posts that posted by single user
     */
    public ArrayList<Post> getAllUserPosts(){
        return new ArrayList<>(postBean.getUserPostList(this.account.getId()));
    }
    
    /**
     * If user is in premium subscription level user this showType to switch between
     * different type of post
     * @return selected post showType
     */
    public String getShowType() {
        return showType;
    }

    /**
     * Set post showType
     * @param showType that are going to set showType
     */
    public void setShowType(String showType) {
        this.showType = showType;
    }

    /**
     * get single post
     * @return single post
     */
    public Post getPost() {
        return post;
    }

    /**
     * set single post
     * @param id used to retrieve post record in post table
     */
    public void setPost(int id) {
        this.post = postBean.getPosttByPostId(id);
    }

    /**
     * Get list of post
     * @return list of post
     */
    public List<Post> getPostList() {
        return postList;
    }

    /**
     * Set list of post
     * @param postList 
     */
    public void setPostList(List<Post> postList) {
        this.postList = postList;
    }

    /**
     * @return the account
     */
    public Account getAccount() {
        return account;
    }

    /**
     * @param account the account to set
     */
    public void setAccount(Account account) {
        this.account = account;
    }
    
    /**
     * Set controller's account at beginning using user_id
     * @param userID 
     */
    public void setAccountwithUserID(int userID) {
        this.account = accountBean.getAccountByUserId(userID);
    }

    /**
     * Get current user's post record
     * @return current user's post record
     */
    public String getExportedRecord() {
        return exportedRecord;
    }

    /**
     * @param exportedRecord to set export record
     */
    public void setExportedRecord(String exportedRecord) {
        this.exportedRecord = exportedRecord;
    }

    /**
     * use JAX-RS to export record for current user
     */
    public void exportRecordForUser(){
        String currentUsername = FacesContext.getCurrentInstance().getExternalContext().getRemoteUser();
        int userid = accountBean.getAccountByUsername(currentUsername).getId();
        Form form = new Form();
        form.param("userid", userid + "");
        
        String target = "http://localhost:8080/AIP_Assignment2-war/api/record";
        Client client = ClientBuilder.newClient();
        
        exportedRecord = client.target(target)
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.form(form), String.class);
        
        client.close();

    }

}
