package aip.assignment2.controller;

import aip.assignment.payment.Charge;
import aip.assignment.payment.ChargeResponse;
import aip.assignment.payment.ChargeSuccussResponse;
import aip.assignment.payment.CreditCard;
import aip.assignment.payment.Customer;
import aip.assignment.payment.Payment;
import aip.assignment2.domain.AccountFacade;
import aip.assignment2.domain.ChargeReceiptFacade;
import aip.assignment2.domain.ServiceFacade;
import aip.assignment2.domain.SubscriptionFacade;
import aip.assignment2.entity.Account;
import aip.assignment2.entity.ChargeReceipt;
import aip.assignment2.entity.Service;
import aip.assignment2.entity.Subscription;
import aip.assignment2.utility.GROUP;
import aip.assignment2.utility.SERVICETYPE;
import java.net.InetAddress;
import java.net.UnknownHostException;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;

/**
 * Subscription controller manage all subscription related functionality, it
 * acting as a intermediate role to transfer data from data transfer object in
 * the backing bean and response to the call from user interface JSF page
 *
 * @author Cayden
 */
@Named
@RequestScoped
public class SubscriptionController {

    /**
     * The current model used by JSF.
     */
    private Account account;
    private Subscription subscription;
    private Service service;
    private Service newService;
    private Account targetAccount;
    private ChargeReceipt charge;
    private CreditCard card;

    @EJB
    private AccountFacade accountBean;
    @EJB
    private SubscriptionFacade subscriptionBean;
    @EJB
    private ServiceFacade serviceBean;
    @EJB
    private Payment payment;
    @EJB
    private ChargeReceiptFacade chargeBean;

    @PostConstruct
    public void InitModel() {
        subscription = new Subscription();
        service = new Service();
        targetAccount = new Account();
        newService = new Service();
        charge = new ChargeReceipt();
        card = new CreditCard();
    }

    /**
     * Initialize target account at beginning of the JSF page launch
     *
     * @param uid used to find account record in account table
     */
    public void initialTargetAccount(int uid) {
        targetAccount = accountBean.getAccountByUserId(uid);
        subscription = subscriptionBean.getUserActivatedSubscription(targetAccount);
        service = subscription.getService();
    }

    /**
     * Upgrade user subscription level if user is free account there is no need
     * to verify user payment statement else to throw payment verification
     * process 1, If user don't have customer token in record use have to type
     * credit card detail to process further 2, after user have got approved
     * customer token, system will store user's customer token into the account
     * table
     *
     * @return URL to redirect user after process
     * @throws UnknownHostException throws when current IP address is not found
     */
    public String updateUserSunscription() throws UnknownHostException {
        targetAccount = accountBean.getAccountByUserId(targetAccount.getId());
        subscription = subscriptionBean.getUserActivatedSubscription(targetAccount);
        Account currentAccount = this.getAccount();

        if (!isFreeService()) {
            String customerToken = targetAccount.getCardtoken();
            if (customerToken == null) {
                if (currentAccount.getGroupname().equals(GROUP.Users)) {
                    String cardToken = payment.cardToken(card);
                    if (cardToken != null) { //Card token successed
                        Customer customer = new Customer();
                        customer.setCardToken(cardToken);
                        customer.setEmail(this.targetAccount.getEmail());
                        customerToken = payment.CustomerToken(customer);
                    } else { //Card token failed
                        showError(null, "Invalid Card");
                        return "/faces/user/profile/updateservice.xhtml?uid=" + targetAccount.getId();
                    }
                }
            }
            ChargeResponse chargeResponse = manageCardCharge(customerToken);
            if (chargeResponse.getError() == null) {
                //Success to charge
                ChargeSuccussResponse csr = chargeResponse.getCsr();
                this.charge.setToken(customerToken);
                this.charge.setAmount(csr.getAmount());
                this.charge.setCurrency(csr.getCurrency());
                this.charge.setEmail(csr.getEmail());
                this.charge.setCreated_at(csr.getCreated_at());
                this.charge.setDescription(csr.getDescription());
                this.charge.setIp_address(csr.getIp_address());
                this.charge.setUser_id(targetAccount.getId());
                this.chargeBean.addChargeReceipt(targetAccount, this.charge);
                this.targetAccount.setCardtoken(customerToken);
                this.accountBean.updateUser(targetAccount);
            }
        }
        subscriptionBean.updateUserSubscription(targetAccount, subscription, newService);

        if (currentAccount.getGroupname().equals(GROUP.Administrator)) {
            return "/faces/admin/listalluser?faces-redirect=true";
        } else {
            return "/faces/user/profile/userprofile.xhtml?uid=" + targetAccount.getId() + "&faces-redirect=true";
        }

    }

    /**
     * Use customer token to manage payment charge
     *
     * @param customerToken a encrypted string to represent user's credit card
     * @return the message of the payment statement
     * @throws UnknownHostException throws when the host IP address is not
     * accessible
     */
    public ChargeResponse manageCardCharge(String customerToken) throws UnknownHostException {
        Charge cg = new Charge();
        if (newService.getName().equals(SERVICETYPE.STANDARD)) {
            cg.setAmount("100");
        } else {
            cg.setAmount("150");
        }
        cg.setCurrency("AUD");
        cg.setDescription("Upgrade Service to " + this.service.getName().toString());
        cg.setEmail(this.targetAccount.getEmail());
        cg.setIp_address(InetAddress.getLocalHost().getHostAddress());
        cg.setCustomer_token(customerToken);
        return payment.Charge(cg);
    }

    /**
     * Check if selected service is free service or not
     *
     * @return ture if selected service is free service or false if it is a
     * payment service
     */
    public boolean isFreeService() {
        if (newService.getName().equals(SERVICETYPE.FREE)) {
            newService = serviceBean.getFreeService();
            return true;
        } else if (newService.getName().equals(SERVICETYPE.PREMIUM)) {
            newService = serviceBean.getPremiumService();
            return false;
        } else {
            newService = serviceBean.getStandardService();
            return false;
        }
    }

    /**
     * Display error message on the screen
     *
     * @param msg message that will display on the screen
     */
    private void showError(String id, String msg) {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(id, new FacesMessage(msg));
    }

    /**
     * Get current service type
     *
     * @return string object to represent current service type
     */
    public String serviceType() {
        return subscriptionBean.getUserServiceType(getAccount()).name().toLowerCase();
    }

    /**
     * get current logged in user's account information
     *
     * @return current logged in user's account
     */
    public Account getAccount() {
        String currentUsername = FacesContext.getCurrentInstance().getExternalContext().getRemoteUser();
        account = accountBean.getAccountByUsername(currentUsername);
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Subscription getSubscription() {
        return subscription;
    }

    /**
     * get array of service type in SERVICETYPE enum
     *
     * @return array of service type
     */
    public SERVICETYPE[] getServiceTypes() {
        return SERVICETYPE.values();
    }

    public Service getService() {
        return service;
    }

    public void setService(int id) {
        this.service = serviceBean.find(id);
    }

    public Account getTargetAccount() {
        return targetAccount;
    }

    public void setTargetAccount(Account targetAccount) {
        this.targetAccount = targetAccount;
    }

    public Service getNewService() {
        return newService;
    }

    public void setNewService(int id) {
        this.newService = serviceBean.find(id);
    }

    /**
     * @return the card
     */
    public CreditCard getCard() {
        return card;
    }

    /**
     * @param card the card to set
     */
    public void setCard(CreditCard card) {
        this.card = card;
    }

}
