package ui;

import model.*;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Scanner;

// Movie application
public class MovieApp {
    private static final String JSON_STORE = "./data/cinemaSchedule.json";
    private final Customer customer1 = new Customer("Jerry");
    private final Customer customer2 = new Customer("Katherine");
    private CinemaSchedule schedule;
    private Scanner input;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;


    // EFFECTS: run the movie application
    public MovieApp() {
        runMovieApp();
    }

    // MODIFIES: this
    // EFFECTS: process user input
    private void runMovieApp() {
        boolean keepGoing = true;
        String command;

        init();

        while (keepGoing) {
            displayMenu();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("q")) {
                keepGoing = false;
            } else {
                processCommand(command);
            }
        }

        System.out.println("\nGoodbye!");
        for (Event event: EventLog.getInstance()) {
            System.out.println(event.getDate());
            System.out.println(event.getDescription());
        }
    }

    // MODIFIES: this
    // EFFECTS: initializes schedule
    private void init() {
        schedule = new CinemaSchedule();
        Movie theAvengersI = new Movie("The Avengers I", "12:00", 10, new LinkedList<>());
        Movie theAvengersII = new Movie("The Avengers II", "13:00", 10, new LinkedList<>());
        input = new Scanner(System.in);
        input.useDelimiter("\n");
        theAvengersI.addCustomer(customer1);
        theAvengersI.addCustomer(customer2);
        theAvengersII.addCustomer(customer1);
        schedule.addMovie(theAvengersI);
        schedule.addMovie(theAvengersII);
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
    }

    // EFFECTS: displays menu of options to user
    private void displayMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\tv -> view movie schedule");
        System.out.println("\ta -> add a movie to schedule");
        System.out.println("\td -> delete a movie from schedule");
        System.out.println("\ts -> select a movie from schedule to view customer list");
        System.out.println("\tsave -> save work room to file");
        System.out.println("\tload -> load work room from file");
        System.out.println("\tq -> quit");
    }

    // MODIFIES: this
    // EFFECTS: processes user command
    private void processCommand(String command) {
        if (command.equals("v")) {
            viewSchedule();
        } else if (command.equals("a")) {
            addMovieToSchedule();
        } else if (command.equals("d")) {
            deleteMovieFromSchedule();
        } else if (command.equals("s")) {
            selectMovieToView();
        } else if (command.equals("save")) {
            saveSchedule();
        } else if (command.equals("load")) {
            loadSchedule();
        } else {
            System.out.println("Selection not valid...");
        }
    }

    // EFFECTS: return all the movies in the schedule
    private void viewSchedule() {
        if (schedule.isEmpty()) {
            System.out.println("The schedule is empty.");
        } else {
            for (int i = 0; i < schedule.length(); i++) {
                System.out.println("<" + schedule.getMovieByIndex(i).getName() + ">"
                        + " " + schedule.getMovieByIndex(i).getTime());
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: add a movie to the schedule
    private void addMovieToSchedule() {
        Movie newMovie = new Movie("", "", 0, new LinkedList<>());
        System.out.println("Please enter a movie name:");
        newMovie.setName(input.next());
        System.out.println("Please enter a time:");
        newMovie.setTime(input.next());
        System.out.println("Please enter the max seat amount:");
        newMovie.setMaxSeatNum(input.nextInt());
        schedule.addMovie(newMovie);
    }

    // MODIFIES: this
    // EFFECTS: delete a movie from the schedule
    private void deleteMovieFromSchedule() {
        viewSchedule();
        System.out.println("\nPlease enter a movie name from above:");
        String inputName = input.next();

        for (int i = 0; i < schedule.length(); i++) {
            if (schedule.getMovieByIndex(i).getName().equals(inputName)) {
                System.out.println("\nPlease enter the corresponding movie time:");
                String inputTime = input.next();
                if (schedule.getMovieByIndex(i).getTime().equals(inputTime)) {
                    schedule.deleteMovie(schedule.getMovieByIndex(i));
                    System.out.println("\nThe movie " + inputName + " is deleted");
                    break;
                } else {
                    System.out.println("\nMovie is missing corresponding time... Error...");
                }
            }
        }
    }

    // REQUIRES: the movie is in the schedule
    // EFFECTS: prompts user to select movies in the schedule to add customer
    private void selectMovieToView() {

        if (schedule.isEmpty()) {
            System.out.println("\nYou do not have movies to select.");
        } else {
            System.out.println("\nPlease enter a movie name:");
            String selection = input.next();
            System.out.println("\nPlease enter a movie time:");
            String inputTime = input.next();
            for (int i = 0; i < schedule.length(); i++) {
                if ((schedule.getMovieByIndex(i).getName().equals(selection))
                        && schedule.getMovieByIndex(i).getTime().equals(inputTime)) {
                    System.out.println("Customer name list: ");
                    for (int a = 0; a < schedule.getMovieByIndex(i).getCustomerList().size(); a++) {
                        System.out.println(schedule.getMovieByIndex(i).getCustomerByIndex(a).getName());
                    }
                    break;
                }
            }
        }
    }

    // EFFECTS: saves the schedule to file
    private void saveSchedule() {
        try {
            jsonWriter.open();
            jsonWriter.write(schedule);
            jsonWriter.close();
            System.out.println("Saved schedule to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads the schedule from file
    private void loadSchedule() {
        try {
            schedule = jsonReader.read();
            System.out.println("Loaded schedule from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }
}

