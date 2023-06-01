package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CustomerTest {
    private Customer customer1;
    private Customer customer2;

    @BeforeEach
    void runBefore() {
        customer1 = new Customer("Jerry");
        customer2 = new Customer("Tom");
    }

    @Test
    void getNameTest() {
        assertEquals("Jerry", customer1.getName());
        assertEquals("Tom", customer2.getName());
    }

    @Test
    void setNameTest() {
        customer1.setName("Jacky");
        assertEquals("Jacky", customer1.getName());
        customer1.setName("Ophelia");
        assertEquals("Ophelia", customer1.getName());
    }


}
