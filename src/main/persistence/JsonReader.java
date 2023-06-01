package persistence;

import model.CinemaSchedule;
import model.Customer;
import model.Movie;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.stream.Stream;

// Represents a reader that reads cinema schedule from JSON data stored in file
public class JsonReader {
    private final String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads cinema schedule from file and returns it;
    // throws IOException if an error occurs reading data from file
    public CinemaSchedule read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseCinemaSchedule(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses cinema schedule from JSON object and returns it
    private CinemaSchedule parseCinemaSchedule(JSONObject jsonObject) {
        CinemaSchedule schedule = new CinemaSchedule();
        addMovies(schedule, jsonObject);
        return schedule;
    }

    // MODIFIES: schedule
    // EFFECTS: parses movies from JSON object and adds them to cinema schedule
    private void addMovies(CinemaSchedule schedule, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("movieNames");
        for (Object json : jsonArray) {
            JSONObject nextMovie = (JSONObject) json;
            addMovie(schedule, nextMovie);
        }
    }

    // MODIFIES: schedule
    // EFFECTS: parses movie from JSON object and adds it to cinema schedule
    private void addMovie(CinemaSchedule schedule, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        String time = jsonObject.getString("time");
        int maxSeatNum = jsonObject.getInt("maxSeatNum");

        Movie movie = new Movie(name, time, maxSeatNum, new LinkedList<>());
        addCustomers(movie, jsonObject);

        schedule.addMovie(movie);
    }

    // MODIFIES: movie
    // EFFECTS: parses customers from JSON object and adds them to the movie
    private void addCustomers(Movie movie, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("customerList");
        for (Object json : jsonArray) {
            JSONObject nextCustomer = (JSONObject) json;
            addCustomer(movie, nextCustomer);
        }
    }

    // MODIFIES: movie
    // EFFECTS: parses customer from JSON object and adds it to the movie
    private void addCustomer(Movie movie, JSONObject jsonObject) {
        String name = jsonObject.getString("customerName");
        Customer customer = new Customer(name);
        movie.addCustomer(customer);
    }
}
