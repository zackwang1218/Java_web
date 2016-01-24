/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aip.assignment.payment;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
/**
 * 
 * @author YuanChao
 * This class store the information of a single charge 
 * To ensure the charge was successfully processed, at least one of the following
 * properties is needed. (cc, card_token, customer_token)
 */

@XmlRootElement(name = "charge")
public class Charge {
    private String email;
    private String description;
    private String amount;
    private String ip_address;
    private String currency;
    private String capture;
    private String card_token;
    private String customer_token;
    private CreditCard cc;

    @XmlElement(name="email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @XmlElement(name="description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    @XmlElement(name="amount")
    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    @XmlElement(name="ip_address")
    public String getIp_address() {
        return ip_address;
    }

    public void setIp_address(String ip_address) {
        this.ip_address = ip_address;
    }

    @XmlElement(name="currency")
    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    @XmlElement(name="capture")
    public String getCapture() {
        return capture;
    }

    public void setCapture(String capture) {
        this.capture = capture;
    }

    @XmlElement(name="card_token")
    public String getCard_token() {
        return card_token;
    }

    public void setCard_token(String card_token) {
        this.card_token = card_token;
    }

    @XmlElement(name="card")
    public CreditCard getCc() {
        return cc;
    }

    public void setCc(CreditCard cc) {
        this.cc = cc;
    }

    @XmlElement(name = "customer_token")
    public String getCustomer_token() {
        return customer_token;
    }

    public void setCustomer_token(String customer_token) {
        this.customer_token = customer_token;
    }
    
    
}
