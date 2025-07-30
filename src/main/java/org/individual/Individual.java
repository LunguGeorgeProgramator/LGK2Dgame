package org.individual;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public abstract class Individual
{
    public int positionX;
    public int positionY;
    public int speed;
    public BufferedImage standStill;
    public BufferedImage movingUp;
    public BufferedImage movingUp2;
    public BufferedImage movingDown;
    public BufferedImage movingDown2;
    public BufferedImage movingLeft;
    public BufferedImage movingLeft2;
    public BufferedImage movingRight;
    public BufferedImage movingRight2;
    public String movementDirection;
    public int frameCounter;
    public int assetNumber;

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

    protected BufferedImage setPlayerAssetImage(BufferedImage startImage, BufferedImage endImage)
    {
        return this.assetNumber == 1 ? startImage : endImage;
    }
}
