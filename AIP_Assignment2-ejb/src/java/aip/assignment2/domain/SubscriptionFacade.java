/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aip.assignment2.domain;

import aip.assignment2.entity.Account;
import aip.assignment2.entity.Service;
import aip.assignment2.entity.Subscription;
import aip.assignment2.utility.SERVICETYPE;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 * Subscription management class consist of methods dealing with reading and 
 * writing user subscription record from subscription table
 * @author Cayden
 */
@Stateless
public class SubscriptionFacade extends AbstractFacade<Subscription> {

    @PersistenceContext(unitName = "AIP_Assignment2-ejbPU")
    private EntityManager em;

    private List<Subscription> subscriptionList;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SubscriptionFacade() {
        super(Subscription.class);
    }

    /**
     * Insert new subscription record into Subscription table
     * @param user provide user_id foreign key to map relationship
     * @param subscription subscription record that are going to store in the
     * subscription table
     * @param service provide service_id foreign key to map relationship
     */
    public void addUserSubscription(Account user, Subscription subscription, Service service) {
        if (!user.getSubscription().isEmpty()) {
            this.deactivatePreSubcription(user);
        }
        user.getSubscription().add(subscription);
        int durationInMonth = 1;
        if (service.getName() == SERVICETYPE.FREE) {

        } else if (service.getName() == SERVICETYPE.STANDARD
                || service.getName() == SERVICETYPE.PREMIUM) {
            Date endDate = getEndDate(subscription.getStart_date(), durationInMonth);
            subscription.setEnd_date(endDate);
        }
        service.getSubscription().add(subscription);
        subscription.setUser(user);
        subscription.setService(service);

        em.persist(subscription);

    }

    /**
     * De-active pre-user's subscription status and insert new subscription 
     * record to subscription table (not remove previous subscription record)
     * @param user provide user_id foreign key to map relationship
     * @param subscription subscription record that are going to store in the
     * subscription table
     * @param service provide service_id foreign key to map relationship
     */
    public void updateUserSubscription(Account user, Subscription subscription, Service service) {
        this.deactivatePreSubcription(user);
        Subscription newSubscription = new Subscription();
        newSubscription.setIsactivated(true);
        newSubscription.setStart_date(new Date());
        newSubscription.setUser(user);
        newSubscription.setService(service);
        user.getSubscription().add(newSubscription);
        service.getSubscription().add(newSubscription);
        Date endDate = getEndDate(newSubscription.getStart_date(), 1);
        newSubscription.setEnd_date(endDate);
        em.persist(newSubscription);
    }

    /**
     * Return user's currently activated service type from subscription table
     * @param user provide user_id to get the matched service type
     * @return SERVICETYPE enum that user currently in
     */
    public SERVICETYPE getUserServiceType(Account user) {
        String query = "select m "
                + "from Subscription m "
                + "where m.user.id = :userid and "
                + "m.isactivated = '1'";
        TypedQuery<Subscription> q = em.createQuery(query, Subscription.class);
        q.setParameter("userid", user.getId());
        return q.getSingleResult().getService().getName();
    }

    /**
     * Return user's currently activated subscription record from subscription 
     * table
     * @param user provide user_id to get the matched subscription
     * @return a single subscription record that is currently activated
     */
    public Subscription getUserActivatedSubscription(Account user) {
        Subscription resultSubs = em.createQuery("select m from Subscription m "
                + "where m.user.id = :userid and "
                + "m.isactivated = '1'", Subscription.class)
                .setParameter("userid", user.getId())
                .getSingleResult();
        if (resultSubs == null) {
            return null;
        } else {
            return resultSubs;
        }
    }

    /**
     * De-activate one user's previous activated subscription record from subscription
     * table in that case system can keep a copy of record about user's subscription
     * history
     * @param user provide user_id to get the specific subscription record
     */
    private void deactivatePreSubcription(Account user) {
        Subscription tempSubscription = new Subscription();
        Account tempAccount = em.find(Account.class, user.getId());
        tempSubscription = getUserActivatedSubscription(tempAccount);
        if (tempSubscription != null) {
            tempSubscription.setIsactivated(false);
            em.merge(tempSubscription);
        }
    }

    /**
     * Count date from start date and return subscription end date 
     * @param startDate the date that start counting
     * @param durationInMonth integer value to represent the on going months that
     * are going to be counted
     * @return subscription end date
     */
    public Date getEndDate(Date startDate, int durationInMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);
        calendar.add(Calendar.MONTH, durationInMonth);
        Date endDate = calendar.getTime();
        return endDate;
    }

}
