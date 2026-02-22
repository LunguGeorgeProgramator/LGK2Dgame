package org.imageAssets.models;

public enum DungeonWorldImagesAssets implements ImageModel
{

    WATER("/tiles/water-exp.png"),
    TEMPLE_FLOOR_CRACKED("/dungeon/temple-floor-cracked.png"),
    INTERIOR_FLOOR("/tiles/floor.png"),
    TEMPLE_FLOOR("/tiles/runinedtemple/temple-floor.png"),
    WORLD_WALL_END("/tiles/end-world-wall.png"),
    WORLD_WALL_START("/tiles/start-world-wall.png"),
    WORLD_WALL_TOP_CORNER_LEFT_BOTTOM("/tiles/top-wall-corner-left-bottom.png"),
    WORLD_WALL_TOP_CORNER_LEFT_TOP("/tiles/top-wall-corner-left-top.png"),
    TOP_WALL_CORNER_RIGHT_TOP("/tiles/top-wall-corner-right-top.png"),
    TOP_WALL_CORNER_RIGHT_BOTTOM("/tiles/top-wall-corner-right-bottom.png"),
    WALL_FRONT_VIEW("/tiles/front-wall.png"),
    TOP_SIDE_WALL("/tiles/top-wall-side.png"),
    TOP_WALL("/tiles/top-wall.png"),
    BROKEN_END_WALL("/dungeon/broken-end-world-wall.png"),
    BROKEN_END_WALL_RIGHT_SIZE("/dungeon/broken-end-world-wall-right.png"),
    TEMPLE_FLOOR_CRACKED_RIGHT_SIZE("/dungeon/temple-floor-cracked-right.png");

    private String path;

    DungeonWorldImagesAssets(String path)
    {
        this.path = path;
    }

    public String getPath()
    {
        return this.path;
    }
}
