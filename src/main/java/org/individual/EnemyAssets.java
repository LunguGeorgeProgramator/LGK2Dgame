package org.individual;

import java.awt.image.BufferedImage;
import java.util.Map;
import java.util.Objects;

import static org.game.GamePanel.tileSize;
import static org.helpers.ToolsHelper.getImageFromAssets;
import static org.individual.Enemy.*;

public enum EnemyAssets
{

    GHOST_MONSTER(1, tileSize * 6, tileSize * 6, 400, ENEMY_DIRECTION_LEFT, 8, "/enemy/ghost/ghost-fly-left.png",
        Map.of(
            ENEMY_DIRECTION_LEFT, Objects.requireNonNull(getImageFromAssets("/enemy/ghost/ghost-fly-left.png")),
            ENEMY_DIRECTION_RIGHT, Objects.requireNonNull(getImageFromAssets("/enemy/ghost/ghost-fly-right.png")),
            COLLISION_ENEMY_ASSET_KEY_PREFIX + ENEMY_DIRECTION_RIGHT, Objects.requireNonNull(getImageFromAssets("/enemy/ghost/ghost-panic-right.png")),
            COLLISION_ENEMY_ASSET_KEY_PREFIX + ENEMY_DIRECTION_LEFT, Objects.requireNonNull(getImageFromAssets("/enemy/ghost/ghost-panic-left.png"))
        )),
    COLOR_MONSTER(2, tileSize * 10, tileSize * 10, 200, ENEMY_DIRECTION_LEFT, 2, "/enemy/color-monster.png", Map.of()),
    COLOR_MONSTER2(3, tileSize * 11, tileSize * 11, 200, ENEMY_DIRECTION_UP, 2, "/enemy/color-monster.png", Map.of()),
    COLOR_MONSTER3(4, tileSize * 12, tileSize * 12, 300, ENEMY_DIRECTION_RIGHT, 1, "/enemy/color-monster.png", Map.of()),
    COLOR_MONSTER4(5, tileSize * 10, tileSize * 5, 400, ENEMY_DIRECTION_DOWN, 1, "/enemy/color-monster.png", Map.of()),
    COLOR_MONSTER5(6, tileSize * 50, tileSize * 18, 200, ENEMY_DIRECTION_UP, 2, "/enemy/color-monster.png", Map.of()),
    COLOR_MONSTER6(7, tileSize * 60, tileSize * 15, 200, ENEMY_DIRECTION_DOWN, 1, "/enemy/color-monster.png", Map.of()),
    COLOR_MONSTER7(8, tileSize * 25, tileSize * 10, 200, ENEMY_DIRECTION_DOWN, 1, "/enemy/color-monster.png", Map.of()),
    COLOR_MONSTER8(9, tileSize * 30, tileSize * 12, 200, ENEMY_DIRECTION_UP, 1, "/enemy/color-monster.png", Map.of()),
    COLOR_MONSTER9(10, tileSize * 35, tileSize * 10, 200, ENEMY_DIRECTION_DOWN, 1, "/enemy/color-monster.png", Map.of()),
    COLOR_MONSTER10(11, tileSize * 40, tileSize * 10, 200, ENEMY_DIRECTION_DOWN, 2, "/enemy/color-monster.png", Map.of()),
    COLOR_MONSTER11(12, tileSize * 25, tileSize * 10, 200, ENEMY_DIRECTION_DOWN, 1, "/enemy/color-monster.png", Map.of());

    final private int enemyId;
    final private int defaultPositionX;
    final private int defaultPositionY;
    final private int maxDistanceAllowedToMove;
    final private String direction;
    final private int speed;
    final private String enemyAssetPath;
    final private Map<String, BufferedImage> enemyAssetsMap;

    EnemyAssets(
            int enemyId,
            int defaultPositionX,
            int defaultPositionY,
            int maxDistanceAllowedToMove,
            String direction,
            int speed,
            String enemyAssetPath,
            Map<String, BufferedImage> enemyAssetsMap
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
}
