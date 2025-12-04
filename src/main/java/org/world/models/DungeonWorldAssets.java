package org.world.models;

import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.Objects;

import static org.helpers.ToolsHelper.getScaledImageFromAssets;

public enum DungeonWorldAssets implements GameWorldAssets
{
    WATER(1, getScaledImageFromAssets("/tiles/water-exp.png"), false),
    TEMPLE_FLOOR_CRACKED(20, getScaledImageFromAssets("/dungeon/temple-floor-cracked.png"), true),
    INTERIOR_FLOOR(21, getScaledImageFromAssets("/tiles/floor.png"), false),
    TEMPLE_FLOOR(22, getScaledImageFromAssets("/tiles/runinedtemple/temple-floor.png"), false),
    WORLD_WALL_END(40, getScaledImageFromAssets("/tiles/end-world-wall.png"), true),
    WORLD_WALL_START(41, getScaledImageFromAssets("/tiles/start-world-wall.png"), true),
    WORLD_WALL_TOP_CORNER_LEFT_BOTTOM(42, getScaledImageFromAssets("/tiles/top-wall-corner-left-bottom.png"), true),
    WORLD_WALL_TOP_CORNER_LEFT_TOP(43, getScaledImageFromAssets("/tiles/top-wall-corner-left-top.png"), true),
    TOP_WALL_CORNER_RIGHT_TOP(44, getScaledImageFromAssets("/tiles/top-wall-corner-right-top.png"), true),
    TOP_WALL_CORNER_RIGHT_BOTTOM(45, getScaledImageFromAssets("/tiles/top-wall-corner-right-bottom.png"), true),
    WALL_FRONT_VIEW(46, getScaledImageFromAssets("/tiles/front-wall.png"), true),
    TOP_SIDE_WALL(47, getScaledImageFromAssets("/tiles/top-wall-side.png"), true),
    TOP_WALL(48, getScaledImageFromAssets("/tiles/top-wall.png"), true),
    BROKEN_END_WALL(49, getScaledImageFromAssets("/dungeon/broken-end-world-wall.png"), true),
    BROKEN_END_WALL_RIGHT_SIZE(50, getScaledImageFromAssets("/dungeon/broken-end-world-wall-right.png"), true),
    TEMPLE_FLOOR_CRACKED_RIGHT_SIZE(51, getScaledImageFromAssets("/dungeon/temple-floor-cracked-right.png"), true);

    private final int index;
    private final BufferedImage worldAssetImage;
    private final boolean solidStopOnCollisionWithPlayer;

    DungeonWorldAssets(int index, BufferedImage worldAssetImage, boolean solidStopOnCollisionWithPlayer)
    {
        this.index = index;
        this.worldAssetImage = worldAssetImage;
        this.solidStopOnCollisionWithPlayer = solidStopOnCollisionWithPlayer;
    }

    public static BufferedImage getWorldImageAssetByIndex(int index)
    {
        return Objects.requireNonNull(getWorldAssetByIndex(index)).getWorldAssetImage();
    }

    public static DungeonWorldAssets getWorldAssetByIndex(int index)
    {
        DungeonWorldAssets worldAssets = Arrays.stream(DungeonWorldAssets.values())
            .filter(s -> s.getIndex() == index)
            .findFirst()
            .orElse(null);
        if (worldAssets == null)
        {
//            throw new RuntimeException("Index not found in world assets!");
            return null;
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

    public boolean getSolidStopOnCollisionWithPlayer()
    {
        return this.solidStopOnCollisionWithPlayer;
    }

    @Override
    public String getItemType()
    {
        return "";
    }

    @Override
    public int getDependencyOnAssetId()
    {
        return 0;
    }

}
