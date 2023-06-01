package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.LinkedList;

// Represents a movie having a time, maximum seat number and a list of customers
public class Movie implements Writable {
    private final LinkedList<Customer> customerList;
    private String name;
    private String time;
    private int maxSeatNum;

    // EFFECTS: movie has a given time, maximum seat number and a list of customers
    public Movie(String name, String time, int maxSeatNum, LinkedList<Customer> customerList) {
        this.name = name;
        this.time = time;
        this.maxSeatNum = maxSeatNum;
        this.customerList = customerList;
    }

    // MODIFIES: this
    // EFFECTS:  add a customer to the movie and return true if the movie is not full,
    //           otherwise return false
    public Boolean addCustomer(Customer customer) {
        if (maxSeatNum > 0) {
            customerList.add(customer);
            maxSeatNum--;
            return true;
        } else {
            return false;
        }
    }

    // EFFECTS: return movie name
    public String getName() {
        return name;
    }

    // MODIFIES: this
    // EFFECTS: set the movie name to a given name
    public void setName(String newName) {
        this.name = newName;
    }

    // EFFECTS: return movie time
    public String getTime() {
        return time;
    }

    // MODIFIES: this
    // EFFECTS: set the movie time to a given time
    public void setTime(String newTime) {
        this.time = newTime;
    }

    // EFFECTS: return maximum seat number
    public int getMaxSeatNum() {
        return maxSeatNum;
    }

    // MODIFIES: this
    // EFFECTS: set the maximum seat number of a movie to a given amount
    public void setMaxSeatNum(int newMaxSeatNum) {
        this.maxSeatNum = newMaxSeatNum;
    }

    // EFFECTS: return a list of customers
    public LinkedList<Customer> getCustomerList() {
        return customerList;
    }

    // EFFECTS: return true if the customer list is empty
    public boolean isEmpty() {
        return customerList.size() == 0;
    }

    // EFFECTS: return the customer of the given index
    public Customer getCustomerByIndex(int i) {
        return customerList.get(i);
    }

    // EFFECTS: return the movie as Json object
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("time", time);
        json.put("maxSeatNum", maxSeatNum);
        json.put("customerList", customersToJson());
        return json;
    }

    // EFFECTS: returns customers in this movie as a JSON array
    private JSONArray customersToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Customer c : customerList) {
            jsonArray.put(c.toJson());
        }

        return jsonArray;
    }
}