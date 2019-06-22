package com.justincamp.demo.models;

import java.util.Objects;

public class Release {

    public int id;
    public String title;
    public int year;
    public String thumb;

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Release)) return false;
        Release other = (Release)o;
        return id == other.id &&
                year == other.year &&
                Objects.equals(title, other.title) &&
                Objects.equals(thumb, other.thumb);
    }

}
