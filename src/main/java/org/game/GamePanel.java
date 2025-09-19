package org.game;

import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Dimension;
import java.awt.Color;

import org.individual.Player;
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
    public final CollisionChecker collisionChecker;
    Thread gameThread;

    public GamePanel()
    {
        this.keyBoardHandler = new KeyBoardHandler();
        this.setPreferredSize(new Dimension(screenWith, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(this.keyBoardHandler);
        this.setFocusable(true);
        this.player = new Player(this, this.keyBoardHandler);
        this.enemies = new Enemies(this, player);
        this.gameWorld = new GameWorld(this, "/worldMaps/WorldMap.txt");
        this.worldItems = new WorldItems(this, this.player);
        this.collisionChecker = new CollisionChecker(this);
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
        this.player.update();
        this.enemies.update();
        this.worldItems.update();
    }

    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        Graphics2D g2D = (Graphics2D) g;

        this.gameWorld.draw(g2D);
        this.enemies.draw(g2D);
        this.player.draw(g2D);
        this.worldItems.draw(g2D);

//        int pX = this.player.positionX;
//        int pY = this.player.positionY;
//        int eX = this.enemy.positionX;
//        int eY = this.enemy.positionY;
//        if (pY< eY)
//        {
//            this.player.draw(g2D);
//            this.enemy.draw(g2D);
//        }
//        else
//        {
//            this.enemy.draw(g2D);
//            this.player.draw(g2D);
//        }

        g2D.dispose(); // free up memory, destroy after draw
    }
}
