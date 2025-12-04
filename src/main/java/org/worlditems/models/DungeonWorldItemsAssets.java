package org.worlditems.models;


import org.world.models.GameWorldAssets;

import java.util.Arrays;
import java.util.Map;
import java.util.Objects;

import static org.helpers.ToolsHelper.getScaledImageFromAssets;

public enum DungeonWorldItemsAssets implements GameWorldAssets
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
    CHEST(3, WorldItemTypes.CHEST.name(), true, 0, 0, DungeonWorldItemsAssets.GOLD_KEY.getItemId(),
        Map.of(
            1, new WorldItemAssetsModel(Objects.requireNonNull(getScaledImageFromAssets("/worlditems/open-chest.png")), "open-chest"),
            2, new WorldItemAssetsModel(Objects.requireNonNull(getScaledImageFromAssets("/worlditems/closed-chest.png")), "closed-chest")
        )),
    DUNGEON_EXIT_ENTRY_WAY(4, WorldItemTypes.DUNGEON_EXIT_ENTRY_WAY.name(), false, 0, 0, 0,
        Map.of(1, new WorldItemAssetsModel(Objects.requireNonNull(getScaledImageFromAssets("/dungeon/cave-dungeon-exit-way.png")), null))),
    SWORD(5, WorldItemTypes.WEAPON.name(), false, 0, 0, 0,
        Map.of(1, new WorldItemAssetsModel(Objects.requireNonNull(getScaledImageFromAssets("/worlditems/player-sword.png")), null))),
    WOOD_BOARD(6, WorldItemTypes.BUILDING.name(), true, 0, 0, 0,
        Map.of(1, new WorldItemAssetsModel(Objects.requireNonNull(getScaledImageFromAssets("/worlditems/wood-board.png")), "impenetrable-forrest"))),
    TOP_WALL_CORNER_RIGHT_TOP(8, WorldItemTypes.BUILDING.name(), false, 0, 0, 0,
        Map.of(1, new WorldItemAssetsModel(Objects.requireNonNull(getScaledImageFromAssets("/tiles/top-wall-corner-right-top.png")), null))),
    TOP_WALL_CORNER_LEFT_TOP(9, WorldItemTypes.BUILDING.name(), false, 0, 0, 0,
            Map.of(1, new WorldItemAssetsModel(Objects.requireNonNull(getScaledImageFromAssets("/tiles/top-wall-corner-left-top.png")), null))),
    TOP_WALL(10, WorldItemTypes.BUILDING.name(), false, 0, 0, 0,
        Map.of(1, new WorldItemAssetsModel(Objects.requireNonNull(getScaledImageFromAssets("/tiles/top-wall.png")), null))),
    GOLD_SWORD(11, WorldItemTypes.WEAPON.name(), false, 0, 0, 0,
            Map.of(1, new WorldItemAssetsModel(Objects.requireNonNull(getScaledImageFromAssets("/dungeon/gold-sword-in-rock.png")), null)));

    private final int itemId;
    private final String itemType;
    private final boolean solidStopOnCollisionWithPlayer;
    private final int defaultPositionX;
    private final int defaultPositionY;
    private final int dependencyOnAssetId;
    private final Map<Integer, WorldItemAssetsModel> itemAssetsMap;

    DungeonWorldItemsAssets(
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
        DungeonWorldItemsAssets worldItemsAssets = Arrays.stream(DungeonWorldItemsAssets.values())
            .filter(s -> s.getItemId() == id)
            .findFirst()
            .orElse(null);
        return worldItemsAssets != null ? worldItemsAssets.name() : null;
    }

    public static DungeonWorldItemsAssets getWorldItemAssetByIndex(int index)
    {
        DungeonWorldItemsAssets worldItemAssets = Arrays.stream(DungeonWorldItemsAssets.values())
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
