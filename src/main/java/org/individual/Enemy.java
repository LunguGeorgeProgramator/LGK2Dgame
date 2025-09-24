package org.individual;

import org.game.CollisionChecker;
import org.game.GamePanel;

import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

import static org.helpers.ToolsHelper.getScaledImageFromAssets;
import static org.game.GamePanel.tileSize;
import static org.world.GameWorld.checkIfAssetIsInsideTheBoundary;

public class Enemy extends Individual
{

    protected static final String COLLISION_ENEMY_ASSET_KEY_PREFIX = "collision-";
    private final String ENEMY_COLLISION_TEXT_KEY = "enemy-collision";
    private final String enemyCollisionText;
    private final Double ENEMY_DAMAGE_TO_PLAYER = 0.001;
    private double hitDamage;
    private final String ENEMY_DIRECTION_LEFT = MovingDirection.LEFT.getValue();
    private final String ENEMY_DIRECTION_RIGHT = MovingDirection.RIGHT.getValue();
    private final String ENEMY_DIRECTION_UP = MovingDirection.UP.getValue();
    private final String ENEMY_DIRECTION_DOWN = MovingDirection.DOWN.getValue();
    private final int maxDistanceAllowedToMove;
    private String direction;
    public BufferedImage enemyAsset;
    private boolean isEnemyCollidingWithPlayer = false;
    final private Map<String, BufferedImage> enemyAssetsMap;
    private final Player player;
    private final CollisionChecker collisionChecker;
    private final GamePanel gamePanel;
    private int frameCounterDamage;
    private boolean isAllowedToInflictDamage = false;

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
        this.maxDistanceAllowedToMove = maxDistanceAllowedToMove;
        this.direction = direction;
        this.player = player;
        this.enemyAssetsMap = enemyAssetsMap;
        buildEnemyCollisionArea();
        this.gamePanel = gamePanel;
        this.collisionChecker = this.gamePanel.collisionChecker;
        this.enemyCollisionText = this.gamePanel.gameTextProvider.getGameTextByKey(ENEMY_COLLISION_TEXT_KEY);
        this.hitDamage = 0;
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
        this.enemyAsset = Objects.requireNonNull(getScaledImageFromAssets(assetPath));
    }

    @Override
    public void update()
    {
        this.isEnemyCollidingWithPlayer = this.collisionChecker.checkPlayerCollisionWithObject(this.player, this.positionX, this.positionY, this.isEnemyCollidingWithPlayer);

        // stop enemy movement if player is colliding with enemy
        if (!this.isEnemyCollidingWithPlayer)
        {
            int maxAllowedMoveLeftRight = (this.initialPositionX + maxDistanceAllowedToMove);
            if (this.positionX < maxAllowedMoveLeftRight && direction.equals(ENEMY_DIRECTION_RIGHT))
            {
                this.positionX = this.positionX + this.speed;
            }
            else if (this.positionX == maxAllowedMoveLeftRight && direction.equals(ENEMY_DIRECTION_RIGHT))
            {
                direction = ENEMY_DIRECTION_LEFT;
            }

            int minAllowedMoveLeftRight = this.initialPositionX;
            if (this.positionX > minAllowedMoveLeftRight && direction.equals(ENEMY_DIRECTION_LEFT))
            {
                this.positionX = this.positionX - this.speed;
            }
            else if (this.positionX == minAllowedMoveLeftRight && direction.equals(ENEMY_DIRECTION_LEFT))
            {
                direction = ENEMY_DIRECTION_RIGHT;
            }

            int maxAllowedMove = (this.initialPositionY + maxDistanceAllowedToMove);
            if (this.positionY < maxAllowedMove && direction.equals(ENEMY_DIRECTION_DOWN))
            {
                this.positionY = this.positionY + this.speed;
            }
            else if (this.positionY == maxAllowedMove && direction.equals(ENEMY_DIRECTION_DOWN))
            {
                direction = ENEMY_DIRECTION_UP;
            }

            int minAllowedMove = this.initialPositionY;
            if (this.positionY > minAllowedMove && direction.equals(ENEMY_DIRECTION_UP))
            {
                this.positionY = this.positionY - this.speed;
            }
            else if (this.positionY == minAllowedMove && direction.equals(ENEMY_DIRECTION_UP))
            {
                direction = ENEMY_DIRECTION_DOWN;
            }
        }
        else
        {
            this.isAllowedToInflictDamage = this.slowDownHealthDamageTaken();
            if (this.isAllowedToInflictDamage)
            {
                this.player.playerHealth = this.player.playerHealth - ENEMY_DAMAGE_TO_PLAYER;
                this.hitDamage = this.hitDamage + ENEMY_DAMAGE_TO_PLAYER; // TODO rethink this display variable is not ok damage is increase over time
                this.frameCounterDamage = 0;
            }

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
            g2D.drawImage(this.enemyAsset, worldEnemyAssetPositionX, worldEnemyAssetPositionY, null);

            if (this.isEnemyCollidingWithPlayer && this.isAllowedToInflictDamage)
            {
                this.gamePanel.gameTextProvider.setTextColor(Color.RED);
                this.gamePanel.gameTextProvider.setTextPosition(this.player.playerScreenX + 50, this.player.playerScreenY + this.randomXYMultiplier());
                this.gamePanel.gameTextProvider.showTextInsideGame(g2D, String.format(this.enemyCollisionText, this.hitDamage));
            }
        }
    }

    private int randomXYMultiplier()
    {
        Random random = new Random();
        return random.nextInt(10) + 1;
    }

    protected boolean slowDownHealthDamageTaken()
    {
        final int numberOfFramesLimit = 25;
        this.frameCounterDamage++;
        return this.frameCounterDamage > numberOfFramesLimit;
    }

}
