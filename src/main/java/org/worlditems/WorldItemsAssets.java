package org.worlditems;


import org.worlditems.models.WorldItemAssetsModel;
import org.worlditems.models.WorldItemDuplicatedBuilder;

import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.List;
import java.util.ArrayList;

import static org.game.GamePanel.tileSize;
import static org.helpers.ToolsHelper.getScaledImageFromAssets;

public enum WorldItemsAssets
{
    GOLD_KEY(1, WorldItemTypes.KEY.name(), false, tileSize * 4, tileSize * 4, 0,
        Map.of(
            1, new WorldItemAssetsModel(Objects.requireNonNull(getScaledImageFromAssets("/worlditems/rusted-key-profile-right.png")), null),
            2, new WorldItemAssetsModel(Objects.requireNonNull(getScaledImageFromAssets("/worlditems/rusted-key-spin-one.png")), null),
            3, new WorldItemAssetsModel(Objects.requireNonNull(getScaledImageFromAssets("/worlditems/rusted-key-spin-back.png")), null),
            4, new WorldItemAssetsModel(Objects.requireNonNull(getScaledImageFromAssets("/worlditems/rusted-key-spin-two.png")), null),
            5, new WorldItemAssetsModel(Objects.requireNonNull(getScaledImageFromAssets("/worlditems/rusted-key-profile-left.png")), null),
            6, new WorldItemAssetsModel(Objects.requireNonNull(getScaledImageFromAssets("/worlditems/rusted-key-spin-three.png")), null),
            7, new WorldItemAssetsModel(Objects.requireNonNull(getScaledImageFromAssets("/worlditems/rusted-key-spin-front.png")), null),
            8, new WorldItemAssetsModel(Objects.requireNonNull(getScaledImageFromAssets("/worlditems/rusted-key-spin-four.png")), null)
        ), List.of()),
    RUBY(2, WorldItemTypes.HEALTH_RESTORATION.name(), false, tileSize * 20, tileSize * 2, 0,
        Map.of(
            1, new WorldItemAssetsModel(Objects.requireNonNull(getScaledImageFromAssets("/worlditems/ruby-front.png")), null),
            2, new WorldItemAssetsModel(Objects.requireNonNull(getScaledImageFromAssets("/worlditems/ruby-spin-left.png")), null),
            3, new WorldItemAssetsModel(Objects.requireNonNull(getScaledImageFromAssets("/worlditems/ruby-side.png")), null),
            4, new WorldItemAssetsModel(Objects.requireNonNull(getScaledImageFromAssets("/worlditems/ruby-spin-right.png")), null)
        ), List.of()),
    CHEST(3, WorldItemTypes.CHEST.name(), true, tileSize * 29, tileSize * 26, WorldItemsAssets.GOLD_KEY.getItemId(),
        Map.of(
            1, new WorldItemAssetsModel(Objects.requireNonNull(getScaledImageFromAssets("/worlditems/open-chest.png")), "open-chest"),
            2, new WorldItemAssetsModel(Objects.requireNonNull(getScaledImageFromAssets("/worlditems/closed-chest.png")), "closed-chest")
        ), List.of()),
    DOOR(4, WorldItemTypes.DOOR.name(), true, tileSize * 26, tileSize * 4, WorldItemsAssets.GOLD_KEY.getItemId(),
        Map.of(
            1, new WorldItemAssetsModel(Objects.requireNonNull(getScaledImageFromAssets("/worlditems/open-door.png")), null),
            2, new WorldItemAssetsModel(Objects.requireNonNull(getScaledImageFromAssets("/worlditems/closed-door.png")), "closed-door")
        ), List.of()),
    BIG_DOOR(5, WorldItemTypes.DOOR.name(), true, tileSize * 20, tileSize * 4, WorldItemsAssets.GOLD_KEY.getItemId(),
        Map.of(
            1, new WorldItemAssetsModel(Objects.requireNonNull(getScaledImageFromAssets("/worlditems/open-big-door.png")), null),
            2, new WorldItemAssetsModel(Objects.requireNonNull(getScaledImageFromAssets("/worlditems/closed-big-door.png")), "closed-big-door")
        ), List.of()),
    OLD_TREE_ITEM(6, WorldItemTypes.VEGETATION.name(), false, tileSize * 33, tileSize * 7, 0,
        Map.of(
            1, new WorldItemAssetsModel(Objects.requireNonNull(getScaledImageFromAssets("/tiles/old-tree-no-background.png")), null)
        ),
        List.of( // set this duplicate item list, so I do not need to keep adding to this enum class for the same item as a tree to build a forest
            // used a builder POJO class so if needed new options can be used for duplicates like assets map.
            new WorldItemDuplicatedBuilder.Builder().setItemId(7).setPositionX(tileSize * 33).setPositionY(tileSize * 7).build(),
            new WorldItemDuplicatedBuilder.Builder().setItemId(8).setPositionX(tileSize * 34).setPositionY(tileSize * 7).build(),
            new WorldItemDuplicatedBuilder.Builder().setItemId(9).setPositionX(tileSize * 33).setPositionY(tileSize * 6).build(),
            new WorldItemDuplicatedBuilder.Builder().setPositionX(tileSize * 34).setPositionY(tileSize * 6).build(),
            new WorldItemDuplicatedBuilder.Builder().setPositionX(tileSize * 33).setPositionY(tileSize * 5).build(),
            new WorldItemDuplicatedBuilder.Builder().setPositionX(tileSize * 34).setPositionY(tileSize * 5).build(),
            new WorldItemDuplicatedBuilder.Builder().setPositionX(tileSize * 35).setPositionY(tileSize * 5).build(),
            new WorldItemDuplicatedBuilder.Builder().setPositionX(tileSize * 35).setPositionY(tileSize * 4).build(),
            new WorldItemDuplicatedBuilder.Builder().setPositionX(tileSize * 38).setPositionY(tileSize * 10).build(),
            new WorldItemDuplicatedBuilder.Builder().setPositionX(tileSize * 45).setPositionY(tileSize * 5).build()
        )
    ),
    TOP_OF_TREE(10, WorldItemTypes.VEGETATION.name(), false, tileSize * 25, tileSize * 23, 0,
        Map.of(
            1, new WorldItemAssetsModel(Objects.requireNonNull(getScaledImageFromAssets("/tiles/impenetrable-tree-top-of-tree.png")), null)
        ),
        List.of(
                new WorldItemDuplicatedBuilder.Builder().setPositionX(tileSize * 25).setPositionY(tileSize * 23).build(),
                new WorldItemDuplicatedBuilder.Builder().setPositionX(tileSize * 26).setPositionY(tileSize * 23).build(),
                new WorldItemDuplicatedBuilder.Builder().setPositionX(tileSize * 27).setPositionY(tileSize * 23).build(),
                new WorldItemDuplicatedBuilder.Builder().setPositionX(tileSize * 28).setPositionY(tileSize * 23).build(),
                new WorldItemDuplicatedBuilder.Builder().setPositionX(tileSize * 29).setPositionY(tileSize * 23).build(),
                new WorldItemDuplicatedBuilder.Builder().setPositionX(tileSize * 30).setPositionY(tileSize * 23).build(),
                new WorldItemDuplicatedBuilder.Builder().setPositionX(tileSize * 31).setPositionY(tileSize * 23).build(),
                new WorldItemDuplicatedBuilder.Builder().setPositionX(tileSize * 32).setPositionY(tileSize * 23).build(),
                new WorldItemDuplicatedBuilder.Builder().setPositionX(tileSize * 33).setPositionY(tileSize * 23).build(),
                new WorldItemDuplicatedBuilder.Builder().setPositionX(tileSize * 25).setPositionY(tileSize * 29).build(),
                new WorldItemDuplicatedBuilder.Builder().setPositionX(tileSize * 26).setPositionY(tileSize * 29).build(),
                new WorldItemDuplicatedBuilder.Builder().setPositionX(tileSize * 27).setPositionY(tileSize * 29).build(),
                new WorldItemDuplicatedBuilder.Builder().setPositionX(tileSize * 28).setPositionY(tileSize * 29).build(),
                new WorldItemDuplicatedBuilder.Builder().setPositionX(tileSize * 29).setPositionY(tileSize * 29).build()
        ));



    private final int itemId;
    private final String itemType;
    private final int defaultPositionX;
    private final int defaultPositionY;
    private final int dependencyOnAssetId;
    private final Map<Integer, WorldItemAssetsModel> itemAssetsMap;
    private final boolean solidStopOnCollisionWithPlayer;
    private final List<WorldItemDuplicatedBuilder> positionOnMapForDuplicatedItemList;

    WorldItemsAssets(
        int itemId,
        String itemType,
        boolean solidStopOnCollisionWithPlayer,
        int defaultPositionX,
        int defaultPositionY,
        int dependencyOnAssetId,
        Map<Integer, WorldItemAssetsModel> itemAssetsMap,
        List<WorldItemDuplicatedBuilder> positionOnMapForDuplicatedItemList
    )
    {
        this.itemId = itemId;
        this.itemType = itemType;
        this.solidStopOnCollisionWithPlayer = solidStopOnCollisionWithPlayer;
        this.defaultPositionX = defaultPositionX;
        this.defaultPositionY = defaultPositionY;
        this.dependencyOnAssetId = dependencyOnAssetId;
        this.itemAssetsMap = itemAssetsMap;
        this.positionOnMapForDuplicatedItemList = positionOnMapForDuplicatedItemList;
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

    public Boolean getSolidStopOnCollisionWithPlayer()
    {
        return this.solidStopOnCollisionWithPlayer;
    }

    public int getDependencyOnAssetId()
    {
        return this.dependencyOnAssetId;
    }

    public List<WorldItemDuplicatedBuilder> getPositionOnMapForDuplicatedItemList()
    {
        return new ArrayList<>(this.positionOnMapForDuplicatedItemList);
    }

    public static String getWorldItemAssetNameById(int id)
    {
        WorldItemsAssets worldItemsAssets = Arrays.stream(WorldItemsAssets.values())
            .filter(s -> s.getItemId() == id)
            .findFirst()
            .orElse(null);
        return worldItemsAssets != null ? worldItemsAssets.name() : null;
    }
}
