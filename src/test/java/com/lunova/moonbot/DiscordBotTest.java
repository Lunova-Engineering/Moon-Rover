package com.lunova.moonbot;

import com.lunova.moonbot.commands.CommandManager;

public class DiscordBotTest {

    public static void main(String[] args) throws Exception {
        CommandManager.initializeCommands();
        CommandManager.COMMANDS.stream().forEach(c -> System.out.println(c.getName()));
/*        InputStream inputStream = DiscordBotTest.class.getClassLoader().getResourceAsStream("movies.json");

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
        }*/
    }

}
