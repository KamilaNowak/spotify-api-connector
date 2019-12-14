package com.nowak.spotifyapiconnector.database.entities;

import com.nowak.spotifyapiconnector.json_objects.pojos.users.ExternalUrls;
import com.nowak.spotifyapiconnector.json_objects.pojos.users.Followers;
import lombok.Getter;

import javax.persistence.*;
import java.util.List;

@Getter
@Entity
@Table(name="usersdata")
public class UserDataEntity {

    @Id
    @Column(name="id")
    private String id;

    @Column(name="displayname")
    private String displayName;

    @Column(name="href")
    private String href;

    @Column(name="type")
    private String type;

    @Column(name="uri")
    private String uri;

    @Column(name="images")
    private String images;

    @Column(name="followers")
    private int followers;

    @Column(name="externalurl")
    private String externalUrls;

    public String getDisplayName() {
        return displayName;
    }

    public UserDataEntity(String id, String displayName, String href, String type, String uri, String images, int followers, String externalUrls) {
        this.id = id;
        this.displayName = displayName;
        this.href = href;
        this.type = type;
        this.uri = uri;
        this.images = images;
        this.followers = followers;
        this.externalUrls = externalUrls;
    }

    public UserDataEntity() {
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }


    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public int getFollowers() {
        return followers;
    }

    public void setFollowers(int followers) {
        this.followers = followers;
    }

    public String getExternalUrls() {
        return externalUrls;
    }

    public void setExternalUrls(String externalUrls) {
        this.externalUrls = externalUrls;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }
}
