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
 * This class keep the information of a successful charge request.
 */
@XmlRootElement(name="response")
public class ChargeSuccussResponse {
    private String token;
    private String success;
    private String amount;
    private String currency;
    private String description;
    private String email;
    private String ip_address;
    private String created_at;
    private String status_message;
    private String error_message;
    private ChargeCard cc;
    private String captured;
    private String authorisatoin_expired;
    private String transfer;
    private String amount_refunded;
    private String totle_fees;
    private String merchant_entitlement;
    private String refund_pending;
    private String settlement_currency;

    @XmlElement(name="token")
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @XmlElement(name="success")
    public String isSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    @XmlElement(name="amount")
    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    @XmlElement(name="currency")
    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    @XmlElement(name="description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @XmlElement(name="email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @XmlElement(name="ip_address")
    public String getIp_address() {
        return ip_address;
    }

    public void setIp_address(String ip_address) {
        this.ip_address = ip_address;
    }

    @XmlElement(name="created_at")
    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    @XmlElement(name="status_message")
    public String getStatus_message() {
        return status_message;
    }

    public void setStatus_message(String status_message) {
        this.status_message = status_message;
    }

    @XmlElement(name="error_message")
    public String getError_message() {
        return error_message;
    }

    public void setError_message(String error_message) {
        this.error_message = error_message;
    }

    @XmlElement(name="card")
    public ChargeCard getCc() {
        return cc;
    }

    public void setCc(ChargeCard cc) {
        this.cc = cc;
    }

    @XmlElement(name="captured")
    public String isCaptured() {
        return captured;
    }

    public void setCaptured(String captured) {
        this.captured = captured;
    }

    @XmlElement(name="authorisation_expired")
    public String getAuthorisatoin_expired() {
        return authorisatoin_expired;
    }

    public void setAuthorisatoin_expired(String authorisatoin_expired) {
        this.authorisatoin_expired = authorisatoin_expired;
    }

    @XmlElement(name="transfer")
    public String getTransfer() {
        return transfer;
    }

    public void setTransfer(String transfer) {
        this.transfer = transfer;
    }

    @XmlElement(name="amount_refunded")
    public String getAmount_refunded() {
        return amount_refunded;
    }

    public void setAmount_refunded(String amount_refunded) {
        this.amount_refunded = amount_refunded;
    }

    @XmlElement(name="total_fees")
    public String getTotle_fees() {
        return totle_fees;
    }

    public void setTotle_fees(String totle_fees) {
        this.totle_fees = totle_fees;
    }

    @XmlElement(name="merchant_entitlement")
    public String getMerchant_entitlement() {
        return merchant_entitlement;
    }

    public void setMerchant_entitlement(String merchant_entitlement) {
        this.merchant_entitlement = merchant_entitlement;
    }

    @XmlElement(name="refund_pending")
    public String isRefund_pending() {
        return refund_pending;
    }

    public void setRefund_pending(String refund_pending) {
        this.refund_pending = refund_pending;
    }

    @XmlElement(name="settlement_currency")
    public String getSettlement_currency() {
        return settlement_currency;
    }

    public void setSettlement_currency(String settlement_currency) {
        this.settlement_currency = settlement_currency;
    }
    
    
}
