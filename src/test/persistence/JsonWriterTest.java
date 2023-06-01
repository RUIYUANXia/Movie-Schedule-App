package persistence;

import model.CinemaSchedule;
import model.Customer;
import model.Movie;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonWriterTest extends JsonTest {

    @Test
    void testWriterInvalidFile() {
        try {
            CinemaSchedule schedule = new CinemaSchedule();
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyWorkroom() {
        try {
            CinemaSchedule schedule = new CinemaSchedule();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptySchedule.json");
            writer.open();
            writer.write(schedule);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptySchedule.json");
            schedule = reader.read();
            List<Movie> movies = schedule.getMovies();
            assertEquals(0, movies.size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralWorkroom() {
        try {
            CinemaSchedule schedule = new CinemaSchedule();
            Movie movie1 = new Movie("The Avengers I", "12:00", 10, new LinkedList<>());
            Movie movie2 = new Movie("The Avengers II", "16:00", 20, new LinkedList<>());
            schedule.addMovie(movie1);
            schedule.addMovie(movie2);
            Customer customer1 = new Customer("Jerry");
            Customer customer2 = new Customer("Katherine");
            movie1.addCustomer(customer1);
            movie1.addCustomer(customer2);
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralSchedule.json");
            writer.open();
            writer.write(schedule);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralSchedule.json");
            schedule = reader.read();
            assertEquals(2, schedule.length());
            checkMovie("The Avengers I", "12:00", 8, schedule.getMovieByIndex(0));
            checkMovie("The Avengers II", "16:00", 20, schedule.getMovieByIndex(1));

            assertEquals(2, schedule.getMovieByIndex(0).getCustomerList().size());
            assertEquals("Jerry", schedule.getMovieByIndex(0).getCustomerByIndex(0).getName());
            assertEquals("Katherine", schedule.getMovieByIndex(0).getCustomerByIndex(1).getName());

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
