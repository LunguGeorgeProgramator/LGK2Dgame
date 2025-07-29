package org.individual;

import org.game.GamePanel;

import java.awt.Color;
import java.awt.Graphics2D;

public class Enemy extends Individual
{

    GamePanel gamePanel;

    public Enemy(GamePanel gamePanel)
    {
        super(200, 200, 4);
        this.gamePanel = gamePanel;
    }

    @Override
    public void update() {

    }

    @Override
    public void draw(Graphics2D g2D) {
        g2D.setColor(Color.GREEN);
        g2D.fillRect(this.positionX, this.positionY, gamePanel.titleSize, gamePanel.titleSize);
    }
}
