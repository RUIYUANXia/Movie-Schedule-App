package model;

import org.json.JSONObject;
import persistence.Writable;

// Represents a customer having a name
public class Customer implements Writable {
    private String name;

    // EFFECTS: customer has a name
    public Customer(String name) {
        this.name = name;
    }

    // EFFECTS: return customer name
    public String getName() {
        return name;
    }

    // MODIFIES: this
    // EFFECTS: set customer name
    public void setName(String newName) {
        this.name = newName;
    }

    // EFFECTS: return the customer as Json object
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("customerName", name);
        return json;
    }
}
