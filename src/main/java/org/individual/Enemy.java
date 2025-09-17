package org.individual;

import org.game.GamePanel;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Objects;

import static org.helpers.ToolsHelper.getImageFromAssets;
import static org.game.GamePanel.tileSize;
import static org.world.GameWorld.checkIfAssetIsInsideTheBoundary;

public class Enemy extends Individual
{

    GamePanel gamePanel;
    private final int maxDistanceAllowedToMove;
    private String direction;
    public BufferedImage enemyAsset;
    Player player;

    public Enemy(
        GamePanel gamePanel,
        int defaultPositionX,
        int defaultPositionY,
        int maxDistanceAllowedToMove,
        String direction,
        int speed,
        String assetPath,
        Player player
    )
    {
        super(defaultPositionX, defaultPositionY, speed, assetPath);
        this.gamePanel = gamePanel;
        this.maxDistanceAllowedToMove = maxDistanceAllowedToMove;
        this.direction = direction;
        this.player = player;
    }

    @Override
    public void getAssetImages(String assetPath)
    {
        this.enemyAsset = Objects.requireNonNull(getImageFromAssets(assetPath));
    }

    @Override
    public void update()
    {
        int maxAllowedMoveLeftRight = (this.initialPositionX + maxDistanceAllowedToMove);
        if (this.positionX < maxAllowedMoveLeftRight && direction.equals("right"))
        {
            this.positionX = this.positionX + this.speed;
        }
        else if (this.positionX == maxAllowedMoveLeftRight && direction.equals("right"))
        {
            direction = "left";
        }

        int minAllowedMoveLeftRight = this.initialPositionX;
        if (this.positionX > minAllowedMoveLeftRight && direction.equals("left"))
        {
            this.positionX = this.positionX - this.speed;
        }
        else if (this.positionX == minAllowedMoveLeftRight && direction.equals("left"))
        {
            direction = "right";
        }

        int maxAllowedMove = (this.initialPositionY + maxDistanceAllowedToMove);
        if (this.positionY < maxAllowedMove && direction.equals("down"))
        {
            this.positionY = this.positionY + this.speed;
        }
        else if (this.positionY == maxAllowedMove && direction.equals("down"))
        {
            direction = "up";
        }

        int minAllowedMove = this.initialPositionY;
        if (this.positionY > minAllowedMove && direction.equals("up"))
        {
            this.positionY = this.positionY - this.speed;
        }
        else if (this.positionY == minAllowedMove && direction.equals("up"))
        {
            direction = "down";
        }
    }

    @Override
    public void draw(Graphics2D g2D)
    {
        int worldEnemyAssetPositionX = this.positionX - this.player.positionX + this.player.playerScreenX;
        int worldEnemyAssetPositionY = this.positionY - this.player.positionY + this.player.playerScreenY;

        // draw enemy only if is inside the screen view
        if(checkIfAssetIsInsideTheBoundary(this.positionX, this.positionY, this.player, tileSize))
        {
            g2D.drawImage(this.enemyAsset, worldEnemyAssetPositionX, worldEnemyAssetPositionY, tileSize, tileSize, null);
        }
    }
}
