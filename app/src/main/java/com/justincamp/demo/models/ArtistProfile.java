package com.justincamp.demo.models;

import java.util.ArrayList;

public class ArtistProfile {

    public int id;
    public String profile;
    public String name;
    public ArrayList<Artist> members;
    public ArrayList<ArtistProfileImage> images;

    public String getMainImageUri() {
        if (images == null || images.size() == 0) return null;
        for (ArtistProfileImage pi : images) {
            if (pi.type.equals("primary")) {
                return pi.uri;
            }
        }
        return images.get(0).uri; // Failsafe
    }

}
