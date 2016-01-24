package aip.assignment2.entity;

import aip.assignment2.utility.SERVICETYPE;
import java.util.*;
import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
public class Service {
    private int id;
    private SERVICETYPE name;
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
     * @return the name
     */
    @NotNull
    @Column(name = "name")
    @Enumerated(EnumType.STRING)
    public SERVICETYPE getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(SERVICETYPE name) {
        this.name = name;
    }

    /**
     * @return the subscription
     */
    @OneToMany(mappedBy="service")
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
