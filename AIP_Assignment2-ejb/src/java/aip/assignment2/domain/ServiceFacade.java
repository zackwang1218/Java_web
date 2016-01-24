/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aip.assignment2.domain;

import aip.assignment2.entity.Service;
import aip.assignment2.utility.SERVICETYPE;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 * Service management class consist of methods dealing with reading user 
 * subscription service record from service table
 * @author Cayden
 */
@Stateless
public class ServiceFacade extends AbstractFacade<Service> {
    @PersistenceContext(unitName = "AIP_Assignment2-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ServiceFacade() {
        super(Service.class);
    }
    
    /**
     * Return free service from service table
     * @return free service object 
     */
    public Service getFreeService(){
        String query = "select m "
                + "from Service m "
                + "where m.name = :name";
        TypedQuery<Service> q = em.createQuery(query, Service.class);
        q.setParameter("name", SERVICETYPE.FREE);
        return q.getSingleResult();
    }
    
    /**
     * Return standard service from service table
     * @return standard service object
     */
    public Service getStandardService(){
        String query = "select m "
                + "from Service m "
                + "where m.name = :name";
        TypedQuery<Service> q = em.createQuery(query, Service.class);
        q.setParameter("name", SERVICETYPE.STANDARD);
        return q.getSingleResult();
    }
    
    /**
     * Return premium service from service table
     * @return premium service object
     */
    public Service getPremiumService(){
        String query = "select m "
                + "from Service m "
                + "where m.name = :name";
        TypedQuery<Service> q = em.createQuery(query, Service.class);
        q.setParameter("name", SERVICETYPE.PREMIUM);
        return q.getSingleResult();
    }
    
}
