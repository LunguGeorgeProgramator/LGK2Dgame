package org.individual;

import org.game.GamePanel;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Objects;

import static org.helpers.ToolsHelper.getImageFromAssets;

public class Enemy extends Individual
{

    GamePanel gamePanel;
    int maxDistanceAllowedDown;
    int maxDistanceAllowedUp;
    String direction;

    public Enemy(
        GamePanel gamePanel,
        int defaultPositionX,
        int defaultPositionY,
        int maxDistanceAllowedDown,
        int maxDistanceAllowedUp,
        String direction,
        int speed
    )
    {
        super(defaultPositionX, defaultPositionY, speed);
        this.gamePanel = gamePanel;
        this.maxDistanceAllowedDown = maxDistanceAllowedDown;
        this.maxDistanceAllowedUp = maxDistanceAllowedUp;
        this.direction = direction;
        getAssetImages();
    }

    @Override
    public void getAssetImages() {

    }

    @Override
    public void update()
    {
        if (this.positionY < maxDistanceAllowedDown && direction.equals("down"))
        {
            this.positionY = this.positionY + this.speed;
        }
        else if (this.positionY == maxDistanceAllowedDown)
        {
            direction = "up";
        }

        if (this.positionY > maxDistanceAllowedUp && direction.equals("up"))
        {
            this.positionY = this.positionY - this.speed;
        }
        else if (this.positionY == maxDistanceAllowedUp)
        {
            direction = "down";
        }
    }

    @Override
    public void draw(Graphics2D g2D)
    {
        BufferedImage enemyAsset = Objects.requireNonNull(getImageFromAssets("/enemy/color-monster.png"));
        g2D.drawImage(enemyAsset, this.positionX, this.positionY, gamePanel.titleSize, gamePanel.titleSize, null);
    }
}
