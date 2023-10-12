package com.lunova.moonbot.movies;

import java.util.List;

/**
 * Represents a movie with its title and associated quotes.
 * <p>
 * Each movie object consists of a title and a list of quotes from that movie.
 * This provides a structured way to capture and retrieve movie-related data.
 * </p>
 *
 * @author Drake - <a href="https://github.com/metorrite">GitHub</a>
 * @project Moon-Bot
 * @since 10.12.2023
 */
public class Movie {

    /**
     * The title of the movie.
     */
    private String title;

    /**
     * A list of quotes associated with this movie.
     */
    private List<Quote> quotes;

    /**
     * Constructs a movie object with a specified title and associated quotes.
     *
     * @param title  the title of the movie.
     * @param quotes the list of quotes from this movie.
     */
    public Movie(String title, List<Quote> quotes) {
        this.title = title;
        this.quotes = quotes;
    }

    /**
     * Retrieves the title of the movie.
     *
     * @return the movie title.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Retrieves the list of quotes from the movie.
     *
     * @return the list of movie quotes.
     */
    public List<Quote> getQuotes() {
        return quotes;
    }

}
