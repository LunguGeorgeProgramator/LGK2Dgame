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
When the game window opens, use the W, A, S, and D keys to move the playable character.

To remove all picked-up items from the map, use main menu button "Start New Game" to clear the player's inventory.

<img width="775" height="600" alt="00" src="https://github.com/user-attachments/assets/fd5a5ae6-8b36-4bab-aae2-ffec13736fb9" />
<img width="772" height="601" alt="12" src="https://github.com/user-attachments/assets/27296bd4-fc5b-4af9-8461-f5992fded2a0" />
<img width="774" height="599" alt="11" src="https://github.com/user-attachments/assets/dc3f21fe-da11-49ee-8060-627488beb73d" />
<img width="774" height="597" alt="15" src="https://github.com/user-attachments/assets/d9243f17-9a16-4471-b504-40eac81f2fbe" />
<img width="771" height="599" alt="14" src="https://github.com/user-attachments/assets/67e9e017-e567-4149-9190-f1d2a72cecee" />
<img width="771" height="597" alt="13" src="https://github.com/user-attachments/assets/b89ed500-1f9a-4924-87f1-92338d3fcab8" />


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

