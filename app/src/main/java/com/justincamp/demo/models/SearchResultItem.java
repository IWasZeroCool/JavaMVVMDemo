package com.justincamp.demo.models;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

public class SearchResultItem {

    public int id;
    public String title;
    @SerializedName("thumb")
    public String thumbUrl;
    @SerializedName("cover_image")
    public String coverImage;

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof SearchResultItem)) return false;
        SearchResultItem other = (SearchResultItem)o;
        return id == other.id &&
                Objects.equals(title, other.title) &&
                Objects.equals(thumbUrl, other.thumbUrl) &&
                Objects.equals(coverImage, other.coverImage);
    }

}
