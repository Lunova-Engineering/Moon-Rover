package com.lunova.moonbot;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.lunova.moonbot.movies.Movie;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class DiscordBotTest {
    public static void main(String[] args) {
        InputStream inputStream = DiscordBotTest.class.getClassLoader().getResourceAsStream("movies.json");

        if (inputStream == null) {
            throw new IllegalArgumentException("File not found!");
        }
        try {
            // Read the file content using commons-IO
            //String jsonContent = FileUtils.readFileToString(file, "UTF-8");

            // Define the type for the list
            Type listType = new TypeToken<ArrayList<Movie>>(){}.getType();

            // Deserialize the JSON content into a list using Gson
            Gson gson = new Gson();
            ArrayList<Movie> movies = gson.fromJson(new InputStreamReader(inputStream), listType);

            // Print the deserialized list
            for (Movie movie : movies) {
                System.out.println("Title: " + movie.getTitle());
                System.out.println("Quotes: ");
                movie.getQuotes().forEach(quote -> System.out.println("        " + quote.getQuote() + " --- " + quote.getValue() + " words."));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
