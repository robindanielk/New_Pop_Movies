package com.example.user.popularmoviesapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by user on 6/12/2017.
 */

public class MovieVideos
{
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("results")
    @Expose
    private List<MovieVideoResults> results = null;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<MovieVideoResults> getResults() {
        return results;
    }

    public void setResults(List<MovieVideoResults> results) {
        this.results = results;
    }
}

