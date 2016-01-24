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
 * A uniformed error message 
 */
@XmlRootElement(name = "messages")
public class ErrorMessage {
    private int code;
    private String message;
    private String param;
    
    @XmlElement(name = "code")
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
    @XmlElement(name = "message")
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    @XmlElement(name = "number")
    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }
    
    
    
}
