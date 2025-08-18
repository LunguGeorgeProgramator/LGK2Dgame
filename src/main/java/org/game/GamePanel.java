package org.game;

import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Dimension;
import java.awt.Color;

import org.individual.Enemy;
import org.individual.Player;
import org.world.GameWorld;

public class GamePanel extends JPanel implements Runnable
{
    final int originalTileSize = 16; // 16 x 16 pixel
    final int scale = 3;

    public final int titleSize = originalTileSize * scale; // 48 x 48 pixel
    public final int maxScreenColumns = 16;
    public final int maxScreenRows = 12;
    final int screenWith = titleSize * maxScreenColumns; // 768 pixels
    final int screeHeight = titleSize * maxScreenRows; // 576 pixels

    final int framePerSecond = 60;
    final KeyBoardHandler keyBoardHandler;
    final Player player;
    final Enemy firtEnemy;
    final Enemy secondEnemy;
    final GameWorld gameWorld;
    Thread gameThread;

    public GamePanel()
    {
        this.keyBoardHandler = new KeyBoardHandler();
        this.setPreferredSize(new Dimension(screenWith, screeHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(this.keyBoardHandler);
        this.setFocusable(true);
        this.player = new Player(this, this.keyBoardHandler);
        this.firtEnemy = new Enemy(this, 200, 200, 300, 200, "down", 1);
        this.secondEnemy = new Enemy(this, 400, 200, 400, 100, "up", 4);
        this.gameWorld = new GameWorld(this);
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
        this.firtEnemy.update();
        this.secondEnemy.update();
    }

    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        Graphics2D g2D = (Graphics2D) g;

        this.gameWorld.draw(g2D);

        this.secondEnemy.draw(g2D);

        int pX = this.player.positionX;
        int pY = this.player.positionY;
        int eX = this.firtEnemy.positionX;
        int eY = this.firtEnemy.positionY;
        if (pY< eY)
        {
            this.player.draw(g2D);
            this.firtEnemy.draw(g2D);
        }
        else
        {
            this.firtEnemy.draw(g2D);
            this.player.draw(g2D);
        }

        g2D.dispose(); // free up memory, destroy after draw
    }
}
