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
 * @author ChaoYuan
 * this class keep the response when a request getting customer token successes.
 */
@XmlRootElement(name="response")
public class CustomerSuccess {
    private String token;
    private String email;
    private String created_at;
    private ChargeCard cc;

    @XmlElement(name="token")
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @XmlElement(name="email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @XmlElement(name="created_at")
    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    @XmlElement(name="card")
    public ChargeCard getCc() {
        return cc;
    }

    public void setCc(ChargeCard cc) {
        this.cc = cc;
    }
    
}
