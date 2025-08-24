package org.world;

import java.awt.image.BufferedImage;
import java.util.Arrays;
import static org.helpers.ToolsHelper.getImageFromAssets;

public enum WorldAssets
{
    GRASS(0, getImageFromAssets("/tiles/grass.png"), false),
    WATER(1, getImageFromAssets("/tiles/water.png"), false),
    WALL(2, getImageFromAssets("/tiles/wall.png"), true),
    SIDE_WALL(3, getImageFromAssets("/tiles/sideWall.png"), true),
    TREE(4, getImageFromAssets("/tiles/tree.png"), true),
    ROAD(5, getImageFromAssets("/tiles/road.png"), false);

    private final int index;
    private final BufferedImage worldAssetImage;
    private final boolean collision;

    WorldAssets(int index, BufferedImage worldAssetImage, boolean collision)
    {
        this.index = index;
        this.worldAssetImage = worldAssetImage;
        this.collision = collision;
    }

    public static BufferedImage getWorldImageAssetByIndex(int index)
    {
        return getWorldAssetByIndex(index).getWorldAssetImage();
    }

    public static WorldAssets getWorldAssetByIndex(int index)
    {
        WorldAssets worldAssets = Arrays.stream(WorldAssets.values())
                .filter(s -> s.getIndex() == index)
                .findFirst()
                .orElse(null);
        if (worldAssets == null)
        {
            throw new RuntimeException("Index not found in world assets!");
        }
        return worldAssets;
    }

    private BufferedImage getWorldAssetImage()
    {
        return this.worldAssetImage;
    }

    private int getIndex()
    {
        return this.index;
    }

    public boolean getCollision()
    {
        return this.collision;
    }

}
