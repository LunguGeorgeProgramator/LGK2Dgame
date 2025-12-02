package org.worlditems;

import org.game.CollisionChecker;
import org.game.GamePanel;
import org.individual.Individual;
import org.individual.Player;
import org.inventory.PlayerInventory;
import org.inventory.models.PlayerInventoryModel;
import org.worlditems.models.WorldItemAssetsModel;
import org.worlditems.models.WorldItemTypes;
import org.worlditems.models.WorldItemsAssets;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.Map;

import static org.game.GamePanel.tileSize;
import static org.helpers.ToolsHelper.checkIfAssetIsInsideTheBoundary;

public class WorldItem extends Individual
{

    WorldItemsAssets[] worldItemsAssets;
    public final String itemAssetName;
    public final int itemAssetId;
    public final int itemWorldMatrixRowIndex;
    public final int itemWorldMatrixColIndex;
    public final int itemDependencyOnAssetId;
    public final String itemAssetType;
    private final GamePanel gamePanel;
    private final Player player;
    public final int positionX;
    public final int positionY;
    private final Map<Integer, WorldItemAssetsModel> itemsAssetsMap;
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
            int itemWorldMatrixRowIndex,
            int itemWorldMatrixColIndex,
            int itemDependencyOnAssetId,
            String itemAssetType,
            int worldItemPositionX,
            int worldItemPositionY,
            Map<Integer, WorldItemAssetsModel> itemsAssetsMap
    )
    {
        super(worldItemPositionX, worldItemPositionY, gamePanel.player.speed);
        this.itemAssetId = itemAssetId;
        this.itemWorldMatrixRowIndex = itemWorldMatrixRowIndex;
        this.itemWorldMatrixColIndex = itemWorldMatrixColIndex;
        this.itemDependencyOnAssetId = itemDependencyOnAssetId;
        this.gamePanel = gamePanel;
        this.player = player;
        this.itemAssetName = itemAssetName;
        this.itemAssetType = itemAssetType;
        this.positionX = worldItemPositionX;
        this.positionY = worldItemPositionY;
        this.itemsAssetsMap = itemsAssetsMap;
        this.worldItemsAssets = WorldItemsAssets.values();
        this.playerInventory = playerInventory;
        this.collisionChecker = gamePanel.collisionChecker;
        this.buildEnemyCollisionArea();
    }

    private void buildEnemyCollisionArea()
    {
        this.collisionArea = new Rectangle();
        this.collisionArea.x = 0;
        this.collisionArea.y = 0;
        this.collisionArea.height = tileSize;
        this.collisionArea.width = tileSize;
    }

    public void update()
    {
        this.setAssetNumber();
        if(checkIfAssetIsInsideTheBoundary(this.positionX, this.positionY, this.player, tileSize * 4))
        {
            this.hasPlayerCollidedWithItem = this.collisionChecker.areRectanglesIntersecting(this.player.worldItemCollisionArea, this.collisionArea);
            if (this.hasPlayerCollidedWithItem)
            {
                String itemInventoryId = this.playerInventory.getWorldItemInventoryId(this);
                PlayerInventoryModel playerInventoryModel = this.playerInventory.getInventoryItemByInventoryId(itemInventoryId);
                if (playerInventoryModel != null)
                {
                    playerInventoryModel.setCount(1);
                    playerInventoryModel.setInInventory(true);
                    this.playerInventory.updateInventoryItem(playerInventoryModel);
                }
                this.setTextShownOnCollision();
            }
            this.healingItemsCollisionBehavior();
        }
    }

    private void setAssetNumber()
    {
        if (this.itemAssetType.equals(WorldItemTypes.CHEST.name()) || this.itemAssetType.equals(WorldItemTypes.DOOR.name()))
        { // TODO this can be rethink a bit, the 1,2 solution is not ok
            String itemAssetNameById = WorldItemsAssets.getWorldItemAssetNameById(this.itemDependencyOnAssetId);
            PlayerInventoryModel playerInventoryModel = playerInventory.getInventoryItemByName(itemAssetNameById);
            boolean isKeyInInventoryForChestAsset = playerInventoryModel != null && playerInventoryModel.getInInventory();
            this.dynamicAssetNumber = this.hasPlayerCollidedWithItem && isKeyInInventoryForChestAsset ? 1 : 2;
        }
        else
        {
            this.changeAssetNumberByFrameCounter(this.itemsAssetsMap.size());
        }
    }

    private void setTextShownOnCollision()
    {
        WorldItemAssetsModel worldItemAssetsModel = this.itemsAssetsMap.get(this.dynamicAssetNumber);
        String gameText = worldItemAssetsModel != null ? worldItemAssetsModel.getImageTextKey() : null;
        this.gamePanel.gameTextProvider.setTextColor(Color.WHITE);
        this.gamePanel.gameTextProvider.setTextPosition(this.player.playerScreenX - 50, this.player.playerScreenY - 20);
        this.textShownOnInteractionWithItem = this.gamePanel.gameTextProvider.getGameTextByKey(gameText);
    }

    public void draw(Graphics2D g2D)
    {
        int worldItemAssetPositionX = this.positionX - this.player.positionX + this.player.playerScreenX;
        int worldItemAssetPositionY = this.positionY - this.player.positionY + this.player.playerScreenY;
        this.collisionArea.x = worldItemAssetPositionX;
        this.collisionArea.y = worldItemAssetPositionY;
        // draw world item only if is inside the screen view
        if(checkIfAssetIsInsideTheBoundary(this.positionX, this.positionY, this.player, tileSize))
        {
            WorldItemAssetsModel worldItemAssetsModel = this.itemsAssetsMap.get(this.dynamicAssetNumber);
            BufferedImage bufferedImage = worldItemAssetsModel != null ? worldItemAssetsModel.getImageAsset() : null;
            if (bufferedImage != null)
            {
                g2D.drawImage(bufferedImage, worldItemAssetPositionX, worldItemAssetPositionY, null);
            }
//            this.gamePanel.drawTestDynamicRectangle(g2D, this.collisionArea.x, this.collisionArea.y, this.collisionArea.width, this.collisionArea.height);
        }
    }

    public void drawWordItemCollisionText(Graphics2D g2D)
    {
        if (this.hasPlayerCollidedWithItem)
        {
            this.gamePanel.gameTextProvider.showTextInsideGame(g2D, this.textShownOnInteractionWithItem);
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
