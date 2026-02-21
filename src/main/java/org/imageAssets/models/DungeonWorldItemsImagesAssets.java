package org.imageAssets.models;

public enum DungeonWorldItemsImagesAssets implements ImageModel
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
    DUNGEON_EXIT_ENTRY_WAY("/dungeon/cave-dungeon-exit-way.png"),
    SWORD("/worlditems/player-sword.png"),
    WOOD_BOARD("/worlditems/wood-board.png"),
    TOP_WALL_CORNER_RIGHT_TOP("/tiles/top-wall-corner-right-top.png"),
    TOP_WALL_CORNER_LEFT_TOP("/tiles/top-wall-corner-left-top.png"),
    TOP_WALL("/tiles/top-wall.png"),
    GOLD_SWORD("/dungeon/gold-sword-in-rock.png");

    private String path;

    DungeonWorldItemsImagesAssets(String path)
    {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
