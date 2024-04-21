# Welcome to My Mastermind in Java
## Task
This is an implementation of the Mastermind number guessing game. The user plays against the program.

There are 8 pieces (numbers 0 -7), and a secret code composed of 4 distinct pieces.

Typically, the user has 10 tries to guess the right pieces and sequence. After each guess, the user will be told the number of correctly placed pieces, and misplaced pieces. In this implementation, the user can enter a secret and/or the maximum number of tries.

The game can be played through a command line interface (locally), or through a Docker container (requires Docker Desktop).

## To run

###
Git pull the repository.</br>

To run locally: In the command line,</br>
1. Compile: </br>
    * javac -cp "src:src/lib/*" src/DAO/*.java src/DBConnectionManager/*.java src/Models/*.java src/View/*.java src/MyMastermind.java</br>
2. Run: </br>
    * java -cp "src:src/lib/*" MyMastermind</br>
3. View data:
    * In the command line, change directory
    * cd src/data
    * Open the SQL shell
        * sqlite3 MM_Reach.db
    * Optional: Turn on headers
        * .header on
    * View the data
        * SELECT * FROM game_data;
    * Exit the sql shell
        * .exit

In the Docker container:
* Start the Desktop Docker
* In a command line, navigate to the directory
* Compile and run:
** ./play_mastermind
* Check data:
** Start game again, but don't play:
*** ./play_mastermind
** Open another terminal and enter container bash shell:
*** docker exec -it game /bin/bash
** change directory to the data dir
*** cd src/data
** Open the sql shell, MM_Reach database
*** sqlite3 MM_Reach.db
** Turn header view on
*** .header on
** View the data
*** SELECT * FROM game_data;
** Exit the sql shell
*** .exit
** Exit the container's bash shell
*** exit



` RUN `javac -cp ".:lib/*" MyMastermind.java Game.java Player.java SecretKeeper.java Guesser.java GameData.java GameDataDAO.java SQLiteGameDataDAO.java`









</br>

Run file with default values:</br> `java MyMastermind`</br>
 </br>







<img 
src="./ScreenCaps/CLInoArgs.png"
alt="Command Line no Arguments" 
title="CLI no Args"
style="display: block; margin: 0 auto; max-width: 200px">
</br>

Run file with flags (input secret and/or tries):</br> `java MyMastermind -c "0234" -t 3`</br>
 </br>
<img 
src="./ScreenCaps/CLIwithArgs.png"
alt="Command Line with Arguments" 
title="CLI with Args"
style="display: block; margin: 0 auto; max-width: 200px">
</br>

### Via Docker:
First compile image:</br> `docker build -t my_mastermind.image .` </br>
 </br>
<img 
src="./ScreenCaps/DockerBuildImage.png"
alt="Build the Docker Image" 
title="Build Docker Image"
style="display: block; margin: 0 auto; max-width: 200px">
</br>




Run file with default vals:</br> `docker run -it my_mastermind.image` </br>
(NOTE: Flag -it will allow the scanner input of the guess) </br>
Press 'Enter' after entering each guess </br>

</br>
Run file with flags (input secret and/or tries):</br>
(NOTE: Flag -it will allow the scanner input of the guess) </br>
 `docker run -it my_mastermind.image -t 3 -c "2456"` </br>
Press 'Enter' after entering each guess </br>
 </br>
<img 
src="./ScreenCaps/DockerRunwithArgs.png"
alt="Run via Docker with Arguments" 
title="Docker Run with Args"
style="display: block; margin: 0 auto; max-width: 200px">
</br>


## Installation
The addition of Docker should allow a user to run my application on any system that supports Docker.

## Game Rules
The Mastermind game requires the user to guess the secret code composed of four distinct pieces. After each guess, the user will be informed of the number of correctly placed pieces and the number of misplaced pieces. The objective is to guess the secret code in the fewest attempts possible.


Does the user need to create the table if they git pull or run in Docker?
Create db and tables
sqlite3 your_database_name.db
CREATE TABLE game_data (
    game_id INTEGER PRIMARY KEY AUTOINCREMENT,
    player_name TEXT,
    rounds_to_solve INTEGER,
    solved BOOLEAN,
    timestamp TIMESTAMP,
    secret_code TEXT,
    guesses TEXT
);

to view data,
enter SQLite interactive shell
sqlite3 MM_Reach.db

view tables:
.tables

view data:
SELECT * FROM game_data;


## Design
I created this in Java, using Object Oriented Programming and SOLID Principles. Creating the class structure is challenging for me, and I thought about the 
nouns I was modelling. It's a game, with two players. I therefore created the following classes:

MyMastermind: Contains the main class; creates a new instance of the Game and starts the game by invoking the startGame method.

#### Models Package
Game: This class manages the game flow, including starting the game, managing the rounds, and finalizing the game data. 
* Single Responsibility: Coordinates game logic
* Dependency Inversion Principle: Relies on abstractions by interacting with the Guesser, SecretKeeper, GameData, and GameDataDAO interfaces rather than concrete implementations

Player: The Player is an abstract class for players. SecretKeeper and Guesser will extend from Player.  
* Open/Closed Principle: It's easily extendable to add more player types without modifying existing code.
* Abstraction: It abstracts player details, providing a base for specific player types.

SecretKeeper: The SecretKeeper generates and maintains the secret code, evaluates guesses, and provides feedback. It extends from Player. 
* Single Responsibility Principle: Focused on maintaining and validating the secret code.
* Dependency Inversion Principle: It uses Player as a base class, promoting use of abstractions over concrete classes.

Guesser: The Guesser represents the player making guesses against the secret code. It extends from Player; it inputs the guess.
* Open/Closed Principle: New guessing strategies can be added without modifying existing code by extending this class.
* Liskov Substitution Principle: As a subclass of Player, it can be used anywhere a Player is expected without affecting the behavior negatively.

GameData: The GameData stores the relevant data about a game session, such as player name, guesses, and outcome.
* Single Responsibility Principle: It's solely responsible for holding and transferring game data.
* Encapsulation: It keeps data safe from unauthorized access, exposing only necessary parts through methods


#### View Package
GameUI: The GameUI handles all user interactions, including displaying messages and capturing user input.
* Single Responsibility Principle: It's dedicated solely to user interface operations.

#### DAO Package
GameDataDAO: This class is an interface for data access operations related to GameData, such as saving and retrieving game data.
* Interface Segregation Principle: Clients will not be forced to depend on methods they do not use.

SQLiteGameDataDAO: This class implements GameDataDAO, providing specific data operations using SQLite.
* Dependency Inversion Principle: It depends on the GameDataDAO abstraction, allowing for flexibility in data storage methods
* Single Responsibility Principle: It manages the database operations specific to GameData

#### DBConnectionManagerPackage
DatabaseConnectionManager: This class manages database connections, ensuring a single active connection or creating a new one as needed
* Single Responsiblity Principle: It centralizes the management of database connections, separating it from other database operations
* Singleton Patter: It ensures that there is a single instance of the connection, reused throughout the application



I've also added Dockerfile to allow a user to run my application on any system that supports Docker.

Included in the game code is Logback. While I was coding it was helpful for debugging. I've kept it in the code so that if the code is extended, logging can continue to help debug.

I've also added a .gitignore file to prevent certain files from being committed to the git repository. This will help keep the repository clean and focused.
