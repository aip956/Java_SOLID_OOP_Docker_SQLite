# Welcome to My Mastermind in Java
## Task
This is an implementation of the Mastermind number guessing game. The user plays against the program.

There are 8 pieces of different colors (but numbers in this case), and a secret code composed of 4 distinct pieces.

Typically, the user has 10 tries to guess the right pieces and sequence. After each guess, the user will be told the number of correctly placed pieces, and misplaced pieces. In this implementation, the user can enter a secret and/or the maximum number of tries.


## Design
I created this in Java, using Object Oriented Programming. Creating the class structure is challenging for me, and I thought about the 
nouns I was modelling. It's a game, with two players. I therefore created the following classes:

MyMastermind: Contains the main class; creates a new instance of the Game and starts the game by invoking the startGame method.

Game: Starts the game. Has the Guesser create a guess, and the secretKeeper provide the feedback. It determines if the guess is valid, and if the guess wins. It tracks the Round, and indicates if too many tries are exceeded.

Player: Abstract class; SecretKeeper and Guesser will extend from Player. They will both access the guessedCode attribute, and the abstract class allows definition and sharing of methods/attributes. The subclasses are inheriting from solely one class in this case, and the makeGuess method should be the default implementation, so an abstract class seemed suitable. 

SecretKeeper: Extends from Player; it determines if a secret and/or max attempts was input in args. If not, it creates a secret and/or defaults the attempts. It provides the feedback (well-placed and misplaced pieces) to the Guesser.

Guesser: Extends from Player; it inputs the guess.


I've also added Dockerfile to allow a user to run my application on any system that supports Docker.

I've also added a .gitignore file to prevent certain files from being committed to the git repository. This will help keep the repository clean and focused.


## To run
### Command line:
First compile:</br> `javac Game.java MyMastermind.java Player.java SecretKeeper.java Guesser.java GameData.java` 

To compile with JDBC, slf4j, loggers:


 `javac -cp ".:lib/sqlite-jdbc-3.45.3.0.jar:lib/slf4j-api-1.7.32.jar" MyMastermind.java Game.java Player.java SecretKeeper.java Guesser.java GameData.java`

</br>

Run file with default values:</br> `java MyMastermind`</br>
 </br>
 java -cp ".:lib/sqlite-jdbc-3.45.3.0.jar" MyMastermind

 java -cp ".:lib/sqlite-jdbc-3.45.3.0.jar:lib/logback-classic-1.2.6.jar:lib/logback-core-1.2.6.jar:lib/slf4j-api-1.7.32.jar" MyMastermind


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
