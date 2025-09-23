package org.worlditems;

import org.world.WorldAssets;

import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;

import static org.game.GamePanel.tileSize;
import static org.helpers.ToolsHelper.getScaledImageFromAssets;

public enum WorldItemsAssets
{
    GOLD_KEY(1, WorldItemTypes.KEY.name(), false, tileSize * 4, tileSize * 4, 0,
        Map.of(
            1, Objects.requireNonNull(getScaledImageFromAssets("/worlditems/rusted-key-profile-right.png")),
            2, Objects.requireNonNull(getScaledImageFromAssets("/worlditems/rusted-key-spin-one.png")),
            3, Objects.requireNonNull(getScaledImageFromAssets("/worlditems/rusted-key-spin-back.png")),
            4, Objects.requireNonNull(getScaledImageFromAssets("/worlditems/rusted-key-spin-two.png")),
            5, Objects.requireNonNull(getScaledImageFromAssets("/worlditems/rusted-key-profile-left.png")),
            6, Objects.requireNonNull(getScaledImageFromAssets("/worlditems/rusted-key-spin-three.png")),
            7, Objects.requireNonNull(getScaledImageFromAssets("/worlditems/rusted-key-spin-front.png")),
            8, Objects.requireNonNull(getScaledImageFromAssets("/worlditems/rusted-key-spin-four.png"))
        )),
    RUBY(2, WorldItemTypes.QUEST.name(), false, tileSize * 20, tileSize * 2, 0,
        Map.of(
            1, Objects.requireNonNull(getScaledImageFromAssets("/worlditems/ruby-front.png")),
            2, Objects.requireNonNull(getScaledImageFromAssets("/worlditems/ruby-spin-left.png")),
            3, Objects.requireNonNull(getScaledImageFromAssets("/worlditems/ruby-side.png")),
            4, Objects.requireNonNull(getScaledImageFromAssets("/worlditems/ruby-spin-right.png"))
        )),
    CHEST(3, WorldItemTypes.CHEST.name(), true, tileSize * 2, tileSize * 6, 0,
        Map.of(
            1, Objects.requireNonNull(getScaledImageFromAssets("/worlditems/open-chest.png")),
            2, Objects.requireNonNull(getScaledImageFromAssets("/worlditems/closed-chest.png"))
        )),
    DOOR(4, WorldItemTypes.DOOR.name(), true, tileSize * 20, tileSize * 4, WorldItemsAssets.GOLD_KEY.getItemId(),
        Map.of(
            1, Objects.requireNonNull(getScaledImageFromAssets("/worlditems/open-door.png")),
            2, Objects.requireNonNull(getScaledImageFromAssets("/worlditems/closed-door.png"))
        )),
    DOOR_2(4, WorldItemTypes.DOOR.name(), true, tileSize * 25, tileSize * 4, WorldItemsAssets.GOLD_KEY.getItemId(),
        Map.of(
            1, Objects.requireNonNull(getScaledImageFromAssets("/worlditems/open-door.png")),
            2, Objects.requireNonNull(getScaledImageFromAssets("/worlditems/closed-door.png"))
        ));

    private final int itemId;
    private final String itemType;
    final private int defaultPositionX;
    final private int defaultPositionY;
    private final int dependencyOnAssetId;
    final private Map<Integer, BufferedImage> itemsAssetsMap;
    final private boolean solidStopOnCollisionWithPlayer;

    WorldItemsAssets(
            int itemId,
            String itemType,
            boolean solidStopOnCollisionWithPlayer,
            int defaultPositionX,
            int defaultPositionY,
            int dependencyOnAssetId,
            Map<Integer, BufferedImage> itemsAssetsMap
    )
    {
        this.itemId = itemId;
        this.itemType = itemType;
        this.solidStopOnCollisionWithPlayer = solidStopOnCollisionWithPlayer;
        this.defaultPositionX = defaultPositionX;
        this.defaultPositionY = defaultPositionY;
        this.dependencyOnAssetId = dependencyOnAssetId;
        this.itemsAssetsMap = itemsAssetsMap;
    }

    public int getItemId()
    {
        return this.itemId;
    }

    public Map<Integer, BufferedImage> getItemsAssetsMap()
    {
        return this.itemsAssetsMap;
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

    public static String getWorldItemAssetNameById(int id)
    {
        WorldItemsAssets worldItemsAssets = Arrays.stream(WorldItemsAssets.values())
            .filter(s -> s.getItemId() == id)
            .findFirst()
            .orElse(null);
        return worldItemsAssets != null ? worldItemsAssets.name() : null;
    }
}
