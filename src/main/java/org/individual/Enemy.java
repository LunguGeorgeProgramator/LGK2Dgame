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

import static org.game.GamePanel.tileSize;
import static org.helpers.ToolsHelper.checkIfAssetIsInsideTheBoundary;
import static org.helpers.ToolsHelper._randomXYMultiplier;

public class Enemy extends Individual
{

    private static final String ENEMY_COLLISION_TEXT_KEY = "enemy-collision";
//    private static final Double ENEMY_DAMAGE_TO_PLAYER = 10.1;
    private static final Double ENEMY_DAMAGE_TO_PLAYER = 1.1;
    private static final int MAX_ASSETS_INDEX = 2;
    private static final int NUMBER_OF_FRAMES_LIMIT = 10;
    private static final String ENEMY_ASSETS_MAP_KEY_NAME = "enemyAssetsMap";
    private static final String ENEMY_COLLISION_ASSETS_MAP_KEY_NAME = "enemyCollisionAssetsMap";
    private static final String ENEMY_UNDER_ATTACK_ASSETS_MAP_KEY_NAME = "enemyUnderAttackAssetsMap";
    private final String enemyCollisionText;
    private double hitDamage;
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
    public int enemyId;
    public String enemyName;
    public int enemyWorldMatrixCol;
    public int enemyWorldMatrixRow;
    public boolean resetEnemyHealth = false;
    public Rectangle attackArea;
    public Rectangle detectionArea;
    public Rectangle damageTakenArea;
    private int worldEnemyAssetPositionX;
    private int worldEnemyAssetPositionY;
    public String enemyWorldPrefix;


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
        this.player = player;
        this.enemyAssetsMap = enemyAssetsMap;
        this.gamePanel = gamePanel;
        this.collisionChecker = this.gamePanel.collisionChecker;
        this.enemyCollisionText = this.gamePanel.gameTextProvider.getGameTextByKey(ENEMY_COLLISION_TEXT_KEY);
        this.hitDamage = 0;
        this.enemyUnderAttackAssetsMap = enemyUnderAttackAssetsMap;
        this.enemyColisionAssetsMap = enemyColisionAssetsMap;
        this.maxDistanceAllowedToMove = maxDistanceAllowedToMove;
        this.individualMovingDirectionList = enemyMovingDirectionList;
        this.movementDirection = !this.individualMovingDirectionList.isEmpty() ? this.individualMovingDirectionList.getFirst() : MovingDirection.UP;
        this.nextMovementIndex = !this.individualMovingDirectionList.isEmpty() ? 0 : null;
        this.setAssetImages();
        this.buildEnemyCollisionArea();
        this.buildEnemyAttackArea();
        this.buildEnemyDetectionArea();
        this.buildEnemyDamageTakenArea();
        this.enemyMaxHealth = 50;
        this.enemyHealth = 50;
    }

    private void buildEnemyCollisionArea()
    {
        this.collisionArea = new Rectangle();
        this.collisionArea.x = 0;
        this.collisionArea.y = 0;
        this.collisionArea.height = tileSize;
        this.collisionArea.width = tileSize;
    }

    private void buildEnemyDamageTakenArea()
    {
        this.damageTakenArea = new Rectangle();
        this.damageTakenArea.x = 0;
        this.damageTakenArea.y = 0;
        this.damageTakenArea.height = tileSize;
        this.damageTakenArea.width = tileSize;
    }

    private void buildEnemyAttackArea()
    {
        this.attackArea = new Rectangle();
        this.attackArea.x = 0;
        this.attackArea.y = 0;
        this.attackArea.height = tileSize * 2;
        this.attackArea.width = tileSize * 2;
    }

    private void buildEnemyDetectionArea()
    {
        this.detectionArea = new Rectangle();
        this.detectionArea.x = 0;
        this.detectionArea.y = 0;
        this.detectionArea.height = tileSize * 5;
        this.detectionArea.width = tileSize * 5;
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

    @Override
    public void update()
    {
        if(checkIfAssetIsInsideTheBoundary(this.positionX, this.positionY, this.player, tileSize * 4))
        {
            this.gamePanel.collisionChecker.checkTile(this, false, this.gamePanel.worldType);
            this.gamePanel.collisionChecker.checkTile(this, true, this.gamePanel.worldType);
            if (!this.activateCollision)
            {
                this._enemyMovingActions();
                this._enemyDamageAndHealthControl();
            }
            boolean trackPlayer = this.collisionChecker.areRectanglesIntersecting(this.player.worldItemCollisionArea, this.detectionArea);
            this._trackMovementTowardPlayer(this.player, trackPlayer);
            this._updateEnemyCollisionAreasAndScreenPositions();
        }
    }

    private void _enemyMovingActions()
    {
        this.isAllowedToInflictDamage = this.slowDownGame();
        this.isEnemyCollidingWithPlayer = this.collisionChecker.areRectanglesIntersecting(this.player.worldItemCollisionArea, this.attackArea);
        if (!this.isEnemyCollidingWithPlayer)
        {
            if (!this.returnToDefaultPosition)
            {
                this.autoMoveIndividual();
            }
        }
    }
    private void _enemyDamageAndHealthControl()
    {
        if (this.isEnemyCollidingWithPlayer)
        {
            if (this.isAllowedToInflictDamage)
            { // only inflict damage to player if colliding
                this.player.playerHealth = this.player.playerHealth - ENEMY_DAMAGE_TO_PLAYER;
                this.hitDamage = this.hitDamage + ENEMY_DAMAGE_TO_PLAYER; // TODO rethink this display variable is not ok damage is increase over time
            }
        }

        boolean isUnderAttack = this.collisionChecker.areRectanglesIntersecting(this.player.attackCollisionArea, this.damageTakenArea);
        if (isUnderAttack && this.isAllowedToInflictDamage)
        {
            this.enemyHealth = this.enemyHealth - this.player.playerDamageToEnemy;;
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

    private void _updateEnemyCollisionAreasAndScreenPositions()
    {
        this.worldEnemyAssetPositionX = this.positionX - this.player.positionX + this.player.playerScreenX;
        this.worldEnemyAssetPositionY = this.positionY - this.player.positionY + this.player.playerScreenY;
        this.damageTakenArea.x = this.worldEnemyAssetPositionX;
        this.damageTakenArea.y = this.worldEnemyAssetPositionY;
        this.attackArea.x = worldEnemyAssetPositionX - (tileSize / 2);
        this.attackArea.y = worldEnemyAssetPositionY - (tileSize / 2);
        this.detectionArea.x = worldEnemyAssetPositionX - (tileSize * 2);
        this.detectionArea.y = worldEnemyAssetPositionY - (tileSize * 2);
    }

    @Override
    public void draw(Graphics2D g2D)
    {
        // draw enemy only if is inside the screen view
        if(checkIfAssetIsInsideTheBoundary(this.positionX, this.positionY, this.player, tileSize))
        {
            this._setEnemyAsset();
            if (this.enemyAsset != null && this.enemyHealth > 0)
            {
                g2D.drawImage(this.enemyAsset, this.worldEnemyAssetPositionX, this.worldEnemyAssetPositionY, null);
                this._drawEnemyLifeBar(g2D, this.worldEnemyAssetPositionX - 2, this.worldEnemyAssetPositionY - 20);
            }
//        this.gamePanel.drawTestDynamicRectangle(g2D, this.collisionArea.x, this.collisionArea.y, this.collisionArea.width, this.collisionArea.height);
//        this.gamePanel.drawTestDynamicRectangle(g2D, this.attackArea.x, this.attackArea.y, this.attackArea.width, this.attackArea.height);
//        this.gamePanel.drawTestDynamicRectangle(g2D, this.detectionArea.x, this.detectionArea.y, this.detectionArea.width, this.detectionArea.height);
        }
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
        if (!this.allEnemyAsssetsMap.get(assetsMapKey).isEmpty() && this.allEnemyAsssetsMap.get(assetsMapKey).get(this.movementDirection) != null)
        {
            Map<Integer, BufferedImage> enemyAssetsMap = this.allEnemyAsssetsMap.get(assetsMapKey).get(this.movementDirection);
            this.enemyAsset = enemyAssetsMap.get(this.dynamicAssetNumber) != null ? enemyAssetsMap.get(this.dynamicAssetNumber) : enemyAssetsMap.get(1);
        }
    }
}
