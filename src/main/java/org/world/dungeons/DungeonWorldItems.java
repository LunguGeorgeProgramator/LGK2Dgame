package org.world.dungeons;

import org.game.GamePanel;
import org.world.WorldItems;
import org.worlditems.WorldItem;
import org.worlditems.models.DungeonWorldItemsAssets;
import org.worlditems.models.WorldItemBuilder;

public class DungeonWorldItems extends WorldItems
{

    public DungeonWorldItems(GamePanel gamePanel, String worldItemsMapPath)
    {
        super(gamePanel, worldItemsMapPath);
    }

    public void addItemToAssetsItemsList(WorldItemBuilder worldItemOptions, int worldAssetIndex)
    {
        DungeonWorldItemsAssets worldItemsAssets = DungeonWorldItemsAssets.getWorldItemAssetByIndex(worldAssetIndex);
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
}
