package persistence;

import model.CinemaSchedule;
import model.Movie;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            CinemaSchedule schedule = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptySchedule() {
        JsonReader reader = new JsonReader("./data/testReaderEmptySchedule.json");
        try {
            CinemaSchedule schedule = reader.read();
            assertEquals(0, schedule.length());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralSchedule() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralSchedule.json");
        try {
            CinemaSchedule schedule = reader.read();
            List<Movie> movies = schedule.getMovies();
            assertEquals(2, movies.size());
            checkMovie("The Avengers I", "12:00", 20, movies.get(0));
            checkMovie("The Avengers II", "13:00", 10, movies.get(1));

            assertEquals(2, movies.get(0).getCustomerList().size());
            assertEquals("Jerry", movies.get(0).getCustomerByIndex(0).getName());
            assertEquals("Katherine", movies.get(0).getCustomerByIndex(1).getName());

            assertEquals(2, movies.get(1).getCustomerList().size());
            assertEquals("Jerry", movies.get(1).getCustomerByIndex(0).getName());
            assertEquals("Katherine", movies.get(1).getCustomerByIndex(1).getName());

        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
