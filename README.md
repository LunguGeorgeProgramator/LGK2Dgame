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

To remove all picked-up items from the map, press the P key to clear the player's inventory.

<img width="782" height="599" alt="enemyAttack" src="https://github.com/user-attachments/assets/26fa2265-742a-44b8-860c-e7c2bfbe5d90" />
<img width="781" height="601" alt="test_spiders" src="https://github.com/user-attachments/assets/b331dd81-b633-426a-a469-6f4016685a3c" />
<img width="770" height="602" alt="test-22" src="https://github.com/user-attachments/assets/c8e9b639-272f-4577-94fa-b05c854fb62a" />


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

