package org.worlditems;

import org.game.CollisionChecker;
import org.game.GamePanel;
import org.individual.Individual;
import org.individual.Player;
import org.inventory.PlayerInventory;
import org.inventory.PlayerInventoryModel;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.Map;

import static org.game.GamePanel.tileSize;
import static org.world.GameWorld.checkIfAssetIsInsideTheBoundary;

public class WorldItem extends Individual
{

    WorldItemsAssets[] worldItemsAssets;
    public final String itemAssetName;
    public final int itemAssetId;
    public final int itemDependencyOnAssetId;
    public final String itemAssetType;
    private final GamePanel gamePanel;
    private final Player player;
    private int frameCounter;
    private int assetNumber;
    public final int positionX;
    public final int positionY;
    private final Map<Integer, WorldItemAssetsModel> itemsAssetsMap;
    public Rectangle collisionArea;
    public final PlayerInventory playerInventory;
    private boolean hasPlayerCollidedWithItem = false;
    private String textShownOnInteractionWithItem;
    private final CollisionChecker collisionChecker;

    public WorldItem(
            GamePanel gamePanel,
            Player player,
            PlayerInventory playerInventory,
            String itemAssetName,
            int itemAssetId,
            int itemDependencyOnAssetId,
            String itemAssetType,
            int worldItemPositionX,
            int worldItemPositionY,
            Map<Integer, WorldItemAssetsModel> itemsAssetsMap
    )
    {
        super(worldItemPositionX, worldItemPositionY, gamePanel.player.speed, "");
        this.itemAssetId = itemAssetId;
        this.itemDependencyOnAssetId = itemDependencyOnAssetId;
        this.gamePanel = gamePanel;
        this.player = player;
        this.itemAssetName = itemAssetName;
        this.itemAssetType = itemAssetType;
        this.positionX = worldItemPositionX;
        this.positionY = worldItemPositionY;
        this.itemsAssetsMap = itemsAssetsMap;
        this.worldItemsAssets = WorldItemsAssets.values();
        buildEnemyCollisionArea();
        this.playerInventory = playerInventory;
        this.collisionChecker = gamePanel.collisionChecker;
    }

    private void buildEnemyCollisionArea()
    { // make the collision area small that the player rectangle so upper corners will not hit solid world assets
        this.collisionArea = new Rectangle();
        this.collisionArea.x = 0;
        this.collisionArea.y = 0;
        this.collisionArea.height = tileSize;
        this.collisionArea.width = tileSize;
    }

    @Override
    public void getAssetImages(String assetPath)
    {
    }

    public void update()
    {
        this.hasPlayerCollidedWithItem = this.collisionChecker.checkPlayerCollisionWithObject(this.player, this.positionX, this.positionY, this.hasPlayerCollidedWithItem, true);
        if (this.hasPlayerCollidedWithItem)
        {
            PlayerInventoryModel playerInventoryModel = this.playerInventory.getInventoryItemByName(this.itemAssetName);
            if (playerInventoryModel != null)
            {
                playerInventoryModel.setCount(1);
                this.playerInventory.updateInventoryItem(playerInventoryModel);
            }
        }
        this.healingItemsCollisionBehavior();
        this.setAssetNumber();
    }

    private void setAssetNumber()
    {
        if (this.itemAssetType.equals(WorldItemTypes.CHEST.name()) || this.itemAssetType.equals(WorldItemTypes.DOOR.name()))
        { // TODO this can be rethink a bit, the 1,2 solution is not ok
            String itemAssetNameById = WorldItemsAssets.getWorldItemAssetNameById(this.itemDependencyOnAssetId);
            PlayerInventoryModel playerInventoryModel = playerInventory.getInventoryItemByName(itemAssetNameById);
            boolean isKeyInInventoryForChestAsset = playerInventoryModel != null && playerInventoryModel.getCount() > 0;
            this.assetNumber = this.hasPlayerCollidedWithItem && isKeyInInventoryForChestAsset ? 1 : 2;
        }
        else
        {
            changeAssetNumberByFrameCounter(this.itemsAssetsMap.size());
        }
    }

    private void setTextShownOnCollision()
    {
        WorldItemAssetsModel worldItemAssetsModel = this.itemsAssetsMap.get(this.assetNumber);
        String gameText = worldItemAssetsModel != null ? worldItemAssetsModel.getImageTextKey() : null;
        this.gamePanel.gameTextProvider.setTextColor(Color.WHITE);
        this.gamePanel.gameTextProvider.setTextPosition(this.player.playerScreenX - 50, this.player.playerScreenY - 20);
        this.textShownOnInteractionWithItem = this.gamePanel.gameTextProvider.getGameTextByKey(gameText);
    }

    public void draw(Graphics2D g2D)
    {
        int worldItemAssetPositionX = this.positionX - this.player.positionX + this.player.playerScreenX;
        int worldItemAssetPositionY = this.positionY - this.player.positionY + this.player.playerScreenY;

        // draw world item only if is inside the screen view
        if(checkIfAssetIsInsideTheBoundary(this.positionX, this.positionY, this.player, tileSize))
        {
            WorldItemAssetsModel worldItemAssetsModel = this.itemsAssetsMap.get(this.assetNumber);
            BufferedImage bufferedImage =  worldItemAssetsModel != null ? worldItemAssetsModel.getImageAsset() : null;
            if (bufferedImage != null)
            {
                g2D.drawImage(bufferedImage, worldItemAssetPositionX, worldItemAssetPositionY, null);
            }
        }
        this.drawWordItemCollisionText(g2D);
    }

    public void drawWordItemCollisionText(Graphics2D g2D)
    {
        if (this.hasPlayerCollidedWithItem)
        {
            this.setTextShownOnCollision();
            this.gamePanel.gameTextProvider.showTextInsideGame(g2D, this.textShownOnInteractionWithItem);
        }
    }

    // Override in child classes if logic needs changing
    protected void changeAssetNumberByFrameCounter(int maxNumberOfAssets)
    {
        final int numberOfFramesLimit = 25;
        this.frameCounter++;
        if (this.frameCounter > numberOfFramesLimit)
        {
            this.assetNumber = this.assetNumber < maxNumberOfAssets ? this.assetNumber + 1 : 1;
            this.frameCounter = 0;
        }
    }

    private void healingItemsCollisionBehavior()
    {
        if (this.hasPlayerCollidedWithItem && this.itemAssetType.equals(WorldItemTypes.HEALTH_RESTORATION.name()))
        {
            this.player.playerHealth = this.player.playerMaxHealth;
        }
    }
}
