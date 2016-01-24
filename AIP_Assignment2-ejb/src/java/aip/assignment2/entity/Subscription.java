package aip.assignment2.entity;

import java.util.*;
import javax.persistence.*;

@Entity
public class Subscription {
    private int id;
    private Account user;
    private Service service;
    private Date start_date;
    private Date end_date;
    private boolean activated;

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
     * @return the start_date
     */
    @Column(name = "start_date")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getStart_date() {
        return start_date;
    }

    /**
     * @param start_date the start_date to set
     */
    public void setStart_date(Date start_date) {
        this.start_date = start_date;
    }

    /**
     * @return the end_date
     */
    @Column(name = "end_date")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getEnd_date() {
        return end_date;
    }

    /**
     * @param end_date the end_date to set
     */
    public void setEnd_date(Date end_date) {
        this.end_date = end_date;
    }

    /**
     * @return the isactivated
     */
    @Column(name = "activated")
    public boolean isIsactivated() {
        return activated;
    }

    /**
     * @param isactivated the isactivated to set
     */
    public void setIsactivated(boolean isactivated) {
        this.activated = isactivated;
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
    
    /**
     * @return the service
     */
    @ManyToOne
    public Service getService() {
        return service;
    }

    /**
     * @param service the service to set
     */
    public void setService(Service service) {
        this.service = service;
    }
    
}
