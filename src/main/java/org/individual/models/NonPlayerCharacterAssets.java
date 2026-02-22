package org.individual.models;

import org.imageAssets.models.ImageModel;

import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.individual.models.NonPlayerCharacterConstants.standStill;
import static org.individual.models.NonPlayerCharacterConstants.vandorSallerAssetsMap;

public enum NonPlayerCharacterAssets
{
    VENDOR_SELLER(1, NonPlayerCharacterType.VENDOR, 0, 0, standStill, vandorSallerAssetsMap);

    final private int npcId;
    final private NonPlayerCharacterType npcType;
    final private int maxDistanceAllowedToMove;
    final private int speed;
    final private List<MovingDirection> npcMovingDirectionList;
    final private Map<MovingDirection, Map<Integer, ImageModel>> npcAssetsMap;

    NonPlayerCharacterAssets(
        int npcId,
        NonPlayerCharacterType npcType,
        int maxDistanceAllowedToMove,
        int speed,
        List<MovingDirection> npcMovingDirectionList,
        Map<MovingDirection, Map<Integer, ImageModel>> npcAssetsMap
    )
    {
        this.npcId = npcId;
        this.npcType = npcType;
        this.maxDistanceAllowedToMove = maxDistanceAllowedToMove;
        this.speed = speed;
        this.npcMovingDirectionList = npcMovingDirectionList;
        this.npcAssetsMap = npcAssetsMap;
    }

    public int getNpcId()
    {
        return this.npcId;
    }

    public NonPlayerCharacterType getNpcType()
    {
        return this.npcType;
    }

    public int getMaxDistanceAllowedToMove()
    {
        return this.maxDistanceAllowedToMove;
    }

    public int getSpeed()
    {
        return this.speed;
    }

    public Map<MovingDirection, Map<Integer, ImageModel>> getNpcAssetsMap()
    {
        return this.npcAssetsMap;
    }

    public List<MovingDirection> getNpcMovingDirectionList()
    {
        return this.npcMovingDirectionList;
    }

    public static NonPlayerCharacterAssets geNpcAssetByIndex(int index)
    {
        NonPlayerCharacterAssets npcAssets = Arrays.stream(NonPlayerCharacterAssets.values())
                .filter(s -> s.getNpcId() == index)
                .findFirst()
                .orElse(null);
        if (npcAssets == null)
        {
//            throw new RuntimeException("Index not found in world item assets!");
            return null;
        }
        return npcAssets;
    }
}
