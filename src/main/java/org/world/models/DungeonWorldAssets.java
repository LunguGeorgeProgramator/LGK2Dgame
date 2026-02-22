package org.world.models;

import org.imageAssets.models.DungeonWorldImagesAssets;
import org.imageAssets.models.ImageModel;

import java.util.Arrays;

public enum DungeonWorldAssets implements GameWorldAssets
{
    WATER(1, DungeonWorldImagesAssets.WATER, false),
    TEMPLE_FLOOR_CRACKED(20, DungeonWorldImagesAssets.TEMPLE_FLOOR_CRACKED, true),
    INTERIOR_FLOOR(21, DungeonWorldImagesAssets.INTERIOR_FLOOR, false),
    TEMPLE_FLOOR(22, DungeonWorldImagesAssets.TEMPLE_FLOOR, false),
    WORLD_WALL_END(40, DungeonWorldImagesAssets.WORLD_WALL_END, true),
    WORLD_WALL_START(41, DungeonWorldImagesAssets.WORLD_WALL_START, true),
    WORLD_WALL_TOP_CORNER_LEFT_BOTTOM(42, DungeonWorldImagesAssets.WORLD_WALL_TOP_CORNER_LEFT_BOTTOM, true),
    WORLD_WALL_TOP_CORNER_LEFT_TOP(43, DungeonWorldImagesAssets.WORLD_WALL_TOP_CORNER_LEFT_TOP, true),
    TOP_WALL_CORNER_RIGHT_TOP(44, DungeonWorldImagesAssets.TOP_WALL_CORNER_RIGHT_TOP, true),
    TOP_WALL_CORNER_RIGHT_BOTTOM(45, DungeonWorldImagesAssets.TOP_WALL_CORNER_RIGHT_BOTTOM, true),
    WALL_FRONT_VIEW(46, DungeonWorldImagesAssets.WALL_FRONT_VIEW, true),
    TOP_SIDE_WALL(47, DungeonWorldImagesAssets.TOP_SIDE_WALL, true),
    TOP_WALL(48, DungeonWorldImagesAssets.TOP_WALL, true),
    BROKEN_END_WALL(49, DungeonWorldImagesAssets.BROKEN_END_WALL, true),
    BROKEN_END_WALL_RIGHT_SIZE(50, DungeonWorldImagesAssets.BROKEN_END_WALL_RIGHT_SIZE, true),
    TEMPLE_FLOOR_CRACKED_RIGHT_SIZE(51, DungeonWorldImagesAssets.TEMPLE_FLOOR_CRACKED_RIGHT_SIZE, true);

    private final int index;
    private final ImageModel worldAssetImage;
    private final boolean solidStopOnCollisionWithPlayer;

    DungeonWorldAssets(int index, ImageModel worldAssetImage, boolean solidStopOnCollisionWithPlayer)
    {
        this.index = index;
        this.worldAssetImage = worldAssetImage;
        this.solidStopOnCollisionWithPlayer = solidStopOnCollisionWithPlayer;
    }

    public static ImageModel getWorldImageAssetByIndex(int index)
    {
        return getWorldAssetByIndex(index).getWorldAssetImage();
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

    private ImageModel getWorldAssetImage()
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
