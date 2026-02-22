package org.individual;

import org.game.CollisionChecker;
import org.game.GamePanel;
import org.imageAssets.ImageLoader;
import org.imageAssets.models.EnemyBossesImagesAssets;
import org.individual.models.MovingDirection;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Map;

import static org.game.GamePanel.tileSize;
import static org.helpers.ToolsHelper.checkIfAssetIsInsideTheBoundary;

public class GrimBoosEnemy  extends Individual
{
    private final Player player;
    private final GamePanel gamePanel;
    public Rectangle attackArea;
    public Rectangle detectionArea;
    private Map<MovingDirection, Map<Integer, BufferedImage>> attackAssetsMap;
    private Map<MovingDirection, BufferedImage> attackPossitionAssetsMap;
    private Map<MovingDirection, BufferedImage> stanbyPossitionAssetsMap;
    private final ImageLoader imageLoader;
    private int boosEnemyAssetPositionX;
    private int boosEnemyAssetPositionY;
    private final int boosWight;
    private final int boosHeight;
    private final int boosTileSize;
    private final CollisionChecker collisionChecker;
    private boolean foundPlayer = false;
    public boolean isBoosEnemyDead = false;
    private boolean isAllowedToInflictDamage = false;
    private final double GRIM_DAMAGE_VALUE = 10.1;
    private String grimBossMapName = "CaveDungeonWorldMap";
    private int grimBossWorldId = 2;


    public GrimBoosEnemy(GamePanel gamePanel)
    {
        super(tileSize * 26, tileSize * 9, 4);
        this.player = gamePanel.player;
        this.gamePanel = gamePanel;
        this.imageLoader = gamePanel.imageLoader;
        this.collisionChecker = gamePanel.collisionChecker;
        this.boosTileSize = boosTileSizeImage;
        this.boosWight = this.boosTileSize;
        this.boosHeight = this.boosTileSize;
        this._buildBossEnemyCollisionArea();
        this._buildBossEnemyAttackArea();
        this._buildBossAssets();
        this._buildEnemyDetectionArea();
        this.maxDistanceAllowedToMove = 200;
        this.individualMovingDirectionList = List.of(MovingDirection.DOWN, MovingDirection.RIGHT, MovingDirection.UP, MovingDirection.LEFT);
        this.movementDirection = this.individualMovingDirectionList.getFirst();
        this.nextMovementIndex = !this.individualMovingDirectionList.isEmpty() ? 0 : null;
        this.enemyHealth = 190;
        this.enemyMaxHealth = 190;
    }

    private void _buildEnemyDetectionArea()
    {
        this.detectionArea = new Rectangle();
        this.detectionArea.x = 0;
        this.detectionArea.y = 0;
        this.detectionArea.height = this.boosHeight * 2;
        this.detectionArea.width = this.boosWight * 2;
    }

    private void _buildBossEnemyCollisionArea()
    { // make the collision area small that the player rectangle so upper corners will not hit solid world assets
        this.collisionArea = new Rectangle();
        this.collisionArea.x = 0;
        this.collisionArea.y = 0;
        this.collisionArea.height = this.boosHeight;
        this.collisionArea.width = this.boosWight / 2;
    }

    private void _buildBossEnemyAttackArea()
    { // make the collision area small that the player rectangle so upper corners will not hit solid world assets
        this.attackArea = new Rectangle();
        this.attackArea.x = 0;
        this.attackArea.y = 0;
        this.attackArea.height = this.boosHeight;
        this.attackArea.width = this.boosWight;
    }

    private void _buildBossAssets()
    {
        BufferedImage assetImage = this.imageLoader.getEnemyBossesImageAssets(EnemyBossesImagesAssets.GRIM_BOOS_ENEMY);
        BufferedImage leftAssetImage = this.imageLoader.getEnemyBossesImageAssets(EnemyBossesImagesAssets.GRIM_BOOS_ENEMY_LEFT);
        this.attackAssetsMap = Map.of(
            MovingDirection.UP, Map.of(
            1, this.imageLoader.getEnemyBossesImageAssets(EnemyBossesImagesAssets.GRIM_WEAPON_ATTACK_UP_UP),
            2, this.imageLoader.getEnemyBossesImageAssets(EnemyBossesImagesAssets.GRIM_WEAPON_ATTACK_CENTER_UP)
            ),
            MovingDirection.RIGHT, Map.of(
            1, this.imageLoader.getEnemyBossesImageAssets(EnemyBossesImagesAssets.GRIM_WEAPON_ATTACK_UP_RIGHT),
            2, this.imageLoader.getEnemyBossesImageAssets(EnemyBossesImagesAssets.GRIM_WEAPON_ATTACK_CENTER_RIGHT)
            ),
            MovingDirection.LEFT, Map.of(
            1, this.imageLoader.getEnemyBossesImageAssets(EnemyBossesImagesAssets.GRIM_WEAPON_ATTACK_UP_LEFT),
            2, this.imageLoader.getEnemyBossesImageAssets(EnemyBossesImagesAssets.GRIM_WEAPON_ATTACK_CENTER_LEFT)
            ),
            MovingDirection.DOWN, Map.of(
            1, this.imageLoader.getEnemyBossesImageAssets(EnemyBossesImagesAssets.GRIM_WEAPON_ATTACK_UP_DOWN),
            2, this.imageLoader.getEnemyBossesImageAssets(EnemyBossesImagesAssets.GRIM_WEAPON_ATTACK_CENTER_DOWN)
            ),
            MovingDirection.STATIONARY, Map.of(1, assetImage, 2, assetImage)
        );
        this.attackPossitionAssetsMap = Map.of(
            MovingDirection.UP, this.imageLoader.getEnemyBossesImageAssets(EnemyBossesImagesAssets.GRIM_BOOS_ENEMY_ATTACK_UP),
            MovingDirection.DOWN, this.imageLoader.getEnemyBossesImageAssets(EnemyBossesImagesAssets.GRIM_BOOS_ENEMY_ATTACK_DOWN),
            MovingDirection.LEFT, this.imageLoader.getEnemyBossesImageAssets(EnemyBossesImagesAssets.GRIM_BOOS_ENEMY_ATTACK_LEFT),
            MovingDirection.RIGHT, this.imageLoader.getEnemyBossesImageAssets(EnemyBossesImagesAssets.GRIM_BOOS_ENEMY_ATTACK_RIGHT),
            MovingDirection.STATIONARY, assetImage
        );
        this.stanbyPossitionAssetsMap = Map.of(
            MovingDirection.UP, assetImage,
            MovingDirection.DOWN, leftAssetImage,
            MovingDirection.LEFT, leftAssetImage,
            MovingDirection.RIGHT, assetImage,
            MovingDirection.STATIONARY, assetImage
        );
    }

    protected int[] _findPlayerPosition(Player player)
    {
        int playerPositionX = player.positionX;
        int playerPositionY = player.positionY;
        switch (this.movementDirection)
        {
            case MovingDirection.LEFT:
                playerPositionY = player.positionY - this.boosTileSize / 2;
                playerPositionX = player.positionX + this.boosTileSize;
                break;
            case MovingDirection.RIGHT:
                playerPositionY = player.positionY - this.boosTileSize / 2;
                playerPositionX = player.positionX - (this.boosTileSize + boosTileSize / 2);
                break;
            case MovingDirection.UP:
                playerPositionY = player.positionY + this.boosTileSize;
                playerPositionX = player.positionX - this.boosTileSize / 2;
                break;
            case MovingDirection.DOWN:
                playerPositionY = player.positionY - (this.boosTileSize + boosTileSize / 2);
                playerPositionX = player.positionX - this.boosTileSize / 2;
                break;
        }
        return new int[]{ playerPositionX, playerPositionY };
    }

    @Override
    public void update()
    {
        this.isBoosEnemyDead = this.isIndividualDead(this.gamePanel.gameSavedStats, this.grimBossWorldId, this.grimBossMapName);
        if (this.isBoosEnemyDead)
        {
            return;
        }
        if (!this.returnToDefaultPosition)
        {
            this.autoMoveIndividual();
        }
        this.isAllowedToInflictDamage = this.slowDownGame();
        this.changeAssetNumberByFrameCounter();
        this.foundPlayer = this.collisionChecker.areRectanglesIntersecting(this.player.worldItemCollisionArea, this.detectionArea);
        this._trackMovementTowardPlayer(this.player, this.foundPlayer);
        boolean isBoosTakingDamage = this.collisionChecker.areRectanglesIntersecting(this.player.attackCollisionArea, this.collisionArea);
        if (isBoosTakingDamage && this.isAllowedToInflictDamage)
        {
            this.enemyHealth = this.enemyHealth - this.player.playerDamageToEnemy;
        }
        boolean isPlayerTakingDamage = this.collisionChecker.areRectanglesIntersecting(this.player.worldItemCollisionArea, this.attackArea);
        if (isPlayerTakingDamage && this.isAllowedToInflictDamage)
        {
            this.player.playerHealth = this.player.playerHealth - GRIM_DAMAGE_VALUE;
        }
        this.isBoosEnemyDead = this.enemyHealth <= 0;
        if (this.isBoosEnemyDead)
        {
            String grimWorldId = this.gamePanel.gameSavedStats.getIndividualWorldIdFormat(this, this.grimBossWorldId);
            this.gamePanel.gameSavedStats.updateEnemyAliveStatus(grimWorldId, false);
        }
    }

    private void _updateBossEnemyCollisionAreasAndScreenPositions()
    {
        this.boosEnemyAssetPositionX = this.positionX - this.player.positionX + this.player.playerScreenX;
        this.boosEnemyAssetPositionY = this.positionY - this.player.positionY + this.player.playerScreenY;
        this.collisionArea.x = boosEnemyAssetPositionX + (this.boosTileSize / 2) / 2;
        this.collisionArea.y = boosEnemyAssetPositionY;

        this.detectionArea.x = boosEnemyAssetPositionX - (tileSize * 2);
        this.detectionArea.y = boosEnemyAssetPositionY - (tileSize * 2);

        this.attackArea.x = boosEnemyAssetPositionX;
        this.attackArea.y = boosEnemyAssetPositionY;

        switch (this.movementDirection)
        {
            case MovingDirection.LEFT:
                this.attackArea.x  = this.attackArea.x - this.boosTileSize;
                break;
            case MovingDirection.RIGHT:
                this.attackArea.x  = this.attackArea.x + this.boosTileSize;
                break;
            case MovingDirection.UP:
                this.attackArea.y = this.attackArea.y - this.boosTileSize;
                break;
            case MovingDirection.DOWN:
                this.attackArea.y = this.attackArea.y + this.boosTileSize;
                break;
        }
    }

    @Override
    public void draw(Graphics2D g2D)
    {
        if (this.isBoosEnemyDead)
        {
            return;
        }
        if(checkIfAssetIsInsideTheBoundary(this.positionX, this.positionY, this.player, this.boosWight))
        {
            this._updateBossEnemyCollisionAreasAndScreenPositions();
            if (this.attackPossitionAssetsMap != null)
            {
                BufferedImage bossAssetImage =  !this.foundPlayer ? this.stanbyPossitionAssetsMap.get(this.movementDirection) : this.attackPossitionAssetsMap.get(this.movementDirection);
                g2D.drawImage(bossAssetImage, this.boosEnemyAssetPositionX, this.boosEnemyAssetPositionY, null);
                if (this.movementDirection != MovingDirection.STATIONARY && this.foundPlayer)
                {
                    g2D.drawImage(this.attackAssetsMap.get(this.movementDirection).get(this.assetNumber), this.attackArea.x, this.attackArea.y, null);
                }
                this._drawEnemyLifeBar(g2D, this.boosEnemyAssetPositionX - 2, this.boosEnemyAssetPositionY - 20);
//        this.gamePanel.drawTestDynamicRectangle(g2D, this.collisionArea.x, this.collisionArea.y, this.collisionArea.width, this.collisionArea.height);
//        this.gamePanel.drawTestDynamicRectangle(g2D, this.attackArea.x, this.attackArea.y, this.attackArea.width, this.attackArea.height);
//        this.gamePanel.drawTestDynamicRectangle(g2D, this.detectionArea.x, this.detectionArea.y, this.detectionArea.width, this.detectionArea.height);
            }
        }
    }

    @Override
    public void drawLastInsideGamePanel(Graphics2D g2D) {

    }
}
