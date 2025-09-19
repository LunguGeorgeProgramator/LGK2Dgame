package org.world;

import org.game.GamePanel;
import org.individual.Player;
import org.worlditems.WorldItem;
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

    public WorldItems(GamePanel gamePanel, Player player)
    {
        this.worldItemsAssets = WorldItemsAssets.values();
        this.gamePanel = gamePanel;
        this.player = player;
        this.worldItemsList = new ArrayList<>();
        initializeWorldItems();
    }

    private void initializeWorldItems()
    {
        for (WorldItemsAssets worldItemAsset : this.worldItemsAssets)
        {
            this.worldItemsList.add(new WorldItem(
                this.gamePanel,
                this.player,
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

    public void draw(Graphics2D g2D)
    {
        for (WorldItem worldItem : this.worldItemsList)
        {
            worldItem.draw(g2D);
        }
    }
}
