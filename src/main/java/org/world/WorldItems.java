package org.world;

import org.game.GamePanel;
import org.individual.Player;
import org.inventory.PlayerInventory;
import org.inventory.PlayerInventoryModel;
import org.worlditems.WorldItem;
import org.worlditems.WorldItemTypes;
import org.worlditems.WorldItemsAssets;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;


public class WorldItems
{

    private final WorldItemsAssets[] worldItemsAssets;
    private final List<WorldItem> worldItemsList;
    private final GamePanel gamePanel;
    private final Player player;
    private final PlayerInventory playerInventory;

    public WorldItems(GamePanel gamePanel, Player player, PlayerInventory playerInventory)
    {
        this.worldItemsAssets = WorldItemsAssets.values();
        this.gamePanel = gamePanel;
        this.player = player;
        this.worldItemsList = new ArrayList<>();
        this.playerInventory = playerInventory;
        initializeWorldItems();
    }

    private void initializeWorldItems()
    {
        for (WorldItemsAssets worldItemAsset : this.worldItemsAssets)
        {
            this.worldItemsList.add(new WorldItem(
                this.gamePanel,
                this.player,
                this.playerInventory,
                worldItemAsset.name(),
                worldItemAsset.getItemType(),
                worldItemAsset.getDefaultPositionX(),
                worldItemAsset.getDefaultPositionY(),
                worldItemAsset.getItemsAssetsMap()
            ));
        }
    }

    public void update()
    {
        for (WorldItem worldItem : this.worldItemsList)
        {
            worldItem.update();
        }
    }

    public void draw(Graphics2D g2D, PlayerInventory playerInventory)
    {
        for (WorldItem worldItem : this.worldItemsList)
        {
            PlayerInventoryModel inventoryItem = this.playerInventory.getInventoryItemByName(worldItem.itemAssetName);
            if (inventoryItem == null)
            {
                PlayerInventoryModel playerInventoryModelAdd = new PlayerInventoryModel();
                playerInventoryModelAdd.setItemName(worldItem.itemAssetName);
                playerInventoryModelAdd.setCount(0);
                playerInventoryModelAdd.setStatus("active");
                playerInventoryModelAdd.setItemType(worldItem.itemAssetType);
                this.playerInventory.addToInventory(playerInventoryModelAdd);
            }

            if (inventoryItem != null && inventoryItem.getCount() > 0 &&
                (inventoryItem.getItemType().equals(WorldItemTypes.KEY.name()) ||
                 inventoryItem.getItemType().equals(WorldItemTypes.QUEST.name())))
            {
                // if item is already in inventory do not draw on game map
                continue;
            }

            worldItem.draw(g2D);
        }
    }
}
