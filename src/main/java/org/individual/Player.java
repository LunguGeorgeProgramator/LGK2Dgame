package org.individual;
import org.game.GamePanel;
import org.game.KeyBoardHandler;
import org.individual.Individual;

import java.awt.Color;
import java.awt.Graphics2D;

public class Player extends Individual
{
    GamePanel gamePanel;
    KeyBoardHandler keyBoardHandler;

    public Player(GamePanel gamePanel, KeyBoardHandler keyBoardHandler)
    {
        super(100, 100, 4); // set player position x, y and speed
        this.gamePanel = gamePanel;
        this.keyBoardHandler = keyBoardHandler;
    }

    @Override
    public void update()
    {
        if (keyBoardHandler.upPressed)
        {
            this.positionY = this.positionY - this.speed;
        }
        else if (keyBoardHandler.downPressed)
        {
            this.positionY = this.positionY + this.speed;
        }
        else if (keyBoardHandler.leftPressed)
        {
            this.positionX = this.positionX - this.speed;
        }
        else if (keyBoardHandler.rightPressed)
        {
            this.positionX = this.positionX + this.speed;
        }
    }

    @Override
    public void draw(Graphics2D g2D)
    {
        g2D.setColor(Color.WHITE);
        g2D.fillRect(this.positionX, this.positionY, gamePanel.titleSize, gamePanel.titleSize);
    }
}
