package ui;

import model.*;
import model.Event;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;

//Graphical User Interface for Movie application
public class GUI {
    private static final String JSON_STORE = "./data/cinemaSchedule.json";
    private CinemaSchedule schedule;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    JTextField nameTextField = new JTextField(30);
    JTextField timeTextField = new JTextField(30);
    JTextField seatNumTextField = new JTextField(30);

    //EFFECTS: run the graphical user interface
    public GUI() {
        runGUI();
    }

    // MODIFIES: this
    // EFFECTS: run the Graphical User Interface version of Movie App
    private void runGUI() {
        JFrame frame = new JFrame("Movie Schedule");
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.setSize(800, 600);
        setJPanelAndButton(frame);
        frame.setVisible(true);

        init();

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent windowEvent) {
                System.out.println("GUI has been closed");
                for (Event event: EventLog.getInstance()) {
                    System.out.println(event.getDate());
                    System.out.println(event.getDescription());
                }
                System.exit(0);
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: initializes schedule
    private void init() {
        schedule = new CinemaSchedule();
        //displayMovieWithCustomers();
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
    }

    // MODIFIES: this
    // EFFECTS: construct a container
    private void setJPanelAndButton(JFrame frame) {
        JPanel container = new JPanel();
        frame.setContentPane(container);
        addLabelsToPanel(container);
        addButtonsToPanel(container);
        container.setLayout(new BoxLayout(container,BoxLayout.Y_AXIS));
    }

    // MODIFIES: container
    // EFFECTS: add labels to the container
    private void addLabelsToPanel(JPanel container) {
        JLabel welcomeLabel = new JLabel("Welcome");
        welcomeLabel.setFont(new Font("Serif", Font.BOLD, 40));
        JLabel nameLabel = new JLabel("Movie Schedule");
        nameLabel.setFont(new Font("Serif", Font.PLAIN, 24));

        container.add(welcomeLabel);
        container.add(nameLabel);
    }

    // MODIFIES: container
    // EFFECTS: add buttons to the container
    private void addButtonsToPanel(JPanel container) {
        JButton viewButton = new JButton("view schedule");
        JButton addButton = new JButton("add movie");
        JButton deleteButton = new JButton("delete movie");
        JButton saveButton = new JButton("save schedule");
        JButton loadButton = new JButton("load schedule");

        container.add(viewButton);
        container.add(addButton);
        container.add(deleteButton);
        container.add(saveButton);
        container.add(loadButton);

        ActionListener viewListener = viewActionListener();
        viewButton.addActionListener(viewListener);

        ActionListener addListener = addActionListener();
        addButton.addActionListener(addListener);

        ActionListener deleteListener = deleteActionListener();
        deleteButton.addActionListener(deleteListener);

        ActionListener saveListener = saveActionListener();
        saveButton.addActionListener(saveListener);

        ActionListener loadListener = loadActionListener();
        loadButton.addActionListener(loadListener);

    }

    // EFFECTS: create an action listener for view schedule
    private ActionListener viewActionListener() {
        return e -> viewSchedule();
    }

    // EFFECTS: create an action listener for add button
    private ActionListener addActionListener() {
        return e -> addMovieToSchedule();
    }

    // EFFECTS: create an action listener for delete button
    private ActionListener deleteActionListener() {
        return e -> deleteMovieFromSchedule();
    }

    // EFFECTS: create an action listener for save button
    private ActionListener saveActionListener() {
        return e -> saveSchedule();
    }

    // EFFECTS: create an action listener for load button
    private ActionListener loadActionListener() {
        return e -> loadSchedule();
    }

    // EFFECTS: return all the movies in the schedule
    private void viewSchedule() {
        JFrame scheduleFrame = new JFrame("schedule list");
        scheduleFrame.setSize(400, 300);
        JPanel viewContainer = new JPanel();
        scheduleFrame.setContentPane(viewContainer);
        if (schedule.isEmpty()) {
            JLabel emptyScheduleLabel = new JLabel("The schedule is empty.");
            viewContainer.add(emptyScheduleLabel);
        } else {
            for (int i = 0; i < schedule.length(); i++) {
                JLabel movieLabel = new JLabel("<" + schedule.getMovieByIndex(i).getName() + ">"
                        + " " + schedule.getMovieByIndex(i).getTime());
                viewContainer.add(movieLabel);
                viewContainer.setLayout(new BoxLayout(viewContainer,BoxLayout.Y_AXIS));
            }
        }
        scheduleFrame.setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: add a movie to the schedule
    private void addMovieToSchedule() {
        JFrame frameForAddingMovie = new JFrame("add new movie");
        JPanel containerForAddingMovie = new JPanel();
        constructFrameAndContainer(frameForAddingMovie, containerForAddingMovie);

        JButton button = new JButton("OK");
        containerForAddingMovie.add(button);
        button.addActionListener(e -> createAndAddMovie());

        frameForAddingMovie.setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: create a new movie based on input from the text field and add the schedule
    private void createAndAddMovie() {
        Movie newMovie = new Movie("", "", 0, new LinkedList<>());
        String name = nameTextField.getText();
        String time = timeTextField.getText();
        int maxSeatNum = Integer.parseInt(seatNumTextField.getText());
        newMovie.setName(name);
        newMovie.setTime(time);
        newMovie.setMaxSeatNum(maxSeatNum);
        schedule.addMovie(newMovie);

        displaySuccessFeedback();
    }

    // EFFECTS: create a new window to give success feedback for the action
    private void displaySuccessFeedback() {
        JFrame feedbackFrame = new JFrame("Success");
        JPanel feedbackPanel = new JPanel();
        JLabel feedbackLabel = new JLabel("Action success!");
        feedbackFrame.setSize(500, 600);
        feedbackFrame.setContentPane(feedbackPanel);
        feedbackPanel.add(feedbackLabel);

        ImageIcon image = new ImageIcon("data/cat.jpeg");
        JLabel imageLabel = new JLabel(image);
        feedbackPanel.add(imageLabel);

        feedbackFrame.setVisible(true);
    }


    // MODIFIES: frame and container
    // EFFECTS: construct the basic frame and container for adding movie
    private void constructFrameAndContainer(JFrame frame, JPanel container) {
        frame.setSize(400, 300);
        frame.setContentPane(container);
        nameTextField.setText("Please enter a movie name.");
        timeTextField.setText("Please enter a time.");
        seatNumTextField.setText("Please enter the maximum seat number.");
        container.add(nameTextField);
        container.add(timeTextField);
        container.add(seatNumTextField);
    }


    // MODIFIES: this
    // EFFECTS: delete a movie from the schedule
    private void deleteMovieFromSchedule() {
        JFrame frameForDeletingMovie = new JFrame("delete new movie");
        JPanel containerForDeletingMovie = new JPanel();
        constructFrameAndContainer(frameForDeletingMovie, containerForDeletingMovie);

        JButton button = new JButton("OK");
        containerForDeletingMovie.add(button);
        button.addActionListener(e -> deleteExistMovie());

        frameForDeletingMovie.setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: delete the movie if it exists
    private void deleteExistMovie() {
        String name = nameTextField.getText();
        String time = timeTextField.getText();
        for (int i = 0; i < schedule.length(); i++) {
            if (schedule.getMovieByIndex(i).getName().equals(name)) {
                if (schedule.getMovieByIndex(i).getTime().equals(time)) {
                    schedule.deleteMovie(schedule.getMovieByIndex(i));
                    displaySuccessFeedback();
                    break;
                } else {
                    displayFailingFeedback();
                }
            }
        }
    }

    // EFFECTS: create a new window to give failure feedback for the action
    private void displayFailingFeedback() {
        JFrame feedbackFrame = new JFrame("Fail");
        JPanel feedbackPanel = new JPanel();
        JLabel feedbackLabel = new JLabel("Action fail!");
        feedbackFrame.setSize(200, 150);
        feedbackFrame.setContentPane(feedbackPanel);
        feedbackPanel.add(feedbackLabel);
        feedbackFrame.setVisible(true);
    }

    // EFFECTS: saves the schedule to file
    private void saveSchedule() {
        try {
            jsonWriter.open();
            jsonWriter.write(schedule);
            jsonWriter.close();
            displaySuccessFeedback();
        } catch (FileNotFoundException e) {
            displayFailingFeedback();
        }
    }

    // MODIFIES: this
    // EFFECTS: loads the schedule from file
    private void loadSchedule() {
        try {
            schedule = jsonReader.read();
            displaySuccessFeedback();
        } catch (IOException e) {
            displayFailingFeedback();
        }
    }

    // helper method
    // MODIFIES: this
    // EFFECTS: add two movies with customers into the app
    private void displayMovieWithCustomers() {
        Customer customer1 = new Customer("Jerry");
        Customer customer2 = new Customer("Katherine");
        Movie theAvengersI = new Movie("The Avengers I", "12:00", 10, new LinkedList<>());
        Movie theAvengersII = new Movie("The Avengers II", "13:00", 10, new LinkedList<>());
        theAvengersI.addCustomer(customer1);
        theAvengersI.addCustomer(customer2);
        theAvengersII.addCustomer(customer1);
        schedule.addMovie(theAvengersI);
        schedule.addMovie(theAvengersII);
    }
}
