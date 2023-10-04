package com.lunova.moonbot.movies;

public class Quote {
    private String quote;
    private int value;

    public Quote(String quote, int value) {
        this.quote = quote;
        this.value = value;
    }

    public String getQuote() {
        return quote;
    }

    public int getValue() {
        return value;
    }
}
