package org.world.models;

import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.Objects;

import static org.helpers.ToolsHelper.getScaledImageFromAssets;

public enum WorldAssets implements GameWorldAssets
{
    GRASS(0, getScaledImageFromAssets("/tiles/grass.png"), false),
    WATER(1, getScaledImageFromAssets("/tiles/water-exp.png"), true),
//    WALL(2, getScaledImageFromAssets("/tiles/front-wall.png"), true),
//    SIDE_WALL(3, getScaledImageFromAssets("/tiles/top-wall-side.png"), true),
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
//    TOP_WALL(20, getScaledImageFromAssets("/tiles/top-wall.png"), true),
    INTERIOR_FLOOR(21, getScaledImageFromAssets("/tiles/floor.png"), false),
    TEMPLE_FLOOR(22, getScaledImageFromAssets("/tiles/runinedtemple/temple-floor.png"), false),
    TEMPLE_FLOOR_END_ONE_LEFT(23, getScaledImageFromAssets("/tiles/runinedtemple/temple-floor-side-end.png"), false),
    TEMPLE_FLOOR_END_TWO_LEFT(24, getScaledImageFromAssets("/tiles/runinedtemple/temple-floor-side-end-two.png"), false),
    TEMPLE_FLOOR_END_ONE_RIGHT(25, getScaledImageFromAssets("/tiles/runinedtemple/temple-floor-side-end-reverted.png"), false),
    TEMPLE_FLOOR_END_TWO_RIGHT(26, getScaledImageFromAssets("/tiles/runinedtemple/temple-floor-side-end-two-reverted.png"), false),
    TEMPLE_FLOOR_END_ONE_UP(27, getScaledImageFromAssets("/tiles/runinedtemple/temple-floor-side-end-up.png"), false),
    TEMPLE_FLOOR_END_TWO_UP(28, getScaledImageFromAssets("/tiles/runinedtemple/temple-floor-side-end-up-two.png"), false),
    TEMPLE_FLOOR_END_ONE_DOWN(29, getScaledImageFromAssets("/tiles/runinedtemple/temple-floor-side-end-down.png"), false),
    TEMPLE_FLOOR_END_TWO_DOWN(30, getScaledImageFromAssets("/tiles/runinedtemple/temple-floor-side-end-down-two.png"), false),
    TEMPLE_BROKEN_FLOOR_ONE(31, getScaledImageFromAssets("/tiles/runinedtemple/temple-broken floor-one.png"), false),
    TEMPLE_BROKEN_FLOOR_TWO(32, getScaledImageFromAssets("/tiles/runinedtemple/temple-broken floor-two.png"), false),
    TEMPLE_BROKEN_FLOOR_THREE(33, getScaledImageFromAssets("/tiles/runinedtemple/temple-broken floor-three.png"), false),
    TEMPLE_WALL_MIDDLE(34, getScaledImageFromAssets("/tiles/runinedtemple/ruin_temple_wall_middle.png"), true),
    TEMPLE_WALL_RIGHT(35, getScaledImageFromAssets("/tiles/runinedtemple/ruin_temple_wall_right_corner.png"), true),
    TEMPLE_WALL_LEFT(36, getScaledImageFromAssets("/tiles/runinedtemple/ruin_temple_wall_left_corner.png"), true),
    TEMPLE_WALL_TOP_LEFT(37, getScaledImageFromAssets("/tiles/runinedtemple/ruin_temple_top_wall_left.png"), true),
    TEMPLE_WALL_TOP_RIGHT(38, getScaledImageFromAssets("/tiles/runinedtemple/ruin_temple_top_wall_right.png"), true),
    TEMPLE_WALL_TOP_PARALLEL(39, getScaledImageFromAssets("/tiles/runinedtemple/ruin_temple_top_wall_middle_parallel.png"), true),
    WORLD_WALL_END(40, getScaledImageFromAssets("/tiles/end-world-wall.png"), true),
    WORLD_WALL_START(41, getScaledImageFromAssets("/tiles/start-world-wall.png"), true),
    WORLD_WALL_TOP_CORNER_LEFT_BOTTOM(42, getScaledImageFromAssets("/tiles/top-wall-corner-left-bottom.png"), true),
    WORLD_WALL_TOP_CORNER_LEFT_TOP(43, getScaledImageFromAssets("/tiles/top-wall-corner-left-top.png"), true),
    TOP_WALL_CORNER_RIGHT_TOP(44, getScaledImageFromAssets("/tiles/top-wall-corner-right-top.png"), true),
    TOP_WALL_CORNER_RIGHT_BOTTOM(45, getScaledImageFromAssets("/tiles/top-wall-corner-right-bottom.png"), true),
    WALL_FRONT_VIEW(46, getScaledImageFromAssets("/tiles/front-wall.png"), true),
    TOP_SIDE_WALL(47, getScaledImageFromAssets("/tiles/top-wall-side.png"), true),
    TOP_WALL(48, getScaledImageFromAssets("/tiles/top-wall.png"), true),
    VENDOR_DESK_LEFT(49, getScaledImageFromAssets("/tiles/furniture/vendor-desk-left-part.png"), true),
    VENDOR_DESK_RIGHT(50, getScaledImageFromAssets("/tiles/furniture/vendor-desk-right-part.png"), true),
    WOOD_FLOOR(51, getScaledImageFromAssets("/tiles/wood-floor.png"), false),
    WOOD_WALL(52, getScaledImageFromAssets("/tiles/wood-wall.png"), true),
    WOOD_WALL_CORNER_LEFT(53, getScaledImageFromAssets("/tiles/wood-wall-end-left.png"), true),
    WOOD_WALL_CORNER_RIGHT(54, getScaledImageFromAssets("/tiles/wood-wall-end-right.png"), true);

    private final int index;
    private final BufferedImage worldAssetImage;
    private final boolean solidStopOnCollisionWithPlayer;

    WorldAssets(int index, BufferedImage worldAssetImage, boolean solidStopOnCollisionWithPlayer)
    {
        this.index = index;
        this.worldAssetImage = worldAssetImage;
        this.solidStopOnCollisionWithPlayer = solidStopOnCollisionWithPlayer;
    }

    public static BufferedImage getWorldImageAssetByIndex(int index)
    {
        return Objects.requireNonNull(getWorldAssetByIndex(index)).getWorldAssetImage();
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
