package org.world;

import org.game.GamePanel;
import org.individual.Player;
import org.inventory.PlayerInventory;
import org.inventory.models.PlayerInventoryModel;
import org.worlditems.WorldItem;
import org.worlditems.models.WorldItemDuplicatedBuilder;
import org.worlditems.WorldItemTypes;
import org.worlditems.WorldItemsAssets;

import java.util.ArrayList;
import java.util.List;


public class WorldItems
{

    private final WorldItemsAssets[] worldItemsAssets;
    private final List<WorldItem> worldItemsList;
    private final GamePanel gamePanel;
    private final Player player;
    private final PlayerInventory playerInventory;
    private static final List<WorldItemTypes> itemsTypesAllowedInInventory = List.of(
        WorldItemTypes.KEY,
        WorldItemTypes.HEALTH_RESTORATION
    );

    public WorldItems(GamePanel gamePanel, Player player)
    {
        this.worldItemsAssets = WorldItemsAssets.values();
        this.gamePanel = gamePanel;
        this.player = player;
        this.worldItemsList = new ArrayList<>();
        this.playerInventory = this.player.playerInventory;
        initializeWorldItems();
    }

    private void addToWorldItemsList(WorldItemsAssets worldItemAsset, WorldItemDuplicatedBuilder duplicatedOptions)
    {
        this.worldItemsList.add(new WorldItem(
            this.gamePanel,
            this.player,
            this.playerInventory,
            worldItemAsset.name(),
            duplicatedOptions != null && duplicatedOptions.getItemId() != null ? duplicatedOptions.getItemId() :worldItemAsset.getItemId(),
            duplicatedOptions != null && duplicatedOptions.getDependencyOnAssetId() != null ? duplicatedOptions.getDependencyOnAssetId() : worldItemAsset.getDependencyOnAssetId(),
            duplicatedOptions != null && duplicatedOptions.getItemType() != null ? duplicatedOptions.getItemType() : worldItemAsset.getItemType(),
            duplicatedOptions != null && duplicatedOptions.getPositionX() != null ? duplicatedOptions.getPositionX() : worldItemAsset.getDefaultPositionX(),
            duplicatedOptions != null && duplicatedOptions.getPositionY() != null ? duplicatedOptions.getPositionY() : worldItemAsset.getDefaultPositionY(),
            duplicatedOptions != null && duplicatedOptions.getItemAssetsMap() != null ? duplicatedOptions.getItemAssetsMap() : worldItemAsset.getItemAssetsMap()
        ));
    }

    private void initializeWorldItems()
    {
        for (WorldItemsAssets worldItemAsset : this.worldItemsAssets)
        {
            for(WorldItemDuplicatedBuilder duplicatedItemOptions : worldItemAsset.getPositionOnMapForDuplicatedItemList())
            {
                this.addToWorldItemsList(worldItemAsset, duplicatedItemOptions);
            }
            this.addToWorldItemsList(worldItemAsset, null);
        }
    }

    public void update()
    {
        for (WorldItem worldItem : this.worldItemsList)
        {
            if (itemsTypesAllowedInInventory.contains(WorldItemTypes.valueOf(worldItem.itemAssetType)))
            {
                PlayerInventoryModel inventoryItem = this.playerInventory.getInventoryItemByName(worldItem.itemAssetName);
                if (inventoryItem == null)
                {
                    PlayerInventoryModel playerInventoryModelAdd = new PlayerInventoryModel();
                    playerInventoryModelAdd.setItemName(worldItem.itemAssetName);
                    playerInventoryModelAdd.setCount(0);
                    playerInventoryModelAdd.setStatus("active");
                    playerInventoryModelAdd.setItemType(worldItem.itemAssetType);
                    playerInventoryModelAdd.setItemId(worldItem.itemAssetId);
                    this.playerInventory.addToInventory(playerInventoryModelAdd);
                }
                else if (inventoryItem.getCount() > 0)
                {
                    // do not check collision or other logic for this item, this is hidden from player inside the inventory
                    continue;
                }
            }
            worldItem.update();
        }
    }

    public void addItemsToDrawList()
    {
        for (WorldItem worldItem : this.worldItemsList)
        {
            if (itemsTypesAllowedInInventory.contains(WorldItemTypes.valueOf(worldItem.itemAssetType)))
            {
                PlayerInventoryModel inventoryItem = this.playerInventory.getInventoryItemByName(worldItem.itemAssetName);
                if (inventoryItem != null && inventoryItem.getCount() > 0)
                {
                    // if item is already in inventory do not draw on game map
                    continue;
                }

            }
            this.gamePanel.individuals.add(worldItem);
        }
    }
}
