package munchhunt.munchhuntproject.Profile;

import munchhunt.munchhuntproject.Objects.User;

/**
 * Created by kyl3g on 9/21/2018.
 */

public class FriendView {
    private int imageId;
    private String name;
    private User mUser;

    public User getUser() {
        return mUser;
    }

    public void setUser(User mUser) {
        this.mUser = mUser;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public FriendView(User mUser){
        this.mUser = mUser;
    }




}
