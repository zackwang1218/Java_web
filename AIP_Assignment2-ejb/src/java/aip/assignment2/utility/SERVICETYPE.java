package aip.assignment2.utility;

/**
 * The modified Servicetype enum to represent the type of service in the subscription 
 * table and friendly showing label to user in JSF page
 * @author Cayden
 */
public enum SERVICETYPE {
    FREE("free"),
    STANDARD("standard"),
    PREMIUM("premium");
    
    private String label;

    /**
     * In purpose of using enum in JSF dropdownlist, set label in a string 
     * that represent current Servicetype
     * @param label followed by enum value
     */
    private SERVICETYPE(String label) {
        this.label = label;
    }

    /**
     * Get current label's value in string
     * @return a string of one label
     */
    public String getLabel() {
        return label;
    }
}
