package org.individual;

import org.game.CollisionChecker;
import org.game.GamePanel;
import org.gamesavedstats.GameSavedStats;
import org.individual.models.MovingDirection;

import javax.swing.*;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.Objects;
import java.util.Map;

import static org.helpers.ToolsHelper.getScaledImageFromAssets;
import static org.game.GamePanel.tileSize;
import static org.helpers.ToolsHelper.checkIfAssetIsInsideTheBoundary;

public class SpiderBossEnemy extends Individual
{

    BufferedImage boosAssetImage;
    BufferedImage boosAttackAssetImage;
    private final GamePanel gamePanel;
    private final Player player;
    private final CollisionChecker collisionChecker;
    private final int maxMovement;
    private final int maxMovementLeft;
    private final int maxMovementRight;
    private final int boosTileSize;
    private static final Double BOSS_ENEMY_DAMAGE_TO_PLAYER = 10.1;
    private boolean isAllowedToInflictDamage = false;
    public boolean isBoosEnemyDead = false;
    private Rectangle attackArea;
    private Map<Integer, BufferedImage> boosEnemyCollisionAssets;
    private Map<Integer, BufferedImage> boosEnemyMovingAssets;
    private int boosEnemyAssetPositionX;
    private int boosEnemyAssetPositionY;
    private boolean hideWinMessage = true;
    private String spiderBossMapName = "WorldMap";
    private int spiderBossWorldId = 1;

    public SpiderBossEnemy(GamePanel gamePanel)
    {
        super(tileSize * 90, tileSize * 13, 4);
        this.maxMovement = tileSize * 9;
        this.maxMovementLeft = this.positionX;
        this.maxMovementRight = this.positionX + this.maxMovement;
        this.gamePanel = gamePanel;
        this.player = this.gamePanel.player;
        this.collisionChecker = this.gamePanel.collisionChecker;
        this.movementDirection = MovingDirection.RIGHT;
        this.boosTileSize = 4;
        this._buildBossEnemyAssets();
        this._buildBossEnemyCollisionArea();
        this._buildBossEnemyAttackArea();
        this.enemyHealth = 190;
        this.enemyMaxHealth = 190;
    }

    private void _buildBossEnemyAssets()
    {
        this.boosAssetImage = Objects.requireNonNull(getScaledImageFromAssets("/boosEnemy/spider/boos-enemy.png", tileSize * this.boosTileSize, tileSize * this.boosTileSize));
        this.boosAttackAssetImage = Objects.requireNonNull(getScaledImageFromAssets("/boosEnemy/spider/boos-enemy-attack.png", tileSize, tileSize * 2));

        this.boosEnemyCollisionAssets = Map.of(
            1, Objects.requireNonNull(getScaledImageFromAssets("/boosEnemy/spider/boos-enemy.png", tileSize * this.boosTileSize, tileSize * this.boosTileSize)),
            2, Objects.requireNonNull(getScaledImageFromAssets("/boosEnemy/spider/boos-enemy-bites.png", tileSize * this.boosTileSize, tileSize * this.boosTileSize))
        );

        this.boosEnemyMovingAssets = Map.of(
            1, Objects.requireNonNull(getScaledImageFromAssets("/boosEnemy/spider/boos-enemy-moving-two.png", tileSize * this.boosTileSize, tileSize * this.boosTileSize)),
            2, Objects.requireNonNull(getScaledImageFromAssets("/boosEnemy/spider/boos-enemy-moving.png", tileSize * this.boosTileSize, tileSize * this.boosTileSize))
        );
    }

    private void _buildBossEnemyCollisionArea()
    { // make the collision area small that the player rectangle so upper corners will not hit solid world assets
        this.collisionArea = new Rectangle();
        this.collisionArea.x = 0;
        this.collisionArea.y = 0;
        this.collisionArea.height = tileSize * this.boosTileSize;
        this.collisionArea.width = tileSize * this.boosTileSize;
    }

    private void _buildBossEnemyAttackArea()
    { // make the collision area small that the player rectangle so upper corners will not hit solid world assets
        this.attackArea = new Rectangle();
        this.attackArea.x = 0;
        this.attackArea.y = 0;
        this.attackArea.height = tileSize * 2;
        this.attackArea.width = tileSize;
    }

    @Override
    public void update()
    {
        this.isBoosEnemyDead = this.isIndividualDead(this.gamePanel.gameSavedStats, this.spiderBossWorldId, spiderBossMapName);
        this.changeAssetNumberByFrameCounter();
        this.changeAssetNumberByFrameCounter(4, 60);
        if(checkIfAssetIsInsideTheBoundary(this.positionX, this.positionY, this.player, tileSize * this.boosTileSize * this.boosTileSize) && !this.isBoosEnemyDead)
        {
            this.isBoosEnemyDead = this.enemyHealth <= 0;
            if (this.isBoosEnemyDead)
            {
                String spiderWorldId = this.gamePanel.gameSavedStats.getIndividualWorldIdFormat(this, this.spiderBossWorldId);
                this.gamePanel.gameSavedStats.updateEnemyAliveStatus(spiderWorldId, false);
            }
            this._boosEnemyMovementAction();
            this._bossEnemyCollidingWithPlayer();
            this._updateBossEnemyCollisionAreasAndScreenPositions();
        }
    }

    private void _boosEnemyMovementAction()
    {
        if (!this.activateCollision)
        {
            if (this.movementDirection.equals(MovingDirection.RIGHT) && this.maxMovementRight > this.positionX)
            {
                this.positionX = this.positionX + this.speed;
                if (this.maxMovementRight <= this.positionX)
                {
                    this.movementDirection = MovingDirection.LEFT;
                }
            }
            else if (this.movementDirection.equals(MovingDirection.LEFT) && this.maxMovementLeft < this.positionX)
            {
                this.positionX = this.positionX - this.speed;
                if (this.maxMovementLeft >= this.positionX)
                {
                    this.movementDirection = MovingDirection.RIGHT;
                }
            }
        }
    }

    private void _bossEnemyCollidingWithPlayer()
    {
        this.isAllowedToInflictDamage = this.slowDownGame();
        boolean trackPlayer = this.collisionChecker.areRectanglesIntersecting(this.player.worldItemCollisionArea, this.collisionArea);
        if (trackPlayer)
        {
            MovingDirection interactionDirection = this.collisionChecker.rectanglesIntersectingOnDirection(this.player.worldItemCollisionArea, this.collisionArea);
            this.player.stopPlayerMovement = interactionDirection != null && !interactionDirection.equals(this.player.movementDirection);
            this.activateCollision = true;
            if (this.isAllowedToInflictDamage)
            {
                this.player.playerHealth = this.player.playerHealth - BOSS_ENEMY_DAMAGE_TO_PLAYER;
            }
        }
        else
        {
            this.activateCollision = false;
            this.player.stopPlayerMovement = false;
        }

        boolean isUnderAttack = this.collisionChecker.areRectanglesIntersecting(this.player.attackCollisionArea, this.collisionArea);
        if (isUnderAttack && this.isAllowedToInflictDamage)
        {
            this.enemyHealth = this.enemyHealth - this.player.playerDamageToEnemy;
            this.activateCollision = true;
        }
        boolean isAttackingPlayer = this.collisionChecker.areRectanglesIntersecting(this.player.worldItemCollisionArea, this.attackArea);
        if (isAttackingPlayer && this.isAllowedToInflictDamage)
        {
            this.player.playerHealth = this.player.playerHealth - BOSS_ENEMY_DAMAGE_TO_PLAYER;
        }
        if (this.isBoosEnemyDead)
        {
            this.activateCollision = false;
            this.player.stopPlayerMovement = false;
        }
    }

    private void _boosDead(Graphics2D g2D)
    {
        Timer timer = new Timer(5000, _ -> this.hideWinMessage = false);
        timer.setRepeats(false);
        timer.start();
        if (this.hideWinMessage)
        {
            this.gamePanel.gameTextProvider.setTextColor(Color.WHITE);
            this.gamePanel.gameTextProvider.setTextPosition(this.player.playerScreenX - 65, this.player.playerScreenY - 20);
            String victoryMessage = this.gamePanel.gameTextProvider.getGameTextByKey("boos-enemy-dead");
            this.gamePanel.gameTextProvider.showTextInsideGame(g2D, victoryMessage);
        }
    }

    private void _updateBossEnemyCollisionAreasAndScreenPositions()
    {
        this.boosEnemyAssetPositionX = this.positionX - this.player.positionX + this.player.playerScreenX;
        this.boosEnemyAssetPositionY = this.positionY - this.player.positionY + this.player.playerScreenY;
        this.collisionArea.x = boosEnemyAssetPositionX;
        this.collisionArea.y = boosEnemyAssetPositionY;
        this.attackArea.x = (boosEnemyAssetPositionX - tileSize) + (tileSize * this.dynamicAssetNumber);
        this.attackArea.y = boosEnemyAssetPositionY + (tileSize * boosTileSize);
    }

    @Override
    public void draw(Graphics2D g2D)
    {
        if(checkIfAssetIsInsideTheBoundary(this.positionX, this.positionY, this.player, tileSize * this.boosTileSize))
        {
            if (this.boosAssetImage != null && !this.isBoosEnemyDead)
            {
                BufferedImage boosEnemyAssetToDraw = this.activateCollision ? this.boosEnemyCollisionAssets.get(this.assetNumber) : this.boosEnemyMovingAssets.get(this.assetNumber);
                g2D.drawImage(boosEnemyAssetToDraw, this.boosEnemyAssetPositionX, this.boosEnemyAssetPositionY, null);
                g2D.drawImage(this.boosAttackAssetImage, this.attackArea.x, this.attackArea.y, null);
                this._drawEnemyLifeBar(g2D, this.boosEnemyAssetPositionX - 2, this.boosEnemyAssetPositionY - 20);
            }
            else
            {
                this._boosDead(g2D);
            }
//            this.gamePanel.drawTestDynamicRectangle(g2D, this.collisionArea.x, this.collisionArea.y, this.collisionArea.width, this.collisionArea.height);
        }
    }

    @Override
    public void drawLastInsideGamePanel(Graphics2D g2D) {

    }

}
