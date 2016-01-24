package aip.assignment.payment;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author YuanChao
 * This class is used to store credit card's information
 * The address_line2, address_postcode and address_state is optional
 */
@XmlRootElement(name="card")
public class CreditCard {
    
    private String publishable_api_key;
    private String number;
    private int expiry_month;
    private int expiry_year;
    private int cvc;
    private String name;
    private String address_line1;
    private String address_line2;
    private String address_city;
    private String address_postcode;
    private String adress_state;
    private String address_country;
    
     @XmlElement(name = "publishable_api_key")
    public String getPublishable_api_key() {
        return publishable_api_key;
    }

    public void setPublishable_api_key(String publishable_api_key) {
        this.publishable_api_key = publishable_api_key;
    }

     @XmlElement(name = "number")
    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
    
     @XmlElement(name = "expiry_month")
    public int getExpiry_month() {
        return expiry_month;
    }

    public void setExpiry_month(int expiry_month) {
        this.expiry_month = expiry_month;
    }
    
     @XmlElement(name = "expiry_year")
    public int getExpiry_year() {
        return expiry_year;
    }

    public void setExpiry_year(int expiry_year) {
        this.expiry_year = expiry_year;
    }
    
     @XmlElement(name = "cvc")
    public int getCvc() {
        return cvc;
    }

    public void setCvc(int cvc) {
        this.cvc = cvc;
    }
    
     @XmlElement(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
     @XmlElement(name = "address_line1")
    public String getAddress_line1() {
        return address_line1;
    }

    public void setAddress_line1(String address_line1) {
        this.address_line1 = address_line1;
    }

     @XmlElement(name = "address_line2")
    public String getAddress_line2() {
        return address_line2;
    }

    public void setAddress_line2(String address_line2) {
        this.address_line2 = address_line2;
    }

     @XmlElement(name = "address_city")
    public String getAddress_city() {
        return address_city;
    }

    public void setAddress_city(String adress_city) {
        this.address_city = adress_city;
    }

     @XmlElement(name = "address_postcode")
    public String getAddress_postcode() {
        return address_postcode;
    }

    public void setAddress_postcode(String address_postcode) {
        this.address_postcode = address_postcode;
    }

     @XmlElement(name = "address_state")
    public String getAdress_state() {
        return adress_state;
    }

    public void setAdress_state(String adress_state) {
        this.adress_state = adress_state;
    }

     @XmlElement(name = "address_country")
    public String getAddress_country() {
        return address_country;
    }

    public void setAddress_country(String address_country) {
        this.address_country = address_country;
    }
    
}
