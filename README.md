# LGK2Dgame
Basic 2d game made in java 24, using a free tutorial.

# Packages

Java 24
gradle-8.14

# How to run

Run command 

````
./gradlew run
````
When the game window opens, use the W, A, S and D keys to move the white rectangle.

To remove all picked-up items from the map, use main menu button "Start New Game" to clear the player's inventory.

<img width="777" height="601" alt="0" src="https://github.com/user-attachments/assets/a886a65a-f0c1-411a-ab89-5ccad5cd5b3b" />
<img width="775" height="601" alt="1" src="https://github.com/user-attachments/assets/f1c460fa-63ef-4085-82b8-8e97b62bd1f0" />
<img width="778" height="602" alt="2" src="https://github.com/user-attachments/assets/7340049b-73a6-4f98-87ea-2c9864f46116" />
<img width="778" height="599" alt="3" src="https://github.com/user-attachments/assets/56e792e4-3bf7-4321-87c9-dcfd842e02e4" />
<img width="778" height="600" alt="4" src="https://github.com/user-attachments/assets/7b5da517-59c2-4c73-b34e-19e9e3beeb25" />

# How to build a windows exe ?

Run the following commands to build the project and generate the `.exe`

```
./gradlew clean build
./gradlew run
./gradlew shadowJar
./gradlew launch4j
```
The generated executable will be located at:
````
LGK2Dgame/build/home/georgel/IdeaProjects/LGK2Dgame/build/exe/LGK2DGame.exe
LGK2Dgame/build/home/georgel/IdeaProjects/LGK2Dgame/build/libs/LGK2DGame-1.0-SNAPSHOT.jar
````
For non-Windows users, please use the following JAR commands:

````
 java -jar build/libs/LGK2DGame.jar
 java -jar LGK2DGame.jar
````

For convenience, I've also included a pre-built `.exe`

⚠️ If you prefer not to run the included `.exe` , feel free to build it yourself using the commands above.

