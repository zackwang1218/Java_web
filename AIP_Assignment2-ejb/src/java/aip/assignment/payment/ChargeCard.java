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
 * this class is used to store the creditcard info coming from a successful charge request
 */
@XmlRootElement(name = "card")
public class ChargeCard {
    private String token;
    private String scheme;
    private String display_number;
    private int expiry_month;
    private int expiry_year;
    private String name;
    private String address_line1;
    private String address_line2;
    private String address_city;
    private int postcode;
    private String address_state;
    private String address_country;
    private String primary;

    @XmlElement(name="token")
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @XmlElement(name="scheme")
    public String getScheme() {
        return scheme;
    }

    public void setScheme(String scheme) {
        this.scheme = scheme;
    }
    
    @XmlElement(name="display_number")
    public String getDisplay_number() {
        return display_number;
    }

    public void setDisplay_number(String display_number) {
        this.display_number = display_number;
    }

    @XmlElement(name="expiry_month")
    public int getExpiry_month() {
        return expiry_month;
    }

    public void setExpiry_month(int expiry_month) {
        this.expiry_month = expiry_month;
    }

    @XmlElement(name="expiry_year")
    public int getExpiry_year() {
        return expiry_year;
    }

    public void setExpiry_year(int expiry_year) {
        this.expiry_year = expiry_year;
    }

    @XmlElement(name="name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlElement(name="address_line1")
    public String getAddress_line1() {
        return address_line1;
    }

    public void setAddress_line1(String address_line1) {
        this.address_line1 = address_line1;
    }

    @XmlElement(name="address_line2")
    public String getAddress_line2() {
        return address_line2;
    }

    public void setAddress_line2(String address_line2) {
        this.address_line2 = address_line2;
    }

    @XmlElement(name="address_city")
    public String getAddress_city() {
        return address_city;
    }

    public void setAddress_city(String address_city) {
        this.address_city = address_city;
    }

    @XmlElement(name="postcode")
    public int getPostcode() {
        return postcode;
    }

    public void setPostcode(int postcode) {
        this.postcode = postcode;
    }

    @XmlElement(name="address_state")
    public String getAddress_state() {
        return address_state;
    }

    public void setAddress_state(String address_state) {
        this.address_state = address_state;
    }

    @XmlElement(name="address_country")
    public String getAddress_country() {
        return address_country;
    }

    public void setAddress_country(String address_country) {
        this.address_country = address_country;
    }

    @XmlElement(name="primary")
    public String getPrimary() {
        return primary;
    }

    public void setPrimary(String primary) {
        this.primary = primary;
    }
    
}
