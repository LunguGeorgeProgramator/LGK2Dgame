package org.world;

import java.awt.image.BufferedImage;
import java.util.Arrays;
import static org.helpers.ToolsHelper.getImageFromAssets;

public enum WorldAssets
{
    GRASS(0, getImageFromAssets("/tiles/grass.png")),
    WATER(1, getImageFromAssets("/tiles/water.png")),
    WALL(2, getImageFromAssets("/tiles/wall.png")),
    SIDE_WALL(3, getImageFromAssets("/tiles/sideWall.png")),
    TREE(4, getImageFromAssets("/tiles/tree.png")),
    ROAD(5, getImageFromAssets("/tiles/road.png"));

    private final int index;
    private final BufferedImage worldAssetImage;

    WorldAssets(int index, BufferedImage worldAssetImage)
    {
        this.index = index;
        this.worldAssetImage = worldAssetImage;
    }

    public static BufferedImage getWorldAssetByIndex(int index)
    {
        WorldAssets worldAssets = Arrays.stream(WorldAssets.values())
            .filter(s -> s.getIndex() == index)
            .findFirst()
            .orElse(null);
        if (worldAssets == null)
        {
            throw new RuntimeException("Index not found in world assets!");
        }
        return worldAssets.getWorldAssetImage();
    }

    private BufferedImage getWorldAssetImage()
    {
        return this.worldAssetImage;
    }

    private int getIndex()
    {
        return this.index;
    }

}
