package org.individual;
import org.game.GamePanel;
import org.game.KeyBoardHandler;
import org.inventory.PlayerInventory;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.Map;
import java.util.Objects;

import static org.helpers.ToolsHelper.getScaledImageFromAssets;
import static org.game.GamePanel.tileSize;

public class Player extends Individual
{
    GamePanel gamePanel;
    KeyBoardHandler keyBoardHandler;
    public PlayerInventory playerInventory;
    public final int playerScreenX;
    public final int playerScreenY;
    public double damageTaken = 0.0;
    public int playerMaxHealth = 200;
    public double playerHealth = this.playerMaxHealth;

    public Player(GamePanel gamePanel, KeyBoardHandler keyBoardHandler, PlayerInventory playerInventory)
    {
        super(100, 100, 4, null); // set player position x, y and speed
        this.gamePanel = gamePanel;
        this.keyBoardHandler = keyBoardHandler;
        this.playerScreenX = (this.gamePanel.screenWith/2) - (tileSize/2);
        this.playerScreenY = (this.gamePanel.screenHeight/2) - (tileSize/2);
        this.playerInventory = playerInventory;
        buildPlayerCollisionArea();
    }

    private void buildPlayerCollisionArea()
    { // make the collision area small that the player rectangle so upper corners will not hit solid world assets
        this.collisionArea = new Rectangle();
        this.collisionArea.x = 4;
        this.collisionArea.y = 16;
        this.collisionArea.height = tileSize - 16;
        this.collisionArea.width = tileSize - 16;
    }

    @Override
    public void getAssetImages(String assetPath)
    {
        this.upMovementImagesAssetsMap = Map.of(
            1, getAssetImage("/player/player-walk-up.png"),
            2, getAssetImage("/player/player-walk-up-two.png")
        );

        this.downMovementImagesAssetsMap = Map.of(
            1, getAssetImage("/player/player-walk-down.png"),
            2, getAssetImage("/player/player-walk-down-two.png")
        );

        this.leftMovementImagesAssetsMap = Map.of(
            1, getAssetImage("/player/player-walk-left.png"),
            2, getAssetImage("/player/player-walk-left-two.png")
        );

        this.rightMovementImagesAssetsMap = Map.of(
            1, getAssetImage("/player/player-walk-right.png"),
            2, getAssetImage("/player/player-walk-right-two.png")
        );

        this.standStillImagesAssetsMap = Map.of(
            "up", getAssetImage("/player/player-stand-still-up.png"),
            "down", getAssetImage("/player/player-stand-still-down.png"),
            "left", getAssetImage("/player/player-stand-still-left.png"),
            "right", getAssetImage("/player/player-stand-still-right.png")
        );
    }

    private BufferedImage getAssetImage(String assetPath)
    {
        return Objects.requireNonNull(getScaledImageFromAssets(assetPath));
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
        this.gamePanel.collisionChecker.checkWorldItems(this);

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
        this.playerHealth = (int) (this.playerHealth - this.damageTaken);
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

        g2D.drawImage(playerAsset, this.playerScreenX, this.playerScreenY, null);
        drawRedSlider(g2D, 0, 0);
    }

    private void drawRedSlider(Graphics2D g2d, int x, int y) {

        int height = 10;
        // Draw background (gray)
        g2d.setColor(Color.LIGHT_GRAY);
        g2d.fillRect(x, y, this.playerMaxHealth, height);

        // Draw red bar representing the current value
        int filledWidth = (int) this.playerHealth;
        g2d.setColor(Color.RED);
        g2d.fillRect(x, y, filledWidth, height);

        // Optional: Draw border
        g2d.setColor(Color.BLACK);
        g2d.drawRect(x, y, this.playerMaxHealth, height);
    }
}
