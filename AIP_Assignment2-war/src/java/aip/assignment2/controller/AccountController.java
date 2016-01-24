package aip.assignment2.controller;

import aip.assignment.payment.Charge;
import aip.assignment.payment.ChargeResponse;
import aip.assignment.payment.ChargeSuccussResponse;
import aip.assignment.payment.CreditCard;
import aip.assignment.payment.Customer;
import aip.assignment.payment.Payment;
import aip.assignment2.domain.*;
import aip.assignment2.entity.*;
import aip.assignment2.utility.GROUP;
import aip.assignment2.utility.SERVICETYPE;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import javax.annotation.*;
import javax.ejb.*;
import javax.enterprise.context.*;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

/**
 * Account controller manage all account related functionality, it acting as a
 * intermediate role to transfer data from data transfer object in the backing bean
 * and response to the call from user interface JSF page
 * @author Cayden
 */
@Named
@RequestScoped
public class AccountController {

    /**
     * The current model used by JSF.
     */
    private Account account;
    private Subscription subscription;
    private Service service;
    private ChargeReceipt charge;
    private String serviceType;
    private String c_password;
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
        account = new Account();
        subscription = new Subscription();
        service = new Service();
        charge = new ChargeReceipt();
        setCard(new CreditCard());
    }

    /**
     * A process to transfer new user and associated records to database
     * 1, check if the user is a free account or payment account if current user
     * is registered as a free account, then no need to verify the payment process
     * else if the user choose to select payment account, user need to go through
     * payment verification.
     * 2, (in payment verification process) check user's card token first and 
     * follow by checking user's customer token for further multiple payment 
     * at last use customer token to pay for the service.
     * 3, save user payment record as well as new user with subscription record 
     * into database.
     * @return Uri to redirect after user submit register form
     * @throws NoSuchAlgorithmException throws when encrypting password fails
     * @throws UnknownHostException throws when the host IP address is not accessible
     */
    public String addUser() throws NoSuchAlgorithmException, UnknownHostException {
        //Add user to database
        setRegistService();
        if (checkServiceType()) {
            String cardToken = payment.cardToken(card);
            if (cardToken != null) { //Card token successed
                Customer customer = new Customer();
                customer.setCardToken(cardToken);
                customer.setEmail(this.account.getEmail());
                String customerToken = payment.CustomerToken(customer);
                if (customerToken != null) { //Customer token successed
                    ChargeResponse chargeResponse = manageCardCharge(customerToken);
                    if (chargeResponse.getError() == null) { //Success to charge
                        this.account.setCardtoken(customerToken);
                        ChargeSuccussResponse csr = chargeResponse.getCsr();
                        this.charge.setToken(customerToken);
                        this.charge.setAmount(csr.getAmount());
                        this.charge.setCurrency(csr.getCurrency());
                        this.charge.setEmail(csr.getEmail());
                        this.charge.setCreated_at(csr.getCreated_at());
                        this.charge.setDescription(csr.getDescription());
                        this.charge.setIp_address(csr.getIp_address());
                    } else { //Failed to charge
                        showError(null, "Fail to charge");
                        return ("regist");
                    }
                }else{ //Customer token failed
                    showError(null,"Invalid Card");
                    return "regist";
                }
            } else { //Card token failed
                showError(null, "Invalid Card");
                return "regist";
            }
        }
        this.account.setGroupname(GROUP.Users);
        String tempPassword = hash256(this.account.getPassword());
        this.account.setPassword(tempPassword);

        if (this.accountBean.addUser(this.account)) {
            this.subscription.setIsactivated(true);
            this.subscription.setStart_date(new Date());
            this.subscriptionBean.addUserSubscription(account, this.subscription, this.service);
            this.chargeBean.addChargeReceipt(account, this.charge);
            return "/post/postlist?faces-redirect=true";
        } else {
            showError(null, "Username already exist!");
            return "regist";
        }
    }

    /**
     * Use customer token to manage payment charge
     * @param customerToken a encrypted string to represent user's credit card
     * @return the message of the payment statement
     * @throws UnknownHostException throws when the host IP address is not accessible
     */
    public ChargeResponse manageCardCharge(String customerToken) throws UnknownHostException {
        Charge cg = new Charge();
        if (this.service.getName().equals(SERVICETYPE.STANDARD)) {
            cg.setAmount("100");
        } else {
            cg.setAmount("150");
        }
        cg.setCurrency("AUD");
        cg.setDescription("Upgrade Service to " + this.service.getName().toString());
        cg.setEmail(this.account.getEmail());
        cg.setIp_address(InetAddress.getLocalHost().getHostAddress());
        cg.setCustomer_token(customerToken);
        return payment.Charge(cg);
    }

    /**
     * Check if the current user is registering as a free account or payment 
     * account
     * @return boolean status true if user is payment account or false if user 
     * is registering a free account
     */
    public boolean checkServiceType() {
        return this.service.getName().equals(SERVICETYPE.STANDARD)
                || this.service.getName().equals(SERVICETYPE.PREMIUM);
    }

    /**
     * Convert user subscription service type from JSF dropdownlist value actual
     * service type
     */
    public void setRegistService() {
        if (this.service.getName().equals(SERVICETYPE.FREE)) {
            this.service = this.serviceBean.getFreeService();
        } else if (this.service.getName().equals(SERVICETYPE.PREMIUM)) {
            this.service = this.serviceBean.getPremiumService();
        } else if (this.service.getName().equals(SERVICETYPE.STANDARD)) {
            this.service = this.serviceBean.getStandardService();
        }
    }

    /**
     * Get all user from account table in a arraylist to display in the JSF page
     * @return list of all account records
     */
    public ArrayList<Account> getAllUsers() {
        return new ArrayList<>(accountBean.getAllAccount());
    }

    /**
     * Get single user's payment receipt from ChargeReceipt table to display in 
     * in administrator's JSF page
     * @return list of all receipts that associated with one particular user
     */
    public ArrayList<ChargeReceipt> getUserReceipt() {
        return new ArrayList<>(chargeBean.getUserReceipt(this.account.getId()));
    }

    /**
     * Update user profile 
     */
    public void updateProfile() {
        this.accountBean.updateUser(account);
        showMSG("Profile Updated");
    }

    /**
     * Update user password:
     * 1, check if two password entered by user are same
     * 2, encrypt password and update user password
     * @throws NoSuchAlgorithmException throws when failed to encrypt user password 
     */
    public void updatePassword() throws NoSuchAlgorithmException{
        if (!this.account.getPassword().equals(c_password)) {
            showError("u_password", "Password not match");
        } else {
            this.account.setPassword(hash256(this.account.getPassword()));
            this.accountBean.updatePassword(account);
            showMSG("Password Updated");
        }
    }

    /**
     * Password encryption from plain text to hashcode Reference code link:
     * https://gist.github.com/avilches/750151
     *
     * @param data
     * @return Byte code
     * @throws NoSuchAlgorithmException
     */
    public static String hash256(String data) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(data.getBytes());
        return bytesToHex(md.digest());
    }

    /**
     * Password encryption from hash code to Hex Reference code link:
     * https://gist.github.com/avilches/750151
     *
     * @param bytes
     * @return Encrypted Password
     */
    public static String bytesToHex(byte[] bytes) {
        StringBuffer result = new StringBuffer();
        for (byte byt : bytes) {
            result.append(Integer.toString((byt & 0xff) + 0x100, 16).substring(1));
        }
        return result.toString();
    }

    /**
     * Used for user login
     *
     * @return Page after user login
     */
    public String login() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request
                = (HttpServletRequest) context.getExternalContext().getRequest();
        try {
            request.login(account.getUsername(), account.getPassword());
            return "/post/postlist?faces-redirect=true";
        } catch (ServletException e) {
            showError(null, "Incorrect username or password");
            return null;
        }
    }

    /**
     * Used for user logout
     *
     * @return Page after user logout
     * @throws ServletException
     */
    public String logout() throws ServletException {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request
                = (HttpServletRequest) context.getExternalContext().getRequest();
        request.logout();
        return "/user/login?faces-redirect=true";
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

    private void showMSG(String msg) {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(msg));
    }

    /**
     * @return the account
     */
    public Account getAccount() {
        return account;
    }

    /**
     * @param id use account id to retrive user account
     */
    public void setAccount(int id) {
        account = accountBean.getAccountByUserId(id);
    }

    /**
     * @return the c_password
     */
    public String getC_password() {
        return c_password;
    }

    /**
     * @param c_password the c_password to set
     */
    public void setC_password(String c_password) {
        this.c_password = c_password;
    }

    /**
     * @return the subscription
     */
    public Subscription getSubscription() {
        return subscription;
    }

    /**
     * @param subscription the subscription to set
     */
    public void setSubscription(Subscription subscription) {
        this.subscription = subscription;
    }

    /**
     * @return the service
     */
    public Service getService() {
        return service;
    }

    /**
     * @param service the service to set
     */
    public void setService(Service service) {
        this.service = service;
    }

    /**
     * get array of service type in servicetype enum
     * @return array of service type
     */
    public SERVICETYPE[] getServiceTypes() {
        return SERVICETYPE.values();
    }

    /**
     * @return the serviceType
     */
    public String getServiceType() {
        return serviceType;
    }

    /**
     * @param serviceType the serviceType to set
     */
    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
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
