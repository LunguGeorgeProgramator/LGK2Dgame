package org.imageAssets.models;


public enum WorldImagesAssets implements ImageModel
{
    GRASS("/tiles/grass.png"),
    WATER("/tiles/water-exp.png"),
    TREE("/tiles/tree.png"),
    ROAD("/tiles/road.png"),
    LAKE_CORNER_LEFT_UP("/tiles/lake-corner-left-up-exp.png"),
    LAKE_CORNER_RIGHT_UP("/tiles/lake-corner-right-up-exp.png"),
    LAKE_CORNER_LEFT_DOWN("/tiles/lake-corner-left-down-exp.png"),
    LAKE_CORNER_RIGHT_DOWN("/tiles/lake-corner-right-down-exp.png"),
    LAKE_SHORE_UP("/tiles/lake-shore-up-exp.png"),
    LAKE_SHORE_DOWN("/tiles/lake-shore-down-exp.png"),
    LAKE_SHORE_LEFT("/tiles/lake-shore-left-exp.png"),
    LAKE_SHORE_RIGHT("/tiles/lake-shore-right-exp.png"),
    LAKE_CORNER_RIGHT_INVERTED_DOWN("/tiles/lake-corner-right-inverted-down-exp.png"),
    //    TOP_WALL("/tiles/top-wall.png"),
    INTERIOR_FLOOR("/tiles/floor.png"),
    TEMPLE_FLOOR("/tiles/runinedtemple/temple-floor.png"),
    TEMPLE_FLOOR_END_ONE_LEFT("/tiles/runinedtemple/temple-floor-side-end.png"),
    TEMPLE_FLOOR_END_TWO_LEFT("/tiles/runinedtemple/temple-floor-side-end-two.png"),
    TEMPLE_FLOOR_END_ONE_RIGHT("/tiles/runinedtemple/temple-floor-side-end-reverted.png"),
    TEMPLE_FLOOR_END_TWO_RIGHT("/tiles/runinedtemple/temple-floor-side-end-two-reverted.png"),
    TEMPLE_FLOOR_END_ONE_UP("/tiles/runinedtemple/temple-floor-side-end-up.png"),
    TEMPLE_FLOOR_END_TWO_UP("/tiles/runinedtemple/temple-floor-side-end-up-two.png"),
    TEMPLE_FLOOR_END_ONE_DOWN("/tiles/runinedtemple/temple-floor-side-end-down.png"),
    TEMPLE_FLOOR_END_TWO_DOWN("/tiles/runinedtemple/temple-floor-side-end-down-two.png"),
    TEMPLE_BROKEN_FLOOR_ONE("/tiles/runinedtemple/temple-broken floor-one.png"),
    TEMPLE_BROKEN_FLOOR_TWO("/tiles/runinedtemple/temple-broken floor-two.png"),
    TEMPLE_BROKEN_FLOOR_THREE("/tiles/runinedtemple/temple-broken floor-three.png"),
    TEMPLE_WALL_MIDDLE("/tiles/runinedtemple/ruin_temple_wall_middle.png"),
    TEMPLE_WALL_RIGHT("/tiles/runinedtemple/ruin_temple_wall_right_corner.png"),
    TEMPLE_WALL_LEFT("/tiles/runinedtemple/ruin_temple_wall_left_corner.png"),
    TEMPLE_WALL_TOP_LEFT("/tiles/runinedtemple/ruin_temple_top_wall_left.png"),
    TEMPLE_WALL_TOP_RIGHT("/tiles/runinedtemple/ruin_temple_top_wall_right.png"),
    TEMPLE_WALL_TOP_PARALLEL("/tiles/runinedtemple/ruin_temple_top_wall_middle_parallel.png"),
    WORLD_WALL_END("/tiles/end-world-wall.png"),
    WORLD_WALL_START("/tiles/start-world-wall.png"),
    WORLD_WALL_TOP_CORNER_LEFT_BOTTOM("/tiles/top-wall-corner-left-bottom.png"),
    WORLD_WALL_TOP_CORNER_LEFT_TOP("/tiles/top-wall-corner-left-top.png"),
    TOP_WALL_CORNER_RIGHT_TOP("/tiles/top-wall-corner-right-top.png"),
    TOP_WALL_CORNER_RIGHT_BOTTOM("/tiles/top-wall-corner-right-bottom.png"),
    WALL_FRONT_VIEW("/tiles/front-wall.png"),
    TOP_SIDE_WALL("/tiles/top-wall-side.png"),
    TOP_WALL("/tiles/top-wall.png"),
    VENDOR_DESK_LEFT("/tiles/furniture/vendor-desk-left-part.png"),
    VENDOR_DESK_RIGHT("/tiles/furniture/vendor-desk-right-part.png"),
    WOOD_FLOOR("/tiles/wood-floor.png"),
    WOOD_WALL("/tiles/wood-wall.png"),
    WOOD_WALL_CORNER_LEFT("/tiles/wood-wall-end-left.png"),
    WOOD_WALL_CORNER_RIGHT("/tiles/wood-wall-end-right.png");

    private final String path;

    WorldImagesAssets(String path)
    {
        this.path = path;
    }

    public String getPath()
    {
        return this.path;
    }
}
