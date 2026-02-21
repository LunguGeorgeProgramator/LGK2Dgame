package org.game;

import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.imageAssets.ImageLoader;
import org.imageAssets.models.WorldImagesAssets;
import org.individual.SpiderBossEnemy;
import org.individual.GrimBoosEnemy;
import org.world.NonPlayerCharacters;
import org.world.dungeons.DungeonWorld;
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
import org.world.dungeons.DungeonWorldEnemies;
import org.world.dungeons.DungeonWorldItems;
import org.world.models.WorldType;

public class GamePanel extends JPanel implements Runnable
{
    public static final int originalTileSize = 16; // 16 x 16 pixel
    static final int scale = 3;

    static public final int tileSize = originalTileSize * scale; // 48 x 48 pixel
//    static public final int maxScreenColumns = 16;
    static public final int maxScreenColumns = 25;
//    static public final int maxScreenRows = 12;
    static public final int maxScreenRows = 14;
    static public final int screenWith = tileSize * maxScreenColumns; // 768 pixels
    static public final int screenHeight = tileSize * maxScreenRows; // 576 pixels

    final int framePerSecond = 90;
    public final KeyBoardAndMouseHandler keyBoardAndMouseHandler;
    public final ImageLoader imageLoader;
    public final Player player;
    public final SpiderBossEnemy spiderBossEnemy;
    public final GrimBoosEnemy grimBoosEnemy;
    final WorldEnemies worldEnemies;
    final NonPlayerCharacters worldNpc;
    final DungeonWorldEnemies caveDungeonWorldEnemies;
    final DungeonWorldEnemies waterDungeonWorldEnemies;
    final GameWorld gameWorld;
    final WorldItems worldItems;
    final DungeonWorldItems caveDungeonWorldItems;
    final DungeonWorldItems waterDungeonWorldItems;
    public DungeonWorld caveDungeonWorld;
    public DungeonWorld waterDungeonWorld;
    public final GameTextProvider gameTextProvider;
    public final CollisionChecker collisionChecker;
    private final Thread gameThread;
    public final PlayerInventory playerInventory;
    public final GameSavedStats gameSavedStats;
    private final GameMenu gameMenu;
    boolean resetEnemiesHealth = false;
    public boolean clearPlayerDamageText = false;
    public GameState gameState;
    public WorldType worldType;
    public Map<WorldType, Map<String, Integer>> LastWorldTypeVisited;
    public int testPositionX, testPositionY, testWith, testHeight;
    public boolean testCollisionArea = false;


    public GamePanel()
    {
        this.worldType = WorldType.MAIN_GAME;
        this.gameState = GameState.RESUME_GAME;
        this.gameSavedStats = new GameSavedStats();
        this.imageLoader = new ImageLoader();
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
        this.spiderBossEnemy = new SpiderBossEnemy(this);
        this.grimBoosEnemy = new GrimBoosEnemy(this);
        this.gameWorld = new GameWorld(this, "/worldMaps/WorldMap.txt");
        this.worldItems = new WorldItems(this, "/worldMaps/WorldMapAssets.txt");
        this.caveDungeonWorldItems = new DungeonWorldItems(this, "/worldMaps/dungeons/cave/CaveDungeonWorldMapAssets.txt");
        this.waterDungeonWorldItems = new DungeonWorldItems(this, "/worldMaps/dungeons/water/WaterDungeonWorldMapAssets.txt");
        this.worldEnemies = new WorldEnemies(this, "/worldMaps/WorldMapEnemies.txt");
        this.caveDungeonWorldEnemies = new DungeonWorldEnemies(this, "/worldMaps/dungeons/cave/CaveDungeonWorldMapEnemies.txt");
        this.waterDungeonWorldEnemies = new DungeonWorldEnemies(this, "/worldMaps/dungeons/water/WaterDungeonWorldMapEnemies.txt");
        this.caveDungeonWorld = new DungeonWorld(this, "/worldMaps/dungeons/cave/CaveDungeonWorldMap.txt");
        this.waterDungeonWorld = new DungeonWorld(this, "/worldMaps/dungeons/water/WaterDungeonWorldMap.txt");
        this.worldNpc = new NonPlayerCharacters(this, "/worldMaps/WorldMapNpc.txt");
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
        this.resetEnemiesHealth = true;
        this.playerInventory.removeAllFromInventory();
        this.gameSavedStats.resurrectAllDeadEnemies();
        this.player.playerHealth = this.player.playerMaxHealth;
        this.player.positionX = 100;
        this.player.positionY = 100;
        this.player.activateCollision = false;
        this.player.stopPlayerMovement = false;
        Timer timer = new Timer(500, e -> {
            this.resetEnemiesHealth = false;
            this.spiderBossEnemy.isBoosEnemyDead = false;
            this.grimBoosEnemy.isBoosEnemyDead = false;
        });
        this.clearPlayerDamageText = true;
        this.worldType = WorldType.MAIN_GAME;
        this.LastWorldTypeVisited = Map.of();
        this.spiderBossEnemy.isBoosEnemyDead = false;
        this.grimBoosEnemy.isBoosEnemyDead = false;
        timer.setRepeats(false);
        timer.start();
        repaint();
    }

    public void update()
    {
        switch (this.worldType)
        {
            case WorldType.CAVE_DUNGEON:
                this.caveDungeonWorldItems.update();
                this.caveDungeonWorldEnemies.update(this.resetEnemiesHealth);
                this.grimBoosEnemy.update();
                this.player.update();
                break;
            case WorldType.WATER_DUNGEON:
                this.waterDungeonWorldItems.update();
                this.waterDungeonWorldEnemies.update(this.resetEnemiesHealth);
                this.player.update();
                break;
            case WorldType.MAIN_GAME:
                this.worldItems.update();
                this.worldEnemies.update(this.resetEnemiesHealth);
                this.spiderBossEnemy.update();
                this.player.update();
                this.worldNpc.update();
        }
    }

    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        Graphics2D g2D = (Graphics2D) g;

        // clean draw list so it will not run in a loop adding again and again, making the game to freeze
        List<Individual> individuals = new ArrayList<>();
        switch (this.worldType)
        {
            case WorldType.CAVE_DUNGEON:
                this.caveDungeonWorld.draw(g2D);
                individuals.add(this.player);
                individuals.add(this.grimBoosEnemy);
                individuals = this.caveDungeonWorldEnemies.addEnemiesToDrawList(individuals);
                individuals = this.caveDungeonWorldItems.addItemsToDrawList(individuals);
                break;
            case WorldType.WATER_DUNGEON:
                this.waterDungeonWorld.draw(g2D);
                individuals.add(this.player);
                individuals = this.waterDungeonWorldEnemies.addEnemiesToDrawList(individuals);
                individuals = this.waterDungeonWorldItems.addItemsToDrawList(individuals);
                break;
            case WorldType.MAIN_GAME:
                this.gameWorld.draw(g2D);
                individuals.add(this.player);
                individuals = this.worldNpc.addEnemiesToDrawList(individuals);
                individuals = this.worldEnemies.addEnemiesToDrawList(individuals);
                individuals.add(this.spiderBossEnemy);
                individuals = this.worldItems.addItemsToDrawList(individuals);
                break;
        }

        // Sort Player/WorldEnemies/World items by Y position
        individuals.sort(Comparator.comparingInt(t -> t.positionY));

        // draw Player/boosEnemy/WorldEnemies/World in game
        for (Individual individual : individuals)
        {
            individual.draw(g2D);
        }

        for (Individual individual : individuals)
        {
            individual.drawLastInsideGamePanel(g2D);
        }

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

//        Runtime rt = Runtime.getRuntime();
//        long used = rt.totalMemory() - rt.freeMemory();
//        System.out.println("Used MB: " + used / 1024 / 1024);

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
