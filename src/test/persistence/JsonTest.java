package persistence;

import model.Movie;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {
    protected void checkMovie(String name, String time, int maxSeatNum, Movie movie) {
        assertEquals(name, movie.getName());
        assertEquals(time, movie.getTime());
        assertEquals(maxSeatNum - movie.getCustomerList().size(), movie.getMaxSeatNum());
    }
}
