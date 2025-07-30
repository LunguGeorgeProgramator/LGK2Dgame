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
            this.movingDown = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/player-down.png")));
            this.movingLeft = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/player-left.png")));
            this.movingRight = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/player-right.png")));
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
    }

    @Override
    public void draw(Graphics2D g2D)
    {
//        g2D.setColor(Color.WHITE);
//        g2D.fillRect(this.positionX, this.positionY, gamePanel.titleSize, gamePanel.titleSize);

        BufferedImage playerAsset;

        switch (this.movementDirection)
        {
            case "up":
                playerAsset = this.movingUp;
                break;
            case "down":
                playerAsset = this.movingDown;
                break;
            case "left":
                playerAsset = this.movingLeft;
                break;
            case "right":
                playerAsset = this.movingRight;
                break;
            case null, default:
                playerAsset = this.standStill;
                break;
        }

        g2D.drawImage(playerAsset, this.positionX, this.positionY, gamePanel.titleSize, gamePanel.titleSize, null);
    }
}
