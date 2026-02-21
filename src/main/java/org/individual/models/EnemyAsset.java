package org.individual.models;

import org.imageAssets.models.ImageModel;

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

    Map<MovingDirection, Map<Integer, ImageModel>> getEnemyAssetsMap();

    Map<MovingDirection, Map<Integer, ImageModel>> getEnemyColisionAssetsMap();

    Map<MovingDirection, Map<Integer, ImageModel>> getEnamyUnderAttackAssetsMap();
}
