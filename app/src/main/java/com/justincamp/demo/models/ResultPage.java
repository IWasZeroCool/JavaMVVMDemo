package com.justincamp.demo.models;

import com.google.gson.annotations.SerializedName;

public class ResultPage {

    @SerializedName("per_page")
    public int perPage;
    public int items;
    public int page;
    public int pages;

}
