package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

public class CinemaScheduleTest {
    private CinemaSchedule schedule1;
    private CinemaSchedule schedule2;
    private Movie movie1;
    private Movie movie2;
    private Movie movie3;
    private Movie movie4;
    private Movie movie5;
    private Movie movie6;
    private Movie movie7;
    private Movie movie8;
    private Movie movie9;
    private Movie movie10;

    @BeforeEach
    void runBefore() {
        schedule1 = new CinemaSchedule();
        schedule2 = new CinemaSchedule();
        movie1 = new Movie("The Avengers I", "12:00", 10, new LinkedList<>());
        movie2 = new Movie("The Avengers II", "16:00", 20, new LinkedList<>());
        movie3 = new Movie("The Avengers III", "11:00", 10, new LinkedList<>());
        movie4 = new Movie("The Avengers IV", "9:00", 20, new LinkedList<>());
        movie5 = new Movie("The Avengers V", "8:00", 10, new LinkedList<>());
        movie6 = new Movie("Captain America I", "13:00", 20, new LinkedList<>());
        movie7 = new Movie("Captain America II", "14:00", 10, new LinkedList<>());
        movie8 = new Movie("Spider Man I", "15:00", 20, new LinkedList<>());
        movie9 = new Movie("Spider Man II", "18:00", 10, new LinkedList<>());
        movie10 = new Movie("Iron Man I", "20:00", 20, new LinkedList<>());
    }

    @Test
    void addMovieEmptyTest() {
        assertTrue(schedule1.isEmpty());
        assertTrue(schedule1.addMovie(movie1));
        assertEquals(1, schedule1.length());
        assertEquals(movie1, schedule1.getMovieByIndex(0));
    }

    @Test
    void addMovieNotEmptyNotFullTest() {
        assertTrue(schedule1.isEmpty());
        createScheduleWithFiveMovies();
        assertEquals(5, schedule1.length());
        assertTrue(schedule1.addMovie(movie1));
    }

    @Test
    void addMovieFullTest() {
        assertTrue(schedule1.isEmpty());
        createScheduleWithTenMovies();
        assertEquals(10, schedule1.length());
        assertFalse(schedule1.addMovie(movie1));
    }

    @Test
    void deleteMovieOnceTest() {
        assertTrue(schedule1.isEmpty());
        createScheduleWithFiveMovies();
        schedule1.deleteMovie(movie1);
        assertEquals(4, schedule1.length());
    }

    @Test
    void deleteMovieTwiceTest() {
        assertTrue(schedule1.isEmpty());
        createScheduleWithFiveMovies();
        schedule1.deleteMovie(movie1);
        schedule1.deleteMovie(movie2);
        assertEquals(3, schedule1.length());
    }

    @Test
    void getScheduleWithLengthOneTest() {
        assertTrue(schedule1.isEmpty());
        schedule1.addMovie(movie1);
        assertEquals(1, schedule1.getMovies().size());
        assertTrue(schedule1.containMovie(movie1));
    }

    @Test
    void getScheduleWithLengthFiveTest() {
        assertTrue(schedule1.isEmpty());
        createScheduleWithFiveMovies();
        assertEquals(5, schedule1.getMovies().size());
        assertTrue(schedule1.containMovie(movie1));
        assertTrue(schedule1.containMovie(movie2));
        assertTrue(schedule1.containMovie(movie3));
        assertTrue(schedule1.containMovie(movie4));
        assertTrue(schedule1.containMovie(movie5));
    }

    @Test
    void getNextMovieOnceTest() {
        schedule1.addMovie(movie1);
        assertEquals(1, schedule1.length());
        assertEquals(movie1, schedule1.getNextMovie());
        assertEquals(0, schedule1.length());
    }

    @Test
    void getNextMovieTwiceTest() {
        assertTrue(schedule1.isEmpty());
        createScheduleWithTenMovies();
        assertEquals(movie1, schedule1.getNextMovie());
        assertEquals(9, schedule1.length());

        assertEquals(movie2, schedule1.getNextMovie());
        assertEquals(8, schedule1.length());
    }

    @Test
    void getMovieByIndexTest() {
        schedule1.addMovie(movie1);
        assertEquals(movie1, schedule1.getMovieByIndex(0));
        schedule1.addMovie(movie2);
        assertEquals(movie2, schedule1.getMovieByIndex(1));
    }

    @Test
    void lengthTest() {
        assertTrue(schedule1.isEmpty());
        schedule1.addMovie(movie1);
        assertEquals(1, schedule1.length());
        schedule1.addMovie(movie2);
        schedule1.addMovie(movie3);
        assertEquals(3, schedule1.length());
    }

    @Test
    void isEmptyTest() {
        assertTrue(schedule1.isEmpty());
        schedule1.addMovie(movie6);
        assertFalse(schedule1.isEmpty());
        createScheduleWithFiveMovies();
        assertFalse(schedule1.isEmpty());
    }

    @Test
    void containMovieTest() {
        assertTrue(schedule1.isEmpty());
        schedule1.addMovie(movie6);
        assertTrue(schedule1.containMovie(movie6));
        assertFalse(schedule1.containMovie(movie1));
        schedule1.deleteMovie(movie6);
        assertFalse(schedule1.containMovie(movie6));
    }

    private void createScheduleWithFiveMovies() {
        schedule1.addMovie(movie1);
        schedule1.addMovie(movie2);
        schedule1.addMovie(movie3);
        schedule1.addMovie(movie4);
        schedule1.addMovie(movie5);
    }

    private void createScheduleWithTenMovies() {
        schedule1.addMovie(movie1);
        schedule1.addMovie(movie2);
        schedule1.addMovie(movie3);
        schedule1.addMovie(movie4);
        schedule1.addMovie(movie5);
        schedule1.addMovie(movie6);
        schedule1.addMovie(movie7);
        schedule1.addMovie(movie8);
        schedule1.addMovie(movie9);
        schedule1.addMovie(movie10);
    }
}
