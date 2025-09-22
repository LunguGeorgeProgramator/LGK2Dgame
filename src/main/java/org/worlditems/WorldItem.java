package org.worlditems;

import org.game.GamePanel;
import org.individual.Player;
import org.inventory.PlayerInventory;
import org.inventory.PlayerInventoryModel;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Font;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.Map;

import static org.game.GamePanel.tileSize;
import static org.world.GameWorld.checkIfAssetIsInsideTheBoundary;

public class WorldItem
{

    WorldItemsAssets[] worldItemsAssets;
    public final String itemAssetName;
    public final String itemAssetType;
    private final GamePanel gamePanel;
    private final Player player;
    private int frameCounter;
    private int assetNumber;
    private final int worldItemPositionX;
    private final int worldItemPositionY;
    private final Map<Integer, BufferedImage> itemsAssetsMap;
    public Rectangle collisionArea;
    public final PlayerInventory playerInventory;
    private boolean hasPlayerCollidedWithItem = false;
    private String textShownOnInteractionWithItem;

    public WorldItem(
        GamePanel gamePanel,
        Player player,
        PlayerInventory playerInventory,
        String itemAssetName,
        String itemAssetType,
        int worldItemPositionX,
        int worldItemPositionY,
        Map<Integer, BufferedImage> itemsAssetsMap
    )
    {
        this.gamePanel = gamePanel;
        this.player = player;
        this.itemAssetName = itemAssetName;
        this.itemAssetType = itemAssetType;
        this.worldItemPositionX = worldItemPositionX;
        this.worldItemPositionY = worldItemPositionY;
        this.itemsAssetsMap = itemsAssetsMap;
        this.worldItemsAssets = WorldItemsAssets.values();
        buildEnemyCollisionArea();
        this.playerInventory = playerInventory;
    }

    private void buildEnemyCollisionArea()
    { // make the collision area small that the player rectangle so upper corners will not hit solid world assets
        this.collisionArea = new Rectangle();
        this.collisionArea.x = 0;
        this.collisionArea.y = 0;
        this.collisionArea.height = tileSize;
        this.collisionArea.width = tileSize;
    }

    public void update()
    {
        this.hasPlayerCollidedWithItem = enemyCheckCollisionWithPlayer();
        if (this.hasPlayerCollidedWithItem)
        {
            PlayerInventoryModel playerInventoryModel = this.playerInventory.getInventoryItemByName(this.itemAssetName);
            if (playerInventoryModel != null)
            {
                playerInventoryModel.setCount(1);
                this.playerInventory.updateInventoryItem(playerInventoryModel);
            }
        }
        setAssetNumber();
    }

    private void setAssetNumber()
    {
        if (this.itemAssetType.equals(WorldItemTypes.CHEST_DOOR.name()))
        {
            PlayerInventoryModel playerInventoryItem = this.playerInventory.getInventoryItemByName(WorldItemsAssets.GOLD_KEY.name());
            boolean isKeyInInventoryForChestAsset = playerInventoryItem != null && playerInventoryItem.getCount() > 0;
            this.assetNumber = this.hasPlayerCollidedWithItem && isKeyInInventoryForChestAsset ? 1 : 2;

            // TODO rethink this logic a bit maybe add test in enum class or make a a separated lang enum or resource file
            if (this.assetNumber == 2)
            {
                this.textShownOnInteractionWithItem = "Can not open chest find key.";
            }
            else
            {
                this.textShownOnInteractionWithItem = "Key used chest empty.";
            }
        }
        else
        {
            changeAssetNumberByFrameCounter(this.itemsAssetsMap.size());
        }
    }

    // TODO add this in a helper class, some logic is in enemy
    private boolean enemyCheckCollisionWithPlayer()
    {
        // player collision rectangle
        int playerLeftX = player.positionX + player.collisionArea.x;
        int playerRightX = player.positionX + player.collisionArea.x + player.collisionArea.width;
        int playerTopY = player.positionY + player.collisionArea.y;
        int playerBottomY = player.positionY + player.collisionArea.y + player.collisionArea.height;

        // item collision rectangle
        int itemLeftX = this.worldItemPositionX + this.collisionArea.x;
        int itemRightX = this.worldItemPositionX + this.collisionArea.x + player.collisionArea.width;
        int itemTopY = this.worldItemPositionY + this.collisionArea.y;
        int itemBottomY = this.worldItemPositionY + this.collisionArea.y + player.collisionArea.height;

        // player world matrix index
        int playerLeftCol = playerLeftX / tileSize;
        int playerRightCol = playerRightX / tileSize;
        int playerTopRow = playerTopY / tileSize;
        int playerBottomRow = playerBottomY / tileSize;

        // enemy world matrix index
        int enemyLeftCol = itemLeftX / tileSize;
        int enemyRightCol = itemRightX / tileSize;
        int enemyTopRow = itemTopY / tileSize;
        int enemyBottomRow = itemBottomY / tileSize;

        if (playerLeftCol == enemyLeftCol && playerTopRow == enemyTopRow)
        {
            return true;
        }
        else if (playerRightCol == enemyRightCol && playerBottomRow == enemyBottomRow)
        {
            return true;
        }
        return false;
    }

    public void draw(Graphics2D g2D)
    {
        int worldItemAssetPositionX = this.worldItemPositionX - this.player.positionX + this.player.playerScreenX;
        int worldItemAssetPositionY = this.worldItemPositionY - this.player.positionY + this.player.playerScreenY;

        // draw world item only if is inside the screen view
        if(checkIfAssetIsInsideTheBoundary(this.worldItemPositionX, this.worldItemPositionY, this.player, tileSize))
        {
            g2D.drawImage(this.itemsAssetsMap.get(this.assetNumber), worldItemAssetPositionX, worldItemAssetPositionY, null);
        }

        if (hasPlayerCollidedWithItem)
        {
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
}
