///////////////////////////////////////////////////////////////////////
//	Name:	LoginAttempt
//	Desc:	An attempt to log into the server
//	Date:	2/7/2003 - Gabe Jones
//	TODO:
///////////////////////////////////////////////////////////////////////
package leo.shared;

// imports

public class LoginAttempt {

    /////////////////////////////////////////////////////////////////
    // Constants
    /////////////////////////////////////////////////////////////////
    public static final short EXISTING_ACCOUNT = 1;
    public static final short NEW_ACCOUNT = 2;
    public static final short EXISTING_OR_NEW = 3;
    private static final long serialVersionUID = 1L;


    /////////////////////////////////////////////////////////////////
    // Properties
    /////////////////////////////////////////////////////////////////
    private final String username;
    private final String password;
    private String email = "";
    private final short isNewAccount;
    private final String version;
    private boolean newsletter = false;


    /////////////////////////////////////////////////////////////////
    // Constructor
    /////////////////////////////////////////////////////////////////
    public LoginAttempt(String newUsername, String newPassword, short isNew, String theVersion) {
        username = newUsername;
        password = newPassword;
        email = "";
        isNewAccount = isNew;
        version = theVersion;
        newsletter = false;
    }

    public LoginAttempt(String newUsername, String newPassword, String newEmail, short isNew, String theVersion, boolean wantNews) {
        username = newUsername;
        password = newPassword;
        email = newEmail;
        isNewAccount = isNew;
        version = theVersion;
        newsletter = wantNews;
    }


    /////////////////////////////////////////////////////////////////
    // Fetch name
    /////////////////////////////////////////////////////////////////
    public String getUsername() {
        return username.toLowerCase();
    }


    /////////////////////////////////////////////////////////////////
    // Fetch password
    /////////////////////////////////////////////////////////////////
    public String getPassword() {
        return password;
    }


    /////////////////////////////////////////////////////////////////
    // Fetch email
    /////////////////////////////////////////////////////////////////
    public String getEmail() {
        return email;
    }


    /////////////////////////////////////////////////////////////////
    // Is this a new account
    /////////////////////////////////////////////////////////////////
    public short isNewAccount() {
        return isNewAccount;
    }


    /////////////////////////////////////////////////////////////////
    // newsletter
    /////////////////////////////////////////////////////////////////
    public boolean newsletter() {
        return newsletter;
    }


    /////////////////////////////////////////////////////////////////
    // Version?
    /////////////////////////////////////////////////////////////////
    public String getVersion() {
        return version;
    }

}
