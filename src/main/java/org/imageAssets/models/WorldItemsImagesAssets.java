package org.imageAssets.models;

public enum WorldItemsImagesAssets implements ImageModel
{
    RUSTED_KEY_POSITION_RIGHT("/worlditems/rusted-key-profile-right.png"),
    RUSTED_KEY_POSITION_SPIN_ONE("/worlditems/rusted-key-spin-one.png"),
    RUSTED_KEY_POSITION_SPIN_BACK("/worlditems/rusted-key-spin-back.png"),
    RUSTED_KEY_POSITION_SPIN_TWO("/worlditems/rusted-key-spin-two.png"),
    RUSTED_KEY_POSITION_SPIN_LEFT("/worlditems/rusted-key-profile-left.png"),
    RUSTED_KEY_POSITION_SPIN_THREE("/worlditems/rusted-key-spin-three.png"),
    RUSTED_KEY_POSITION_SPIN_FRONT("/worlditems/rusted-key-spin-front.png"),
    RUSTED_KEY_POSITION_SPIN_FOUR("/worlditems/rusted-key-spin-four.png"),
    RUBY_FRONT("/worlditems/ruby-front.png"),
    RUBY_SPIN_LEFT("/worlditems/ruby-spin-left.png"),
    RUBY_SIDE("/worlditems/ruby-side.png"),
    RUBY_SPIN_RIGHT("/worlditems/ruby-spin-right.png"),
    CHEST_OPEN("/worlditems/open-chest.png"),
    CHEST_CLOSED("/worlditems/closed-chest.png"),
    DOOR_OPEN("/worlditems/open-door.png"),
    DOOR_CLOSED("/worlditems/closed-door.png"),
    BIG_DOOR_OPEN("/worlditems/open-big-door.png"),
    BIG_DOOR_CLOSED("/worlditems/closed-big-door.png"),
    OLD_TREE_ITEM("/tiles/old-tree-no-background.png"),
    IMPENETRABLE_FOREST_TOP_TREE("/tiles/impenetrable-tree-top-of-tree.png"),
    IMPENETRABLE_FOREST_CENTER_TREE("/tiles/impenetrable-tree-center-forest.png"),
    IMPENETRABLE_FOREST_BOTTOM_LEFT_TREE("/tiles/impenetrable-tree-bottom-left-forest.png"),
    IMPENETRABLE_FOREST_BOTTOM_TREE("/tiles/impenetrable-tree-bottom-forest.png"),
    IMPENETRABLE_FOREST_BOTTOM_RIGHT_TREE("/tiles/impenetrable-tree-bottom-right-forest.png"),
    TOP_WALL_END_LEFT("/tiles/top-wall-end-left.png"),
    TOP_WALL_END_RIGHT("/tiles/top-wall-end-right.png"),
    //    TOP_WALL_CORNER_LEFT_BOTTOM("/tiles/top-wall-corner-left-bottom.png"),
//    TOP_WALL_CORNER_RIGHT_BOTTOM(getScaledImageFromAssets("/tiles/top-wall-corner-right-bottom.png"),
    TOP_WALL_CORNER_LEFT_TOP("/tiles/top-wall-corner-left-top.png"),
    TOP_WALL_CORNER_RIGHT_TOP("/tiles/top-wall-corner-right-top.png"),
    //    TOP_WALL_CORNER_SIDE("/tiles/top-wall-side.png"),
    TOP_WALL("/tiles/top-wall.png"),
    //    FRONT_WALL("/tiles/front-wall.png"),
    WINDOW_WALL("/tiles/window-wall.png"),
    SWORD("/worlditems/player-sword.png"),
    GRASS_PLANT("/tiles/grass-plant.png"),
    WOOD_BOARD("/worlditems/wood-board.png"),
    RUINED_BASE_PILLAR("/tiles/runinedtemple/base-pillar.png"),
    RUINED_TOP_PILLAR("/tiles/runinedtemple/top-piller.png"),
    RUINED_TOP_WALL_MIDDLE("/tiles/runinedtemple/ruin_temple_top_wall_middle.png"),
    RUINED_TEMPLE_DOOR_OPEN("/tiles/runinedtemple/ruin_temple_open_door.png"),
    RUINED_TEMPLE_DOOR_CLOSED("/tiles/runinedtemple/ruin_temple_closed_door.png"),
    CAVE_DUNGEON_ENTRY_WAY("/dungeon/cave-dungeon-entry-way.png"),
    WATER_DUNGEON_ENTRY_WAY("/dungeon/water-dungeon-entry-way.png"),
    DUNGEON_EXIT_ENTRY_WAY("/dungeon/cave-dungeon-exit-way.png"),
    BIG_DOOR_WOOD_RIGHT_OPEN("/worlditems/big-door-open-right.png"),
    BIG_DOOR_WOOD_RIGHT_CLOSED("/worlditems/big-door-closed-right.png"),
    BIG_DOOR_WOOD_LEFT_OPEN("/worlditems/big-door-open-left.png"),
    BIG_DOOR_WOOD_LEFT_CLOSED("/worlditems/big-door-closed-left.png"),
    BIG_DOOR_STONE_RIGHT_OPEN("/tiles/runinedtemple/temple-door-open-right.png"),
    BIG_DOOR_STONE_RIGHT_CLOSED("/tiles/runinedtemple/temple-door-closed-right.png"),
    BIG_DOOR_STONE_LEFT_OPEN("/tiles/runinedtemple/temple-door-open-left.png"),
    BIG_DOOR_STONE_LEFT_CLOSED("/tiles/runinedtemple/temple-door-closed-left.png"),
    BARREL("/worlditems/barrel.png");
    
    private final String path;

    WorldItemsImagesAssets(String path)
    {
        this.path = path;
    }

    public String getPath()
    {
        return this.path;
    }

    public static String getAssetImagePath(String itemAssetName)
    {
        try
        {
            WorldItemsImagesAssets worldItemsImagesAssets = Enum.valueOf(WorldItemsImagesAssets.class, itemAssetName);
            return worldItemsImagesAssets.getPath();
        }
        catch (IllegalArgumentException e)
        {
            System.err.println("Invalid environment specified: " + itemAssetName);
            return null;
        }
    }
}
