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
 * this class stores the reply of a request getting customer token 
 * If the request successes, the cs gets the response.
 * Otherwise, the error, error_description and em will hold the error messages.
 */
@XmlRootElement(name="customerresponse")
public class CustomerResponse {
    private CustomerSuccess cs;
    private ErrorMessage em;
    private String error;
    private String error_description;

    @XmlElement(name="response")
    public CustomerSuccess getCs() {
        return cs;
    }

    public void setCs(CustomerSuccess cs) {
        this.cs = cs;
    }

    @XmlElement(name="messages")
    public ErrorMessage getEm() {
        return em;
    }

    public void setEm(ErrorMessage em) {
        this.em = em;
    }

    @XmlElement(name="error")
    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    @XmlElement(name="error_description")
    public String getError_description() {
        return error_description;
    }

    public void setError_description(String error_description) {
        this.error_description = error_description;
    }
    
    
    
}
