package com.mattsencenbaugh.popularmovies.models;

import java.io.Serializable;

/**
 * Created by msencenb on 12/27/17.
 */

public class Video implements Serializable {
    private String name;
    private String site;
    private String type;
    private Integer size;
    private String key;

    public Video() {}

    public String getYoutubeLink() {
        return "https://www.youtube.com/watch?v=" + key;
    }

    public String getKey() {
        return key;
    }

    public String getName() {
        return name;
    }

    public Integer getSize() {
        return size;
    }

    public String getSite() {
        return site;
    }

    public String getType() {
        return type;
    }
}
