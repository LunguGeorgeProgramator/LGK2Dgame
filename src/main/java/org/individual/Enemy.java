package org.individual;

import org.game.CollisionChecker;
import org.game.GamePanel;

import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.List;
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
    private final Double PLAYER_DAMAGE_TO_ENEMY = 10.1;
    private double hitDamage;
    private final int maxDistanceAllowedToMove;
    private String direction;
    public BufferedImage enemyAsset;
    private boolean isEnemyCollidingWithPlayer = false;
    final private Map<String, BufferedImage> enemyAssetsMap;
    private final Player player;
    private final CollisionChecker collisionChecker;
    private final GamePanel gamePanel;
    private boolean isAllowedToInflictDamage = false;
    private final Map<String, Map<Integer, BufferedImage>> enemyUnderAttackAssetsMap;
    private final List<MovingDirection> enemyMovingDirectionList;
    private int maxAllowedDownMovement;
    private int maxAllowedUpMovement;
    private int maxAllowedRightMovement;
    private int maxAllowedLeftMovement;
    private Integer nextMovementIndex;
    public int enemyMaxHealth = 50;
    public double enemyHealth = 50;
    public int enemyId;
    public String enemyName;
    public int enemyWorldMatrixCol;
    public int enemyWorldMatrixRow;
    public boolean resetEnemyHealth = false;


    public Enemy(
        GamePanel gamePanel,
        int enemyId,
        String enemyName,
        int enemyWorldMatrixCol,
        int enemyWorldMatrixRow,
        int defaultPositionX,
        int defaultPositionY,
        int maxDistanceAllowedToMove,
        List<MovingDirection> enemyMovingDirectionList,
        int speed,
        String assetPath,
        Player player,
        Map<String, BufferedImage> enemyAssetsMap,
        Map<String, Map<Integer, BufferedImage>> enemyUnderAttackAssetsMap
    )
    {
        super(defaultPositionX, defaultPositionY, speed, assetPath);
        this.enemyId = enemyId;
        this.enemyName = enemyName;
        this.enemyWorldMatrixCol = enemyWorldMatrixCol;
        this.enemyWorldMatrixRow = enemyWorldMatrixRow;
        this.maxDistanceAllowedToMove = maxDistanceAllowedToMove;
        this.player = player;
        this.enemyAssetsMap = enemyAssetsMap;
        this.enemyMovingDirectionList = enemyMovingDirectionList;
        this.direction = !this.enemyMovingDirectionList.isEmpty() ? this.enemyMovingDirectionList.getFirst().getValue() : MovingDirection.UP.getValue();
        buildEnemyCollisionArea();
        this.gamePanel = gamePanel;
        this.collisionChecker = this.gamePanel.collisionChecker;
        this.enemyCollisionText = this.gamePanel.gameTextProvider.getGameTextByKey(ENEMY_COLLISION_TEXT_KEY);
        this.hitDamage = 0;
        this.enemyUnderAttackAssetsMap = enemyUnderAttackAssetsMap;
        this.nextMovementIndex = !this.enemyMovingDirectionList.isEmpty() ? 0 : null;
        initializeMaxMovementDirection();
    }

    private void initializeMaxMovementDirection()
    {
        this.maxAllowedDownMovement = this.initialPositionY + this.maxDistanceAllowedToMove;
        this.maxAllowedUpMovement = this.initialPositionY - this.maxDistanceAllowedToMove;
        this.maxAllowedRightMovement = this.initialPositionX + this.maxDistanceAllowedToMove;
        this.maxAllowedLeftMovement = this.initialPositionX - this.maxDistanceAllowedToMove;
    }

    private void resetMaxMovementDirection()
    {
        this.maxAllowedDownMovement = this.positionY + this.maxDistanceAllowedToMove;
        this.maxAllowedUpMovement = this.positionY - this.maxDistanceAllowedToMove;
        this.maxAllowedRightMovement = this.positionX + this.maxDistanceAllowedToMove;
        this.maxAllowedLeftMovement = this.positionX - this.maxDistanceAllowedToMove;
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

    private void getNextDirectionOfMovement()
    {
        this.nextMovementIndex = this.nextMovementIndex + 1 == this.enemyMovingDirectionList.size() ? 0 : this.nextMovementIndex + 1;
        MovingDirection nextDirection = this.enemyMovingDirectionList.get(this.nextMovementIndex);
        this.direction = nextDirection.getValue();
    }

    @Override
    public void update()
    {
        this.isEnemyCollidingWithPlayer = this.collisionChecker.checkPlayerCollisionWithObject(this.player, this.positionX, this.positionY);
        if (!this.isEnemyCollidingWithPlayer)
        {
            if (this.direction.equals(MovingDirection.DOWN.getValue()) && this.maxAllowedDownMovement > this.positionY)
            {
                this.positionY = this.positionY + this.speed;
                if (this.maxAllowedDownMovement <= this.positionY)
                {
                    this.getNextDirectionOfMovement();
                }
            }
            else if (this.direction.equals(MovingDirection.UP.getValue()) && this.maxAllowedUpMovement < this.positionY)
            {
                this.positionY = this.positionY - this.speed;
                if (this.maxAllowedUpMovement >= this.positionY)
                {
                    this.getNextDirectionOfMovement();
                }
            }
            else if (this.direction.equals(MovingDirection.RIGHT.getValue()) && this.maxAllowedRightMovement > this.positionX)
            {
                this.positionX = this.positionX + this.speed;
                if (this.maxAllowedRightMovement <= this.positionX)
                {
                    this.getNextDirectionOfMovement();
                }
            }
            else if (this.direction.equals(MovingDirection.LEFT.getValue()) && this.maxAllowedLeftMovement < this.positionX)
            {
                this.positionX = this.positionX - this.speed;
                if (this.maxAllowedLeftMovement >= this.positionX)
                {
                    this.getNextDirectionOfMovement();
                }
            }
            else
            {
                this.resetMaxMovementDirection();
            }
        }
        else
        {
            this.isAllowedToInflictDamage = this.slowDownGame();
            if (this.isAllowedToInflictDamage)
            { // only inflict damage to player is he is not swinging his sword
                if (!this.player.isPlayerSwordSwing)
                {
                    this.player.playerHealth = this.player.playerHealth - ENEMY_DAMAGE_TO_PLAYER;
                    this.hitDamage = this.hitDamage + ENEMY_DAMAGE_TO_PLAYER; // TODO rethink this display variable is not ok damage is increase over time
                }
                else
                {
                    this.enemyHealth = this.enemyHealth - PLAYER_DAMAGE_TO_ENEMY;
                }
            }
        }
        this.changeAssetNumberByFrameCounter(2, 10);

        if (this.resetEnemyHealth)
        { // for debug
            this.enemyHealth = this.enemyMaxHealth;
        }

        if (this.enemyHealth <= 0)
        {
            String enemyWorldId = this.gamePanel.gameSavedStats.getEnemyWorldIdFormat(this);
            this.gamePanel.gameSavedStats.updateEnemyAliveStatus(enemyWorldId, false);
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
                    if (this.player.isPlayerSwordSwing && this.enemyUnderAttackAssetsMap != null && !this.enemyUnderAttackAssetsMap.isEmpty())
                    {
                        Map<Integer, BufferedImage> underAttacAssetsMap = this.enemyUnderAttackAssetsMap.get(this.direction);
                        this.enemyAsset = underAttacAssetsMap != null && !underAttacAssetsMap.isEmpty() ? underAttacAssetsMap.get(this.dynamicAssetNumber) : null;
                    }
                    else
                    {
                        this.enemyAsset = this.enemyAssetsMap.get(collisionAssetKey);
                    }
                }
                else
                {
                    this.enemyAsset = this.enemyAssetsMap.get(this.direction);
                }
            }

            if (this.enemyAsset != null && this.enemyHealth > 0)
            {
                g2D.drawImage(this.enemyAsset, worldEnemyAssetPositionX, worldEnemyAssetPositionY, null);
                this.drawEnemyLifeBar(g2D, worldEnemyAssetPositionX - 2, worldEnemyAssetPositionY - 20);
            }

            if (this.isEnemyCollidingWithPlayer && this.isAllowedToInflictDamage)
            {
                this.gamePanel.gameTextProvider.setTextColor(Color.RED);
                this.gamePanel.gameTextProvider.setTextPosition(this.player.playerScreenX + 50, this.player.playerScreenY + this.randomXYMultiplier());
                this.gamePanel.gameTextProvider.showTextInsideGame(g2D, String.format(this.enemyCollisionText, this.hitDamage));
            }
        }
    }

    private void drawEnemyLifeBar(Graphics2D g2d, int positionX, int positionY)
    {
        int height = 10;
        // Draw background (gray)
        g2d.setColor(Color.LIGHT_GRAY);
        g2d.fillRect(positionX, positionY, this.enemyMaxHealth, height);

        // Draw red bar representing the current value
        int filledWidth = (int) this.enemyHealth;
        g2d.setColor(Color.RED);
        g2d.fillRect(positionX, positionY, filledWidth, height);

        // Optional: Draw border
        g2d.setColor(Color.ORANGE);
        g2d.drawRect(positionX, positionY, this.enemyMaxHealth, height);
    }

    private int randomXYMultiplier()
    {
        Random random = new Random();
        return random.nextInt(10) + 1;
    }

}
