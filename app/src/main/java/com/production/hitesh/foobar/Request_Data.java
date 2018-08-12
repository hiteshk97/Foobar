package com.production.hitesh.foobar;

import android.graphics.Bitmap;

/**
 * Created by hitesh on 3/23/18.
 */

public class Request_Data {
    private String username;
    private String  name;
    private Bitmap profie;

    public Request_Data(String username, String name, Bitmap profie) {
        this.username = username;
        this.name = name;
        this.profie = profie;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Bitmap getProfie() {
        return profie;
    }

    public void setProfie(Bitmap profie) {
        this.profie = profie;
    }
}
