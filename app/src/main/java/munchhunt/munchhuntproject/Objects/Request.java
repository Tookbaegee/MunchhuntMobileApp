package munchhunt.munchhuntproject.Objects;

public class Request {
    //type: 0 friend, 1 party
    private int type = 0;
    private String senderID = "";

    public Request(){
    }

    public Request(int type, String senderID){
        this.type = type;
        this. senderID = senderID;
    }


    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getSenderID() {
        return senderID;
    }

    public void setSenderID(String senderID) {
        this.senderID = senderID;
    }
}
