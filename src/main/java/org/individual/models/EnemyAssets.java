package org.individual.models;

import org.imageAssets.models.ImageModel;

import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.individual.models.EnemyAssetsConstance.ghostMosterAssetsMap;
import static org.individual.models.EnemyAssetsConstance.ghostMosterColisionAssetsMap;
import static org.individual.models.EnemyAssetsConstance.ghostMosterUnderAttackAssetsMap;
import static org.individual.models.EnemyAssetsConstance.colorMosterAssetsMap;
import static org.individual.models.EnemyAssetsConstance.spiderMosterAssetsMap;
import static org.individual.models.EnemyAssetsConstance.spiderMosterColisionAssetsMap;
import static org.individual.models.EnemyAssetsConstance.spiderMosterUnderAttackAssetsMap;
import static org.individual.models.EnemyAssetsConstance.upDown;
import static org.individual.models.EnemyAssetsConstance.downUp;
import static org.individual.models.EnemyAssetsConstance.leftRight;
import static org.individual.models.EnemyAssetsConstance.rightLeft;
import static org.individual.models.EnemyAssetsConstance.square;
import static org.individual.models.EnemyAssetsConstance.invertedSquare;
import static org.individual.models.EnemyAssetsConstance.snakeMovement;

public enum EnemyAssets implements EnemyAsset
{

    GHOST_MONSTER(1, 100, 8, leftRight, ghostMosterAssetsMap, ghostMosterColisionAssetsMap, ghostMosterUnderAttackAssetsMap),
    COLOR_MONSTER(2, 250, 2, square, colorMosterAssetsMap, Map.of(), Map.of()),
    COLOR_MONSTER_SIDE_MOVING(3, 350, 2, square, colorMosterAssetsMap, Map.of(), Map.of()),
    SLOW_GHOST_MONSTER(4, 150, 4, rightLeft, ghostMosterAssetsMap, ghostMosterColisionAssetsMap, ghostMosterUnderAttackAssetsMap),
    MOVING_RIGHT_COLOR_MONSTER_SIDE_MOVING(5, 250, 2, upDown, colorMosterAssetsMap, Map.of(), Map.of()),
    MOVING_UP_COLOR_MONSTER(6, 50, 2, snakeMovement, colorMosterAssetsMap, Map.of(), Map.of()),
    SPIDER(7, 100, 2, invertedSquare, spiderMosterAssetsMap, spiderMosterColisionAssetsMap, spiderMosterUnderAttackAssetsMap),
    SIDE_TO_SIDE_MOVING_SPIDER(8, 100, 1, rightLeft, spiderMosterAssetsMap, spiderMosterColisionAssetsMap, spiderMosterUnderAttackAssetsMap),
    FASTER_SIDE_TO_SIDE_MOVING_SPIDER(9, 100, 5, leftRight, spiderMosterAssetsMap, spiderMosterColisionAssetsMap, spiderMosterUnderAttackAssetsMap),
    UP_DOWN_MOVING_SPIDER(10, 200, 1, downUp, spiderMosterAssetsMap, spiderMosterColisionAssetsMap, spiderMosterUnderAttackAssetsMap);

    final private int enemyId;
    final private int maxDistanceAllowedToMove;
    final private int speed;
    final private List<MovingDirection> enemyMovingDirectionList;
    final private Map<MovingDirection, Map<Integer, ImageModel>> enemyAssetsMap;
    final private Map<MovingDirection, Map<Integer, ImageModel>> enemyColisionAssetsMap;
    final private Map<MovingDirection, Map<Integer, ImageModel>> enemyUnderAttackAssetsMap;

    EnemyAssets(
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
}
