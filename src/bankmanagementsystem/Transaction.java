package bankmanagementsystem;

public class Transaction {

    private String senderEmail;
    private String receiverEmail;
    private String receiveAmount;
    private String receiveTime;

    public Transaction(String senderEmail, String receiverEmail, String receiveAmount, String receiveTime) {
        this.senderEmail = senderEmail;
        this.receiverEmail = receiverEmail;
        this.receiveAmount = receiveAmount;
        this.receiveTime = receiveTime;
    }

    // Getters and setters
    public String getSenderEmail() {
        return senderEmail;
    }

    public void setSenderEmail(String senderEmail) {
        this.senderEmail = senderEmail;
    }
    
        public String getReceiverEmail() {
        return receiverEmail;
    }

    public void setReceiverEmail(String receiverEmail) {
        this.receiverEmail = receiverEmail;
    }
    

public String getReceiveAmount() {
    return receiveAmount;
}

public void setReceiveAmount(String receiveAmount) {
    this.receiveAmount = receiveAmount;
}

public String getReceiveTime() {
    return receiveTime;
}

public void setReceiveTime(String receiveTime) {
    this.receiveTime = receiveTime;
}

}
