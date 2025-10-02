package org.world;

import org.game.GamePanel;
import org.inventory.PlayerInventory;
import org.inventory.models.PlayerInventoryModel;
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

    private final List<WorldItem> worldItemsList;
    private final PlayerInventory playerInventory;
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

    private void addToWorldItemsList(WorldItemsAssets worldItemAsset, WorldItemBuilder duplicatedOptions)
    {
        this.worldItemsList.add(new WorldItem(
            this.gamePanel,
            this.player,
            this.playerInventory,
            worldItemAsset.name(),
            duplicatedOptions != null && duplicatedOptions.getItemId() != null ? duplicatedOptions.getItemId() :worldItemAsset.getItemId(),
            duplicatedOptions != null && duplicatedOptions.getItemWorldRowIndex() != null ? duplicatedOptions.getItemWorldRowIndex() : -1,
            duplicatedOptions != null && duplicatedOptions.getItemWorldColIndex() != null ? duplicatedOptions.getItemWorldColIndex() : -1,
            duplicatedOptions != null && duplicatedOptions.getDependencyOnAssetId() != null ? duplicatedOptions.getDependencyOnAssetId() : worldItemAsset.getDependencyOnAssetId(),
            duplicatedOptions != null && duplicatedOptions.getItemType() != null ? duplicatedOptions.getItemType() : worldItemAsset.getItemType(),
            duplicatedOptions != null && duplicatedOptions.getPositionX() != null ? duplicatedOptions.getPositionX() : worldItemAsset.getDefaultPositionX(),
            duplicatedOptions != null && duplicatedOptions.getPositionY() != null ? duplicatedOptions.getPositionY() : worldItemAsset.getDefaultPositionY(),
            duplicatedOptions != null && duplicatedOptions.getItemAssetsMap() != null ? duplicatedOptions.getItemAssetsMap() : worldItemAsset.getItemAssetsMap()
        ));
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
                WorldItemsAssets worldItemsAssets = WorldItemsAssets.getWorldItemAssetByIndex(worldAssetIndex);
                if (worldItemsAssets != null)
                {
                    this.addToWorldItemsList(worldItemsAssets,
                        new WorldItemBuilder.Builder()
                            .setItemWorldRowIndex(i)
                            .setItemWorldColIndex(j)
                            .setPositionX(worldPositionX)
                            .setPositionY(worldPositionY)
                            .setSolidStopOnCollisionWithPlayer(true)
                            .build());
                }
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
