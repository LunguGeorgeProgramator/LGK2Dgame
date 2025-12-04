package org.individual.models;

import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Map;

public interface EnemyAsset
{

    int getEnemyId();

    String name();

    int getMaxDistanceAllowedToMove();

    int getSpeed();

    List<MovingDirection> getEnemyMovingDirectionList();

    Map<MovingDirection, Map<Integer, BufferedImage>> getEnemyAssetsMap();

    Map<MovingDirection, Map<Integer, BufferedImage>> getEnemyColisionAssetsMap();

    Map<MovingDirection, Map<Integer, BufferedImage>> getEnamyUnderAttackAssetsMap();
}
