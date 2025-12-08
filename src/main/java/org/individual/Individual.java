package org.individual;

import org.gamesavedstats.GameSavedStats;
import org.gamesavedstats.models.EnemyStatsModel;
import org.individual.models.MovingDirection;

import java.awt.Rectangle;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Map;

import static org.game.GamePanel.tileSize;

public abstract class Individual
{
    private static String INDIVIDUAL_WORLD_ID_FORMAT = "%d%d%d";
    public int positionX;
    public int positionY;
    public int initialPositionX;
    public int initialPositionY;
    public int speed;
    public MovingDirection movementDirection;
    protected int frameCounter;
    protected int dynamicFrameCounter;
    protected int slowDownFrameCounter;
    protected int assetNumber;
    protected int dynamicAssetNumber;
    protected MovingDirection stoppedDirection;
    protected MovingDirection defaultStoppedDirection = MovingDirection.DOWN;
    protected Map<MovingDirection, BufferedImage> standStillImagesAssetsMap;
    protected Map<Integer, BufferedImage> upMovementImagesAssetsMap;
    protected Map<Integer, BufferedImage> downMovementImagesAssetsMap;
    protected Map<Integer, BufferedImage> leftMovementImagesAssetsMap;
    protected Map<Integer, BufferedImage> rightMovementImagesAssetsMap;
    public Rectangle collisionArea;
    public boolean activateCollision = false;
    public double enemyHealth = 100;
    public int enemyMaxHealth = 100;
    protected MovingDirection trackingMovingDirection;
    protected int maxAllowedDownMovement;
    protected int maxAllowedUpMovement;
    protected int maxAllowedRightMovement;
    protected int maxAllowedLeftMovement;
    protected int maxDistanceAllowedToMove;
    protected Integer nextMovementIndex;
    protected List<MovingDirection> individualMovingDirectionList;
    protected boolean returnToDefaultPosition = false;

    public Individual(int positionX, int positionY, int speed)
    {
        this.initialPositionX = positionX;
        this.initialPositionY = positionY;
        this.positionX = positionX;
        this.positionY = positionY;
        this.speed = speed;
        this._resetMaxMovementDirection();
    }

    // force all children classes to have this methods
    public abstract void update();
    public abstract void draw(Graphics2D g2D);

    // Override in child classes if logic needs changing
    protected void changeAssetNumberByFrameCounter()
    {
        final int numberOfFramesLimit = 25;
        this.frameCounter++;
//        this.frameCounter = this.frameCounter + 1;
        if (this.frameCounter > numberOfFramesLimit)
        {
            this.assetNumber = this.assetNumber == 1 ? 2 : 1;
            this.frameCounter = 0;
        }
    }

    protected void changeAssetNumberByFrameCounter(int maxNumberOfAssets)
    {
        changeAssetNumberByFrameCounter(maxNumberOfAssets, 25);
    }

    protected void changeAssetNumberByFrameCounter(int maxNumberOfAssets, int numberOfFramesLimit)
    {
        this.dynamicFrameCounter++;
        if (this.dynamicFrameCounter > numberOfFramesLimit)
        {
            this.dynamicAssetNumber = this.dynamicAssetNumber < maxNumberOfAssets ? this.dynamicAssetNumber + 1 : 1;
            this.dynamicFrameCounter = 0;
        }
    }

    public boolean slowDownGame()
    {
        final int numberOfFramesLimit = 25;
        this.slowDownFrameCounter++;
        if (this.slowDownFrameCounter > numberOfFramesLimit)
        {
            this.slowDownFrameCounter = 0;
            return true;
        }
        return false;
    }

    protected void _drawEnemyLifeBar(Graphics2D g2d, int positionX, int positionY)
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

    public boolean isIndividualDead(GameSavedStats gameSavedStats, int individualId, String mapName)
    {
        String individualWorldId = gameSavedStats.getIndividualWorldIdFormat(this, individualId);
        EnemyStatsModel enemyStatsModel = gameSavedStats.getEnemyStatsByEnemyWorldId(individualWorldId);
        if (enemyStatsModel == null)
        {
            EnemyStatsModel newEnemyStatsModel = new EnemyStatsModel();
            newEnemyStatsModel.setMapName(mapName);
            newEnemyStatsModel.setEnemyWorldId(individualWorldId);
            newEnemyStatsModel.setAlive(true);
            enemyStatsModel = newEnemyStatsModel;
            gameSavedStats.addToEnemyStats(enemyStatsModel);
        }
        return !enemyStatsModel.getIsAlive();
    }

    protected int[] _findPlayerPosition(Player player)
    {
        int playerPositionX = player.positionX;
        int playerPositionY = player.positionY;
        switch (this.movementDirection)
        {
            case MovingDirection.LEFT:
                playerPositionX = player.positionX + tileSize;
                break;
            case MovingDirection.RIGHT:
                playerPositionX = player.positionX - tileSize;
                break;
            case MovingDirection.UP:
                playerPositionY = player.positionY - tileSize;
                break;
            case MovingDirection.DOWN:
                playerPositionY = player.positionY + tileSize;
                break;
        }
        return new int[]{ playerPositionX, playerPositionY };
    }

    protected void _trackMovementTowardPlayer(Player player,  boolean trackPlayer)
    {
        if (!this.activateCollision && trackPlayer)
        {
            int [] playerPositionXY = this._findPlayerPosition(player);
            int playerPositionX = playerPositionXY[0];
            int playerPositionY = playerPositionXY[1];
            this.positionY = this._moveTowardScreenPosition(positionY, playerPositionY, this.speed);
            this.positionX = this._moveTowardScreenPosition(positionX, playerPositionX, this.speed);
            this.returnToDefaultPosition = true;
        }
        if (this.returnToDefaultPosition && !trackPlayer)
        {
            if (this.initialPositionX == this.positionX && this.initialPositionY == this.positionY)
            {
                this.returnToDefaultPosition = false;
            }
            this.positionY = this._moveTowardScreenPosition(positionY, this.initialPositionY, this.speed);
            this.positionX = this._moveTowardScreenPosition(positionX, this.initialPositionX, this.speed);
        }
        if (this.activateCollision)
        {
            this.positionY = this._moveTowardScreenPosition(positionY, this.initialPositionY, this.speed);
            this.positionX = this._moveTowardScreenPosition(positionX, this.initialPositionX, this.speed);
        }
    }

    protected int _moveTowardScreenPosition(int positionA, int positionB, int speed)
    {
        if (positionA < positionB)
        {
            positionA += speed;
        }
        else if (positionA > positionB)
        {
            positionA -= speed;
        }
        return positionA;
    }

    protected void autoMoveIndividual()
    {
        if (this.movementDirection != null)
        {
            if (this.movementDirection.equals(MovingDirection.DOWN) && this.maxAllowedDownMovement > this.positionY)
            {
                this.positionY = this.positionY + this.speed;
                if (this.maxAllowedDownMovement <= this.positionY)
                {
                    this.getNextDirectionOfMovement();
                }
            }
            else if (this.movementDirection.equals(MovingDirection.UP) && this.maxAllowedUpMovement < this.positionY)
            {
                this.positionY = this.positionY - this.speed;
                if (this.maxAllowedUpMovement >= this.positionY)
                {
                    this.getNextDirectionOfMovement();
                }
            }
            else if (this.movementDirection.equals(MovingDirection.RIGHT) && this.maxAllowedRightMovement > this.positionX)
            {
                this.positionX = this.positionX + this.speed;
                if (this.maxAllowedRightMovement <= this.positionX)
                {
                    this.getNextDirectionOfMovement();
                }
            }
            else if (this.movementDirection.equals(MovingDirection.LEFT) && this.maxAllowedLeftMovement < this.positionX)
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
    }

    private void getNextDirectionOfMovement()
    {
        this.nextMovementIndex = this.nextMovementIndex + 1 == this.individualMovingDirectionList.size() ? 0 : this.nextMovementIndex + 1;
        this.movementDirection = !this.individualMovingDirectionList.isEmpty() ? this.individualMovingDirectionList.get(this.nextMovementIndex) : MovingDirection.STATIONARY;
    }

    protected void _resetMaxMovementDirection()
    {
        this.maxAllowedDownMovement = this.positionY + this.maxDistanceAllowedToMove;
        this.maxAllowedUpMovement = this.positionY - this.maxDistanceAllowedToMove;
        this.maxAllowedRightMovement = this.positionX + this.maxDistanceAllowedToMove;
        this.maxAllowedLeftMovement = this.positionX - this.maxDistanceAllowedToMove;
    }
}