package com.production.hitesh.foobar;

import android.graphics.Bitmap;

/**
 * Created by hitesh on 2/25/18.
 */

public class Data {
    private String friend_username;
    private String friend_name;
    private Bitmap profile;
    private String message;
    private String time;
private String last_message;
private Bitmap send_rec;
private String username;
    public Bitmap getSend_rec() {
        return send_rec;
    }

    public void setSend_rec(Bitmap send_rec) {
        this.send_rec = send_rec;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    //for message recieve
    public Data(String friend_name, Bitmap profile, String time, String message) {
        this.friend_name = friend_name;
        this.profile = profile;
        this.time = time;
        this.message = message;
    }

    public Data(String friend_name, Bitmap profile, String last_message, Bitmap send_rec, String username) {
        this.send_rec=send_rec;
this.username=username;

        this.friend_name = friend_name;
        this.profile = profile;
        this.last_message = last_message;
    }

    public String getLast_message() {
        return last_message;
    }

    public void setLast_message(String last_message) {
        this.last_message = last_message;
    }

    public Data(String friend_name, Bitmap profile) {
        this.friend_name = friend_name;
        this.profile = profile;
    }

    public Bitmap getProfile() {
        return profile;
    }

    public void setProfile(Bitmap profile) {
        this.profile = profile;
    }

    public String getFriend_name() {
        return friend_name;
    }

    public void setFriend_name(String friend_name) {
        this.friend_name = friend_name;
    }

    public Data(String friend_username, String friend_name,Bitmap profile) {

        this.friend_username = friend_username;
        this.profile=profile;
        this.friend_name = friend_name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Data(String message, String time) {

        this.message = message;
        this.time = time;
    }

    public String getFriend_username() {

        return friend_username;
    }

    public void setFriend_username(String friend) {
        this.friend_username = friend;
    }

    public Data(String friend) {
        this.friend_username = friend;
    }
}
