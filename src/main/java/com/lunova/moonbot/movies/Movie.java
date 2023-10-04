package com.lunova.moonbot.movies;

import java.util.List;

public class Movie {
    private String title;
    private List<Quote> quotes;

    public Movie(String title, List<Quote> quotes) {
        this.title = title;
        this.quotes = quotes;
    }

    public String getTitle() {
        return title;
    }

    public List<Quote> getQuotes() {
        return quotes;
    }
}
