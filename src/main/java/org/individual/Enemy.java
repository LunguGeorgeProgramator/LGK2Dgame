package org.individual;

import org.game.CollisionChecker;
import org.game.GamePanel;
import org.individual.models.MovingDirection;

import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Map;

import static org.game.GamePanel.originalTileSize;
import static org.game.GamePanel.tileSize;
import static org.world.GameWorld.checkIfAssetIsInsideTheBoundary;
import static org.helpers.ToolsHelper._randomXYMultiplier;

public class Enemy extends Individual
{

    private static final String ENEMY_COLLISION_TEXT_KEY = "enemy-collision";
    private static final Double ENEMY_DAMAGE_TO_PLAYER = 10.1;
    private static final Double PLAYER_DAMAGE_TO_ENEMY = 10.1;
    private static final int MAX_ASSETS_INDEX = 2;
    private static final int NUMBER_OF_FRAMES_LIMIT = 10;
    private static final String ENEMY_ASSETS_MAP_KEY_NAME = "enemyAssetsMap";
    private static final String ENEMY_COLLISION_ASSETS_MAP_KEY_NAME = "enemyCollisionAssetsMap";
    private static final String ENEMY_UNDER_ATTACK_ASSETS_MAP_KEY_NAME = "enemyUnderAttackAssetsMap";
    private final String enemyCollisionText;
    private double hitDamage;
    private final int maxDistanceAllowedToMove;
    private MovingDirection direction;
    public BufferedImage enemyAsset;
    private boolean isEnemyCollidingWithPlayer = false;
    final private Map<MovingDirection, Map<Integer, BufferedImage>> enemyAssetsMap;
    private final Player player;
    private final CollisionChecker collisionChecker;
    private final GamePanel gamePanel;
    private boolean isAllowedToInflictDamage = false;
    private final Map<MovingDirection, Map<Integer, BufferedImage>> enemyUnderAttackAssetsMap;
    private final Map<MovingDirection, Map<Integer, BufferedImage>> enemyColisionAssetsMap;
    private Map<String, Map<MovingDirection, Map<Integer, BufferedImage>>> allEnemyAsssetsMap;
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
    public Rectangle damageArea;


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
        Player player,
        Map<MovingDirection, Map<Integer, BufferedImage>> enemyAssetsMap,
        Map<MovingDirection, Map<Integer, BufferedImage>> enemyColisionAssetsMap,
        Map<MovingDirection, Map<Integer, BufferedImage>> enemyUnderAttackAssetsMap
    )
    {
        super(defaultPositionX, defaultPositionY, speed);
        this.enemyId = enemyId;
        this.enemyName = enemyName;
        this.enemyWorldMatrixCol = enemyWorldMatrixCol;
        this.enemyWorldMatrixRow = enemyWorldMatrixRow;
        this.maxDistanceAllowedToMove = maxDistanceAllowedToMove;
        this.player = player;
        this.enemyAssetsMap = enemyAssetsMap;
        this.enemyMovingDirectionList = enemyMovingDirectionList;
        this.direction = !this.enemyMovingDirectionList.isEmpty() ? this.enemyMovingDirectionList.getFirst() : MovingDirection.UP;
        this.gamePanel = gamePanel;
        this.collisionChecker = this.gamePanel.collisionChecker;
        this.enemyCollisionText = this.gamePanel.gameTextProvider.getGameTextByKey(ENEMY_COLLISION_TEXT_KEY);
        this.hitDamage = 0;
        this.enemyUnderAttackAssetsMap = enemyUnderAttackAssetsMap;
        this.enemyColisionAssetsMap = enemyColisionAssetsMap;
        this.nextMovementIndex = !this.enemyMovingDirectionList.isEmpty() ? 0 : null;
        _resetMaxMovementDirection();
        this.setAssetImages();
        this.buildEnemyCollisionArea();
        this.buildEnemyDamageCollisionArea();
    }

    private void buildEnemyCollisionArea()
    {
        this.collisionArea = new Rectangle();
        this.collisionArea.x = 0;
        this.collisionArea.y = 0;
        this.collisionArea.height = tileSize / 2;
        this.collisionArea.width = tileSize / 2;
    }

    private void buildEnemyDamageCollisionArea()
    {
        this.damageArea = new Rectangle();
        this.damageArea.x = 0;
        this.damageArea.y = 0;
        this.damageArea.height = tileSize;
        this.damageArea.width = tileSize;
    }

    private void _resetMaxMovementDirection()
    {
        this.maxAllowedDownMovement = this.positionY + this.maxDistanceAllowedToMove;
        this.maxAllowedUpMovement = this.positionY - this.maxDistanceAllowedToMove;
        this.maxAllowedRightMovement = this.positionX + this.maxDistanceAllowedToMove;
        this.maxAllowedLeftMovement = this.positionX - this.maxDistanceAllowedToMove;
    }

    public void setAssetImages()
    {
        this.enemyAsset = null;
        this.allEnemyAsssetsMap = Map.of(
            ENEMY_ASSETS_MAP_KEY_NAME, (this.enemyAssetsMap != null && !this.enemyAssetsMap.isEmpty() ? this.enemyAssetsMap : Map.of()),
            ENEMY_COLLISION_ASSETS_MAP_KEY_NAME, (this.enemyColisionAssetsMap != null && !this.enemyColisionAssetsMap.isEmpty() ?  this.enemyColisionAssetsMap : Map.of()),
            ENEMY_UNDER_ATTACK_ASSETS_MAP_KEY_NAME, (this.enemyUnderAttackAssetsMap != null && !this.enemyUnderAttackAssetsMap.isEmpty() ? this.enemyUnderAttackAssetsMap : Map.of())
        );
    }

    private void getNextDirectionOfMovement()
    {
        this.nextMovementIndex = this.nextMovementIndex + 1 == this.enemyMovingDirectionList.size() ? 0 : this.nextMovementIndex + 1;
        this.direction = this.enemyMovingDirectionList.get(this.nextMovementIndex);
    }

    @Override
    public void update()
    {
        this.isAllowedToInflictDamage = this.slowDownGame();
        this.isEnemyCollidingWithPlayer = this.collisionChecker.isPlayerCollidingWithIndividual(this.player, this.damageArea);
        if (!this.isEnemyCollidingWithPlayer)
        {
            if (this.direction.equals(MovingDirection.DOWN) && this.maxAllowedDownMovement > this.positionY)
            {
                this.positionY = this.positionY + this.speed;
                if (this.maxAllowedDownMovement <= this.positionY)
                {
                    this.getNextDirectionOfMovement();
                }
            }
            else if (this.direction.equals(MovingDirection.UP) && this.maxAllowedUpMovement < this.positionY)
            {
                this.positionY = this.positionY - this.speed;
                if (this.maxAllowedUpMovement >= this.positionY)
                {
                    this.getNextDirectionOfMovement();
                }
            }
            else if (this.direction.equals(MovingDirection.RIGHT) && this.maxAllowedRightMovement > this.positionX)
            {
                this.positionX = this.positionX + this.speed;
                if (this.maxAllowedRightMovement <= this.positionX)
                {
                    this.getNextDirectionOfMovement();
                }
            }
            else if (this.direction.equals(MovingDirection.LEFT) && this.maxAllowedLeftMovement < this.positionX)
            {
                this.positionX = this.positionX - this.speed;
                if (this.maxAllowedLeftMovement >= this.positionX)
                {
                    this.getNextDirectionOfMovement();
                }
            }
            else
            {
                this._resetMaxMovementDirection();
            }
        }
        else
        {
            if (this.isAllowedToInflictDamage)
            { // only inflict damage to player if colliding
                this.player.playerHealth = this.player.playerHealth - ENEMY_DAMAGE_TO_PLAYER;
                this.hitDamage = this.hitDamage + ENEMY_DAMAGE_TO_PLAYER; // TODO rethink this display variable is not ok damage is increase over time
            }
        }

        boolean isUnderAttack = this.collisionChecker.isEnemyUnderAttack(this.player.attackCollisionArea, this.collisionArea);
        if (isUnderAttack && this.isAllowedToInflictDamage)
        {
            this.enemyHealth = this.enemyHealth - PLAYER_DAMAGE_TO_ENEMY;
        }

        this.changeAssetNumberByFrameCounter(MAX_ASSETS_INDEX, NUMBER_OF_FRAMES_LIMIT);

        if (this.resetEnemyHealth)
        { // for debug
            this.enemyHealth = this.enemyMaxHealth;
        }

        if (this.enemyHealth <= 0)
        {
            this.isAllowedToInflictDamage = false;
            this.isEnemyCollidingWithPlayer = false;
            String enemyWorldId = this.gamePanel.gameSavedStats.getEnemyWorldIdFormat(this);
            this.gamePanel.gameSavedStats.updateEnemyAliveStatus(enemyWorldId, false);
        }
    }

    @Override
    public void draw(Graphics2D g2D)
    {
        int worldEnemyAssetPositionX = this.positionX - this.player.positionX + this.player.playerScreenX;
        int worldEnemyAssetPositionY = this.positionY - this.player.positionY + this.player.playerScreenY;
        this.collisionArea.x = worldEnemyAssetPositionX + (tileSize / 2) / 2;
        this.collisionArea.y = worldEnemyAssetPositionY + (tileSize / 2) / 2;
        this.damageArea.x = worldEnemyAssetPositionX;
        this.damageArea.y = worldEnemyAssetPositionY;

        // draw enemy only if is inside the screen view
        if(checkIfAssetIsInsideTheBoundary(this.positionX, this.positionY, this.player, tileSize))
        {
            this._setEnemyAsset();
            if (this.enemyAsset != null && this.enemyHealth > 0)
            {
                g2D.drawImage(this.enemyAsset, worldEnemyAssetPositionX, worldEnemyAssetPositionY, null);
                this._drawEnemyLifeBar(g2D, worldEnemyAssetPositionX - 2, worldEnemyAssetPositionY - 20);
            }
        }
//        this.gamePanel.drawTestDynamicRectangle(g2D, this.collisionArea.x, this.collisionArea.y, this.collisionArea.width, this.collisionArea.height);
//        this.gamePanel.drawTestDynamicRectangle(g2D, this.damageArea.x, this.damageArea.y, this.damageArea.width, this.damageArea.height);
    }

    public void drawEnemyText(Graphics2D g2D)
    {
        if (this.isEnemyCollidingWithPlayer && this.isAllowedToInflictDamage)
        {
            this.gamePanel.gameTextProvider.setTextColor(Color.RED);
            this.gamePanel.gameTextProvider.setTextPosition(this.player.playerScreenX + 50, this.player.playerScreenY + _randomXYMultiplier());
            this.gamePanel.gameTextProvider.showTextInsideGame(g2D, String.format(this.enemyCollisionText, this.hitDamage));
        }
    }

    private void _setEnemyAsset()
    {
        this._checkAssetsMap(ENEMY_ASSETS_MAP_KEY_NAME);
        if (this.isEnemyCollidingWithPlayer)
        {
            if(this.player.isPlayerSwingSword && this.enemyUnderAttackAssetsMap != null)
            {
                this._checkAssetsMap(ENEMY_UNDER_ATTACK_ASSETS_MAP_KEY_NAME);
            }
            else
            {
                this._checkAssetsMap(ENEMY_COLLISION_ASSETS_MAP_KEY_NAME);
            }
        }
    }

    private void _checkAssetsMap(String assetsMapKey)
    {
        if (!this.allEnemyAsssetsMap.get(assetsMapKey).isEmpty() && this.allEnemyAsssetsMap.get(assetsMapKey).get(this.direction) != null)
        {
            Map<Integer, BufferedImage> enemyAssetsMap = this.allEnemyAsssetsMap.get(assetsMapKey).get(this.direction);
            this.enemyAsset = enemyAssetsMap.get(this.dynamicAssetNumber) != null ? enemyAssetsMap.get(this.dynamicAssetNumber) : enemyAssetsMap.get(1);
        }
    }

    private void _drawEnemyLifeBar(Graphics2D g2d, int positionX, int positionY)
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
}
