package org.individual;
import org.game.GamePanel;
import org.game.KeyBoardAndMouseHandler;
import org.imageAssets.ImageLoader;
import org.imageAssets.models.PlayerImagesAssets;
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
    private final ImageLoader imageLoader;
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
    public Rectangle playerDamageTakenArea;
    public final int defaultMovementSpeed;
    public boolean stopPlayerMovement;


    public Player(GamePanel gamePanel)
    {
        super(tileSize * 2, tileSize * 2, 4); // set player position x, y and speed
        this.defaultMovementSpeed = 4;
        this.gamePanel = gamePanel;
        this.imageLoader = gamePanel.imageLoader;
        this.keyBoardAndMouseHandler = this.gamePanel.keyBoardAndMouseHandler;
        this.playerScreenX = (GamePanel.screenWith /2) - (tileSize/2);
        this.playerScreenY = (GamePanel.screenHeight /2) - (tileSize/2);
        this.playerInventory = this.gamePanel.playerInventory;
        this.buildPlayerCollisionArea();
        this.getAssetImages();
    }

    private void buildPlayerCollisionArea()
    { // make the collision area small that the player rectangle so upper corners will not hit solid world assets
        this.collisionArea = new Rectangle();
        this.collisionArea.x = (((tileSize / 2) / 2) / 2) / 2 + 2;
        this.collisionArea.y = tileSize / 2;
        this.collisionArea.height = tileSize / 2;
        this.collisionArea.width = tileSize - (((tileSize / 2) / 2));

        this.worldItemCollisionArea = new Rectangle();
        this.worldItemCollisionArea.x = 0;
        this.worldItemCollisionArea.y =  0;
        this.worldItemCollisionArea.height = tileSize + ((tileSize / 2) / 2);
        this.worldItemCollisionArea.width = tileSize + ((tileSize / 2) / 2);

        this.playerDamageTakenArea = new Rectangle();
        this.playerDamageTakenArea.x = 0;
        this.playerDamageTakenArea.y = 0;
        this.playerDamageTakenArea.height = tileSize / 2 + (tileSize / 2);
        this.playerDamageTakenArea.width = tileSize + ((tileSize / 2) / 2);
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

        this.playerDamageTakenArea.x = this.playerScreenX - (((tileSize / 2) / 2) / 2);
        this.playerDamageTakenArea.y =  this.playerScreenY + ((tileSize / 2) / 2);
    }

    public void getAssetImages()
    {
        this.upMovementImagesAssetsMap = Map.of(
            1, this.imageLoader.getPlayerImageAssetByKey(PlayerImagesAssets.PLAYER_WALK_UP),
            2, this.imageLoader.getPlayerImageAssetByKey(PlayerImagesAssets.PLAYER_WALK_UP_TWO)
        );

        this.downMovementImagesAssetsMap = Map.of(
            1, this.imageLoader.getPlayerImageAssetByKey(PlayerImagesAssets.PLAYER_WALK_DOWN),
            2, this.imageLoader.getPlayerImageAssetByKey(PlayerImagesAssets.PLAYER_WALK_DOWN_TWO)
        );

        this.leftMovementImagesAssetsMap = Map.of(
            1, this.imageLoader.getPlayerImageAssetByKey(PlayerImagesAssets.PLAYER_WALK_LEFT),
            2, this.imageLoader.getPlayerImageAssetByKey(PlayerImagesAssets.PLAYER_WALK_LEFT_TWO)
        );

        this.rightMovementImagesAssetsMap = Map.of(
            1, this.imageLoader.getPlayerImageAssetByKey(PlayerImagesAssets.PLAYER_WALK_RIGHT),
            2, this.imageLoader.getPlayerImageAssetByKey(PlayerImagesAssets.PLAYER_WALK_RIGHT_TWO)
        );

        this.standStillImagesAssetsMap = Map.of(
            MovingDirection.UP, this.imageLoader.getPlayerImageAssetByKey(PlayerImagesAssets.PLAYER_STAND_STILL_UP),
            MovingDirection.DOWN, this.imageLoader.getPlayerImageAssetByKey(PlayerImagesAssets.PLAYER_STAND_STILL_DOWN),
            MovingDirection.LEFT, this.imageLoader.getPlayerImageAssetByKey(PlayerImagesAssets.PLAYER_STAND_STILL_LEFT),
            MovingDirection.RIGHT, this.imageLoader.getPlayerImageAssetByKey(PlayerImagesAssets.PLAYER_STAND_STILL_RIGHT)
        );

        this.swordSwingAttackImagesAssetsMap = Map.of(
            MovingDirection.RIGHT, Map.of(
            1, this.imageLoader.getPlayerImageAssetByKey(PlayerImagesAssets.SWORD_SWING_UP),
            2, this.imageLoader.getPlayerImageAssetByKey(PlayerImagesAssets.SWORD_SWING_CENTER),
            3, this.imageLoader.getPlayerImageAssetByKey(PlayerImagesAssets.SWORD_SWING_DOWN)
            ),
            MovingDirection.LEFT, Map.of(
            1, this.imageLoader.getPlayerImageAssetByKey(PlayerImagesAssets.SWORD_SWING_UP_LEFT),
            2, this.imageLoader.getPlayerImageAssetByKey(PlayerImagesAssets.SWORD_SWING_CENTER_LEFT),
            3, this.imageLoader.getPlayerImageAssetByKey(PlayerImagesAssets.SWORD_SWING_DOWN_LEFT)
            ),
            MovingDirection.UP, Map.of(
            1, this.imageLoader.getPlayerImageAssetByKey(PlayerImagesAssets.SWORD_SWING_UP_UP),
            2, this.imageLoader.getPlayerImageAssetByKey(PlayerImagesAssets.SWORD_SWING_CENTER_UP),
            3, this.imageLoader.getPlayerImageAssetByKey(PlayerImagesAssets.SWORD_SWING_DOWN_UP)
            ),
            MovingDirection.DOWN, Map.of(
            1, this.imageLoader.getPlayerImageAssetByKey(PlayerImagesAssets.SWORD_SWING_UP_DOWN),
            2, this.imageLoader.getPlayerImageAssetByKey(PlayerImagesAssets.SWORD_SWING_CENTER_DOWN),
            3, this.imageLoader.getPlayerImageAssetByKey(PlayerImagesAssets.SWORD_SWING_DOWN_DOWN)
            )
        );

        this.goldSwordSwingAttackImagesAssetsMap = Map.of(
            MovingDirection.RIGHT, Map.of(
                1, this.imageLoader.getPlayerImageAssetByKey(PlayerImagesAssets.GOLD_SWORD_SWING_UP),
                2, this.imageLoader.getPlayerImageAssetByKey(PlayerImagesAssets.GOLD_SWORD_SWING_CENTER),
                3, this.imageLoader.getPlayerImageAssetByKey(PlayerImagesAssets.GOLD_SWORD_SWING_DOWN)
            ),
            MovingDirection.LEFT, Map.of(
                1, this.imageLoader.getPlayerImageAssetByKey(PlayerImagesAssets.GOLD_SWORD_SWING_UP_LEFT),
                2, this.imageLoader.getPlayerImageAssetByKey(PlayerImagesAssets.GOLD_SWORD_SWING_CENTER_LEFT),
                3, this.imageLoader.getPlayerImageAssetByKey(PlayerImagesAssets.GOLD_SWORD_SWING_DOWN_LEFT)
            ),
            MovingDirection.UP, Map.of(
                1, this.imageLoader.getPlayerImageAssetByKey(PlayerImagesAssets.GOLD_SWORD_SWING_UP_UP),
                2, this.imageLoader.getPlayerImageAssetByKey(PlayerImagesAssets.GOLD_SWORD_SWING_CENTER_UP),
                3, this.imageLoader.getPlayerImageAssetByKey(PlayerImagesAssets.GOLD_SWORD_SWING_DOWN_UP)
            ),
            MovingDirection.DOWN, Map.of(
                1, this.imageLoader.getPlayerImageAssetByKey(PlayerImagesAssets.GOLD_SWORD_SWING_UP_DOWN),
                2, this.imageLoader.getPlayerImageAssetByKey(PlayerImagesAssets.GOLD_SWORD_SWING_CENTER_DOWN),
                3, this.imageLoader.getPlayerImageAssetByKey(PlayerImagesAssets.GOLD_SWORD_SWING_DOWN_DOWN)
            )
        );

        this.swordSwingWhenMovingImagesAssetsMap = Map.of(
            MovingDirection.RIGHT, Map.of(
                1, this.imageLoader.getPlayerImageAssetByKey(PlayerImagesAssets.PLAYER_SWORD_START_SWING_ARM),
                2, this.imageLoader.getPlayerImageAssetByKey(PlayerImagesAssets.PLAYER_SWORD_START_SWING_ARM_TWO)
            ),
            MovingDirection.LEFT, Map.of(
                1, this.imageLoader.getPlayerImageAssetByKey(PlayerImagesAssets.PLAYER_SWORD_START_SWING_ARM_LEFT),
                2, this.imageLoader.getPlayerImageAssetByKey(PlayerImagesAssets.PLAYER_SWORD_START_SWING_ARM_TWO_LEFT)
            ),
            MovingDirection.UP, Map.of(
                1, this.imageLoader.getPlayerImageAssetByKey(PlayerImagesAssets.PLAYER_WALK_UP),
                2, this.imageLoader.getPlayerImageAssetByKey(PlayerImagesAssets.PLAYER_WALK_UP_TWO)
            ),
            MovingDirection.DOWN, Map.of(
                1, this.imageLoader.getPlayerImageAssetByKey(PlayerImagesAssets.PLAYER_WALK_DOWN),
                2, this.imageLoader.getPlayerImageAssetByKey(PlayerImagesAssets.PLAYER_WALK_DOWN_TWO)
            )
        );

        this.swordSwingStandStillImagesAssetsMap = Map.of(
            MovingDirection.UP, this.imageLoader.getPlayerImageAssetByKey(PlayerImagesAssets.PLAYER_STAND_STILL_UP),
            MovingDirection.DOWN, this.imageLoader.getPlayerImageAssetByKey(PlayerImagesAssets.PLAYER_STAND_STILL_DOWN),
            MovingDirection.LEFT, this.imageLoader.getPlayerImageAssetByKey(PlayerImagesAssets.PLAYER_SWORD_START_SWING_ARM_STAND_STILL_LEFT),
            MovingDirection.RIGHT, this.imageLoader.getPlayerImageAssetByKey(PlayerImagesAssets.PLAYER_SWORD_START_SWING_ARM_STAND_STILL)
        );
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

        boolean collisionOne = this.gamePanel.collisionChecker.checkTileCollision(this, false, this.gamePanel.worldType);
        boolean collisionTwo = this.gamePanel.collisionChecker.checkTileCollision(this, true, this.gamePanel.worldType);
        this.activateCollision = collisionOne || collisionTwo;

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

//        this.gamePanel.drawTestDynamicRectangle(g2D, this.playerDamageTakenArea.x, this.playerDamageTakenArea.y, this.playerDamageTakenArea.width, this.playerDamageTakenArea.height);
//        this.gamePanel.drawTestDynamicRectangle(g2D, this.playerScreenX, this.playerScreenY, this.collisionArea.width, this.collisionArea.height);
//        this.gamePanel.drawTestDynamicRectangle(g2D, this.playerScreenX, this.playerScreenY, tileSize, tileSize);
        g2D.drawImage(this.isPlayerSwingSword ? playerSwingSwordAsset : playerAsset, this.playerScreenX, this.playerScreenY, null);
        this._drawPlayerAttackCollisionArea(g2D, swingSwordAsset);
    }

    @Override
    public void drawLastInsideGamePanel(Graphics2D g2D) {

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
