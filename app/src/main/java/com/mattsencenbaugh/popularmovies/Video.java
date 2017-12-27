package com.mattsencenbaugh.popularmovies;

import java.io.Serializable;

/**
 * Created by msencenb on 12/27/17.
 */

public class Video implements Serializable {
    private String name;
    private String site;
    private String type;
    private Integer size;

    public Video() {}

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
