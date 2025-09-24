package org.individual;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Map;

public abstract class Individual
{
    public int positionX;
    public int positionY;
    public int initialPositionX;
    public int initialPositionY;
    public int speed;
    public String movementDirection;
    protected int frameCounter;
    protected int slowDownFrameCounter;
    protected int assetNumber;
    protected String stoppedDirection;
    protected String defaultStoppedDirection = "down";
    protected Map<String, BufferedImage> standStillImagesAssetsMap;
    protected Map<Integer, BufferedImage> upMovementImagesAssetsMap;
    protected Map<Integer, BufferedImage> downMovementImagesAssetsMap;
    protected Map<Integer, BufferedImage> leftMovementImagesAssetsMap;
    protected Map<Integer, BufferedImage> rightMovementImagesAssetsMap;
    public Rectangle collisionArea;
    public boolean activateCollision = false;

    public Individual(int positionX, int positionY, int speed, String assetPath)
    {
        this.initialPositionX = positionX;
        this.initialPositionY = positionY;
        this.positionX = positionX;
        this.positionY = positionY;
        this.speed = speed;
        getAssetImages(assetPath);
    }

    // force all children classes to have this methods
    public abstract void getAssetImages(String assetPath);
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
