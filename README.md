crossword-puzzle
==
Example project to display software engineering principles in a simple game

# Design

This project was built using the JavaFX framework. JavaFX organizes the project into the Model-View-Presenter 
(MVP) design pattern through the use of FXML. When the main.xml is launched, it creates an instance of JavaFX and builds 
the stage and the screen. Theater terminology is used to describe its application architecture, where the stage is 
the window, and the screen refers to the contents. 

## Model
The "Model" consists of application-specific domain objects. In the case for this project, the model is a JSONFileReader 
class that reads JSON and maps the data into LinkedLists with a container factory.

## View
The "View" is the gui.fxml. The template defines the visual nodes and attaches controls that connect the view 
to the controller. The user interacts directly with the view and actions are linked to the controller using controls.

## Presenter
The "Presenter" is the Controller class. Framework specific annotation tags are used to link the view's action attribute 
to a function. The controller uses a command pattern to signal the gameBoard. 

The controller is responsible for initializing the game board and passing user actions to the board. The game board and 
puzzle are both singletons. The gameboard selects a puzzle, builds the tiles and hosts the business logic. Creation of 
the tiles is implemented with a builder factory. 

## Main Classes
Controller - Accepts user input and informs the game of all events.
GameBoard - Board is an 5×5 set of boxes containing all tiles. Controls the flow of the game.
Puzzle - Consolidates the model data
Tile - Represents one square on the gameBoard


## Project Scope
This puzzle was inspired by the New York Times mini-crossword. 

- Display a puzzle game board
- Accept a guess
- Check for correctness
- Reveal tiles
- clear puzzle
- select another puzzle


## How to Play
1. Download repository
2. Run the Main file

## Challenges
This is my first project written in Java. I was also very new to JavaFX and intentionally applying software design principles to the design of the application. 

