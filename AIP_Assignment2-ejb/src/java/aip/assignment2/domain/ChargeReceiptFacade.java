package aip.assignment2.domain;

import aip.assignment2.entity.Account;
import aip.assignment2.entity.ChargeReceipt;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 * Payment receipt data management class consist of method dealing with underlying 
 * receipt record table
 * @author Cayden
 */
@Stateless
public class ChargeReceiptFacade extends AbstractFacade<ChargeReceipt> {
    @PersistenceContext(unitName = "AIP_Assignment2-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ChargeReceiptFacade() {
        super(ChargeReceipt.class);
    }
    
    /**
     * Insert payment charge receipt record into Charge Receipt table
     * @param account present a reference key to map between Charge Receipt table
     * and user account table
     * @param charge the record of user's payment receipt that going to store in
     * the Charge Receipt database
     */
    public void addChargeReceipt(Account account, ChargeReceipt charge){
        ChargeReceipt tempCharge = charge;
        tempCharge.setUser_id(account.getId());
        em.persist(tempCharge);
    }
    
    /**
     * Method to get user's payment history from Charge Receipt database
     * @param userID to retrieve data from Charge Receipt table
     * @return a list of user's payment record object 
     */
    public List<ChargeReceipt> getUserReceipt(int userID){
        String query = "select m "
                + "from ChargeReceipt m "
                + "where m.user_id = :userID";
        TypedQuery<ChargeReceipt> q = em.createQuery(query, ChargeReceipt.class);
        q.setParameter("userID", userID);
        return q.getResultList();
    }
    
}
