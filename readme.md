Project overview
-----------------------

This is a command-line game which is similar to the Warzone computer game, where there is a map (connected graph) with multiple territories. The game proceeds until one of the players has conquered the whole map.

User can select a map for the game and update it as needed. User can add/remove players to the game and choose strategies for non-human players.

Every territory is randomly assigned to some player initially. Players get armies at the beginning of a round. In each round players issue orders in a round-robin fashion which will be executed once all players issue their orders for the round.

Players may receive bonus armies if they conquer all the territories of a continent. Players may also receive some random cards when they conquer a territory in a round. 

As a game is being played, user can save the game in progress to a file, and load the game later in exactly the same state as saved.

Game can be run in Single game mode or tournament mode.

Single game mode: user selects a map file, loads it, and chooses the number and behavior of players. The game proceeds until one of the players has conquered the whole map. If no human player is selected, the game proceeds fully automatically without any user interaction.

Tournament mode: proceeds without any user interaction and show the results of the tournament at the end.

Design Patterns used:
-----------------------
Used **State pattern** to implement various phases of the application.

Used **Observer pattern** to populate the game log file.

Used **Command pattern** to issue and execute the orders.

Used **Adapter pattern** to enable the application to read/write from/to files in different formats.

Used **Strategy pattern** to decide the behaviour of issuing order for non-human players

System Requirements:
-----------------------
- Java 17 / JDK17

Continuous Integration:
-------------------------
Used Github Actions for continuous integration. You can find the configuration at .github/workflows/maven.yml.

How to run?
---------------
If you are using an IDE, run the main method in the GameDriver class.

You can also create a jar using the "mvn clean verify package" command and run the jar using Java command.