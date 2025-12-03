package org.world;

import org.game.GamePanel;
import org.inventory.PlayerInventory;
import org.inventory.models.PlayerInventoryModel;
import org.world.models.GameWorldAssets;
import org.worlditems.WorldItem;
import org.worlditems.models.WorldItemBuilder;
import org.worlditems.models.WorldItemTypes;
import org.worlditems.models.WorldItemsAssets;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import static org.game.GamePanel.tileSize;


public class WorldItems extends GameWorld
{

    public final List<WorldItem> worldItemsList;
    public final PlayerInventory playerInventory;
    private static final List<WorldItemTypes> itemsTypesAllowedInInventory = List.of(
        WorldItemTypes.KEY,
        WorldItemTypes.HEALTH_RESTORATION,
        WorldItemTypes.WEAPON
    );

    public WorldItems(GamePanel gamePanel, String worldItemsMapPath)
    {
        super(gamePanel, worldItemsMapPath);
        this.worldItemsList = new ArrayList<>();
        this.playerInventory = this.gamePanel.playerInventory;
        initializeWorldItems();
    }

    public void addItemToAssetsItemsList(WorldItemBuilder worldItemOptions, int worldAssetIndex)
    {
        WorldItemsAssets worldItemsAssets = WorldItemsAssets.getWorldItemAssetByIndex(worldAssetIndex);
        if (worldItemsAssets != null)
        {
            this.worldItemsList.add(
                new WorldItem(
                    this.gamePanel,
                    this.player,
                    this.playerInventory,
                    worldItemsAssets.name(),
                    worldItemOptions != null && worldItemOptions.getItemId() != null ? worldItemOptions.getItemId() :worldItemsAssets.getItemId(),
                    worldItemOptions != null && worldItemOptions.getItemWorldRowIndex() != null ? worldItemOptions.getItemWorldRowIndex() : -1,
                    worldItemOptions != null && worldItemOptions.getItemWorldColIndex() != null ? worldItemOptions.getItemWorldColIndex() : -1,
                    worldItemOptions != null && worldItemOptions.getDependencyOnAssetId() != null ? worldItemOptions.getDependencyOnAssetId() : worldItemsAssets.getDependencyOnAssetId(),
                    worldItemOptions != null && worldItemOptions.getItemType() != null ? worldItemOptions.getItemType() : worldItemsAssets.getItemType(),
                    worldItemOptions != null && worldItemOptions.getPositionX() != null ? worldItemOptions.getPositionX() : worldItemsAssets.getDefaultPositionX(),
                    worldItemOptions != null && worldItemOptions.getPositionY() != null ? worldItemOptions.getPositionY() : worldItemsAssets.getDefaultPositionY(),
                    worldItemOptions != null && worldItemOptions.getItemAssetsMap() != null ? worldItemOptions.getItemAssetsMap() : worldItemsAssets.getItemAssetsMap()
                )
            );
        }
    }

    private void initializeWorldItems()
    {
        for (int i = 0; i < this.worldMap.length; i++)
        {
            for (int j = 0; j < this.worldMap[i].length; j++)
            {
                int worldAssetIndex = this.worldMap[i][j];

                if (worldAssetIndex < 0)
                {
                    continue;
                }
                int worldPositionX = tileSize * i;
                int worldPositionY = tileSize * j;

                WorldItemBuilder worldItemOptions = new WorldItemBuilder.Builder()
                    .setItemWorldRowIndex(i)
                    .setItemWorldColIndex(j)
                    .setPositionX(worldPositionX)
                    .setPositionY(worldPositionY)
                    .setSolidStopOnCollisionWithPlayer(true)
                    .build();

                this.addItemToAssetsItemsList(worldItemOptions, worldAssetIndex);
            }
        }
    }

    public void update()
    {
        for (WorldItem worldItem : this.worldItemsList)
        {
            if (itemsTypesAllowedInInventory.contains(WorldItemTypes.valueOf(worldItem.itemAssetType)))
            {
                String itemInventoryId = this.playerInventory.getWorldItemInventoryId(worldItem);
                PlayerInventoryModel inventoryItem = this.playerInventory.getInventoryItemByInventoryId(itemInventoryId);
                if (inventoryItem == null)
                {
                    PlayerInventoryModel playerInventoryModelAdd = getPlayerInventoryModel(worldItem, itemInventoryId);
                    this.playerInventory.addToInventory(playerInventoryModelAdd);
                }
                else if (inventoryItem.getInInventory())
                {
                    // do not check collision or other logic for this item, this is hidden from player inside the inventory
                    continue;
                }
            }
            worldItem.update();
        }
    }

    private static PlayerInventoryModel getPlayerInventoryModel(WorldItem worldItem, String itemInventoryId) {
        PlayerInventoryModel playerInventoryModelAdd = new PlayerInventoryModel();
        playerInventoryModelAdd.setItemName(worldItem.itemAssetName);
        playerInventoryModelAdd.setCount(0);
        playerInventoryModelAdd.setStatus("active");
        playerInventoryModelAdd.setItemType(worldItem.itemAssetType);
        playerInventoryModelAdd.setItemId(worldItem.itemAssetId);
        playerInventoryModelAdd.setItemWorldMatrixCol(worldItem.itemWorldMatrixColIndex);
        playerInventoryModelAdd.setItemWorldMatrixRow(worldItem.itemWorldMatrixRowIndex);
        playerInventoryModelAdd.setItemInventoryId(itemInventoryId);
        playerInventoryModelAdd.setInInventory(false);
        return playerInventoryModelAdd;
    }

    public void addItemsToDrawList()
    {
        for (WorldItem worldItem : this.worldItemsList)
        {
            if (itemsTypesAllowedInInventory.contains(WorldItemTypes.valueOf(worldItem.itemAssetType)))
            {
                String itemInventoryId = this.playerInventory.getWorldItemInventoryId(worldItem);
                PlayerInventoryModel inventoryItem = this.playerInventory.getInventoryItemByInventoryId(itemInventoryId);
                if (inventoryItem != null && inventoryItem.getInInventory())
                {
                    // if item is already in inventory do not draw on game map
                    continue;
                }

            }
            this.gamePanel.individuals.add(worldItem);
        }
    }

    public void drawTextOmCollision(Graphics2D g2D)
    {
        for (WorldItem worldItem : this.worldItemsList)
        {
            worldItem.drawWordItemCollisionText(g2D);
        }
    }

}
