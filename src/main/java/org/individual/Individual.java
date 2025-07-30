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
    public BufferedImage movingDown;
    public BufferedImage movingLeft;
    public BufferedImage movingRight;
    public String movementDirection;

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
}
