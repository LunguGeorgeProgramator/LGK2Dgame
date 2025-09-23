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
    private static final List<WorldItemTypes> itemsTypesAllowedInInventory = List.of(
        WorldItemTypes.KEY,
        WorldItemTypes.QUEST
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

    private void initializeWorldItems()
    {
        for (WorldItemsAssets worldItemAsset : this.worldItemsAssets)
        {
            this.worldItemsList.add(new WorldItem(
                this.gamePanel,
                this.player,
                this.playerInventory,
                worldItemAsset.name(),
                worldItemAsset.getItemId(),
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
            }
            worldItem.update();
        }
    }

    public void draw(Graphics2D g2D)
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
            worldItem.draw(g2D);
//            int pX = this.player.positionX;
//            int pY = this.player.positionY;
//            int eX = worldItem.worldItemPositionX;
//            int eY = worldItem.worldItemPositionY;
//            if (pY< eY)
//            {
//                this.player.draw(g2D);
//                worldItem.draw(g2D);
//            }
//            else
//            {
//                worldItem.draw(g2D);
//                this.player.draw(g2D);
//            }
        }
    }
}
