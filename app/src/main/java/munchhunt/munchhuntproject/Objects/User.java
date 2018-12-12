package munchhunt.munchhuntproject.Objects;

import java.util.ArrayList;
import java.util.List;

public class User {
    private String email;
    private String password;
    private String id;
    private String firebaseId;
    private String name;
    private List<Request> requests = new ArrayList<Request>();

    private List<String> friendsList = new ArrayList<String>();
    private DietPattern diet = new DietPattern();
    private List<History> history = new ArrayList<History>();

    public User(String id, String firebaseId, String name, String password, String email, List<String> friendsList, DietPattern diet, List<History> history)
    {
        this.id = id;
        this.name = name;
        this.password = password;
        this.email = email;
        this.friendsList = friendsList;
        this.diet = diet;
        this.history = history;
        Request request = new Request();
        this.requests.add(request);
        this.firebaseId = firebaseId;

    }



    public User()
    {
        Request request = new Request();
        this.requests.add(request);
    }


    public String getPassword()
    {
        return password;
    }
    public String getId()
    {
        return id;
    }
    public String getEmail()
    {
        return email;
    }
    public void setEmail(String email)
    {
        this.email = email;
    }
    public void setPassword(String password)
    {
        this.password = password;
    }
    public void setId(String id)
    {
        this.id = id;
    }




    public List<String> getFriendsList() {
        return friendsList;
    }

    public void setFriendsList(List<String> friendsList) {
        this.friendsList = friendsList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addFriend(String friendID){
        if(!friendsList.contains(friendID))
            friendsList.add(friendID);
    }

    public void removeFriend(String friendID){
        if(friendsList.contains(friendID)){
            friendsList.remove(friendID);
        }
    }

    public DietPattern getDiet() {
        return diet;
    }

    public void setDiet(DietPattern diet) {
        this.diet = diet;
    }

    public List<History> getHistory() {
        return history;
    }
    public void setHistory(List<History> history)
    {
        this.history = history;
    }

    public String toString(){
        return email + password + id + name + friendsList.toString() + diet.toString();
    }

    public void setRequests(List<Request> requests){
        this.requests = requests;
    }
    public List<Request> getRequests() {
        return requests;
    }

    public boolean hasFriendRequest(String senderID){
        boolean flag = false;
        for(int i = 0; i < requests.size(); i++){
            if(requests.get(i).getType() == 0){
                if(requests.get(i).getSenderID().equals(senderID)){
                    flag = true;
                    break;
                }
                else
                    flag = false;
            }
            else
                flag = false;
        }
        return flag;
    }

    public void addFriendRequest(String senderID){

        if(!hasFriendRequest(senderID)){
            Request friendRequest = new Request(0, senderID);
            requests.add(friendRequest);
        }
    }

    public void removeFriendRequest(String senderID){
        if (hasFriendRequest(senderID)){
            for(int i = 0; i < requests.size(); i++){
                if(requests.get(i).getType() == 0){
                    if(requests.get(i).getSenderID().equals(senderID)){
                        requests.remove(i);
                        break;
                    }
                }
            }
        }

    }

    public boolean hasPartyRequest(String senderID){
        boolean flag = false;
        for(int i = 0; i < requests.size(); i++){
            if(requests.get(i).getType() == 1){
                if(requests.get(i).getSenderID().equals(senderID)){
                    flag = true;
                    break;
                }
                else
                    flag = false;
            }
            else
                flag = false;
        }
        return flag;
    }


    public void addPartyRequest(String senderID){

        if(!hasFriendRequest(senderID)){
            Request friendRequest = new Request(1, senderID);
            requests.add(friendRequest);
        }
    }

    public void removePartyRequest(String senderID){
        if (hasPartyRequest(senderID)){
            for(int i = 0; i < requests.size(); i++){
                if(requests.get(i).getType() == 1){
                    if(requests.get(i).getSenderID().equals(senderID)){
                        requests.remove(i);
                        break;
                    }
                }
            }
        }

    }

    public String getFirebaseId() {
        return firebaseId;
    }

    public void setFirebaseId(String firebaseId) {
        this.firebaseId = firebaseId;
    }
}