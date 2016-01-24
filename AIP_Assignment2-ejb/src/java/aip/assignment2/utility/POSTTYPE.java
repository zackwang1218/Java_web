package aip.assignment2.utility;

/**
 * The modified Posttype enum to represent the type of post in the post table and 
 * friendly showing label to user in JSF page
 * @author Cayden
 */
public enum POSTTYPE {
    RENTIN("Looking for room"),
    RENTOUT("Looking for flatmate");
    
    private String label;
    
    /**
     * In purpose of using enum in JSF dropdownlist, set label in a string 
     * that represent current Posttype
     * @param label followed by enum value
     */
    private POSTTYPE(String label){
        this.label = label;
    }
    
    /**
     * Get current label's value in string
     * @return a string of one label
     */
    public String getLabel(){
        return this.label;
    }
}
