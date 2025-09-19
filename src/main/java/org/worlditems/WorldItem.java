package org.worlditems;

import org.game.GamePanel;
import org.individual.Player;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Map;

import static org.game.GamePanel.tileSize;
import static org.world.GameWorld.checkIfAssetIsInsideTheBoundary;

public class WorldItem
{

    WorldItemsAssets[] worldItemsAssets;
    private final GamePanel gamePanel;
    private final Player player;
    private int frameCounter;
    private int assetNumber;
    private final int worldItemPositionX;
    private final int worldItemPositionY;
    private final Map<Integer, BufferedImage> itemsAssetsMap;

    public WorldItem(
        GamePanel gamePanel,
        Player player,
        int worldItemPositionX,
        int worldItemPositionY,
        Map<Integer, BufferedImage> itemsAssetsMap
    )
    {
        this.gamePanel = gamePanel;
        this.player = player;
        this.worldItemPositionX = worldItemPositionX;
        this.worldItemPositionY = worldItemPositionY;
        this.itemsAssetsMap = itemsAssetsMap;
        this.worldItemsAssets = WorldItemsAssets.values();
    }

    public void update()
    {
        changeAssetNumberByFrameCounter(this.itemsAssetsMap.size());
    }

    public void draw(Graphics2D g2D)
    {
        int worldItemAssetPositionX = this.worldItemPositionX - this.player.positionX + this.player.playerScreenX;
        int worldItemAssetPositionY = this.worldItemPositionY - this.player.positionY + this.player.playerScreenY;

        // draw world item only if is inside the screen view
        if(checkIfAssetIsInsideTheBoundary(this.worldItemPositionX, this.worldItemPositionY, this.player, tileSize))
        {
            g2D.drawImage(this.itemsAssetsMap.get(this.assetNumber), worldItemAssetPositionX, worldItemAssetPositionY, tileSize, tileSize, null);
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
