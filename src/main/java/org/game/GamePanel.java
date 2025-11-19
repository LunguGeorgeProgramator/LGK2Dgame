package org.game;

import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Dimension;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.game.models.GameState;
import org.gamesavedstats.GameSavedStats;
import org.gametexts.GameTextProvider;
import org.gameuserinterface.GameMenu;
import org.individual.Individual;
import org.individual.Player;
import org.inventory.PlayerInventory;
import org.world.WorldEnemies;
import org.world.GameWorld;
import org.world.WorldItems;

public class GamePanel extends JPanel implements Runnable
{
    public static final int originalTileSize = 16; // 16 x 16 pixel
    static final int scale = 3;

    static public final int tileSize = originalTileSize * scale; // 48 x 48 pixel
    static public final int maxScreenColumns = 16;
//    static public final int maxScreenColumns = 25;
    static public final int maxScreenRows = 12;
//    static public final int maxScreenRows = 14;
    static public final int screenWith = tileSize * maxScreenColumns; // 768 pixels
    static public final int screenHeight = tileSize * maxScreenRows; // 576 pixels

    final int framePerSecond = 60;
    public final KeyBoardAndMouseHandler keyBoardAndMouseHandler;
    public final Player player;
    final WorldEnemies worldEnemies;
    final GameWorld gameWorld;
    final WorldItems worldItems;
    public final GameTextProvider gameTextProvider;
    public final CollisionChecker collisionChecker;
    private final Thread gameThread;
    public final PlayerInventory playerInventory;
    public final GameSavedStats gameSavedStats;
    public List<Individual> individuals;
    private final GameMenu gameMenu;
    boolean resetEnemiesHealth = false;
    public GameState gameState;


    public int testPositionX, testPositionY, testWith, testHeight;
    public boolean testCollisionArea = false;


    public GamePanel()
    {
        this.gameState = GameState.RESUME_GAME;
        this.gameSavedStats = new GameSavedStats();
        this.playerInventory = new PlayerInventory(this);
        this.keyBoardAndMouseHandler = new KeyBoardAndMouseHandler(this);
        this.gameTextProvider = new GameTextProvider();
        this.gameMenu = new GameMenu(this, this.keyBoardAndMouseHandler);
        this.setPreferredSize(new Dimension(screenWith, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(this.keyBoardAndMouseHandler);
        this.addMouseListener(this.keyBoardAndMouseHandler);
        this.addMouseMotionListener(this.keyBoardAndMouseHandler);
        this.setFocusable(true);
        this.collisionChecker = new CollisionChecker(this);
        this.player = new Player(this);
        this.gameWorld = new GameWorld(this, "/worldMaps/WorldMap.txt");
        this.worldItems = new WorldItems(this, "/worldMaps/WorldMapAssets.txt");
        this.worldEnemies = new WorldEnemies(this, "/worldMaps/WorldMapEnemies.txt");
        this.individuals = new ArrayList<>();
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void setGameState(GameState gameStateValue)
    {
        if (gameState != GameState.OPEN_GAME_MENU)
        {
            this.gameState = this.isGameState(gameStateValue) ? GameState.RESUME_GAME: gameStateValue;
        }
    }

    @Override
    public void run()
    {
        // draw the screen every 0.016 seconds
        double drawInterval = (double) 1000000000 / framePerSecond;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        while (gameThread != null)
        {
            // on game window starts for the first time open game menu
            if (delta == 0)
            {
                gameState = GameState.OPEN_GAME_MENU;
            }

            currentTime = System.nanoTime();
            delta = delta + ((currentTime - lastTime) / drawInterval);
            lastTime = currentTime;
            if (delta >= 1)
            {
                // UPDATE, information as player position (update)
                if (this.isGameState(GameState.RESUME_GAME))
                {
                    update();
                }
                // DRAW, draw graphic as images, textures (paintComponent)
                repaint();
                delta--;
            }
        }
    }

    public boolean isGameState(GameState comparedGameState)
    {
        return gameState == comparedGameState;
    }

    public void resetGame()
    {
        resetEnemiesHealth = true;
        this.playerInventory.removeAllFromInventory();
        this.gameSavedStats.resurrectAllDeadEnemies();
        this.player.playerHealth = this.player.playerMaxHealth;
        this.player.positionX = 100;
        this.player.positionY = 100;
        Timer timer = new Timer(500, e -> {
            resetEnemiesHealth = false;
        });
        timer.setRepeats(false);
        timer.start();
    }

    public void update()
    {
        this.worldItems.update();
        this.worldEnemies.update(resetEnemiesHealth);
        this.player.update();
    }

    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        Graphics2D g2D = (Graphics2D) g;

        this.gameWorld.draw(g2D);

        this.individuals.add(this.player);
        this.worldEnemies.addEnemiesToDrawList();
        this.worldItems.addItemsToDrawList();
        // this.player.draw(g2D);

        // Sort Player/WorldEnemies/World items by Y position
        individuals.sort(Comparator.comparingInt(t -> t.positionY));
        // draw Player/WorldEnemies/World in game
        for (Individual individual : this.individuals)
        {
            individual.draw(g2D);
        }
        // clean draw list so it will not run in a loop adding again and again, making the game to freeze
        for (int i = 0;  i < this.individuals.size(); i++)
        {
            individuals.remove(i);
        }

        this.worldEnemies.drawEnemyText(g2D);
        this.worldItems.drawTextOmCollision(g2D);
        this.player.drawRedSlider(g2D);

        // TODO: debug text, remove latter add some window pop up
        this.gameTextProvider.setTextColor(Color.YELLOW);
        this.gameTextProvider.setTextPosition(screenWith / 2, tileSize - 20);
        String enemyCollisionText = this.gameTextProvider.getGameTextByKey("game-help-debug");
        this.gameTextProvider.showTextInsideGame(g2D, enemyCollisionText);

        if (isGameState(GameState.OPEN_PLAYER_INVENTORY))
        {
            this.playerInventory.drawPlayerInventoryWindow(g2D);
        }

        if (testCollisionArea)
        {
            drawTestDynamicRectangle(g2D);
        }

        if (isGameState(GameState.OPEN_GAME_MENU))
        {
            this.gameMenu.drawGameMenu(g2D);
        }

        g2D.dispose(); // free up memory, destroy after draw
    }

    public void drawTestDynamicRectangle(Graphics2D g2D)
    {
        g2D.setColor(new Color(0, 255, 100, 190));
        g2D.fillRect(testPositionX, testPositionY, testWith, testHeight);
        g2D.setColor(Color.BLACK);
        g2D.drawRect(testPositionX, testPositionY, testWith, testHeight);
    }

    public void drawTestDynamicRectangle(Graphics2D g2D, int testPositionX, int testPositionY, int testWith, int testHeight)
    {
        g2D.setColor(new Color(0, 255, 100, 190));
        g2D.fillRect(testPositionX, testPositionY, testWith, testHeight);
        g2D.setColor(Color.BLACK);
        g2D.drawRect(testPositionX, testPositionY, testWith, testHeight);
    }

}
