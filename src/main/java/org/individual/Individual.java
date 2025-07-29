package org.individual;

import java.awt.Graphics2D;

public abstract class Individual
{
    public int positionX;
    public int positionY;
    public int speed;

    public Individual(int positionX, int positionY, int speed)
    {
        this.positionX = positionX;
        this.positionY = positionY;
        this.speed = speed;
    }

    // force all children classes to have this methods
    public abstract void update();
    public abstract void draw(Graphics2D g2D);
}
