package org.world.models;

import org.imageAssets.models.WorldImagesAssets;
import java.util.Arrays;

public enum WorldAssets implements GameWorldAssets
{
    GRASS(0, WorldImagesAssets.GRASS, false),
    WATER(1, WorldImagesAssets.WATER, true),
    TREE(4, WorldImagesAssets.TREE, true),
    ROAD(5, WorldImagesAssets.ROAD, false),
    LAKE_CORNER_LEFT_UP(6, WorldImagesAssets.LAKE_CORNER_LEFT_UP, true),
    LAKE_CORNER_RIGHT_UP(7, WorldImagesAssets.LAKE_CORNER_RIGHT_UP, true),
    LAKE_CORNER_LEFT_DOWN(8, WorldImagesAssets.LAKE_CORNER_LEFT_DOWN, true),
    LAKE_CORNER_RIGHT_DOWN(9, WorldImagesAssets.LAKE_CORNER_RIGHT_DOWN, true),
    LAKE_SHORE_UP(10, WorldImagesAssets.LAKE_SHORE_UP, true),
    LAKE_SHORE_DOWN(11, WorldImagesAssets.LAKE_SHORE_DOWN, true),
    LAKE_SHORE_LEFT(12, WorldImagesAssets.LAKE_SHORE_LEFT, true),
    LAKE_SHORE_RIGHT(13, WorldImagesAssets.LAKE_SHORE_RIGHT, true),
    LAKE_CORNER_RIGHT_INVERTED_DOWN(14, WorldImagesAssets.LAKE_CORNER_RIGHT_INVERTED_DOWN, true),
    INTERIOR_FLOOR(21, WorldImagesAssets.INTERIOR_FLOOR, false),
    TEMPLE_FLOOR(22, WorldImagesAssets.TEMPLE_FLOOR, false),
    TEMPLE_FLOOR_END_ONE_LEFT(23, WorldImagesAssets.TEMPLE_FLOOR_END_ONE_LEFT, false),
    TEMPLE_FLOOR_END_TWO_LEFT(24, WorldImagesAssets.TEMPLE_FLOOR_END_TWO_LEFT, false),
    TEMPLE_FLOOR_END_ONE_RIGHT(25, WorldImagesAssets.TEMPLE_FLOOR_END_ONE_RIGHT, false),
    TEMPLE_FLOOR_END_TWO_RIGHT(26, WorldImagesAssets.TEMPLE_FLOOR_END_TWO_RIGHT, false),
    TEMPLE_FLOOR_END_ONE_UP(27, WorldImagesAssets.TEMPLE_FLOOR_END_ONE_UP, false),
    TEMPLE_FLOOR_END_TWO_UP(28, WorldImagesAssets.TEMPLE_FLOOR_END_TWO_UP, false),
    TEMPLE_FLOOR_END_ONE_DOWN(29, WorldImagesAssets.TEMPLE_FLOOR_END_ONE_DOWN, false),
    TEMPLE_FLOOR_END_TWO_DOWN(30, WorldImagesAssets.TEMPLE_FLOOR_END_TWO_DOWN, false),
    TEMPLE_BROKEN_FLOOR_ONE(31, WorldImagesAssets.TEMPLE_BROKEN_FLOOR_ONE, false),
    TEMPLE_BROKEN_FLOOR_TWO(32, WorldImagesAssets.TEMPLE_BROKEN_FLOOR_TWO, false),
    TEMPLE_BROKEN_FLOOR_THREE(33, WorldImagesAssets.TEMPLE_BROKEN_FLOOR_THREE, false),
    TEMPLE_WALL_MIDDLE(34, WorldImagesAssets.TEMPLE_WALL_MIDDLE, true),
    TEMPLE_WALL_RIGHT(35, WorldImagesAssets.TEMPLE_WALL_RIGHT, true),
    TEMPLE_WALL_LEFT(36, WorldImagesAssets.TEMPLE_WALL_LEFT, true),
    TEMPLE_WALL_TOP_LEFT(37, WorldImagesAssets.TEMPLE_WALL_TOP_LEFT, true),
    TEMPLE_WALL_TOP_RIGHT(38, WorldImagesAssets.TEMPLE_WALL_TOP_RIGHT, true),
    TEMPLE_WALL_TOP_PARALLEL(39, WorldImagesAssets.TEMPLE_WALL_TOP_PARALLEL, true),
    WORLD_WALL_END(40, WorldImagesAssets.WORLD_WALL_END, true),
    WORLD_WALL_START(41, WorldImagesAssets.WORLD_WALL_START, true),
    WORLD_WALL_TOP_CORNER_LEFT_BOTTOM(42, WorldImagesAssets.WORLD_WALL_TOP_CORNER_LEFT_BOTTOM, true),
    WORLD_WALL_TOP_CORNER_LEFT_TOP(43, WorldImagesAssets.WORLD_WALL_TOP_CORNER_LEFT_TOP, true),
    TOP_WALL_CORNER_RIGHT_TOP(44, WorldImagesAssets.TOP_WALL_CORNER_RIGHT_TOP, true),
    TOP_WALL_CORNER_RIGHT_BOTTOM(45, WorldImagesAssets.TOP_WALL_CORNER_RIGHT_BOTTOM, true),
    WALL_FRONT_VIEW(46, WorldImagesAssets.WALL_FRONT_VIEW, true),
    TOP_SIDE_WALL(47, WorldImagesAssets.TOP_SIDE_WALL, true),
    TOP_WALL(48, WorldImagesAssets.TOP_WALL, true),
    VENDOR_DESK_LEFT(49, WorldImagesAssets.VENDOR_DESK_LEFT, true),
    VENDOR_DESK_RIGHT(50, WorldImagesAssets.VENDOR_DESK_RIGHT, true),
    WOOD_FLOOR(51, WorldImagesAssets.WOOD_FLOOR, false),
    WOOD_WALL(52, WorldImagesAssets.WOOD_WALL, true),
    WOOD_WALL_CORNER_LEFT(53, WorldImagesAssets.WOOD_WALL_CORNER_LEFT, true),
    WOOD_WALL_CORNER_RIGHT(54, WorldImagesAssets.WOOD_WALL_CORNER_RIGHT, true);

    private final int index;
    private final WorldImagesAssets assetName;
    private final boolean solidStopOnCollisionWithPlayer;

    WorldAssets(int index, WorldImagesAssets assetName, boolean solidStopOnCollisionWithPlayer)
    {
        this.index = index;
        this.assetName = assetName;
        this.solidStopOnCollisionWithPlayer = solidStopOnCollisionWithPlayer;
    }

    public static WorldImagesAssets getWorldImageAssetByIndexImgLoad(int index)
    {
        WorldAssets worldAssets = getWorldAssetByIndex(index);
        return worldAssets.getAssetName();
    }

    public static WorldAssets getWorldAssetByIndex(int index)
    {
        WorldAssets worldAssets = Arrays.stream(WorldAssets.values())
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

    private WorldImagesAssets getAssetName()
    {
        return this.assetName;
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
