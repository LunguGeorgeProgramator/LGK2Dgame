package org.individual.models;

import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static org.helpers.ToolsHelper.getScaledImageFromAssets;

public class EnemyAssetsConstance
{
    public static List<MovingDirection> upDown = List.of(MovingDirection.UP, MovingDirection.DOWN);
    public static List<MovingDirection> downUp = List.of(MovingDirection.DOWN, MovingDirection.UP);
    public static List<MovingDirection> leftRight = List.of(MovingDirection.LEFT, MovingDirection.RIGHT);
    public static List<MovingDirection> rightLeft = List.of(MovingDirection.LEFT, MovingDirection.RIGHT);
    public static List<MovingDirection> square = List.of(MovingDirection.DOWN, MovingDirection.RIGHT, MovingDirection.UP, MovingDirection.LEFT);
    public static List<MovingDirection> invertedSquare = List.of(MovingDirection.UP, MovingDirection.LEFT, MovingDirection.DOWN, MovingDirection.RIGHT);
    public static List<MovingDirection> snakeMovement = List.of(
            MovingDirection.DOWN,
            MovingDirection.RIGHT,
            MovingDirection.DOWN,
            MovingDirection.RIGHT,
            MovingDirection.UP,
            MovingDirection.LEFT,
            MovingDirection.UP
    );

    public static Map<MovingDirection, Map<Integer, BufferedImage>> colorMosterAssetsMap = Map.of(
        MovingDirection.DOWN, Map.of(
            1, Objects.requireNonNull(getScaledImageFromAssets("/enemy/colormonster/color-monster-front.png"))
        ),
        MovingDirection.UP, Map.of(
            1, Objects.requireNonNull(getScaledImageFromAssets("/enemy/colormonster/color-monster-back.png"))
        ),
        MovingDirection.LEFT, Map.of(
            1, Objects.requireNonNull(getScaledImageFromAssets("/enemy/colormonster/color-monster-left.png"))
        ),
        MovingDirection.RIGHT, Map.of(
            1, Objects.requireNonNull(getScaledImageFromAssets("/enemy/colormonster/color-monster-right.png"))
        )
    );
    public static Map<MovingDirection, Map<Integer, BufferedImage>> ghostMosterAssetsMap = Map.of(
        MovingDirection.LEFT, Map.of(
            1, Objects.requireNonNull(getScaledImageFromAssets("/enemy/ghost/ghost-fly-left.png"))
        ),
        MovingDirection.RIGHT, Map.of(
            1, Objects.requireNonNull(getScaledImageFromAssets("/enemy/ghost/ghost-fly-right.png"))
        )
    );
    public static Map<MovingDirection, Map<Integer, BufferedImage>> ghostMosterColisionAssetsMap = Map.of(
        MovingDirection.RIGHT, Map.of(
        1, Objects.requireNonNull(getScaledImageFromAssets("/enemy/ghost/ghost-panic-right.png"))
        ),
        MovingDirection.LEFT, Map.of(
        1, Objects.requireNonNull(getScaledImageFromAssets("/enemy/ghost/ghost-panic-left.png"))
        )
    );
    public static Map<MovingDirection, Map<Integer, BufferedImage>> ghostMosterUnderAttackAssetsMap = Map.of(
        MovingDirection.RIGHT, Map.of(
        1, Objects.requireNonNull(getScaledImageFromAssets("/enemy/ghost/ghost-panic-right.png")),
        2, Objects.requireNonNull(getScaledImageFromAssets("/enemy/ghost/ghost-panic-right-under-attack.png"))
        ),
        MovingDirection.LEFT, Map.of(
        1, Objects.requireNonNull(getScaledImageFromAssets("/enemy/ghost/ghost-panic-left.png")),
        2, Objects.requireNonNull(getScaledImageFromAssets("/enemy/ghost/ghost-panic-left-under-attack.png"))
        )
    );
    public static Map<MovingDirection, Map<Integer, BufferedImage>> spiderMosterAssetsMap = Map.of(
        MovingDirection.DOWN, Map.of(
        1 , Objects.requireNonNull(getScaledImageFromAssets("/enemy/spider/spider-front-view-moving-one.png")),
        2 , Objects.requireNonNull(getScaledImageFromAssets("/enemy/spider/spider-front-view-moving-two.png"))
        ),
        MovingDirection.UP, Map.of (
        1 , Objects.requireNonNull(getScaledImageFromAssets("/enemy/spider/spider-back-view-moving-one.png")),
        2 , Objects.requireNonNull(getScaledImageFromAssets("/enemy/spider/spider-back-view-moving-two.png"))
        ),
        MovingDirection.LEFT, Map.of(
        1 , Objects.requireNonNull(getScaledImageFromAssets("/enemy/spider/spider-left-view-moving-one.png")),
        2 , Objects.requireNonNull(getScaledImageFromAssets("/enemy/spider/spider-left-view-moving-two.png"))
        ),
        MovingDirection.RIGHT, Map.of(
        1 , Objects.requireNonNull(getScaledImageFromAssets("/enemy/spider/spider-right-view-moving-one.png")),
        2 , Objects.requireNonNull(getScaledImageFromAssets("/enemy/spider/spider-right-view-moving-two.png"))
        )
    );

    public static Map<MovingDirection, Map<Integer, BufferedImage>> spiderMosterColisionAssetsMap = Map.of(
        MovingDirection.UP, Map.of(
        1, Objects.requireNonNull(getScaledImageFromAssets("/enemy/spider/spider-back-view.png"))
        ),
        MovingDirection.DOWN, Map.of(
        1, Objects.requireNonNull(getScaledImageFromAssets("/enemy/spider/spider-front-view.png"))
        ),
        MovingDirection.RIGHT, Map.of(
        1, Objects.requireNonNull(getScaledImageFromAssets("/enemy/spider/spider-right-view.png"))
        ),
        MovingDirection.LEFT, Map.of(
        1, Objects.requireNonNull(getScaledImageFromAssets("/enemy/spider/spider-left-view.png"))
        )
    );

    public static Map<MovingDirection, Map<Integer, BufferedImage>> spiderMosterUnderAttackAssetsMap = Map.of(
        MovingDirection.UP, Map.of(
        1, Objects.requireNonNull(getScaledImageFromAssets("/enemy/spider/spider-back-view.png")),
        2, Objects.requireNonNull(getScaledImageFromAssets("/enemy/spider/spider-back-view-under-attack.png"))
        ),
        MovingDirection.DOWN, Map.of(
        1, Objects.requireNonNull(getScaledImageFromAssets("/enemy/spider/spider-front-view.png")),
        2, Objects.requireNonNull(getScaledImageFromAssets("/enemy/spider/spider-front-view-under-attack.png"))
        ),
        MovingDirection.RIGHT, Map.of(
        1, Objects.requireNonNull(getScaledImageFromAssets("/enemy/spider/spider-right-view.png")),
        2, Objects.requireNonNull(getScaledImageFromAssets("/enemy/spider/spider-right-view-under-attack.png"))
        ),
        MovingDirection.LEFT, Map.of(
        1, Objects.requireNonNull(getScaledImageFromAssets("/enemy/spider/spider-left-view.png")),
        2, Objects.requireNonNull(getScaledImageFromAssets("/enemy/spider/spider-left-view-under-attack.png"))
        )
    );
}