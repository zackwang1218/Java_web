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
 * This class hold teh resposne of a request getting credit card token.
 * If the request is successful, the sr receive the response.
 * If the request failed, error and em keep the error messages.
 */

@XmlRootElement(name = "response")
public class TokenResponse {
    private TokenSuccussResponse sr;
    private String error;
    private ErrorMessage em;

    @XmlElement(name = "response")
    public TokenSuccussResponse getSr() {
        return sr;
    }

    public void setSr(TokenSuccussResponse sr) {
        this.sr = sr;
    }

    
    @XmlElement(name = "error")
    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
    
    @XmlElement(name = "messages")
    public ErrorMessage getEm() {
        return em;
    }

    public void setEm(ErrorMessage em) {
        this.em = em;
    }
    
}
