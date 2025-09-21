package org.worlditems;

import java.awt.image.BufferedImage;
import java.util.Map;
import java.util.Objects;

import static org.game.GamePanel.tileSize;
import static org.helpers.ToolsHelper.getImageFromAssets;

public enum WorldItemsAssets
{
    KEY(1, WorldItemTypes.KEY.name(), tileSize * 4, tileSize * 4,
        Map.of(
            1, Objects.requireNonNull(getImageFromAssets("/worlditems/rusted-key-profile-right.png")),
            2, Objects.requireNonNull(getImageFromAssets("/worlditems/rusted-key-spin-one.png")),
            3, Objects.requireNonNull(getImageFromAssets("/worlditems/rusted-key-spin-back.png")),
            4, Objects.requireNonNull(getImageFromAssets("/worlditems/rusted-key-spin-two.png")),
            5, Objects.requireNonNull(getImageFromAssets("/worlditems/rusted-key-profile-left.png")),
            6, Objects.requireNonNull(getImageFromAssets("/worlditems/rusted-key-spin-three.png")),
            7, Objects.requireNonNull(getImageFromAssets("/worlditems/rusted-key-spin-front.png")),
            8, Objects.requireNonNull(getImageFromAssets("/worlditems/rusted-key-spin-four.png"))
        )),
    RUBY(1, WorldItemTypes.QUEST.name(), tileSize * 5, tileSize * 5,
        Map.of(
            1, Objects.requireNonNull(getImageFromAssets("/worlditems/ruby-front.png")),
            2, Objects.requireNonNull(getImageFromAssets("/worlditems/ruby-spin-left.png")),
            3, Objects.requireNonNull(getImageFromAssets("/worlditems/ruby-side.png")),
            4, Objects.requireNonNull(getImageFromAssets("/worlditems/ruby-spin-right.png"))
        )),
    CHEST(1, WorldItemTypes.CHEST_DOOR.name(), tileSize * 2, tileSize * 6,
        Map.of(
                1, Objects.requireNonNull(getImageFromAssets("/worlditems/open-chest.png")),
                2, Objects.requireNonNull(getImageFromAssets("/worlditems/closed-chest.png"))
        ));

    private final int itemId;
    private final String itemType;
    final private int defaultPositionX;
    final private int defaultPositionY;
    final private Map<Integer, BufferedImage> itemsAssetsMap;

    WorldItemsAssets(
        int itemId,
        String itemType,
        int defaultPositionX,
        int defaultPositionY,
        Map<Integer, BufferedImage> itemsAssetsMap
    )
    {
        this.itemId = itemId;
        this.itemType = itemType;
        this.defaultPositionX = defaultPositionX;
        this.defaultPositionY = defaultPositionY;
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
}
