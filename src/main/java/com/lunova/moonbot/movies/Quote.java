package com.lunova.moonbot.movies;

/**
 * Represents a quote from a movie along with its associated value.
 * <p>
 * Each quote object consists of the actual quote and a value, which might represent
 * its popularity, relevance, or any other metric.
 * </p>
 *
 * @author Drake - <a href="https://github.com/metorrite">GitHub</a>
 * @project Moon-Bot
 * @since 10.12.2023
 */
public class Quote {

    /**
     * The content of the quote.
     */
    private String quote;

    /**
     * The value associated with this quote.
     */
    private int value;

    /**
     * Constructs a quote object with the specified content and value.
     *
     * @param quote the content of the quote.
     * @param value the value associated with this quote.
     */
    public Quote(String quote, int value) {
        this.quote = quote;
        this.value = value;
    }

    /**
     * Retrieves the content of the quote.
     *
     * @return the quote content.
     */
    public String getQuote() {
        return quote;
    }

    /**
     * Retrieves the value associated with the quote.
     *
     * @return the quote's value.
     */
    public int getValue() {
        return value;
    }

}
