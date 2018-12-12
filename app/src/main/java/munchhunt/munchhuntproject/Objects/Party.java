package munchhunt.munchhuntproject.Objects;

import java.util.ArrayList;
import java.util.List;

public class Party {

    private List<String> party = new ArrayList<String>();
    private String id;

    public Party(){
        id = "";
        party.add("");
    }

    public void setparty(List<String> userArray){
        party = userArray;

    }

    public List<String> getparty(){
        return party;
    }

    public void addToParty(String userToBeInvitedID){
        party.add(userToBeInvitedID);
    }
/*
    public void addAllToParty(User currentUser){
        List<User> allfriends = currentUser.getFriendsList();
        for(int i = 0; i < allfriends.size(); i++){
            if(checkInParty(currentUser) && !checkInParty(allfriends.get(i)) && checkMutual(currentUser, allfriends.get(i))){
                addToParty(currentUser, allfriends.get(i));
            }
        }
    }
*/


    public void leaveParty(String currentUserID){
        party.remove(currentUserID);
    }

    public boolean checkLastMember(){
        boolean last = false;
        if(party.size() == 1){
            last = true;
        }

        return last;
    }

    public void setEmpty(){

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}