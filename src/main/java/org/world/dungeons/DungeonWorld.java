package org.world.dungeons;

import org.game.GamePanel;
import org.imageAssets.models.ImageModel;
import org.world.GameWorld;
import org.world.models.DungeonWorldAssets;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Map;


public class DungeonWorld extends GameWorld
{

    private final Map<ImageModel, BufferedImage> worldImagesAssetsBufferedImageMap;

    public DungeonWorld(GamePanel gamePanel, String mapPath)
    {
        super(gamePanel, mapPath);
        this.worldImagesAssetsBufferedImageMap = gamePanel.imageLoader.getDungeonWorldImagesAssets();
    }

    protected void drawWorldAsset(Graphics2D g2D, int worldAssetIndex, int worldAssetPositionX, int worldAssetPositionY)
    {
        ImageModel worldImagesAssets = DungeonWorldAssets.getWorldImageAssetByIndex(worldAssetIndex);
        BufferedImage imageAsset = this.worldImagesAssetsBufferedImageMap.get(worldImagesAssets);
        g2D.drawImage(imageAsset, worldAssetPositionX, worldAssetPositionY, null);
    }

}
