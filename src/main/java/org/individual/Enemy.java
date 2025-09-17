package org.individual;

import org.game.GamePanel;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Map;
import java.util.Objects;

import static org.helpers.ToolsHelper.getImageFromAssets;
import static org.game.GamePanel.tileSize;
import static org.world.GameWorld.checkIfAssetIsInsideTheBoundary;

public class Enemy extends Individual
{

    static protected final String COLLISION_ENEMY_ASSET_KEY_PREFIX = "collision-";
    GamePanel gamePanel;
    private final int maxDistanceAllowedToMove;
    private String direction;
    public BufferedImage enemyAsset;
    public BufferedImage enemyMovingAsset;
    public BufferedImage enemyCollisionAsset;
    private boolean isEnemyCollidingWithPlayer = false;
    final private Map<String, BufferedImage> enemyAssetsMap;
    Player player;

    public Enemy(
        GamePanel gamePanel,
        int defaultPositionX,
        int defaultPositionY,
        int maxDistanceAllowedToMove,
        String direction,
        int speed,
        String assetPath,
        Player player,
        Map<String, BufferedImage> enemyAssetsMap
    )
    {
        super(defaultPositionX, defaultPositionY, speed, assetPath);
        this.gamePanel = gamePanel;
        this.maxDistanceAllowedToMove = maxDistanceAllowedToMove;
        this.direction = direction;
        this.player = player;
        this.enemyAssetsMap = enemyAssetsMap;
        buildEnemyCollisionArea();
    }

    private void buildEnemyCollisionArea()
    { // make the collision area small that the player rectangle so upper corners will not hit solid world assets
        this.collisionArea = new Rectangle();
        this.collisionArea.x = 0;
        this.collisionArea.y = 0;
        this.collisionArea.height = tileSize;
        this.collisionArea.width = tileSize;
    }

    @Override
    public void getAssetImages(String assetPath)
    {
        this.enemyMovingAsset = Objects.requireNonNull(getImageFromAssets(assetPath));
        this.enemyCollisionAsset = Objects.requireNonNull(getImageFromAssets("/enemy/ghost/ghost-panic-left.png")); // TODO: add this in enemy assets class EnemyAssets or create a list of assets
    }

    @Override
    public void update()
    {
        // stop enemy movement if player is colliding with enemy
        if (!enemyCheckCollisionWithPlayer())
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

    }

    private boolean enemyCheckCollisionWithPlayer()
    {
        // player collision rectangle
        int playerLeftX = player.positionX + player.collisionArea.x;
        int playerRightX = player.positionX + player.collisionArea.x + player.collisionArea.width;
        int playerTopY = player.positionY + player.collisionArea.y;
        int playerBottomY = player.positionY + player.collisionArea.y + player.collisionArea.height;

        // enemy collision rectangle
        int enemyLeftX = this.positionX + this.collisionArea.x;
        int enemyRightX = this.positionX + this.collisionArea.x + player.collisionArea.width;
        int enemyTopY = this.positionY + this.collisionArea.y;
        int enemyBottomY = this.positionY + this.collisionArea.y + player.collisionArea.height;

        // player world matrix index
        int playerLeftCol = playerLeftX / tileSize;
        int playerRightCol = playerRightX / tileSize;
        int playerTopRow = playerTopY / tileSize;
        int playerBottomRow = playerBottomY / tileSize;

        // enemy world matrix index
        int enemyLeftCol = enemyLeftX / tileSize;
        int enemyRightCol = enemyRightX / tileSize;
        int enemyTopRow = enemyTopY / tileSize;
        int enemyBottomRow = enemyBottomY / tileSize;

        if (playerLeftCol == enemyLeftCol && playerTopRow == enemyTopRow)
        {
            this.isEnemyCollidingWithPlayer = true;
            return true;
        }
        else if (playerRightCol == enemyRightCol && playerBottomRow == enemyBottomRow)
        {
            this.isEnemyCollidingWithPlayer = true;
            return true;
        }

        this.isEnemyCollidingWithPlayer = false;
        return false;
    }

    @Override
    public void draw(Graphics2D g2D)
    {
        int worldEnemyAssetPositionX = this.positionX - this.player.positionX + this.player.playerScreenX;
        int worldEnemyAssetPositionY = this.positionY - this.player.positionY + this.player.playerScreenY;

        // draw enemy only if is inside the screen view
        if(checkIfAssetIsInsideTheBoundary(this.positionX, this.positionY, this.player, tileSize))
        {
            this.enemyAsset = this.isEnemyCollidingWithPlayer ? this.enemyCollisionAsset : this.enemyMovingAsset;
            if (!this.enemyAssetsMap.isEmpty() && this.enemyAssetsMap.containsKey(this.direction))
            {
                String collisionAssetKey = COLLISION_ENEMY_ASSET_KEY_PREFIX + this.direction;
                if (this.isEnemyCollidingWithPlayer && this.enemyAssetsMap.containsKey(collisionAssetKey))
                {
                    this.enemyAsset = this.enemyAssetsMap.get(collisionAssetKey);
                }
                else
                {
                    this.enemyAsset = this.enemyAssetsMap.get(this.direction);
                }
            }
            g2D.drawImage(this.enemyAsset, worldEnemyAssetPositionX, worldEnemyAssetPositionY, tileSize, tileSize, null);
        }
    }
}
