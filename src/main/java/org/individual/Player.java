package org.individual;
import org.game.GamePanel;
import org.game.KeyBoardHandler;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Map;
import java.util.Objects;

import static org.helpers.ToolsHelper.getImageFromAssets;

public class Player extends Individual
{
    GamePanel gamePanel;
    KeyBoardHandler keyBoardHandler;
    public final int playerScreenX;
    public final int playerScreenY;

    public Player(GamePanel gamePanel, KeyBoardHandler keyBoardHandler)
    {
        super(100, 100, 4, null); // set player position x, y and speed
        this.gamePanel = gamePanel;
        this.keyBoardHandler = keyBoardHandler;
        this.playerScreenX = (this.gamePanel.screenWith/2) - (this.gamePanel.tileSize/2);
        this.playerScreenY = (this.gamePanel.screeHeight/2) - (this.gamePanel.tileSize/2);
        buildPlayerCollisionArea();
    }

    private void buildPlayerCollisionArea()
    { // make the collision area small that the player rectangle so upper corners will not hit solid world assets
        this.collisionArea = new Rectangle();
        this.collisionArea.x = 8;
        this.collisionArea.y = 16;
        this.collisionArea.height = this.gamePanel.tileSize - 16;
        this.collisionArea.width = this.gamePanel.tileSize - 16;
    }

    @Override
    public void getAssetImages(String assetPath)
    {
        this.upMovementImagesAssetsMap = Map.of(
            1, getAssetImage("/player/player-up.png"),
            2, getAssetImage("/player/player-up-2.png")
        );

        this.downMovementImagesAssetsMap = Map.of(
            1, getAssetImage("/player/player-down.png"),
            2, getAssetImage("/player/player-down-2.png")
        );

        this.leftMovementImagesAssetsMap = Map.of(
            1, getAssetImage("/player/player-left.png"),
            2, getAssetImage("/player/player-left-2.png")
        );

        this.rightMovementImagesAssetsMap = Map.of(
            1, getAssetImage("/player/player-right.png"),
            2, getAssetImage("/player/player-right-2.png")
        );

        this.standStillImagesAssetsMap = Map.of(
            "up", getAssetImage("/player/player-up-stand-still.png"),
            "down", getAssetImage("/player/player-stand.png"),
            "left", getAssetImage("/player/player-left-stand-still.png"),
            "right", getAssetImage("/player/player-right-stand-still.png")
        );
    }

    private BufferedImage getAssetImage(String assetPath)
    {
        return Objects.requireNonNull(getImageFromAssets(assetPath));
    }

    @Override
    public void update()
    {
        if (keyBoardHandler.upPressed)
        {
            this.movementDirection = "up";
            this.stoppedDirection = "up";
        }
        else if (keyBoardHandler.downPressed)
        {
            this.movementDirection = "down";
            this.stoppedDirection = "down";
        }
        else if (keyBoardHandler.leftPressed)
        {
            this.movementDirection = "left";
            this.stoppedDirection = "left";
        }
        else if (keyBoardHandler.rightPressed)
        {
            this.movementDirection = "right";
            this.stoppedDirection = "right";
        }
        else
        {
            this.movementDirection = null;
        }

        this.activateCollision = false;
        this.gamePanel.collisionChecker.checkTile(this);

        if (!this.activateCollision)
        {
            switch (this.movementDirection)
            {
                case "up":
                    this.positionY = this.positionY - this.speed;
                    break;
                case "down":
                    this.positionY = this.positionY + this.speed;
                    break;
                case "left":
                    this.positionX = this.positionX - this.speed;
                    break;
                case "right":
                    this.positionX = this.positionX + this.speed;
                    break;
                case null , default:
                    break;
            }
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

        g2D.drawImage(playerAsset, this.playerScreenX, this.playerScreenY, gamePanel.tileSize, gamePanel.tileSize, null);
    }
}
