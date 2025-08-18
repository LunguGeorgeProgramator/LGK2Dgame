package org.individual;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Map;

public abstract class Individual
{
    public int positionX;
    public int positionY;
    protected int speed;
    protected String movementDirection;
    protected int frameCounter;
    protected int assetNumber;
    protected String stoppedDirection;
    protected String defaultStoppedDirection = "down";
    protected Map<String, BufferedImage> standStillImagesAssetsMap;
    protected Map<Integer, BufferedImage> upMovementImagesAssetsMap;
    protected Map<Integer, BufferedImage> downMovementImagesAssetsMap;
    protected Map<Integer, BufferedImage> leftMovementImagesAssetsMap;
    protected Map<Integer, BufferedImage> rightMovementImagesAssetsMap;

    public Individual(int positionX, int positionY, int speed)
    {
        this.positionX = positionX;
        this.positionY = positionY;
        this.speed = speed;
        getAssetImages();
    }

    // force all children classes to have this methods
    public abstract void getAssetImages();
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
}
