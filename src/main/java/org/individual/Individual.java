package org.individual;

import org.individual.models.MovingDirection;

import java.awt.Rectangle;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Map;

public abstract class Individual
{
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

    public Individual(int positionX, int positionY, int speed)
    {
        this.initialPositionX = positionX;
        this.initialPositionY = positionY;
        this.positionX = positionX;
        this.positionY = positionY;
        this.speed = speed;
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
}
