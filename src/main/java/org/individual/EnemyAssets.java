package org.individual;

import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static org.helpers.ToolsHelper.getScaledImageFromAssets;
import static org.individual.Enemy.COLLISION_ENEMY_ASSET_KEY_PREFIX;

public enum EnemyAssets
{

    GHOST_MONSTER(1, 150,
        List.of(
            MovingDirection.LEFT,
            MovingDirection.RIGHT
        ), 8, "/enemy/ghost/ghost-fly-left.png", EnemyAssetsMaps.ghostMosterAssetsMap, EnemyAssetsMaps.ghostMosterUnderAttackAssetsMap),
    COLOR_MONSTER(2, 250,
        List.of(
            MovingDirection.DOWN,
            MovingDirection.RIGHT,
            MovingDirection.UP,
            MovingDirection.LEFT
        ), 2, "/enemy/colormonster/color-monster-front.png", EnemyAssetsMaps.colorMosterAssetsMap, Map.of()),
    COLOR_MONSTER_SIDE_MOVING(3, 350,
        List.of(
            MovingDirection.DOWN,
            MovingDirection.RIGHT,
            MovingDirection.UP,
            MovingDirection.LEFT
        ), 2, "/enemy/colormonster/color-monster-left.png", EnemyAssetsMaps.colorMosterAssetsMap, Map.of()),
    SLOW_GHOST_MONSTER(4, 150,
        List.of(
            MovingDirection.RIGHT,
            MovingDirection.LEFT
        ), 4, "/enemy/ghost/ghost-fly-left.png", EnemyAssetsMaps.ghostMosterAssetsMap, EnemyAssetsMaps.ghostMosterUnderAttackAssetsMap),
    MOVING_RIGHT_COLOR_MONSTER_SIDE_MOVING(5, 250,
        List.of(
            MovingDirection.UP,
            MovingDirection.DOWN
        ), 2, "/enemy/colormonster/color-monster-left.png", EnemyAssetsMaps.colorMosterAssetsMap, Map.of()),
    MOVING_UP_COLOR_MONSTER(6, 50,
        List.of(
            MovingDirection.DOWN,
            MovingDirection.RIGHT,
            MovingDirection.DOWN,
            MovingDirection.RIGHT,
            MovingDirection.UP,
            MovingDirection.LEFT,
            MovingDirection.UP
        ), 2, "/enemy/colormonster/color-monster-front.png", EnemyAssetsMaps.colorMosterAssetsMap, Map.of());

    final private int enemyId;
    final private int maxDistanceAllowedToMove;
    final private List<MovingDirection> enamyMovingDirectionList;
    final private int speed;
    final private String enemyAssetPath;
    final private Map<String, BufferedImage> enemyAssetsMap;
    final private Map<String, Map<Integer, BufferedImage>> enemyUnderAttackAssetsMap;

    EnemyAssets(
            int enemyId,
            int maxDistanceAllowedToMove,
            List<MovingDirection> enamyMovingDirectionList,
            int speed,
            String enemyAssetPath,
            Map<String, BufferedImage> enemyAssetsMap,
            Map<String, Map<Integer, BufferedImage>> enemyUnderAttackAssetsMap
    )
    {
        this.enemyId = enemyId;
        this.maxDistanceAllowedToMove = maxDistanceAllowedToMove;
        this.enamyMovingDirectionList = enamyMovingDirectionList;
        this.speed = speed;
        this.enemyAssetPath = enemyAssetPath;
        this.enemyAssetsMap = enemyAssetsMap;
        this.enemyUnderAttackAssetsMap = enemyUnderAttackAssetsMap;
    }

    public int getEnemyId()
    {
        return this.enemyId;
    }

    public int getMaxDistanceAllowedToMove()
    {
        return this.maxDistanceAllowedToMove;
    }

    public int getSpeed()
    {
        return this.speed;
    }

    public String getEnemyAssetPath()
    {
        return this.enemyAssetPath;
    }

    public Map<String, BufferedImage> getEnemyAssetsMap()
    {
        return this.enemyAssetsMap;
    }

    public  Map<String, Map<Integer, BufferedImage>> getEnamyUnderAttackAssetsMap()
    {
        return this.enemyUnderAttackAssetsMap;
    }

    public List<MovingDirection> getEnamyMovingDirectionList()
    {
        return this.enamyMovingDirectionList;
    }

    public static EnemyAssets getEnemyAssetByIndex(int index)
    {
        EnemyAssets enemyAssets = Arrays.stream(EnemyAssets.values())
                .filter(s -> s.getEnemyId() == index)
                .findFirst()
                .orElse(null);
        if (enemyAssets == null)
        {
//            throw new RuntimeException("Index not found in world item assets!");
            return null;
        }
        return enemyAssets;
    }

    private static class EnemyAssetsMaps
    {
        public static Map<String, BufferedImage> colorMosterAssetsMap = Map.of(
            MovingDirection.DOWN.getValue(), Objects.requireNonNull(getScaledImageFromAssets("/enemy/colormonster/color-monster-front.png")),
            MovingDirection.UP.getValue(), Objects.requireNonNull(getScaledImageFromAssets("/enemy/colormonster/color-monster-back.png")),
            MovingDirection.LEFT.getValue(), Objects.requireNonNull(getScaledImageFromAssets("/enemy/colormonster/color-monster-left.png")),
            MovingDirection.RIGHT.getValue(), Objects.requireNonNull(getScaledImageFromAssets("/enemy/colormonster/color-monster-right.png"))
        );
        public static Map<String, BufferedImage> ghostMosterAssetsMap = Map.of(
            MovingDirection.LEFT.getValue(), Objects.requireNonNull(getScaledImageFromAssets("/enemy/ghost/ghost-fly-left.png")),
            MovingDirection.RIGHT.getValue(), Objects.requireNonNull(getScaledImageFromAssets("/enemy/ghost/ghost-fly-right.png")),
            COLLISION_ENEMY_ASSET_KEY_PREFIX + MovingDirection.RIGHT.getValue(), Objects.requireNonNull(getScaledImageFromAssets("/enemy/ghost/ghost-panic-right.png")),
            COLLISION_ENEMY_ASSET_KEY_PREFIX + MovingDirection.LEFT.getValue(), Objects.requireNonNull(getScaledImageFromAssets("/enemy/ghost/ghost-panic-left.png"))
        );
        public static Map<String, Map<Integer, BufferedImage>> ghostMosterUnderAttackAssetsMap = Map.of(
            MovingDirection.RIGHT.getValue(), Map.of(
                1, Objects.requireNonNull(getScaledImageFromAssets("/enemy/ghost/ghost-panic-right.png")),
                2, Objects.requireNonNull(getScaledImageFromAssets("/enemy/ghost/ghost-panic-right-under-attack.png"))
            ),
            MovingDirection.LEFT.getValue(), Map.of(
                1, Objects.requireNonNull(getScaledImageFromAssets("/enemy/ghost/ghost-panic-left.png")),
                2, Objects.requireNonNull(getScaledImageFromAssets("/enemy/ghost/ghost-panic-left-under-attack.png"))
            )
        );
    }
}
