package org.world.dungeons;

import org.game.GamePanel;
import org.world.GameWorld;
import org.world.models.DungeonWorldAssets;

import java.awt.Graphics2D;


public class DungeonWorld extends GameWorld
{

    public DungeonWorld(GamePanel gamePanel, String mapPath)
    {
        super(gamePanel, mapPath);
    }

    protected void drawWorldAsset(Graphics2D g2D, int worldAssetIndex, int worldAssetPositionX, int worldAssetPositionY)
    {
        g2D.drawImage(DungeonWorldAssets.getWorldImageAssetByIndex(worldAssetIndex), worldAssetPositionX, worldAssetPositionY, null);
    }

}
