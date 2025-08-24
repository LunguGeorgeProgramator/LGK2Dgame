package org.individual;

import static org.game.GamePanel.tileSize;

public enum EnemyAssets
{

    GHOST_UP_AND_DOWN(1, tileSize * 6, tileSize * 6, 200, "up", 1, "/enemy/color-monster.png"),
    GHOST_LEFT_AND_RIGHT(2, tileSize * 10, tileSize * 10, 0, "down", 1, "/enemy/color-monster.png"),
    GHOST_LEFT_AND_RIGHT2(3, tileSize * 11, tileSize * 11, 200, "up", 1, "/enemy/color-monster.png"),
    GHOST_LEFT_AND_RIGHT3(4, tileSize * 12, tileSize * 12, 300, "up", 1, "/enemy/color-monster.png"),
    GHOST_LEFT_AND_RIGHT4(5, tileSize * 10, tileSize * 5, 400, "down", 1, "/enemy/color-monster.png"),
    GHOST_LEFT_AND_RIGHT5(6, tileSize * 50, tileSize * 18, 200, "up", 2, "/enemy/color-monster.png"),
    GHOST_LEFT_AND_RIGHT6(7, tileSize * 60, tileSize * 15, 200, "down", 1, "/enemy/color-monster.png"),
    GHOST_LEFT_AND_RIGHT7(8, tileSize * 25, tileSize * 10, 200, "down", 1, "/enemy/color-monster.png"),
    GHOST_LEFT_AND_RIGHT8(9, tileSize * 30, tileSize * 12, 200, "up", 1, "/enemy/color-monster.png"),
    GHOST_LEFT_AND_RIGHT9(10, tileSize * 35, tileSize * 10, 200, "down", 1, "/enemy/color-monster.png"),
    GHOST_LEFT_AND_RIGHT10(11, tileSize * 40, tileSize * 10, 200, "down", 2, "/enemy/color-monster.png"),
    GHOST_LEFT_AND_RIGHT11(12, tileSize * 25, tileSize * 10, 200, "down", 1, "/enemy/color-monster.png");

    final private int enemyId;
    final private int defaultPositionX;
    final private int defaultPositionY;
    final private int maxDistanceAllowedToMove;
    final private String direction;
    final private int speed;
    final private String enemyAssetPath;

    EnemyAssets(
            int enemyId,
            int defaultPositionX,
            int defaultPositionY,
            int maxDistanceAllowedToMove,
            String direction,
            int speed,
            String enemyAssetPath
    )
    {
        this.enemyId = enemyId;
        this.defaultPositionX = defaultPositionX;
        this.defaultPositionY = defaultPositionY;
        this.maxDistanceAllowedToMove = maxDistanceAllowedToMove;
        this.direction = direction;
        this.speed = speed;
        this.enemyAssetPath = enemyAssetPath;
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
}
