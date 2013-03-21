package com.example.facebookfriendviewer.objects;

/**
 * Class that represents the Album object in database
 *
 * @author Witkowsky Dmitry
 */
public class Album {

    private String id;
    private String accId;
    private String albumId;
    private String name;
    private String pictureUrl;


    public String getAccId() {
        return accId;
    }

    public void setAccId(String accId) {
        this.accId = accId;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public String getAlbumId() {

        return albumId;
    }

    public void setAlbumId(String albumId) {
        this.albumId = albumId;
    }
}
