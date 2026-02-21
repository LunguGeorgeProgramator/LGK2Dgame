package org.worlditems.models;


import org.imageAssets.models.WorldItemsImagesAssets;
import org.world.models.GameWorldAssets;

import java.util.Arrays;
import java.util.Map;

import static org.game.GamePanel.tileSize;

public enum WorldItemsAssets implements GameWorldAssets
{
    GOLD_KEY(1, WorldItemTypes.KEY.name(), false, 0, 0, 0,
        Map.of(
            1, new WorldItemAssetsModel(WorldItemsImagesAssets.RUSTED_KEY_POSITION_RIGHT, null),
            2, new WorldItemAssetsModel(WorldItemsImagesAssets.RUSTED_KEY_POSITION_SPIN_ONE, null),
            3, new WorldItemAssetsModel(WorldItemsImagesAssets.RUSTED_KEY_POSITION_SPIN_BACK, null),
            4, new WorldItemAssetsModel(WorldItemsImagesAssets.RUSTED_KEY_POSITION_SPIN_TWO, null),
            5, new WorldItemAssetsModel(WorldItemsImagesAssets.RUSTED_KEY_POSITION_SPIN_LEFT, null),
            6, new WorldItemAssetsModel(WorldItemsImagesAssets.RUSTED_KEY_POSITION_SPIN_THREE, null),
            7, new WorldItemAssetsModel(WorldItemsImagesAssets.RUSTED_KEY_POSITION_SPIN_FRONT, null),
            8, new WorldItemAssetsModel(WorldItemsImagesAssets.RUSTED_KEY_POSITION_SPIN_FOUR, null)
        )),
    RUBY(2, WorldItemTypes.HEALTH_RESTORATION.name(), false, 0, 0, 0,
        Map.of(
            1, new WorldItemAssetsModel(WorldItemsImagesAssets.RUBY_FRONT, null),
            2, new WorldItemAssetsModel(WorldItemsImagesAssets.RUBY_SPIN_LEFT, null),
            3, new WorldItemAssetsModel(WorldItemsImagesAssets.RUBY_SIDE, null),
            4, new WorldItemAssetsModel(WorldItemsImagesAssets.RUBY_SPIN_RIGHT, null)
        )),
    CHEST(3, WorldItemTypes.CHEST.name(), true, 0, 0, WorldItemsAssets.GOLD_KEY.getItemId(),
        Map.of(
            1, new WorldItemAssetsModel(WorldItemsImagesAssets.CHEST_OPEN, "open-chest"),
            2, new WorldItemAssetsModel(WorldItemsImagesAssets.CHEST_CLOSED, "closed-chest")
        )),
    DOOR(4, WorldItemTypes.DOOR.name(), true, 0, 0, WorldItemsAssets.GOLD_KEY.getItemId(),
        Map.of(
            1, new WorldItemAssetsModel(WorldItemsImagesAssets.DOOR_OPEN, null),
            2, new WorldItemAssetsModel(WorldItemsImagesAssets.DOOR_CLOSED, "closed-door")
        )),
    BIG_DOOR(5, WorldItemTypes.DOOR.name(), true, tileSize * 20, tileSize * 4, WorldItemsAssets.GOLD_KEY.getItemId(),
        Map.of(
            1, new WorldItemAssetsModel(WorldItemsImagesAssets.BIG_DOOR_OPEN, null),
            2, new WorldItemAssetsModel(WorldItemsImagesAssets.BIG_DOOR_CLOSED, "closed-big-door")
        )),
    OLD_TREE_ITEM(6, WorldItemTypes.VEGETATION.name(), false, 0, 0, 0,
        Map.of(1, new WorldItemAssetsModel(WorldItemsImagesAssets.OLD_TREE_ITEM, null))),
    IMPENETRABLE_FOREST_TOP_TREE(7, WorldItemTypes.VEGETATION.name(), false, 0, 0, 0,
        Map.of(1, new WorldItemAssetsModel(WorldItemsImagesAssets.IMPENETRABLE_FOREST_TOP_TREE, null))),
    IMPENETRABLE_FOREST_CENTER_TREE(8, WorldItemTypes.VEGETATION.name(), true, 0, 0, 0,
        Map.of(1, new WorldItemAssetsModel(WorldItemsImagesAssets.IMPENETRABLE_FOREST_CENTER_TREE, null))),
    IMPENETRABLE_FOREST_BOTTOM_LEFT_TREE(9, WorldItemTypes.VEGETATION.name(), true, 0, 0, 0,
        Map.of(1, new WorldItemAssetsModel(WorldItemsImagesAssets.IMPENETRABLE_FOREST_BOTTOM_LEFT_TREE, null))),
    IMPENETRABLE_FOREST_BOTTOM_TREE(10, WorldItemTypes.VEGETATION.name(), true, 0, 0, 0,
        Map.of(1, new WorldItemAssetsModel(WorldItemsImagesAssets.IMPENETRABLE_FOREST_BOTTOM_TREE, null))),
    IMPENETRABLE_FOREST_BOTTOM_RIGHT_TREE(11, WorldItemTypes.VEGETATION.name(), true, 0, 0, 0,
        Map.of(1, new WorldItemAssetsModel(WorldItemsImagesAssets.IMPENETRABLE_FOREST_BOTTOM_RIGHT_TREE, null))),
    TOP_WALL_END_LEFT(12, WorldItemTypes.BUILDING.name(), false, 0, 0, 0,
        Map.of(1, new WorldItemAssetsModel(WorldItemsImagesAssets.TOP_WALL_END_LEFT, null))),
    TOP_WALL_END_RIGHT(13, WorldItemTypes.BUILDING.name(), false, 0, 0, 0,
        Map.of(1, new WorldItemAssetsModel(WorldItemsImagesAssets.TOP_WALL_END_RIGHT, null))),
//    TOP_WALL_CORNER_LEFT_BOTTOM(15, WorldItemTypes.BUILDING.name(), true, 0, 0, 0,
//        Map.of(1, new WorldItemAssetsModel(WorldItemsImagesAssets.TOP_WALL_CORNER_LEFT_BOTTOM, null))),
//    TOP_WALL_CORNER_RIGHT_BOTTOM(16, WorldItemTypes.BUILDING.name(), true, 0, 0, 0,
//        Map.of(1, new WorldItemAssetsModel(WorldItemsImagesAssets.TOP_WALL_CORNER_RIGHT_BOTTOM, null))),
    TOP_WALL_CORNER_LEFT_TOP(17, WorldItemTypes.BUILDING.name(), false, 0, 0, 0,
        Map.of(1, new WorldItemAssetsModel(WorldItemsImagesAssets.TOP_WALL_CORNER_LEFT_TOP, null))),
    TOP_WALL_CORNER_RIGHT_TOP(18, WorldItemTypes.BUILDING.name(), false, 0, 0, 0,
        Map.of(1, new WorldItemAssetsModel(WorldItemsImagesAssets.TOP_WALL_CORNER_RIGHT_TOP, null))),
//    TOP_WALL_CORNER_SIDE(19, WorldItemTypes.BUILDING.name(), true, 0, 0, 0,
//        Map.of(1, new WorldItemAssetsModel(WorldItemsImagesAssets.TOP_WALL_CORNER_SIDE, null))),
    TOP_WALL(20, WorldItemTypes.BUILDING.name(), false, 0, 0, 0,
        Map.of(1, new WorldItemAssetsModel(WorldItemsImagesAssets.TOP_WALL, null))),
//    FRONT_WALL(21, WorldItemTypes.BUILDING.name(), true, 0, 0, 0,
//        Map.of(1, new WorldItemAssetsModel(WorldItemsImagesAssets.FRONT_WALL, null))),
    WINDOW_WALL(22, WorldItemTypes.BUILDING.name(), true, 0, 0, 0,
        Map.of(1, new WorldItemAssetsModel(WorldItemsImagesAssets.WINDOW_WALL, null))),
    SWORD(23, WorldItemTypes.WEAPON.name(), false, 0, 0, 0,
        Map.of(1, new WorldItemAssetsModel(WorldItemsImagesAssets.SWORD, null))),
    GRASS_PLANT(24, WorldItemTypes.VEGETATION.name(), false, 0, 0, 0,
        Map.of(1, new WorldItemAssetsModel(WorldItemsImagesAssets.GRASS_PLANT, null))),
    WOOD_BOARD(25, WorldItemTypes.BUILDING.name(), true, 0, 0, 0,
        Map.of(1, new WorldItemAssetsModel(WorldItemsImagesAssets.WOOD_BOARD, "impenetrable-forrest"))),
    RUINED_BASE_PILLAR(26, WorldItemTypes.BUILDING.name(), true, 0, 0, 0,
        Map.of(1, new WorldItemAssetsModel(WorldItemsImagesAssets.RUINED_BASE_PILLAR, null))),
    RUINED_TOP_PILLAR(27, WorldItemTypes.BUILDING.name(), false, 0, 0, 0,
        Map.of(1, new WorldItemAssetsModel(WorldItemsImagesAssets.RUINED_TOP_PILLAR, null))),
    RUINED_TOP_WALL_MIDDLE(28, WorldItemTypes.BUILDING.name(), false, 0, 0, 0,
        Map.of(1, new WorldItemAssetsModel(WorldItemsImagesAssets.RUINED_TOP_WALL_MIDDLE, null))),
    RUINED_TEMPLE_DOOR(29, WorldItemTypes.DOOR.name(), true, 0, 0, WorldItemsAssets.GOLD_KEY.getItemId(),
        Map.of(
            1, new WorldItemAssetsModel(WorldItemsImagesAssets.RUINED_TEMPLE_DOOR_OPEN, null),
            2, new WorldItemAssetsModel(WorldItemsImagesAssets.RUINED_TEMPLE_DOOR_CLOSED, "closed-door")
        )),
    CAVE_DUNGEON_ENTRY_WAY(30, WorldItemTypes.CAVE_DUNGEON_ENTRY_WAY.name(), false, 0, 0, 0,
        Map.of(1, new WorldItemAssetsModel(WorldItemsImagesAssets.CAVE_DUNGEON_ENTRY_WAY, null))),
    WATER_DUNGEON_ENTRY_WAY(31, WorldItemTypes.WATER_DUNGEON_ENTRY_WAY.name(), false, 0, 0, 0,
        Map.of(1, new WorldItemAssetsModel(WorldItemsImagesAssets.WATER_DUNGEON_ENTRY_WAY, null))),
    DUNGEON_EXIT_ENTRY_WAY(32, WorldItemTypes.DUNGEON_EXIT_ENTRY_WAY.name(), false, 0, 0, 0,
        Map.of(1, new WorldItemAssetsModel(WorldItemsImagesAssets.DUNGEON_EXIT_ENTRY_WAY, null))),
    BIG_DOOR_WOOD_RIGHT(33, WorldItemTypes.DOOR.name(), true, tileSize * 20, tileSize * 4, WorldItemsAssets.GOLD_KEY.getItemId(),
        Map.of(
            1, new WorldItemAssetsModel(WorldItemsImagesAssets.BIG_DOOR_WOOD_RIGHT_OPEN, null),
            2, new WorldItemAssetsModel(WorldItemsImagesAssets.BIG_DOOR_WOOD_RIGHT_CLOSED, "closed-big-door")
        )),
    BIG_DOOR_WOOD_LEFT(34, WorldItemTypes.DOOR.name(), true, tileSize * 20, tileSize * 4, WorldItemsAssets.GOLD_KEY.getItemId(),
        Map.of(
            1, new WorldItemAssetsModel(WorldItemsImagesAssets.BIG_DOOR_WOOD_LEFT_OPEN, null),
            2, new WorldItemAssetsModel(WorldItemsImagesAssets.BIG_DOOR_WOOD_LEFT_CLOSED, "closed-big-door")
        )),
    BIG_DOOR_STONE_RIGHT(35, WorldItemTypes.DOOR.name(), true, tileSize * 20, tileSize * 4, WorldItemsAssets.GOLD_KEY.getItemId(),
        Map.of(
        1, new WorldItemAssetsModel(WorldItemsImagesAssets.BIG_DOOR_STONE_RIGHT_OPEN, null),
        2, new WorldItemAssetsModel(WorldItemsImagesAssets.BIG_DOOR_STONE_RIGHT_CLOSED, "closed-big-door")
        )),
    BIG_DOOR_STONE_LEFT(36, WorldItemTypes.DOOR.name(), true, tileSize * 20, tileSize * 4, WorldItemsAssets.GOLD_KEY.getItemId(),
        Map.of(
        1, new WorldItemAssetsModel(WorldItemsImagesAssets.BIG_DOOR_STONE_LEFT_OPEN, null),
        2, new WorldItemAssetsModel(WorldItemsImagesAssets.BIG_DOOR_STONE_LEFT_CLOSED, "closed-big-door")
        )),
    BARREL(37, WorldItemTypes.FURNITURE.name(), true, tileSize * 20, tileSize * 4, 0,
        Map.of(1, new WorldItemAssetsModel(WorldItemsImagesAssets.BARREL, null)));

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
