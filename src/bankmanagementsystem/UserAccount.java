
package bankmanagementsystem;


public class UserAccount {
     private String userAccountName;
    private String userAccountEmail;
    private String userAccountDeposit;
    private String userAccountWithdraw;
    private String userAccountSend;
    private String userAccountReceive;

    // Constructor
    public UserAccount(String name, String email, String deposit, String withdraw, String send, String receive) {
       userAccountName = name;
     userAccountEmail = email;
userAccountDeposit = deposit;
    userAccountWithdraw = withdraw;
  userAccountSend = send;
      userAccountReceive = receive;
    }

    
  
 
 
 

    // Getters and setters
    public String getName() {
        return userAccountName;
    }

    public void setName(String name) {
        userAccountName = name;
    }

    public String getEmail() {
        return userAccountEmail;
    }

    public void setEmail(String email) {
    userAccountEmail= email;
    }

    public String getDeposit() {
        return userAccountDeposit;
    }

    public void setDeposit(String deposit) {
 userAccountDeposit = deposit;
    }

    public String getWithdraw() {
        return userAccountWithdraw;
    }

    public void setWithdraw(String withdraw) {
   userAccountWithdraw = withdraw;
    }

    public String getSend() {
        return userAccountSend;
    }

    public void setSend(String send) {
 userAccountSend = send;
    }

    public String getReceive() {
        return userAccountReceive;
    }

    public void setReceive(String receive) {
     userAccountReceive = receive;
    }
}
