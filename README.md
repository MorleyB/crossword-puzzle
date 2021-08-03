crossword-puzzle
==
Example project to display software engineering principles in a simple game

# Design

This project was built using the JavaFX framework. JavaFX organizes the project into the Model-View-Presenter 
(MVP) design pattern through the use of FXML. When the main.xml is launched, it creates an instance of JavaFX and builds 
the stage and the screen. JavaFX uses theater terminology to describe its application architecture, where the stage is 
the window, and the screen refers to the contents. 

## Model

The "Model" consists of application-specific domain objects. In the case for this project, the model is a JSONFileReader 
class that reads JSON and maps the data into LinkedLists with a container factory.

## View
The "View" is the gui.fxml. A FXML template, that defines the visual nodes and attaches controls that connect the view 
to the controller. The name of the controller is also defined in this file. The user interacts directly with the view.

## Presenter
The "Presenter" is the Controller class that handles the events from the fxml template's controls. Framework specific 
annotation tags are used to link the view's action attribute to a function.

The controller is responsible for selecting a puzzle, building the game board and setting the first hint. The game board
is a function that sets the number of tiles shown and calls a tile builder factory to a create tile.  


Principles
-
- separation of concerns
- modularity

Patterns
-

Creational: Builder, Factory