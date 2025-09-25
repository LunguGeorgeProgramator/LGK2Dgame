package org.world;

import java.awt.image.BufferedImage;
import java.util.Arrays;
import static org.helpers.ToolsHelper.getScaledImageFromAssets;

public enum WorldAssets
{
    GRASS(0, getScaledImageFromAssets("/tiles/grass.png"), false),
    WATER(1, getScaledImageFromAssets("/tiles/water-exp.png"), true),
    WALL(2, getScaledImageFromAssets("/tiles/wall.png"), true),
    SIDE_WALL(3, getScaledImageFromAssets("/tiles/sideWall.png"), true),
    TREE(4, getScaledImageFromAssets("/tiles/tree.png"), true),
    ROAD(5, getScaledImageFromAssets("/tiles/road.png"), false),
    LAKE_CORNER_LEFT_UP(6, getScaledImageFromAssets("/tiles/lake-corner-left-up-exp.png"), true),
    LAKE_CORNER_RIGHT_UP(7, getScaledImageFromAssets("/tiles/lake-corner-right-up-exp.png"), true),
    LAKE_CORNER_LEFT_DOWN(8, getScaledImageFromAssets("/tiles/lake-corner-left-down-exp.png"), true),
    LAKE_CORNER_RIGHT_DOWN(9, getScaledImageFromAssets("/tiles/lake-corner-right-down-exp.png"), true),
    LAKE_SHORE_UP(10, getScaledImageFromAssets("/tiles/lake-shore-up-exp.png"), true),
    LAKE_SHORE_DOWN(11, getScaledImageFromAssets("/tiles/lake-shore-down-exp.png"), true),
    LAKE_SHORE_LEFT(12, getScaledImageFromAssets("/tiles/lake-shore-left-exp.png"), true),
    LAKE_SHORE_RIGHT(13, getScaledImageFromAssets("/tiles/lake-shore-right-exp.png"), true),
    LAKE_CORNER_RIGHT_INVERTED_DOWN(14, getScaledImageFromAssets("/tiles/lake-corner-right-inverted-down-exp.png"), true),
    IMPENETRABLE_FOREST_CENTER_TREE(15, getScaledImageFromAssets("/tiles/impenetrable-tree-center-forest.png"), true),
    IMPENETRABLE_FOREST_BOTTOM_LEFT_TREE(16, getScaledImageFromAssets("/tiles/impenetrable-tree-bottom-left-forest.png"), true),
    IMPENETRABLE_FOREST_BOTTOM_TREE(17, getScaledImageFromAssets("/tiles/impenetrable-tree-bottom-forest.png"), true),
    IMPENETRABLE_FOREST_BOTTOM_RIGHT_TREE(18, getScaledImageFromAssets("/tiles/impenetrable-tree-bottom-right-forest.png"), true),
    IMPENETRABLE_FOREST_TOP_TREE(19, getScaledImageFromAssets("/tiles/impenetrable-tree-top-forest.png"), true);

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
