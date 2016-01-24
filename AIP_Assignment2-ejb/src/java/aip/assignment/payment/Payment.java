/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aip.assignment.payment;

import java.util.Base64;
import javax.ejb.Stateless;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author ChaoYuan
 * This class is used to communicate with the Pin payment API.
 * To make a single payment: cardToken -> charge 
 * To make payments over time: CustomerToken -> charge
 */
@Stateless
public class Payment {

    private static final String testTokenURL = "https://test-api.pin.net.au/1/cards";
    private static final String testCharge = "https://test-api.pin.net.au/1/charges";
    private static final String testCustomer = "https://test-api.pin.net.au/1/customers";
    private static final String publicKey = "pk_zFxURF3uswqE00-o3nDUaQ";
    private static final String sercureKey = "DA4ognKkq_TtHeyVwX45Xw";

    /**
     * This method send credit card information to Pin payment using HTTP Post method.
     * Both the post form data and response is in json form.
     * @param cc Credit card information
     * @return If the request successed, this method would return the card token. Or it will return null.
     */
    public String cardToken(CreditCard cc) {
        cc.setPublishable_api_key(publicKey);
        Client client = ClientBuilder.newClient();
        WebTarget myResource = client.target(testTokenURL);
        TokenResponse rs = myResource.request(MediaType.APPLICATION_JSON_TYPE).post(Entity.json(cc)).readEntity(TokenResponse.class);
        if (rs.getSr() != null) {
            String cardToken = rs.getSr().getToken();
            return cardToken;
        } else {
            return null;
        }   
    }

    /**
     * This method sent charge request to Pin Payment via HTTP POST.
     * @param c necessary charge information.
     * @return return the resposne coming from the server.
     */
    public ChargeResponse Charge(Charge c) {
        Client client = ClientBuilder.newClient();
        String encoded = encodeBase64(sercureKey + ":");
        WebTarget myResource = client.target(testCharge);
        ChargeResponse cr = myResource.request(MediaType.APPLICATION_JSON_TYPE).header("Authorization", "Basic " + encoded).post(Entity.json(c)).readEntity(ChargeResponse.class);
        return cr;
    }
    /**
     * This method send customer info who want to make payments over time. 
     * Get card token before using this method.
     * @param c customer info
     * @return customer token
     */
    public String CustomerToken(Customer c){
        Client client = ClientBuilder.newClient();
        String encoded = encodeBase64(sercureKey+":");
        WebTarget myResource = client.target(testCustomer);
        CustomerResponse cr = myResource.request(MediaType.APPLICATION_JSON_TYPE).header("Authorization", "Basic " + encoded).post(Entity.json(c)).readEntity(CustomerResponse.class);
        if(cr.getCs()!=null){
            return cr.getCs().getToken();
        }else{
            return null;
        }
    }
    
    /**
     * Encoder for HTTP basic authorization.
     * @param input username+ ":"+password
     * @return encoded string
     */
    private String encodeBase64(String input){
        byte[] bytes = input.getBytes();
        String encoded = Base64.getEncoder().encodeToString(bytes);
        return encoded;
    }
}
