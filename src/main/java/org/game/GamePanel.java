package org.game;

import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Dimension;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.gametexts.GameTextProvider;
import org.individual.Individual;
import org.individual.Player;
import org.inventory.PlayerInventory;
import org.world.Enemies;
import org.world.GameWorld;
import org.world.WorldItems;

public class GamePanel extends JPanel implements Runnable
{
    static final int originalTileSize = 16; // 16 x 16 pixel
    static final int scale = 3;

    static public final int tileSize = originalTileSize * scale; // 48 x 48 pixel
    static public final int maxScreenColumns = 16;
    static public final int maxScreenRows = 12;
    static public final int screenWith = tileSize * maxScreenColumns; // 768 pixels
    static public final int screenHeight = tileSize * maxScreenRows; // 576 pixels

    final int framePerSecond = 60;
    final KeyBoardHandler keyBoardHandler;
    public final Player player;
    final Enemies enemies;
    final GameWorld gameWorld;
    final WorldItems worldItems;
    public final GameTextProvider gameTextProvider;
    public final CollisionChecker collisionChecker;
    Thread gameThread;
    public final PlayerInventory playerInventory;
    public List<Individual> individuals;

    public GamePanel()
    {
        this.playerInventory = new PlayerInventory();
        this.keyBoardHandler = new KeyBoardHandler();
        this.gameTextProvider = new GameTextProvider();
        this.setPreferredSize(new Dimension(screenWith, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(this.keyBoardHandler);
        this.setFocusable(true);
        this.collisionChecker = new CollisionChecker(this);
        this.player = new Player(this, this.keyBoardHandler, this.playerInventory);
        this.enemies = new Enemies(this, player);
        this.gameWorld = new GameWorld(this, "/worldMaps/WorldMap.txt");
        this.worldItems = new WorldItems(this, this.player);
        this.individuals = new ArrayList<>();
    }

    public void startGameThread()
    {
        gameThread = new Thread(this);
        gameThread.start();
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
            currentTime = System.nanoTime();
            delta = delta + ((currentTime - lastTime) / drawInterval);
            lastTime = currentTime;
            if (delta >= 1)
            {
                // UPDATE, information as player position (update)
                update();
                // DRAW, draw graphic as images, textures (paintComponent)
                repaint();
                delta--;
            }
        }
    }

    public void update()
    {
        if (this.keyBoardHandler.pKeyPressed)
        { // remove all items from player inventory
            this.playerInventory.removeAllFromInventory();
            this.player.playerHealth = this.player.playerMaxHealth;
        }
        this.enemies.update();
        this.worldItems.update();
        this.player.update();
    }

    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        Graphics2D g2D = (Graphics2D) g;

        this.individuals.add(this.player);
        this.gameWorld.draw(g2D);
        this.enemies.addEnemiesToDrawList();
        this.worldItems.addItemsToDrawList();
        // this.player.draw(g2D);

        // Sort Player/Enemies/World items by Y position
        individuals.sort(Comparator.comparingInt(t -> t.positionY));
        // draw Player/Enemies/World in game
        for (Individual individual : this.individuals)
        {
            individual.draw(g2D);
        }
        // clean draw list so it will not run in a loop adding again and again, making the game to freeze
        for (int i = 0;  i < this.individuals.size(); i++)
        {
            individuals.remove(i);
        }

        // TODO: debug text, remove latter
        this.gameTextProvider.setTextColor(Color.YELLOW);
        this.gameTextProvider.setTextPosition(tileSize * 10, tileSize);
        String enemyCollisionText = this.gameTextProvider.getGameTextByKey("game-help-debug");
        this.gameTextProvider.showTextInsideGame(g2D, enemyCollisionText);

        g2D.dispose(); // free up memory, destroy after draw
    }
}
