package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.LinkedList;

// Represents a cinema schedule with a list of movies
// with maximum size MAX_SIZE
public class CinemaSchedule implements Writable {

    public static final int MAX_SIZE = 10;
    private final LinkedList<Movie> schedule;
    private String name;

    // EFFECTS: construct a new cinema schedule that is empty
    public CinemaSchedule() {
        schedule = new LinkedList<>();
    }

    // MODIFIES: this
    // EFFECTS:  add a movie to the schedule and return true if the schedule is not full,
    //           otherwise return false
    public boolean addMovie(Movie movie) {
        if (schedule.size() < MAX_SIZE) {
            schedule.add(movie);
            EventLog.getInstance().logEvent(new Event("Added movie: " + movie.getName()));
            return true;
        } else {
            return false;
        }
    }

    // REQUIRES: the schedule is not empty and the movie is in the schedule
    // MODIFIES: this
    // EFFECTS:  delete the movie from the schedule
    public void deleteMovie(Movie movie) {
        schedule.remove(movie);
        EventLog.getInstance().logEvent(new Event("Removed movie: " + movie.getName()));
    }

    // EFFECTS: get a list of movies in the schedule
    public LinkedList<Movie> getMovies() {
        return schedule;
    }

    // REQUIRES: the schedule is not empty
    // MODIFIES: this
    // EFFECTS:  remove the movie at the front of the schedule and return this movie
    public Movie getNextMovie() {
        return schedule.removeFirst();
    }

    // EFFECTS: return the movie of the given index
    public Movie getMovieByIndex(int i) {
        return schedule.get(i);
    }

    // EFFECTS: get the length of the schedule
    public int length() {
        return schedule.size();
    }

    // EFFECTS: return true if the schedule is empty
    public boolean isEmpty() {
        return schedule.size() == 0;
    }

    // EFFECTS: return true if movie is in the schedule
    public boolean containMovie(Movie movie) {
        return schedule.contains(movie);
    }

    // EFFECTS: return the schedule with a list of movie names as Json object
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("movieNames", moviesToJson());
        return json;
    }

    // EFFECTS: returns movies in this schedule as a JSON array
    private JSONArray moviesToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Movie movie : schedule) {
            jsonArray.put(movie.toJson());
        }

        return jsonArray;
    }
}
