package aip.assignment2.domain;

import aip.assignment2.entity.*;
import aip.assignment2.utility.POSTTYPE;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 * Post data management class consist of method dealing with reading and writing 
 * user post record from post table
 * 
 * @author Cayden
 */
@Stateless
public class PostFacade extends AbstractFacade<Post> {

    @PersistenceContext(unitName = "AIP_Assignment2-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PostFacade() {
        super(Post.class);
    }

    /**
     * Insert new post record into Post table
     * @param user that contain author information of the post
     * @param newPost the post that contain detail of post record
     */
    public void addPost(Account user, Post newPost) {
        user.getPosts().add(newPost);
        newPost.setUser(user);
        em.persist(newPost);
    }

    /**
     * Update post record in the post table
     * @param post that have new post record
     */
    public void updatePost(Post post) {
        em.merge(post);
    }

    /**
     * Get single post referenced by author id 
     * @param id the post id that representing the author of the post
     * @return a single post 
     */
    public Post getPosttByPostId(int id) {
        String query = "select m from Post m "
                + "where m.id = :id";
        TypedQuery<Post> q = em.createQuery(query, Post.class);
        q.setParameter("id", id);
        return q.getSingleResult();
    }
    
    /**
     * Get all post that published by single user
     * @param userID the user id that representing the author of the post
     * @return a list of matched posts
     */
    public List<Post> getUserPostList(int userID){
        String query = "select m "
                + "from Post m "
                + "where m.user.id = :userid";
        TypedQuery<Post> q =em.createQuery(query,Post.class);
        q.setParameter("userid", userID);
        return q.getResultList();
    }

    /**
     * Get total number of single user's sticky posts
     * @param user that holding the author information of the sticky post
     * @return number of the sticky post count
     */
    public int getUserStickPostCount(Account user) {
        int rowCount = (int) (long) em.createQuery(
                "select count(m) from Post m where m.issticky = '1' and "
                + "m.user.id = :userid")
                .setParameter("userid", user.getId())
                .getSingleResult();
        return rowCount;
    }

    /**
     * Get total number of single user's total posts
     * @param user that holding the author information of the post
     * @return number of the user's post count
     */
    public int getUserTotalPostCount(Account user) {
        int rowCount = (int) (long) em.createQuery(
                "select count(m) from Post m where m.user.id = :userid")
                .setParameter("userid", user.getId())
                .getSingleResult();
        return rowCount;
    }

    /**
     * Get all posts regardless of post type from post table in the database
     * @return a list of posts in record
     */
    public List<Post> getAllPost() {
        String query = "select m "
                + "from Post m ";
        TypedQuery<Post> q = em.createQuery(query, Post.class);
        return q.getResultList();
    }

    /**
     * Get all regular type posts from post table in the database
     * @return a list of regular posts
     */
    public List<Post> getAllRegularPost() {
        String query = "select m "
                + "from Post m "
                + "where m.issticky = '0'";
        TypedQuery<Post> q = em.createQuery(query, Post.class);
        return q.getResultList();
    }

    /**
     * Get all sticky type posts from post table in the database
     * @return a list of sticky posts 
     */
    public List<Post> getAllStickyPost() {
        String query = "select m "
                + "from Post m "
                + "where m.issticky = '1'";
        TypedQuery<Post> q = em.createQuery(query, Post.class);
        return q.getResultList();
    }

    /**
     * Get all regular type posts about room rent in from post table in the 
     * database
     * @return a list of regular rent in posts 
     */
    public List<Post> getAllRentinRegularPost() {
        String query = "select m "
                + "from Post m "
                + "where m.posttype = :posttype and "
                + "m.issticky = '0'";
        TypedQuery<Post> q = em.createQuery(query, Post.class);
        q.setParameter("posttype", POSTTYPE.RENTIN);
        return q.getResultList();
    }

    /**
     * Get all sticky type posts about room rent in from post table in the 
     * database
     * @return a list of regular rent in posts 
     */
    public List<Post> getAllRentinStickyPost() {
        String query = "select m "
                + "from Post m "
                + "where m.posttype = :posttype and "
                + "m.issticky = '1'";
        TypedQuery<Post> q = em.createQuery(query, Post.class);
        q.setParameter("posttype", POSTTYPE.RENTIN);
        return q.getResultList();
    }

    /**
     * Get all regular type posts about room rent out from post table in the 
     * database
     * @return a list of regular rent in posts 
     */
    public List<Post> getAllRentoutRegularPost() {
        String query = "select m "
                + "from Post m "
                + "where m.posttype = :posttype and "
                + "m.issticky = '0'";
        TypedQuery<Post> q = em.createQuery(query, Post.class);
        q.setParameter("posttype", POSTTYPE.RENTOUT);
        return q.getResultList();
    }

    /**
     * Get all sticky type posts about room rent out from post table in the 
     * database
     * @return a list of sticky rent in posts 
     */
    public List<Post> getAllRentoutStickyPost() {
        String query = "select m "
                + "from Post m "
                + "where m.posttype = :posttype and "
                + "m.issticky = '1'";
        TypedQuery<Post> q = em.createQuery(query, Post.class);
        q.setParameter("posttype", POSTTYPE.RENTOUT);
        return q.getResultList();
    }
    
    /**
     * Get all posts regardless of post type from post table in the 
     * database
     * @param id the user id that used to retrieve post list
     * @return a list of user's published posts in post table 
     */
    public List<Post> getPostByUserIdTest(int id) {
        String query = "select m from Post m "
                + "where m.user.id = :id";
        TypedQuery<Post> q = em.createQuery(query, Post.class);
        q.setParameter("id", id);
        List<Post> postList = q.getResultList();
        if (postList.size() == 0){
            return null;
        }
        else
            return postList;
    }
    
    /**
     * Remove post record from post table in the database
     * @param id 
     */
    public void removePost(int id){
        Post removePost = em.find(Post.class, id);
        em.remove(removePost);
    }

}
