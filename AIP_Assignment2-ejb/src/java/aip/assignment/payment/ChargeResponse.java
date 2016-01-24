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
 * This class stores the reply of a charge request.
 * If the request is success, csr will store the necessary informations.
 * If the request is fialed, error,rerror_description and em will work together keeping error info.
 */
@XmlRootElement(name="chargeresponse")
public class ChargeResponse {
    private ChargeSuccussResponse csr;
    private String error;
    private String error_description;
    private String charge_token;
    private ErrorMessage em;

    @XmlElement(name="response")
    public ChargeSuccussResponse getCsr() {
        return csr;
    }

    public void setCsr(ChargeSuccussResponse csr) {
        this.csr = csr;
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

    @XmlElement(name="charge_token")
    public String getCharge_token() {
        return charge_token;
    }

    public void setCharge_token(String charge_token) {
        this.charge_token = charge_token;
    }

    @XmlElement(name="messages")
    public ErrorMessage getEm() {
        return em;
    }

    public void setEm(ErrorMessage em) {
        this.em = em;
    }
    
    
}
