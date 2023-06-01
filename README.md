# Movie App Project

## What will the application do?

This application is designed for **staffs** who working in a cinema. 
It will allow staffs to manage the movies to be played in a cinema.
Staffs need to set *appropriate time*, *maximum amount of seats* for each movie.
Staffs can also add *customer* to a particular movie. This app can help 
staffs to work efficiently and have movies organized.

## Why is this project of interest to you?

I am interested in watching movies, and I am really curious about the movie system
in cinemas that help organized the movie schedule. I want to create a system
of my own that can help staffs to manage all the movies efficiently.
Another exciting thing is I can add whatever movies I want into the system!


## User Stories

- As a user, I want to be able to view the list of movies in my cinema schedule.
- As a user, I want to be able to add a movie to my cinema schedule.
- As a user, I want to be able to delete a movie from my cinema schedule.
- As a user, I want to be able to see the list of customers of a movie.

- As a user, I want to be able to save my cinema schedule to file.
- As a user, I want to be able to load my cinema schedule from file.


### Phase 4: Task 2
#### Sample of events:
- GUI has been closed
- Sat Nov 20 05:22:19 PST 2021
- Added movie: Spider Man
- Sat Nov 20 05:22:41 PST 2021
- Added movie: Spider Man II
- Sat Nov 20 05:22:45 PST 2021
- Added movie: Spider Man III
- Sat Nov 20 05:23:00 PST 2021
- Removed movie: Spider Man III

For this particular sample, 3 movies were added to the schedule, and 1 is removed at the end.

### Phase 4: Task 3

Based on the UML Design Diagram, the Graphical User Interface has a single field of CinemaSchedule, 
a single field of jsonWriter and a single field of jsonReader. The Cinema Schedule class, Movie class and
Customer class all implements the Writable interface, since all three classes need to have the method of transforming
data into json files. Cinema Schedule class has a list of Movies, and Movie class has a list of Customers.
Finally, the EventLog class has a list of type Event, and list of Event is part of type EventLog.
In general, it does not have a lot of unnecessary couplings.

##### Refactoring:

- Use more helper methods, and try to break "large" methods into "small pieces" in the GUI to improve readability.
- Since there are some common functionality in cinema schedule class and movie class,
for example, the customerToJson in movie class and the movieToJson in cinema schedule class, I
will use a new class to implement the method, and pass argument to specify movie schedule or customer list as required 
from Movie and Cinema Schedule classes.