package org.individual;

import org.game.GamePanel;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Objects;

import static org.helpers.ToolsHelper.getImageFromAssets;
import static org.game.GamePanel.tileSize;

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
        int maxAllowedMove = (this.initialPositionY + maxDistanceAllowedToMove);
        if (this.positionY < maxAllowedMove && direction.equals("down"))
        {
            this.positionY = this.positionY + this.speed;
        }
        else if (this.positionY == maxAllowedMove)
        {
            direction = "up";
        }

        int minAllowedMove = this.initialPositionY;
        if (this.positionY > minAllowedMove && direction.equals("up"))
        {
            this.positionY = this.positionY - this.speed;
        }
        else if (this.positionY == minAllowedMove)
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
        if(checkIfAssetIsInsideTheBoundary(worldEnemyAssetPositionX, worldEnemyAssetPositionY, this.player))
        {
            g2D.drawImage(this.enemyAsset, worldEnemyAssetPositionX, worldEnemyAssetPositionY, tileSize, tileSize, null);
        }
    }

    private boolean checkIfAssetIsInsideTheBoundary(int worldAssetPositionX, int worldAssetPositionY, Player player)
    {
        int halfOfScreenWith = GamePanel.screenWith / 2;
        int halfOfScreenHeight = GamePanel.screenHeight / 2;
        boolean isAssetBoundaryLeftLimitDraw = worldAssetPositionX + GamePanel.tileSize > player.playerScreenX - halfOfScreenWith;
        boolean isAssetBoundaryRightLimitDraw = worldAssetPositionX + GamePanel.tileSize < player.positionX + player.playerScreenX - halfOfScreenWith;
        boolean isAssetBoundaryDownLimitDraw = worldAssetPositionY + GamePanel.tileSize > player.positionY - player.playerScreenY - GamePanel.screenHeight;
        boolean isAssetBoundaryUpLimitDraw = worldAssetPositionY - GamePanel.tileSize < player.positionY + halfOfScreenHeight;
        return isAssetBoundaryUpLimitDraw && isAssetBoundaryDownLimitDraw && isAssetBoundaryLeftLimitDraw && isAssetBoundaryRightLimitDraw;
    }
}
