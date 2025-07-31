package org.individual;
import org.game.GamePanel;
import org.game.KeyBoardHandler;

import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Map;
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
        this.upMovementImagesAssetsMap = Map.of(
            1, this.getImageFromAssets("/player/player-up.png"),
            2, this.getImageFromAssets("/player/player-up-2.png")
        );

        this.downMovementImagesAssetsMap = Map.of(
            1, this.getImageFromAssets("/player/player-down.png"),
            2, this.getImageFromAssets("/player/player-down-2.png")
        );

        this.leftMovementImagesAssetsMap = Map.of(
            1, this.getImageFromAssets("/player/player-left.png"),
            2, this.getImageFromAssets("/player/player-left-2.png")
        );

        this.rightMovementImagesAssetsMap = Map.of(
            1, this.getImageFromAssets("/player/player-right.png"),
            2, this.getImageFromAssets("/player/player-right-2.png")
        );

        this.standStillImagesAssetsMap = Map.of(
            "up", this.getImageFromAssets("/player/player-up-stand-still.png"),
            "down", this.getImageFromAssets("/player/player-stand.png"),
            "left", this.getImageFromAssets("/player/player-left-stand-still.png"),
            "right", this.getImageFromAssets("/player/player-right-stand-still.png")
        );
    }

    private BufferedImage getPlayerStandAsset(String assetName)
    {
        try
        {
            return ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/" + assetName)));
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public void update()
    {
        if (keyBoardHandler.upPressed)
        {
            this.positionY = this.positionY - this.speed;
            this.movementDirection = "up";
            this.stoppedDirection = "up";
        }
        else if (keyBoardHandler.downPressed)
        {
            this.positionY = this.positionY + this.speed;
            this.movementDirection = "down";
            this.stoppedDirection = "down";
        }
        else if (keyBoardHandler.leftPressed)
        {
            this.positionX = this.positionX - this.speed;
            this.movementDirection = "left";
            this.stoppedDirection = "left";
        }
        else if (keyBoardHandler.rightPressed)
        {
            this.positionX = this.positionX + this.speed;
            this.movementDirection = "right";
            this.stoppedDirection = "right";
        }
        else
        {
            this.movementDirection = null;
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
                playerAsset = this.upMovementImagesAssetsMap.get(this.assetNumber);
                break;
            case "down":
                playerAsset = this.downMovementImagesAssetsMap.get(this.assetNumber);
                break;
            case "left":
                playerAsset = this.leftMovementImagesAssetsMap.get(this.assetNumber);
                break;
            case "right":
                playerAsset = this.rightMovementImagesAssetsMap.get(this.assetNumber);
                break;
            case null, default:
                String playerStoppedDirection = this.stoppedDirection != null ? this.stoppedDirection : this.defaultStoppedDirection;
                playerAsset = this.standStillImagesAssetsMap.get(playerStoppedDirection);
                break;
        }

        g2D.drawImage(playerAsset, this.positionX, this.positionY, gamePanel.titleSize, gamePanel.titleSize, null);
    }
}
