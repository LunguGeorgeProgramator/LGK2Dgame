package org.individual;

import java.awt.image.BufferedImage;
import java.util.Map;
import java.util.Objects;

import static org.game.GamePanel.tileSize;
import static org.helpers.ToolsHelper.getScaledImageFromAssets;
import static org.individual.Enemy.COLLISION_ENEMY_ASSET_KEY_PREFIX;

public enum EnemyAssets
{

    GHOST_MONSTER(1, tileSize * 6, tileSize * 6, 400, MovingDirection.LEFT.getValue(), 8, "/enemy/ghost/ghost-fly-left.png",
        Map.of(
            MovingDirection.LEFT.getValue(), Objects.requireNonNull(getScaledImageFromAssets("/enemy/ghost/ghost-fly-left.png")),
            MovingDirection.RIGHT.getValue(), Objects.requireNonNull(getScaledImageFromAssets("/enemy/ghost/ghost-fly-right.png")),
            COLLISION_ENEMY_ASSET_KEY_PREFIX + MovingDirection.RIGHT.getValue(), Objects.requireNonNull(getScaledImageFromAssets("/enemy/ghost/ghost-panic-right.png")),
            COLLISION_ENEMY_ASSET_KEY_PREFIX + MovingDirection.LEFT.getValue(), Objects.requireNonNull(getScaledImageFromAssets("/enemy/ghost/ghost-panic-left.png"))
        ),
        Map.of(
            MovingDirection.RIGHT.getValue(), Map.of(
            1, Objects.requireNonNull(getScaledImageFromAssets("/enemy/ghost/ghost-panic-right.png")),
            2, Objects.requireNonNull(getScaledImageFromAssets("/enemy/ghost/ghost-panic-right-under-attack.png"))
            ),
            MovingDirection.LEFT.getValue(), Map.of(
            1, Objects.requireNonNull(getScaledImageFromAssets("/enemy/ghost/ghost-panic-left.png")),
            2, Objects.requireNonNull(getScaledImageFromAssets("/enemy/ghost/ghost-panic-left-under-attack.png"))
            )
        )),
    COLOR_MONSTER(2, tileSize * 10, tileSize * 10, 200, MovingDirection.LEFT.getValue(), 2, "/enemy/color-monster.png", Map.of(), Map.of()),
    COLOR_MONSTER2(3, tileSize * 11, tileSize * 11, 200, MovingDirection.UP.getValue(), 2, "/enemy/color-monster.png", Map.of(), Map.of()),
    COLOR_MONSTER3(4, tileSize * 12, tileSize * 12, 300, MovingDirection.RIGHT.getValue(), 1, "/enemy/color-monster.png", Map.of(), Map.of()),
    COLOR_MONSTER4(5, tileSize * 10, tileSize * 5, 400, MovingDirection.DOWN.getValue(), 1, "/enemy/color-monster.png", Map.of(), Map.of()),
    COLOR_MONSTER5(6, tileSize * 50, tileSize * 18, 200, MovingDirection.UP.getValue(), 2, "/enemy/color-monster.png", Map.of(), Map.of()),
    COLOR_MONSTER6(7, tileSize * 60, tileSize * 15, 200, MovingDirection.DOWN.getValue(), 1, "/enemy/color-monster.png", Map.of(), Map.of()),
    COLOR_MONSTER7(8, tileSize * 25, tileSize * 10, 200, MovingDirection.DOWN.getValue(), 1, "/enemy/color-monster.png", Map.of(), Map.of()),
    COLOR_MONSTER8(9, tileSize * 30, tileSize * 12, 200, MovingDirection.UP.getValue(), 1, "/enemy/color-monster.png", Map.of(), Map.of()),
    COLOR_MONSTER9(10, tileSize * 35, tileSize * 10, 200, MovingDirection.DOWN.getValue(), 1, "/enemy/color-monster.png", Map.of(), Map.of()),
    COLOR_MONSTER10(11, tileSize * 40, tileSize * 10, 200, MovingDirection.DOWN.getValue(), 2, "/enemy/color-monster.png", Map.of(), Map.of()),
    COLOR_MONSTER11(12, tileSize * 25, tileSize * 10, 200, MovingDirection.DOWN.getValue(), 1, "/enemy/color-monster.png", Map.of(), Map.of());

    final private int enemyId;
    final private int defaultPositionX;
    final private int defaultPositionY;
    final private int maxDistanceAllowedToMove;
    final private String direction;
    final private int speed;
    final private String enemyAssetPath;
    final private Map<String, BufferedImage> enemyAssetsMap;
    final private Map<String, Map<Integer, BufferedImage>> enemyUnderAttackAssetsMap;

    EnemyAssets(
            int enemyId,
            int defaultPositionX,
            int defaultPositionY,
            int maxDistanceAllowedToMove,
            String direction,
            int speed,
            String enemyAssetPath,
            Map<String, BufferedImage> enemyAssetsMap,
            Map<String, Map<Integer, BufferedImage>> enemyUnderAttackAssetsMap
    )
    {
        this.enemyId = enemyId;
        this.defaultPositionX = defaultPositionX;
        this.defaultPositionY = defaultPositionY;
        this.maxDistanceAllowedToMove = maxDistanceAllowedToMove;
        this.direction = direction;
        this.speed = speed;
        this.enemyAssetPath = enemyAssetPath;
        this.enemyAssetsMap = enemyAssetsMap;
        this.enemyUnderAttackAssetsMap = enemyUnderAttackAssetsMap;
    }

    public int getEnemyId()
    {
        return this.enemyId;
    }

    public int getDefaultPositionX()
    {
        return this.defaultPositionX;
    }

    public int getDefaultPositionY()
    {
        return this.defaultPositionY;
    }

    public int getMaxDistanceAllowedToMove()
    {
        return this.maxDistanceAllowedToMove;
    }

    public String getDirection()
    {
        return this.direction;
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
}
