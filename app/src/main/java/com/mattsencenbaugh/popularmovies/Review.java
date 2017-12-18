package com.mattsencenbaugh.popularmovies;

import java.io.Serializable;

/**
 * Created by msencenb on 12/18/17.
 */

public class Review implements Serializable {
    private String author;
    private String content;

    public Review() {}

    public String getAuthor() {
        return author;
    }

    public String getContent() {
        return content;
    }
}
