package com.justincamp.demo.models;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

public class Artist {

    public int id;
    public String name;
    @SerializedName("thumbnail_url")
    public String thumbnail;
    public boolean active;

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Artist)) return false;
        Artist other = (Artist) o;
        return id == other.id &&
                active == other.active &&
                Objects.equals(name, other.name) &&
                Objects.equals(thumbnail, other.thumbnail);
    }

}
