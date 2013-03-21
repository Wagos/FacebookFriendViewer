package com.example.facebookfriendviewer.objects;

/**
 * Class that represents the Friend object in database
 *
 * @author Witkowsky Dmitry
 */
public class Friend {

    private String id;
    private String profileImageUrl;
    private String name;
    private String accID;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAccID() {
        return accID;
    }

    public void setAccID(String accID) {
        this.accID = accID;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfileImageUrl() {

        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }
}
