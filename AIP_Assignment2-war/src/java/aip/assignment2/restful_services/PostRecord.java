/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aip.assignment2.restful_services;

import aip.assignment2.utility.POSTTYPE;
import java.io.*;
import java.util.*;
import javax.xml.bind.annotation.*;

//returned object of web services contains the post records created by a specific user
@XmlRootElement
public class PostRecord implements Serializable{
    private String username;
    private POSTTYPE posttype;
    private String subject;
    private String content;
    private Date postdate;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public POSTTYPE getPosttype() {
        return posttype;
    }

    public void setPosttype(POSTTYPE posttype) {
        this.posttype = posttype;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getPostdate() {
        return postdate;
    }

    public void setPostdate(Date postdate) {
        this.postdate = postdate;
    }
    
}
