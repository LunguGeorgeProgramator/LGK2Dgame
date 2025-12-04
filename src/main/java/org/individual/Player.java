package org.individual;
import org.game.GamePanel;
import org.game.KeyBoardAndMouseHandler;
import org.individual.models.MovingDirection;
import org.inventory.PlayerInventory;
import org.inventory.models.PlayerInventoryModel;
import org.worlditems.models.DungeonWorldItemsAssets;
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
    KeyBoardAndMouseHandler keyBoardAndMouseHandler;
    private static final int PLAYER_HEALTH_BAR_HEIGHT = 10;
    private static final int PLAYER_HEALTH_BAR_POSITION_X = 0;
    private static final int PLAYER_HEALTH_BAR_POSITION_Y = 0;
    public static final double PLAYER_DAMAGE_BLUE_STEEL_SWORD = 10.1;
    public static final double PLAYER_DAMAGE_GOLD_STEEL_SWORD = 50.1;
    public double playerDamageToEnemy;
    public PlayerInventory playerInventory;
    public final int playerScreenX;
    public final int playerScreenY;
    public double damageTaken = 0.0;
    public int playerMaxHealth = 200;
    public double playerHealth = this.playerMaxHealth;
    public boolean isPlayerSwingSword = false;
    public boolean isSwordInPlayerInventory = false;
    public boolean isGoldSwordInPlayerInventory = false;
    private Map<MovingDirection, BufferedImage> swordSwingStandStillImagesAssetsMap;
    private Map<MovingDirection, Map<Integer, BufferedImage>> swordSwingWhenMovingImagesAssetsMap;
    private Map<MovingDirection, Map<Integer, BufferedImage>> swordSwingAttackImagesAssetsMap;
    private Map<MovingDirection, Map<Integer, BufferedImage>> goldSwordSwingAttackImagesAssetsMap;
    public Rectangle attackCollisionArea;
    public Rectangle worldItemCollisionArea;
    public final int defaultMovementSpeed;
    public boolean stopPlayerMovement;


    public Player(GamePanel gamePanel)
    {
        super(tileSize * 2, tileSize * 2, 6); // set player position x, y and speed
        this.defaultMovementSpeed = 6;
        this.gamePanel = gamePanel;
        this.keyBoardAndMouseHandler = this.gamePanel.keyBoardAndMouseHandler;
        this.playerScreenX = (GamePanel.screenWith /2) - (tileSize/2);
        this.playerScreenY = (GamePanel.screenHeight /2) - (tileSize/2);
        this.playerInventory = this.gamePanel.playerInventory;
        this.buildPlayerCollisionArea();
        this.buildPlayerWorldItemCollisionArea();
        this.getAssetImages();
    }

    private void buildPlayerCollisionArea()
    { // make the collision area small that the player rectangle so upper corners will not hit solid world assets
        this.collisionArea = new Rectangle();
        this.collisionArea.x = (((tileSize / 2) / 2) / 2) / 2;
        this.collisionArea.y = tileSize / 2 - (((tileSize / 2) / 2) / 2) / 2 + 1;
        this.collisionArea.height = tileSize / 2;
        this.collisionArea.width = tileSize - (((tileSize / 2) / 2) / 2);
    }

    private void buildPlayerWorldItemCollisionArea()
    {
        this.worldItemCollisionArea = new Rectangle();
        this.worldItemCollisionArea.x = 0;
        this.worldItemCollisionArea.y =  0;
        this.worldItemCollisionArea.height = tileSize + ((tileSize / 2) / 2);
        this.worldItemCollisionArea.width = tileSize + ((tileSize / 2) / 2);
    }

    private void clearAttackCollisionArea()
    {
        this.attackCollisionArea = new Rectangle();
        this.attackCollisionArea.x = this.playerScreenX + tileSize;
        this.attackCollisionArea.y =  this.playerScreenY + tileSize;
        this.attackCollisionArea.height = tileSize;
        this.attackCollisionArea.width = tileSize;
    }

    private void updateCollisionAreaRectanglesPositions()
    {
        this.worldItemCollisionArea.x = this.playerScreenX - (((tileSize / 2) / 2) / 2);
        this.worldItemCollisionArea.y =  this.playerScreenY - (((tileSize / 2) / 2) / 2);
    }

    public void getAssetImages()
    {
        this.upMovementImagesAssetsMap = Map.of(
            1, getAssetImage("/player/walking/player-walk-up.png"),
            2, getAssetImage("/player/walking/player-walk-up-two.png")
        );

        this.downMovementImagesAssetsMap = Map.of(
            1, getAssetImage("/player/walking/player-walk-down.png"),
            2, getAssetImage("/player/walking/player-walk-down-two.png")
        );

        this.leftMovementImagesAssetsMap = Map.of(
            1, getAssetImage("/player/walking/player-walk-left.png"),
            2, getAssetImage("/player/walking/player-walk-left-two.png")
        );

        this.rightMovementImagesAssetsMap = Map.of(
            1, getAssetImage("/player/walking/player-walk-right.png"),
            2, getAssetImage("/player/walking/player-walk-right-two.png")
        );

        this.standStillImagesAssetsMap = Map.of(
            MovingDirection.UP, getAssetImage("/player/player-stand-still-up.png"),
            MovingDirection.DOWN, getAssetImage("/player/player-stand-still-down.png"),
            MovingDirection.LEFT, getAssetImage("/player/player-stand-still-left.png"),
            MovingDirection.RIGHT, getAssetImage("/player/player-stand-still-right.png")
        );

        this.swordSwingAttackImagesAssetsMap = Map.of(
            MovingDirection.RIGHT, Map.of(
            1, getAssetImage("/player/attack/sword-swing-up.png"),
            2, getAssetImage("/player/attack/sword-swing-center.png"),
            3, getAssetImage("/player/attack/sword-swing-down.png")
            ),
            MovingDirection.LEFT, Map.of(
            1, getAssetImage("/player/attack/sword-swing-up-left.png"),
            2, getAssetImage("/player/attack/sword-swing-center-left.png"),
            3, getAssetImage("/player/attack/sword-swing-down-left.png")
            ),
            MovingDirection.UP, Map.of(
            1, getAssetImage("/player/attack/sword-swing-up-up.png"),
            2, getAssetImage("/player/attack/sword-swing-center-up.png"),
            3, getAssetImage("/player/attack/sword-swing-down-up.png")
            ),
            MovingDirection.DOWN, Map.of(
            1, getAssetImage("/player/attack/sword-swing-up-down.png"),
            2, getAssetImage("/player/attack/sword-swing-center-down.png"),
            3, getAssetImage("/player/attack/sword-swing-down-down.png")
            )
        );

        this.goldSwordSwingAttackImagesAssetsMap = Map.of(
            MovingDirection.RIGHT, Map.of(
                1, getAssetImage("/player/attack/gold-sword-swing-up.png"),
                2, getAssetImage("/player/attack/gold-sword-swing-center.png"),
                3, getAssetImage("/player/attack/gold-sword-swing-down.png")
            ),
            MovingDirection.LEFT, Map.of(
                1, getAssetImage("/player/attack/gold-sword-swing-up-left.png"),
                2, getAssetImage("/player/attack/gold-sword-swing-center-left.png"),
                3, getAssetImage("/player/attack/gold-sword-swing-down-left.png")
            ),
            MovingDirection.UP, Map.of(
                1, getAssetImage("/player/attack/gold-sword-swing-up-up.png"),
                2, getAssetImage("/player/attack/gold-sword-swing-center-up.png"),
                3, getAssetImage("/player/attack/gold-sword-swing-down-up.png")
            ),
            MovingDirection.DOWN, Map.of(
                1, getAssetImage("/player/attack/gold-sword-swing-up-down.png"),
                2, getAssetImage("/player/attack/gold-sword-swing-center-down.png"),
                3, getAssetImage("/player/attack/gold-sword-swing-down-down.png")
            )
        );

        this.swordSwingWhenMovingImagesAssetsMap = Map.of(
            MovingDirection.RIGHT, Map.of(
                1, getAssetImage("/player/swingingSword/player-sword-start-swing-arm.png"),
                2, getAssetImage("/player/swingingSword/player-sword-start-swing-arm-two.png")
            ),
            MovingDirection.LEFT, Map.of(
                1, getAssetImage("/player/swingingSword/player-sword-start-swing-arm-left.png"),
                2, getAssetImage("/player/swingingSword/player-sword-start-swing-arm-two-left.png")
            ),
            MovingDirection.UP, Map.of(
                1, getAssetImage("/player/walking/player-walk-up.png"),
                2, getAssetImage("/player/walking/player-walk-up-two.png")
            ),
            MovingDirection.DOWN, Map.of(
                1, getAssetImage("/player/walking/player-walk-down.png"),
                2, getAssetImage("/player/walking/player-walk-down-two.png")
            )
        );

        this.swordSwingStandStillImagesAssetsMap = Map.of(
            MovingDirection.UP, getAssetImage("/player/player-stand-still-up.png"),
            MovingDirection.DOWN, getAssetImage("/player/player-stand-still-down.png"),
            MovingDirection.LEFT, getAssetImage("/player/swingingSword/player-sword-start-swing-arm-stand-still-left.png"),
            MovingDirection.RIGHT, getAssetImage("/player/swingingSword/player-sword-start-swing-arm-stand-still.png")
        );
    }

    private BufferedImage getAssetImage(String assetPath)
    {
        return Objects.requireNonNull(getScaledImageFromAssets(assetPath));
    }

    private void _checkPlayerInventoryForWeapons()
    {
        PlayerInventoryModel inventoryModel = this.playerInventory.getInventoryItemByName(WorldItemsAssets.SWORD.name());
        PlayerInventoryModel inventoryModelGoldSword = this.playerInventory.getInventoryItemByName(DungeonWorldItemsAssets.GOLD_SWORD.name());
        this.isSwordInPlayerInventory = inventoryModel != null && inventoryModel.getInInventory();
        this.isGoldSwordInPlayerInventory = inventoryModelGoldSword != null && inventoryModelGoldSword.getInInventory();
        this.isPlayerSwingSword = keyBoardAndMouseHandler.spaceBarePressed && (this.isSwordInPlayerInventory || this.isGoldSwordInPlayerInventory);
        this.playerDamageToEnemy = this.isGoldSwordInPlayerInventory ? PLAYER_DAMAGE_GOLD_STEEL_SWORD : PLAYER_DAMAGE_BLUE_STEEL_SWORD;
    }

    @Override
    public void update()
    {

        this._checkPlayerInventoryForWeapons();
        this.changeAssetNumberByFrameCounter(3, 5);

        // player moving
        if (keyBoardAndMouseHandler.upPressed)
        {
            this.movementDirection = MovingDirection.UP;
            this.stoppedDirection = MovingDirection.UP;
        }
        else if (keyBoardAndMouseHandler.downPressed)
        {
            this.movementDirection = MovingDirection.DOWN;
            this.stoppedDirection = MovingDirection.DOWN;
        }
        else if (keyBoardAndMouseHandler.leftPressed)
        {
            this.movementDirection = MovingDirection.LEFT;
            this.stoppedDirection = MovingDirection.LEFT;
        }
        else if (keyBoardAndMouseHandler.rightPressed)
        {
            this.movementDirection = MovingDirection.RIGHT;
            this.stoppedDirection = MovingDirection.RIGHT;
        }
        else
        {
            this.movementDirection = null;
        }

        this.gamePanel.collisionChecker.checkTile(this, false, this.gamePanel.worldType);
        this.gamePanel.collisionChecker.checkTile(this, true, this.gamePanel.worldType);

        if (!this.activateCollision && !this.stopPlayerMovement)
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

        // increase player speed when pressing F key
        this.speed = this.keyBoardAndMouseHandler.fastKeyPressed ? 30 : this.defaultMovementSpeed;

        this._setAttackDirection();
        this._playerIsDead();
        this.updateCollisionAreaRectanglesPositions();
    }

    private void _setAttackDirection()
    {
        if (this.isPlayerSwingSword)
        {
            this.clearAttackCollisionArea();
            if (this.movementDirection == MovingDirection.RIGHT || this.stoppedDirection == MovingDirection.RIGHT)
            {
                clearAttackCollisionArea();
                // extend player collision area if swinging the sward
                this.attackCollisionArea.y = this.attackCollisionArea.y - tileSize;
            }
            else if (this.movementDirection == MovingDirection.DOWN || this.stoppedDirection == MovingDirection.DOWN)
            {
                clearAttackCollisionArea();
                this.attackCollisionArea.x = this.attackCollisionArea.x - tileSize;
            }
            else if (this.movementDirection == MovingDirection.LEFT || this.stoppedDirection == MovingDirection.LEFT)
            {
                clearAttackCollisionArea();
                this.attackCollisionArea.x = this.attackCollisionArea.x - tileSize * 2;
                this.attackCollisionArea.y = this.attackCollisionArea.y - tileSize;
            }
            else if (this.movementDirection == MovingDirection.UP || this.stoppedDirection == MovingDirection.UP)
            {
                clearAttackCollisionArea();
                this.attackCollisionArea.x = this.attackCollisionArea.x - tileSize;
                this.attackCollisionArea.y = this.attackCollisionArea.y - tileSize * 2;
            }
        }
        else
        {
            this.attackCollisionArea = null;
        }
    }

    @Override
    public void draw(Graphics2D g2D)
    {
        BufferedImage playerAsset;
        BufferedImage playerSwingSwordAsset;
        BufferedImage swingSwordAsset;

        switch (this.movementDirection != null ? this.movementDirection : null)
        {
            case MovingDirection.UP:
                playerAsset = this.upMovementImagesAssetsMap.get(this.assetNumber);
                playerSwingSwordAsset = this.swordSwingWhenMovingImagesAssetsMap.get(this.movementDirection).get(this.assetNumber);
                swingSwordAsset = this.isGoldSwordInPlayerInventory ?
                    this.goldSwordSwingAttackImagesAssetsMap.get(this.movementDirection).get(this.dynamicAssetNumber) :
                    this.swordSwingAttackImagesAssetsMap.get(this.movementDirection).get(this.dynamicAssetNumber);
                break;
            case MovingDirection.DOWN:
                playerAsset = this.downMovementImagesAssetsMap.get(this.assetNumber);
                playerSwingSwordAsset = this.swordSwingWhenMovingImagesAssetsMap.get(this.movementDirection).get(this.assetNumber);
                swingSwordAsset = this.isGoldSwordInPlayerInventory ?
                    this.goldSwordSwingAttackImagesAssetsMap.get(this.movementDirection).get(this.dynamicAssetNumber) :
                    this.swordSwingAttackImagesAssetsMap.get(this.movementDirection).get(this.dynamicAssetNumber);
                break;
            case MovingDirection.LEFT:
                playerAsset = this.leftMovementImagesAssetsMap.get(this.assetNumber);
                playerSwingSwordAsset = this.swordSwingWhenMovingImagesAssetsMap.get(this.movementDirection).get(this.assetNumber);
                swingSwordAsset = this.isGoldSwordInPlayerInventory ?
                    this.goldSwordSwingAttackImagesAssetsMap.get(this.movementDirection).get(this.dynamicAssetNumber) :
                    this.swordSwingAttackImagesAssetsMap.get(this.movementDirection).get(this.dynamicAssetNumber);
                break;
            case MovingDirection.RIGHT:
                playerAsset = this.rightMovementImagesAssetsMap.get(this.assetNumber);
                playerSwingSwordAsset = this.swordSwingWhenMovingImagesAssetsMap.get(this.movementDirection).get(this.assetNumber);
                swingSwordAsset = this.isGoldSwordInPlayerInventory ?
                    this.goldSwordSwingAttackImagesAssetsMap.get(this.movementDirection).get(this.dynamicAssetNumber) :
                    this.swordSwingAttackImagesAssetsMap.get(this.movementDirection).get(this.dynamicAssetNumber);
                break;
            case null, default:
                MovingDirection playerStoppedDirection = this.stoppedDirection != null ? this.stoppedDirection : this.defaultStoppedDirection;
                playerAsset = this.standStillImagesAssetsMap.get(playerStoppedDirection);
                playerSwingSwordAsset = this.swordSwingStandStillImagesAssetsMap.get(playerStoppedDirection);
                swingSwordAsset = this.isGoldSwordInPlayerInventory ?
                    this.goldSwordSwingAttackImagesAssetsMap.get(playerStoppedDirection).get(this.dynamicAssetNumber) :
                    this.swordSwingAttackImagesAssetsMap.get(playerStoppedDirection).get(this.dynamicAssetNumber);
                break;
        }

//        this.gamePanel.drawTestDynamicRectangle(g2D, this.worldItemCollisionArea.x, this.worldItemCollisionArea.y, this.worldItemCollisionArea.width, this.worldItemCollisionArea.height);
        g2D.drawImage(this.isPlayerSwingSword ? playerSwingSwordAsset : playerAsset, this.playerScreenX, this.playerScreenY, null);
        this._drawPlayerAttackCollisionArea(g2D, swingSwordAsset);
    }

    private void _playerIsDead()
    {
        if (this.playerHealth <= 0)
        {
            this.gamePanel.resetGame();
        }
    }

    public void _drawPlayerAttackCollisionArea(Graphics2D g2D, BufferedImage swingSwordAsset)
    {
        if (this.isPlayerSwingSword && this.attackCollisionArea != null)
        {
            g2D.drawImage(swingSwordAsset, this.attackCollisionArea.x, this.attackCollisionArea.y, null);
//           this.gamePanel.drawTestDynamicRectangle(g2D, this.attackCollisionArea.x, this.attackCollisionArea.y, this.attackCollisionArea.width, this.attackCollisionArea.height);
        }
    }

    public void drawRedSlider(Graphics2D g2d)
    {
        int height = PLAYER_HEALTH_BAR_HEIGHT;
        // Draw background (gray)
        g2d.setColor(Color.LIGHT_GRAY);
        g2d.fillRect(PLAYER_HEALTH_BAR_POSITION_X, PLAYER_HEALTH_BAR_POSITION_Y, this.playerMaxHealth, height);

        // Draw red bar representing the current value
        int filledWidth = (int) this.playerHealth;
        g2d.setColor(Color.RED);
        g2d.fillRect(PLAYER_HEALTH_BAR_POSITION_X, PLAYER_HEALTH_BAR_POSITION_Y, filledWidth, height);

        // Optional: Draw border
        g2d.setColor(Color.BLACK);
        g2d.drawRect(PLAYER_HEALTH_BAR_POSITION_X, PLAYER_HEALTH_BAR_POSITION_Y, this.playerMaxHealth, height);
    }
}
