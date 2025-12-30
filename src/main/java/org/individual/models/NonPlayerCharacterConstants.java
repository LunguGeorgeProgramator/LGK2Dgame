package org.individual.models;

import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static org.helpers.ToolsHelper.getScaledImageFromAssets;

public class NonPlayerCharacterConstants
{
    public static List<MovingDirection> standStill = List.of();
    public static List<MovingDirection> upDown = List.of(MovingDirection.UP, MovingDirection.DOWN);
    public static List<MovingDirection> downUp = List.of(MovingDirection.DOWN, MovingDirection.UP);
    public static List<MovingDirection> leftRight = List.of(MovingDirection.LEFT, MovingDirection.RIGHT);
    public static List<MovingDirection> rightLeft = List.of(MovingDirection.LEFT, MovingDirection.RIGHT);
    public static List<MovingDirection> square = List.of(MovingDirection.DOWN, MovingDirection.RIGHT, MovingDirection.UP, MovingDirection.LEFT);
    public static List<MovingDirection> invertedSquare = List.of(MovingDirection.UP, MovingDirection.LEFT, MovingDirection.DOWN, MovingDirection.RIGHT);

    public static Map<MovingDirection, Map<Integer, BufferedImage>> vandorSallerAssetsMap = Map.of(
        MovingDirection.STATIONARY, Map.of(
            1, Objects.requireNonNull(getScaledImageFromAssets("/npc/vendor-seller-npc.png"))
        )
    );
}
