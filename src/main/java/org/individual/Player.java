package org.individual;
import org.game.GamePanel;
import org.game.KeyBoardHandler;

import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Player extends Individual
{
    GamePanel gamePanel;
    KeyBoardHandler keyBoardHandler;

    public Player(GamePanel gamePanel, KeyBoardHandler keyBoardHandler)
    {
        super(100, 100, 4); // set player position x, y and speed
        this.gamePanel = gamePanel;
        this.keyBoardHandler = keyBoardHandler;
        getAssetImages();
    }

    @Override
    public void getAssetImages()
    {
        try
        {
            this.standStill = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/player-stand.png")));
            this.movingUp = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/player-up.png")));
            this.movingUp2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/player-up-2.png")));
            this.movingDown = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/player-down.png")));
            this.movingDown2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/player-down-2.png")));
            this.movingLeft = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/player-left.png")));
            this.movingLeft2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/player-left-2.png")));
            this.movingRight = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/player-right.png")));
            this.movingRight2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/player-right-2.png")));
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }
    }

    @Override
    public void update()
    {
        if (keyBoardHandler.upPressed)
        {
            this.positionY = this.positionY - this.speed;
            this.movementDirection = "up";
        }
        else if (keyBoardHandler.downPressed)
        {
            this.positionY = this.positionY + this.speed;
            this.movementDirection = "down";
        }
        else if (keyBoardHandler.leftPressed)
        {
            this.positionX = this.positionX - this.speed;
            this.movementDirection = "left";
        }
        else if (keyBoardHandler.rightPressed)
        {
            this.positionX = this.positionX + this.speed;
            this.movementDirection = "right";
        }
        else
        {
            this.movementDirection = "standstill";
        }

        this.changeAssetNumberByFrameCounter();
    }

    @Override
    public void draw(Graphics2D g2D)
    {
        BufferedImage playerAsset;

        switch (this.movementDirection)
        {
            case "up":
                playerAsset = this.setPlayerAssetImage(this.movingUp, this.movingUp2);
                break;
            case "down":
                playerAsset = this.setPlayerAssetImage(this.movingDown, this.movingDown2);
                break;
            case "left":
                playerAsset = this.setPlayerAssetImage(this.movingLeft, this.movingLeft2);
                break;
            case "right":
                playerAsset = this.setPlayerAssetImage(this.movingRight, this.movingRight2);
                break;
            case null, default:
                playerAsset = this.standStill;
                break;
        }

        g2D.drawImage(playerAsset, this.positionX, this.positionY, gamePanel.titleSize, gamePanel.titleSize, null);
    }
}
