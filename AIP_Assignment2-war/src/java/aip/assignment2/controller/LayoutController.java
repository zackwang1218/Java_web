package aip.assignment2.controller;

import aip.assignment2.domain.AccountFacade;
import aip.assignment2.domain.DataStoreException;
import aip.assignment2.domain.PostFacade;
import aip.assignment2.domain.SubscriptionFacade;
import aip.assignment2.entity.Account;
import aip.assignment2.entity.Subscription;
import aip.assignment2.utility.GROUP;
import aip.assignment2.utility.SERVICETYPE;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

/**
 * Layout controller to manage application's layout page
 * @author Cayden
 */
@ManagedBean(name = "layoutControler")
@RequestScoped
public class LayoutController {

    private Account account;

    @EJB
    private AccountFacade accountService;
    @EJB
    private SubscriptionFacade subscriptionBean;
    @EJB
    private PostFacade postBean;

    @PostConstruct
    public void InitModel() {
        account = new Account();
    }

    /**
     * Get current User; username and display on the page
     * @return login user first name
     * @throws DataStoreException throws when server cannot receive logged in user
     * status
     */
    public Account getAccount() throws DataStoreException {
        String currentUsername = FacesContext.getCurrentInstance().getExternalContext().getRemoteUser();
        account = accountService.getAccountByUsername(currentUsername);
        return account;
    }

    /**
     * check whether the current logged in use is administrator group or user group
     * @return ture if current logged in user is administrator user group
     * @throws DataStoreException throws when server cannot receive logged in user
     * status
     */
    public boolean isAdminAccount() throws DataStoreException {
        if (this.getAccount() != null && account.getGroupname().equals(GROUP.Administrator)) {
            return true;
        } else {
            return false;
        }
    }
    
    /**
     * Check if current logged in user level reached its maximum post limit
     * @return true if user haven't reached maximum post limit
     * @throws DataStoreException throws when server cannot receive logged in user
     * status
     */
    public boolean isMaxiumPost() throws DataStoreException {
        Account tempUser = getAccount();
        Subscription tempSubscription = subscriptionBean.getUserActivatedSubscription(tempUser);
        if (tempSubscription.getService().getName() == SERVICETYPE.FREE) {
            //Free Account can only post maximun 1 post
            if (postBean.getUserTotalPostCount(tempUser)<1) {
                return true;
            }else{
                return false;
            }
        } else if (tempSubscription.getService().getName() == SERVICETYPE.STANDARD) {
            //Maxium 10 posts avaliable for standard user
            if (postBean.getUserTotalPostCount(tempUser) < 10) {
                return true;
            } else {
                return false;
            }
        } else if (tempSubscription.getService().getName() == SERVICETYPE.PREMIUM) {
            //Maxium 50 posts avaliable for premium user
            if (postBean.getUserStickPostCount(tempUser) < 50) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

}
