package org.individual.models;

import org.imageAssets.models.ImageModel;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.individual.models.EnemyAssetsConstance.*;

public enum DungeonEnemyAssets implements EnemyAsset
{

    GHOST_MONSTER(1, 100, 2, invertedSquare, ghostMosterAssetsMap, ghostMosterColisionAssetsMap, ghostMosterUnderAttackAssetsMap),
    COLOR_MONSTER(2, 250, 2, square, colorMosterAssetsMap, Map.of(), Map.of()),
    COLOR_MONSTER_SIDE_MOVING(3, 350, 2, square, colorMosterAssetsMap, Map.of(), Map.of()),
    SLOW_GHOST_MONSTER(4, 200, 1, downUp, ghostMosterAssetsMap, ghostMosterColisionAssetsMap, ghostMosterUnderAttackAssetsMap),
    MOVING_RIGHT_COLOR_MONSTER_SIDE_MOVING(5, 250, 2, upDown, colorMosterAssetsMap, Map.of(), Map.of()),
    MOVING_UP_COLOR_MONSTER(6, 50, 2, snakeMovement, colorMosterAssetsMap, Map.of(), Map.of()),
    SPIDER(7, 100, 2, invertedSquare, spiderMosterAssetsMap, spiderMosterColisionAssetsMap, spiderMosterUnderAttackAssetsMap),
    SIDE_TO_SIDE_MOVING_SPIDER(8, 100, 1, rightLeft, spiderMosterAssetsMap, spiderMosterColisionAssetsMap, spiderMosterUnderAttackAssetsMap),
    FASTER_SIDE_TO_SIDE_MOVING_SPIDER(9, 100, 5, leftRight, spiderMosterAssetsMap, spiderMosterColisionAssetsMap, spiderMosterUnderAttackAssetsMap),
    UP_DOWN_MOVING_SPIDER(10, 200, 1, downUp, spiderMosterAssetsMap, spiderMosterColisionAssetsMap, spiderMosterUnderAttackAssetsMap),
    GHOST_MONSTER_SHORT_DISTANCE(11, 50, 1, rightLeft, ghostMosterAssetsMap, ghostMosterColisionAssetsMap, ghostMosterUnderAttackAssetsMap),
    SID_TO_SIDE_GHOST_MONSTER(12, 100, 1, rightLeft, ghostMosterAssetsMap, ghostMosterColisionAssetsMap, ghostMosterUnderAttackAssetsMap);

    final private int enemyId;
    final private int maxDistanceAllowedToMove;
    final private int speed;
    final private List<MovingDirection> enemyMovingDirectionList;
    final private Map<MovingDirection, Map<Integer, ImageModel>> enemyAssetsMap;
    final private Map<MovingDirection, Map<Integer, ImageModel>> enemyColisionAssetsMap;
    final private Map<MovingDirection, Map<Integer, ImageModel>> enemyUnderAttackAssetsMap;

    DungeonEnemyAssets(
        int enemyId,
        int maxDistanceAllowedToMove,
        int speed,
        List<MovingDirection> enemyMovingDirectionList,
        Map<MovingDirection, Map<Integer, ImageModel>> enemyAssetsMap,
        Map<MovingDirection, Map<Integer, ImageModel>> enemyColisionAssetsMap,
        Map<MovingDirection, Map<Integer, ImageModel>> enemyUnderAttackAssetsMap
    )
    {
        this.enemyId = enemyId;
        this.maxDistanceAllowedToMove = maxDistanceAllowedToMove;
        this.speed = speed;
        this.enemyMovingDirectionList = enemyMovingDirectionList;
        this.enemyAssetsMap = enemyAssetsMap;
        this.enemyColisionAssetsMap = enemyColisionAssetsMap;
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

    public Map<MovingDirection, Map<Integer, ImageModel>> getEnemyAssetsMap()
    {
        return this.enemyAssetsMap;
    }

    public Map<MovingDirection, Map<Integer, ImageModel>> getEnemyColisionAssetsMap()
    {
        return this.enemyColisionAssetsMap;
    }

    public  Map<MovingDirection, Map<Integer, ImageModel>> getEnamyUnderAttackAssetsMap()
    {
        return this.enemyUnderAttackAssetsMap;
    }

    public List<MovingDirection> getEnemyMovingDirectionList()
    {
        return this.enemyMovingDirectionList;
    }

    public static DungeonEnemyAssets getEnemyAssetByIndex(int index)
    {
        DungeonEnemyAssets enemyAssets = Arrays.stream(DungeonEnemyAssets.values())
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
}
