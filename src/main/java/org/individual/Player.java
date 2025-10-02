package org.individual;
import org.game.GamePanel;
import org.game.KeyBoardHandler;
import org.individual.models.MovingDirection;
import org.inventory.PlayerInventory;
import org.inventory.models.PlayerInventoryModel;
import org.worlditems.models.WorldItemsAssets;

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
    public boolean isPlayerSwingSword = false;
    public boolean isSwordInPlayerInventory = false;
    private Map<Integer, BufferedImage> swordSwingImagesAssetsMap;
    private Map<Integer, BufferedImage> swordSwingWhenMovingImagesAssetsMap;

    public Player(GamePanel gamePanel)
    {
        super(100, 100, 4); // set player position x, y and speed
        this.gamePanel = gamePanel;
        this.keyBoardHandler = this.gamePanel.keyBoardHandler;
        this.playerScreenX = (GamePanel.screenWith /2) - (tileSize/2);
        this.playerScreenY = (GamePanel.screenHeight /2) - (tileSize/2);
        this.playerInventory = this.gamePanel.playerInventory;
        buildPlayerCollisionArea();
        this.getAssetImages();
    }

    private void buildPlayerCollisionArea()
    { // make the collision area small that the player rectangle so upper corners will not hit solid world assets
        this.collisionArea = new Rectangle();
        this.collisionArea.x = 4;
        this.collisionArea.y = 16;
        this.collisionArea.height = tileSize - 16;
        this.collisionArea.width = tileSize - 16;
    }

    public void getAssetImages()
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
            MovingDirection.UP, getAssetImage("/player/player-stand-still-up.png"),
            MovingDirection.DOWN, getAssetImage("/player/player-stand-still-down.png"),
            MovingDirection.LEFT, getAssetImage("/player/player-stand-still-left.png"),
            MovingDirection.RIGHT, getAssetImage("/player/player-stand-still-right.png")
        );

        this.swordSwingImagesAssetsMap = Map.of(
            1, getAssetImage("/player/player-sword-start-swing.png"),
            2, getAssetImage("/player/player-sword-middle-swing.png"),
            3, getAssetImage("/player/player-sword-finish-swing.png")
        );

        this.swordSwingWhenMovingImagesAssetsMap = Map.of(
            1, getAssetImage("/player/player-sword-start-swing-moving.png"),
            2, getAssetImage("/player/player-sword-middle-swing.png"),
            3, getAssetImage("/player/player-sword-finish-swing-moving.png")
        );
    }

    private BufferedImage getAssetImage(String assetPath)
    {
        return Objects.requireNonNull(getScaledImageFromAssets(assetPath));
    }

    @Override
    public void update()
    {
        // player sword swing
        PlayerInventoryModel inventoryModel = this.playerInventory.getInventoryItemByName(WorldItemsAssets.SWORD.name());
        this.isSwordInPlayerInventory = inventoryModel != null && inventoryModel.getInInventory();
        this.isPlayerSwingSword = keyBoardHandler.spaceBarePressed && this.isSwordInPlayerInventory;

        this.changeAssetNumberByFrameCounter(this.swordSwingImagesAssetsMap.size(), 7);

        // player moving
        if (keyBoardHandler.upPressed)
        {
            this.movementDirection = MovingDirection.UP;
            this.stoppedDirection = MovingDirection.UP;
        }
        else if (keyBoardHandler.downPressed)
        {
            this.movementDirection = MovingDirection.DOWN;
            this.stoppedDirection = MovingDirection.DOWN;
        }
        else if (keyBoardHandler.leftPressed)
        {
            this.movementDirection = MovingDirection.LEFT;
            this.stoppedDirection = MovingDirection.LEFT;
        }
        else if (keyBoardHandler.rightPressed)
        {
            this.movementDirection = MovingDirection.RIGHT;
            this.stoppedDirection = MovingDirection.RIGHT;
        }
        else
        {
            this.movementDirection = null;
        }

        this.activateCollision = false;
        this.gamePanel.collisionChecker.checkTile(this, false);
        this.gamePanel.collisionChecker.checkTile(this, true);
//        this.gamePanel.collisionChecker.checkWorldItems(this);

        if (!this.activateCollision)
        {
            switch (this.movementDirection != null ? this.movementDirection : null)
            {
                case MovingDirection.UP:
                    this.positionY = this.positionY - this.speed;
                    break;
                case MovingDirection.DOWN:
                    this.positionY = this.positionY + this.speed;
                    break;
                case MovingDirection.LEFT:
                    this.positionX = this.positionX - this.speed;
                    break;
                case MovingDirection.RIGHT:
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

        switch (this.movementDirection != null ? this.movementDirection : null)
        {
            case MovingDirection.UP:
                playerAsset = this.upMovementImagesAssetsMap.get(this.assetNumber);
                break;
            case MovingDirection.DOWN:
                playerAsset = this.downMovementImagesAssetsMap.get(this.assetNumber);
                break;
            case MovingDirection.LEFT:
                playerAsset = this.leftMovementImagesAssetsMap.get(this.assetNumber);
                break;
            case MovingDirection.RIGHT:
                playerAsset = this.rightMovementImagesAssetsMap.get(this.assetNumber);
                break;
            case null, default:
                MovingDirection playerStoppedDirection = this.stoppedDirection != null ? this.stoppedDirection : this.defaultStoppedDirection;
                playerAsset = this.standStillImagesAssetsMap.get(playerStoppedDirection);
                break;
        }

        if (this.isPlayerSwingSword)
        {
            playerAsset = this.movementDirection == null ? this.swordSwingImagesAssetsMap.get(this.dynamicAssetNumber) : this.swordSwingWhenMovingImagesAssetsMap.get(this.dynamicAssetNumber);
        }

        g2D.drawImage(playerAsset, this.playerScreenX, this.playerScreenY, null);
        drawRedSlider(g2D, 0, 0);
    }

    private void drawRedSlider(Graphics2D g2d, int x, int y)
    {

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
