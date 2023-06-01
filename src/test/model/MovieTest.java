package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

class MovieTest {
    private Movie movie1;
    private Movie movie2;

    private Customer customer1;
    private Customer customer2;
    private Customer customer3;
    private Customer customer4;
    private Customer customer5;
    private Customer customer6;
    private Customer customer7;
    private Customer customer8;
    private Customer customer9;
    private Customer customer10;

    @BeforeEach
    void runBefore() {
        movie1 = new Movie("The Avengers I", "12:00", 10, new LinkedList<>());
        movie2 = new Movie("The Avengers II", "16:00", 20, new LinkedList<>());
        customer1 = new Customer("A");
        customer2 = new Customer("B");
        customer3 = new Customer("C");
        customer4 = new Customer("D");
        customer5 = new Customer("E");
        customer6 = new Customer("F");
        customer7 = new Customer("G");
        customer8 = new Customer("H");
        customer9 = new Customer("I");
        customer10 = new Customer("J");
    }

    @Test
    void constructorTest() {
        assertEquals("The Avengers I", movie1.getName());
        assertEquals("12:00", movie1.getTime());
        assertEquals(10, movie1.getMaxSeatNum());
        assertEquals(0, movie1.getCustomerList().size());
    }

    @Test
    void addCustomerEmptyTest() {
        assertTrue(movie1.isEmpty());
        assertTrue(movie1.addCustomer(customer1));
        assertEquals(1, movie1.getCustomerList().size());
    }

    @Test
    void addCustomerNotEmptyNotFullTest() {
        assertTrue(movie1.isEmpty());
        createMovieWithFiveCustomer();
        assertEquals(5, movie1.getCustomerList().size());
        assertTrue(movie1.addCustomer(customer6));
    }

    @Test
    void addCustomerFullTest() {
        assertTrue(movie1.isEmpty());
        createMovieWithTenCustomer();
        assertEquals(10, movie1.getCustomerList().size());
        assertFalse(movie1.addCustomer(customer1));
    }

    @Test
    void getNameTest() {
        assertEquals("The Avengers I", movie1.getName());
        assertEquals("The Avengers II", movie2.getName());
    }

    @Test
    void getTimeTest() {
        assertEquals("12:00", movie1.getTime());
        assertEquals("16:00", movie2.getTime());
    }

    @Test
    void getMaxSeatNumTest() {
        assertEquals(10, movie1.getMaxSeatNum());
        assertEquals(20, movie2.getMaxSeatNum());
    }

    @Test
    void getCustomerListTest() {
        assertEquals(0, movie1.getCustomerList().size());
        movie1.addCustomer(customer1);
        assertEquals(1, movie1.getCustomerList().size());
        movie1.addCustomer(customer2);
        assertEquals(2, movie1.getCustomerList().size());
    }

    @Test
    void isEmptyTest() {
        assertTrue(movie1.isEmpty());
        movie1.addCustomer(customer6);
        assertFalse(movie1.isEmpty());
        createMovieWithFiveCustomer();
        assertFalse(movie1.isEmpty());
    }

    @Test
    void setNameTest() {
        movie1.setName("Happiness I");
        assertEquals("Happiness I", movie1.getName());
        movie1.setName("Happiness II");
        assertEquals("Happiness II", movie1.getName());
    }

    @Test
    void setTimeTest() {
        movie1.setTime("12:00");
        assertEquals("12:00", movie1.getTime());
        movie1.setTime("20:00");
        assertEquals("20:00", movie1.getTime());
    }

    @Test
    void setMaxSeatNumTest() {
        movie1.setMaxSeatNum(20);
        assertEquals(20, movie1.getMaxSeatNum());
        movie1.setMaxSeatNum(40);
        assertEquals(40, movie1.getMaxSeatNum());
    }

    @Test
    void getCustomerByIndexTest() {
        movie1.addCustomer(customer1);
        assertEquals(customer1, movie1.getCustomerByIndex(0));
        movie1.addCustomer(customer2);
        assertEquals(customer2, movie1.getCustomerByIndex(1));
    }


    private void createMovieWithFiveCustomer() {
        movie1.addCustomer(customer1);
        movie1.addCustomer(customer2);
        movie1.addCustomer(customer3);
        movie1.addCustomer(customer4);
        movie1.addCustomer(customer5);
    }

    private void createMovieWithTenCustomer() {
        movie1.addCustomer(customer1);
        movie1.addCustomer(customer2);
        movie1.addCustomer(customer3);
        movie1.addCustomer(customer4);
        movie1.addCustomer(customer5);
        movie1.addCustomer(customer6);
        movie1.addCustomer(customer7);
        movie1.addCustomer(customer8);
        movie1.addCustomer(customer9);
        movie1.addCustomer(customer10);
    }
}
