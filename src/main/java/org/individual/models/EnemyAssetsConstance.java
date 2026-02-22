package org.individual.models;

import org.imageAssets.models.EnemyImagesAssets;
import org.imageAssets.models.ImageModel;

import java.util.List;
import java.util.Map;

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

    public static Map<MovingDirection, Map<Integer, ImageModel>> colorMosterAssetsMap = Map.of(
        MovingDirection.DOWN, Map.of(
            1, EnemyImagesAssets.COLOR_MONSTER_FRONT
        ),
        MovingDirection.UP, Map.of(
            1, EnemyImagesAssets.COLOR_MONSTER_BACK
        ),
        MovingDirection.LEFT, Map.of(
            1, EnemyImagesAssets.COLOR_MONSTER_LEFT
        ),
        MovingDirection.RIGHT, Map.of(
            1, EnemyImagesAssets.COLOR_MONSTER_RIGHT
        )
    );
    public static Map<MovingDirection, Map<Integer, ImageModel>> ghostMosterAssetsMap = Map.of(
        MovingDirection.LEFT, Map.of(
            1, EnemyImagesAssets.GHOST_MONSTER_LEFT
        ),
        MovingDirection.RIGHT, Map.of(
            1, EnemyImagesAssets.GHOST_MONSTER_RIGHT
        ),
        MovingDirection.UP, Map.of(
            1, EnemyImagesAssets.GHOST_MONSTER_UP
        ),
        MovingDirection.DOWN, Map.of(
            1, EnemyImagesAssets.GHOST_MONSTER_DOWN
        )
    );
    public static Map<MovingDirection, Map<Integer, ImageModel>> ghostMosterColisionAssetsMap = Map.of(
        MovingDirection.RIGHT, Map.of(
        1, EnemyImagesAssets.GHOST_MONSTER_PANIC_RIGHT
        ),
        MovingDirection.LEFT, Map.of(
        1, EnemyImagesAssets.GHOST_MONSTER_PANIC_LEFT
        )
    );
    public static Map<MovingDirection, Map<Integer, ImageModel>> ghostMosterUnderAttackAssetsMap = Map.of(
        MovingDirection.RIGHT, Map.of(
        1, EnemyImagesAssets.GHOST_MONSTER_PANIC_UNDER_ATTACK_RIGHT_ONE,
        2, EnemyImagesAssets.GHOST_MONSTER_PANIC_UNDER_ATTACK_RIGHT_TWO
        ),
        MovingDirection.LEFT, Map.of(
        1, EnemyImagesAssets.GHOST_MONSTER_PANIC_UNDER_ATTACK_LEFT_ONE,
        2, EnemyImagesAssets.GHOST_MONSTER_PANIC_UNDER_ATTACK_LEFT_TWO
        )
    );
    public static Map<MovingDirection, Map<Integer, ImageModel>> spiderMosterAssetsMap = Map.of(
        MovingDirection.DOWN, Map.of(
        1 , EnemyImagesAssets.SPIDER_FRONT_MOVE_ONE,
        2 , EnemyImagesAssets.SPIDER_FRONT_MOVE_TWO
        ),
        MovingDirection.UP, Map.of (
        1 , EnemyImagesAssets.SPIDER_BACK_MOVE_ONE,
        2 , EnemyImagesAssets.SPIDER_BACK_MOVE_TWO
        ),
        MovingDirection.LEFT, Map.of(
        1 , EnemyImagesAssets.SPIDER_LEFT_MOVE_ONE,
        2 , EnemyImagesAssets.SPIDER_LEFT_MOVE_TWO
        ),
        MovingDirection.RIGHT, Map.of(
        1 , EnemyImagesAssets.SPIDER_RIGHT_MOVE_ONE,
        2 , EnemyImagesAssets.SPIDER_RIGHT_MOVE_TWO
        )
    );

    public static Map<MovingDirection, Map<Integer, ImageModel>> spiderMosterColisionAssetsMap = Map.of(
        MovingDirection.UP, Map.of(
        1, EnemyImagesAssets.SPIDER_BACK_VIEW
        ),
        MovingDirection.DOWN, Map.of(
        1, EnemyImagesAssets.SPIDER_FRONT_VIEW
        ),
        MovingDirection.RIGHT, Map.of(
        1, EnemyImagesAssets.SPIDER_RIGHT_VIEW
        ),
        MovingDirection.LEFT, Map.of(
        1, EnemyImagesAssets.SPIDER_LEFT_VIEW
        )
    );

    public static Map<MovingDirection, Map<Integer, ImageModel>> spiderMosterUnderAttackAssetsMap = Map.of(
        MovingDirection.UP, Map.of(
        1, EnemyImagesAssets.SPIDER_BACK_VIEW,
        2, EnemyImagesAssets.SPIDER_BACK_VIEW_UNDER_ATTACK
        ),
        MovingDirection.DOWN, Map.of(
        1, EnemyImagesAssets.SPIDER_FRONT_VIEW,
        2, EnemyImagesAssets.SPIDER_FRONT_VIEW_UNDER_ATTACK
        ),
        MovingDirection.RIGHT, Map.of(
        1, EnemyImagesAssets.SPIDER_RIGHT_VIEW,
        2, EnemyImagesAssets.SPIDER_RIGHT_VIEW_UNDER_ATTACK
        ),
        MovingDirection.LEFT, Map.of(
        1, EnemyImagesAssets.SPIDER_LEFT_VIEW,
        2, EnemyImagesAssets.SPIDER_LEFT_VIEW_UNDER_ATTACK
        )
    );
}