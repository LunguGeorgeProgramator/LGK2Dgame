package org.worlditems.models;


import org.world.models.GameWorldAssets;

import java.util.Arrays;
import java.util.Map;
import java.util.Objects;

import static org.game.GamePanel.tileSize;
import static org.helpers.ToolsHelper.getScaledImageFromAssets;

public enum WorldItemsAssets implements GameWorldAssets
{
    GOLD_KEY(1, WorldItemTypes.KEY.name(), false, 0, 0, 0,
        Map.of(
            1, new WorldItemAssetsModel(Objects.requireNonNull(getScaledImageFromAssets("/worlditems/rusted-key-profile-right.png")), null),
            2, new WorldItemAssetsModel(Objects.requireNonNull(getScaledImageFromAssets("/worlditems/rusted-key-spin-one.png")), null),
            3, new WorldItemAssetsModel(Objects.requireNonNull(getScaledImageFromAssets("/worlditems/rusted-key-spin-back.png")), null),
            4, new WorldItemAssetsModel(Objects.requireNonNull(getScaledImageFromAssets("/worlditems/rusted-key-spin-two.png")), null),
            5, new WorldItemAssetsModel(Objects.requireNonNull(getScaledImageFromAssets("/worlditems/rusted-key-profile-left.png")), null),
            6, new WorldItemAssetsModel(Objects.requireNonNull(getScaledImageFromAssets("/worlditems/rusted-key-spin-three.png")), null),
            7, new WorldItemAssetsModel(Objects.requireNonNull(getScaledImageFromAssets("/worlditems/rusted-key-spin-front.png")), null),
            8, new WorldItemAssetsModel(Objects.requireNonNull(getScaledImageFromAssets("/worlditems/rusted-key-spin-four.png")), null)
        )),
    RUBY(2, WorldItemTypes.HEALTH_RESTORATION.name(), false, 0, 0, 0,
        Map.of(
            1, new WorldItemAssetsModel(Objects.requireNonNull(getScaledImageFromAssets("/worlditems/ruby-front.png")), null),
            2, new WorldItemAssetsModel(Objects.requireNonNull(getScaledImageFromAssets("/worlditems/ruby-spin-left.png")), null),
            3, new WorldItemAssetsModel(Objects.requireNonNull(getScaledImageFromAssets("/worlditems/ruby-side.png")), null),
            4, new WorldItemAssetsModel(Objects.requireNonNull(getScaledImageFromAssets("/worlditems/ruby-spin-right.png")), null)
        )),
    CHEST(3, WorldItemTypes.CHEST.name(), true, 0, 0, WorldItemsAssets.GOLD_KEY.getItemId(),
        Map.of(
            1, new WorldItemAssetsModel(Objects.requireNonNull(getScaledImageFromAssets("/worlditems/open-chest.png")), "open-chest"),
            2, new WorldItemAssetsModel(Objects.requireNonNull(getScaledImageFromAssets("/worlditems/closed-chest.png")), "closed-chest")
        )),
    DOOR(4, WorldItemTypes.DOOR.name(), true, 0, 0, WorldItemsAssets.GOLD_KEY.getItemId(),
        Map.of(
            1, new WorldItemAssetsModel(Objects.requireNonNull(getScaledImageFromAssets("/worlditems/open-door.png")), null),
            2, new WorldItemAssetsModel(Objects.requireNonNull(getScaledImageFromAssets("/worlditems/closed-door.png")), "closed-door")
        )),
    BIG_DOOR(5, WorldItemTypes.DOOR.name(), true, tileSize * 20, tileSize * 4, WorldItemsAssets.GOLD_KEY.getItemId(),
        Map.of(
            1, new WorldItemAssetsModel(Objects.requireNonNull(getScaledImageFromAssets("/worlditems/open-big-door.png")), null),
            2, new WorldItemAssetsModel(Objects.requireNonNull(getScaledImageFromAssets("/worlditems/closed-big-door.png")), "closed-big-door")
        )),
    OLD_TREE_ITEM(6, WorldItemTypes.VEGETATION.name(), false, 0, 0, 0,
        Map.of(1, new WorldItemAssetsModel(Objects.requireNonNull(getScaledImageFromAssets("/tiles/old-tree-no-background.png")), null))),
    IMPENETRABLE_FOREST_TOP_TREE(7, WorldItemTypes.VEGETATION.name(), false, 0, 0, 0,
        Map.of(1, new WorldItemAssetsModel(Objects.requireNonNull(getScaledImageFromAssets("/tiles/impenetrable-tree-top-of-tree.png")), null))),
    IMPENETRABLE_FOREST_CENTER_TREE(8, WorldItemTypes.VEGETATION.name(), true, 0, 0, 0,
        Map.of(1, new WorldItemAssetsModel(Objects.requireNonNull(getScaledImageFromAssets("/tiles/impenetrable-tree-center-forest.png")), null))),
    IMPENETRABLE_FOREST_BOTTOM_LEFT_TREE(9, WorldItemTypes.VEGETATION.name(), true, 0, 0, 0,
        Map.of(1, new WorldItemAssetsModel(Objects.requireNonNull(getScaledImageFromAssets("/tiles/impenetrable-tree-bottom-left-forest.png")), null))),
    IMPENETRABLE_FOREST_BOTTOM_TREE(10, WorldItemTypes.VEGETATION.name(), true, 0, 0, 0,
        Map.of(1, new WorldItemAssetsModel(Objects.requireNonNull(getScaledImageFromAssets("/tiles/impenetrable-tree-bottom-forest.png")), null))),
    IMPENETRABLE_FOREST_BOTTOM_RIGHT_TREE(11, WorldItemTypes.VEGETATION.name(), true, 0, 0, 0,
        Map.of(1, new WorldItemAssetsModel(Objects.requireNonNull(getScaledImageFromAssets("/tiles/impenetrable-tree-bottom-right-forest.png")), null))),
    TOP_WALL_END_LEFT(12, WorldItemTypes.BUILDING.name(), false, 0, 0, 0,
        Map.of(1, new WorldItemAssetsModel(Objects.requireNonNull(getScaledImageFromAssets("/tiles/top-wall-end-left.png")), null))),
    TOP_WALL_END_RIGHT(13, WorldItemTypes.BUILDING.name(), false, 0, 0, 0,
        Map.of(1, new WorldItemAssetsModel(Objects.requireNonNull(getScaledImageFromAssets("/tiles/top-wall-end-right.png")), null))),
//    TOP_WALL_CORNER_LEFT_BOTTOM(15, WorldItemTypes.BUILDING.name(), true, 0, 0, 0,
//        Map.of(1, new WorldItemAssetsModel(Objects.requireNonNull(getScaledImageFromAssets("/tiles/top-wall-corner-left-bottom.png")), null))),
//    TOP_WALL_CORNER_RIGHT_BOTTOM(16, WorldItemTypes.BUILDING.name(), true, 0, 0, 0,
//        Map.of(1, new WorldItemAssetsModel(Objects.requireNonNull(getScaledImageFromAssets("/tiles/top-wall-corner-right-bottom.png")), null))),
    TOP_WALL_CORNER_LEFT_TOP(17, WorldItemTypes.BUILDING.name(), false, 0, 0, 0,
        Map.of(1, new WorldItemAssetsModel(Objects.requireNonNull(getScaledImageFromAssets("/tiles/top-wall-corner-left-top.png")), null))),
    TOP_WALL_CORNER_RIGHT_TOP(18, WorldItemTypes.BUILDING.name(), false, 0, 0, 0,
        Map.of(1, new WorldItemAssetsModel(Objects.requireNonNull(getScaledImageFromAssets("/tiles/top-wall-corner-right-top.png")), null))),
//    TOP_WALL_CORNER_SIDE(19, WorldItemTypes.BUILDING.name(), true, 0, 0, 0,
//        Map.of(1, new WorldItemAssetsModel(Objects.requireNonNull(getScaledImageFromAssets("/tiles/top-wall-side.png")), null))),
    TOP_WALL(20, WorldItemTypes.BUILDING.name(), false, 0, 0, 0,
        Map.of(1, new WorldItemAssetsModel(Objects.requireNonNull(getScaledImageFromAssets("/tiles/top-wall.png")), null))),
//    FRONT_WALL(21, WorldItemTypes.BUILDING.name(), true, 0, 0, 0,
//        Map.of(1, new WorldItemAssetsModel(Objects.requireNonNull(getScaledImageFromAssets("/tiles/front-wall.png")), null))),
    WINDOW_WALL(22, WorldItemTypes.BUILDING.name(), true, 0, 0, 0,
        Map.of(1, new WorldItemAssetsModel(Objects.requireNonNull(getScaledImageFromAssets("/tiles/window-wall.png")), null))),
    SWORD(23, WorldItemTypes.WEAPON.name(), false, 0, 0, 0,
        Map.of(1, new WorldItemAssetsModel(Objects.requireNonNull(getScaledImageFromAssets("/worlditems/player-sword.png")), null))),
    GRASS_PLANT(24, WorldItemTypes.VEGETATION.name(), false, 0, 0, 0,
        Map.of(1, new WorldItemAssetsModel(Objects.requireNonNull(getScaledImageFromAssets("/tiles/grass-plant.png")), null))),
    WOOD_BOARD(25, WorldItemTypes.BUILDING.name(), true, 0, 0, 0,
        Map.of(1, new WorldItemAssetsModel(Objects.requireNonNull(getScaledImageFromAssets("/worlditems/wood-board.png")), "impenetrable-forrest"))),
    RUINED_BASE_PILLAR(26, WorldItemTypes.BUILDING.name(), true, 0, 0, 0,
        Map.of(1, new WorldItemAssetsModel(Objects.requireNonNull(getScaledImageFromAssets("/tiles/runinedtemple/base-pillar.png")), null))),
    RUINED_TOP_PILLAR(27, WorldItemTypes.BUILDING.name(), false, 0, 0, 0,
        Map.of(1, new WorldItemAssetsModel(Objects.requireNonNull(getScaledImageFromAssets("/tiles/runinedtemple/top-piller.png")), null))),
    RUINED_TOP_WALL_MIDDLE(28, WorldItemTypes.BUILDING.name(), false, 0, 0, 0,
        Map.of(1, new WorldItemAssetsModel(Objects.requireNonNull(getScaledImageFromAssets("/tiles/runinedtemple/ruin_temple_top_wall_middle.png")), null))),
    RUINED_TEMPLE_DOOR(29, WorldItemTypes.DOOR.name(), true, 0, 0, WorldItemsAssets.GOLD_KEY.getItemId(),
        Map.of(
            1, new WorldItemAssetsModel(Objects.requireNonNull(getScaledImageFromAssets("/tiles/runinedtemple/ruin_temple_open_door.png")), null),
            2, new WorldItemAssetsModel(Objects.requireNonNull(getScaledImageFromAssets("/tiles/runinedtemple/ruin_temple_closed_door.png")), "closed-door")
        )),
    CAVE_DUNGEON_ENTRY_WAY(30, WorldItemTypes.CAVE_DUNGEON_ENTRY_WAY.name(), false, 0, 0, 0,
        Map.of(1, new WorldItemAssetsModel(Objects.requireNonNull(getScaledImageFromAssets("/dungeon/cave-dungeon-entry-way.png")), null))),
    WATER_DUNGEON_ENTRY_WAY(31, WorldItemTypes.WATER_DUNGEON_ENTRY_WAY.name(), false, 0, 0, 0,
        Map.of(1, new WorldItemAssetsModel(Objects.requireNonNull(getScaledImageFromAssets("/dungeon/water-dungeon-entry-way.png")), null))),
    DUNGEON_EXIT_ENTRY_WAY(32, WorldItemTypes.DUNGEON_EXIT_ENTRY_WAY.name(), false, 0, 0, 0,
        Map.of(1, new WorldItemAssetsModel(Objects.requireNonNull(getScaledImageFromAssets("/dungeon/cave-dungeon-exit-way.png")), null)));

    private final int itemId;
    private final String itemType;
    private final boolean solidStopOnCollisionWithPlayer;
    private final int defaultPositionX;
    private final int defaultPositionY;
    private final int dependencyOnAssetId;
    private final Map<Integer, WorldItemAssetsModel> itemAssetsMap;

    WorldItemsAssets(
        int itemId,
        String itemType,
        boolean solidStopOnCollisionWithPlayer,
        int defaultPositionX,
        int defaultPositionY,
        int dependencyOnAssetId,
        Map<Integer, WorldItemAssetsModel> itemAssetsMap
    )
    {
        this.itemId = itemId;
        this.itemType = itemType;
        this.solidStopOnCollisionWithPlayer = solidStopOnCollisionWithPlayer;
        this.defaultPositionX = defaultPositionX;
        this.defaultPositionY = defaultPositionY;
        this.dependencyOnAssetId = dependencyOnAssetId;
        this.itemAssetsMap = itemAssetsMap;
    }

    public int getItemId()
    {
        return this.itemId;
    }

    public Map<Integer, WorldItemAssetsModel> getItemAssetsMap()
    {
        return this.itemAssetsMap;
    }

    public int getDefaultPositionX()
    {
        return this.defaultPositionX;
    }

    public int getDefaultPositionY()
    {
        return this.defaultPositionY;
    }

    public String getItemType()
    {
        return this.itemType;
    }

    public boolean getSolidStopOnCollisionWithPlayer()
    {
        return this.solidStopOnCollisionWithPlayer;
    }

    public int getDependencyOnAssetId()
    {
        return this.dependencyOnAssetId;
    }

    public static String getWorldItemAssetNameById(int id)
    {
        WorldItemsAssets worldItemsAssets = Arrays.stream(WorldItemsAssets.values())
            .filter(s -> s.getItemId() == id)
            .findFirst()
            .orElse(null);
        return worldItemsAssets != null ? worldItemsAssets.name() : null;
    }

    public static WorldItemsAssets getWorldItemAssetByIndex(int index)
    {
        WorldItemsAssets worldItemAssets = Arrays.stream(WorldItemsAssets.values())
                .filter(s -> s.getItemId() == index)
                .findFirst()
                .orElse(null);
        if (worldItemAssets == null)
        {
//            throw new RuntimeException("Index not found in world item assets!");
            return null;
        }
        return worldItemAssets;
    }
}
