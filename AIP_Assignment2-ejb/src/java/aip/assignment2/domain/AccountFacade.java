package aip.assignment2.domain;

import aip.assignment2.entity.*;
import java.util.*;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 * Account data management class consist of method dealing with underlying user
 * account table
 * @author Cayden
 */
@Stateless
public class AccountFacade extends AbstractFacade<Account> {

    @PersistenceContext(unitName = "AIP_Assignment2-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AccountFacade() {
        super(Account.class);
    }

    /**
     * Add new User to the user account table
     * @param user that going to be inserted
     * @return true after successfully saved in table or false when the user
     * with same username already exist
     */
    public boolean addUser(Account user) {
        if (!this.checkUserExist(user.getUsername())) {
            em.persist(user);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Update user record in the user table
     * @param user that going to be updated
     */
    public void updateUser(Account user) {
        Account tempAccount = em.find(Account.class, user.getId());
        tempAccount.setFirstName(user.getFirstName());
        tempAccount.setLastName(user.getLastName());
        tempAccount.setGender(user.getGender());
        tempAccount.setPhonenumber(user.getPhonenumber());
        tempAccount.setEmail(user.getEmail());
        tempAccount.setCardtoken(user.getCardtoken());
        em.merge(tempAccount);
    }

    /**
     * Update only the user password associated with user account
     * @param user who's password will be updated 
     */
    public void updatePassword(Account user) {
        Account tempAccount = em.find(Account.class, user.getId());
        tempAccount.setPassword(user.getPassword());
        em.merge(tempAccount);
    }

    /**
     * Get all individual account from user table
     * @return List of user account 
     */
    public List<Account> getAllAccount() {
        String query = "select m "
                + "from Account m ";
        TypedQuery<Account> q = em.createQuery(query, Account.class);
        return q.getResultList();
    }
    
    /**
     * Return user account according to user's username
     * @param username that are going to compare with username in table to get
     * the paired user account
     * @return single matched user account
     */
    public Account getAccountByUsername(String username) {
        String query = "select m "
                + "from Account m "
                + "where m.username = :username";
        TypedQuery<Account> q = em.createQuery(query, Account.class);
        q.setParameter("username", username);
        return q.getSingleResult();
    }

    /**
     * Return user account according to user's id
     * @param id that are going compare with user id in table to get the paired
     * user account
     * @return single matched user account
     */
    public Account getAccountByUserId(int id) {
        String query = "select m "
                + "from Account m "
                + "where m.id = :id";
        TypedQuery<Account> q = em.createQuery(query, Account.class);
        q.setParameter("id", id);
        return q.getSingleResult();
    }

    /**
     * Check whether there is account record with same username exist
     * @param username use to find the same username in table
     * @return ture if there is account record with same username exist or false
     * if there is no matching username
     */
    public boolean checkUserExist(String username) {
        int rowCount = (int) (long) em.createQuery(
                "SELECT COUNT(m) FROM Account m WHERE m.username = :username")
                .setParameter("username", username)
                .getSingleResult();
        return rowCount != 0;
    }
}
