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
    private final int maxDistanceAllowedDown;
    private final int maxDistanceAllowedUp;
    private String direction;
    private BufferedImage enemyAsset;

    public Enemy(
        GamePanel gamePanel,
        int defaultPositionX,
        int defaultPositionY,
        int maxDistanceAllowedDown,
        int maxDistanceAllowedUp,
        String direction,
        int speed,
        String assetPath
    )
    {
        super(defaultPositionX, defaultPositionY, speed, assetPath);
        this.gamePanel = gamePanel;
        this.maxDistanceAllowedDown = maxDistanceAllowedDown;
        this.maxDistanceAllowedUp = maxDistanceAllowedUp;
        this.direction = direction;
    }

    @Override
    public void getAssetImages(String assetPath)
    {
        this.enemyAsset = Objects.requireNonNull(getImageFromAssets(assetPath));
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
        g2D.drawImage(this.enemyAsset, this.positionX, this.positionY, gamePanel.titleSize, gamePanel.titleSize, null);
    }
}
