package org.worlditems.models;


import java.util.Map;

public class WorldItemDuplicatedBuilder
{
    private final Integer itemId;
    private final String itemType;
    private final Integer positionX;
    private final Integer positionY;
    private final Integer dependencyOnAssetId;
    private final Map<Integer, WorldItemAssetsModel> itemAssetsMap;
    private final Boolean solidStopOnCollisionWithPlayer;

    private WorldItemDuplicatedBuilder(Builder builder)
    {
        this.itemId = builder.itemId;
        this.itemType = builder.itemType;
        this.positionX = builder.positionX;
        this.positionY = builder.positionY;
        this.dependencyOnAssetId = builder.dependencyOnAssetId;
        this.itemAssetsMap = builder.itemAssetsMap;
        this.solidStopOnCollisionWithPlayer = builder.solidStopOnCollisionWithPlayer;
    }

    public Integer getItemId()
    {
        return itemId;
    }

    public String getItemType()
    {
        return itemType;
    }

    public Integer getPositionX()
    {
        return positionX;
    }

    public Integer getPositionY()
    {
        return positionY;
    }

    public Integer getDependencyOnAssetId()
    {
        return dependencyOnAssetId;
    }

    public Map<Integer, WorldItemAssetsModel> getItemAssetsMap()
    {
        return itemAssetsMap;
    }

    public Boolean getSolidStopOnCollisionWithPlayer()
    {
        return solidStopOnCollisionWithPlayer;
    }


    // Builder Class
    public static class Builder
    {
        // all are optional, no required calls variable
        private Integer itemId = null;
        private String itemType = null;
        private Integer positionX = null;
        private Integer positionY = null;
        private Integer dependencyOnAssetId = null;
        private Map<Integer, WorldItemAssetsModel> itemAssetsMap = null;
        private Boolean solidStopOnCollisionWithPlayer = null;

        public Builder setItemId(Integer itemId)
        {
            this.itemId = itemId;
            return this;
        }

        public Builder setItemType(String itemType)
        {
            this.itemType = itemType;
            return this;
        }

        public Builder setPositionX(Integer positionX)
        {
            this.positionX = positionX;
            return this;
        }

        public Builder setPositionY(Integer positionY)
        {
            this.positionY = positionY;
            return this;
        }

        public Builder setDependencyOnAssetId(Integer dependencyOnAssetId)
        {
            this.dependencyOnAssetId = dependencyOnAssetId;
            return this;
        }

        public Builder setItemAssetsMap(Map<Integer, WorldItemAssetsModel> itemAssetsMap)
        {
            this.itemAssetsMap = itemAssetsMap;
            return this;
        }

        public Builder setSolidStopOnCollisionWithPlayer(Boolean solidStopOnCollisionWithPlayer)
        {
            this.solidStopOnCollisionWithPlayer = solidStopOnCollisionWithPlayer;
            return this;
        }

        public WorldItemDuplicatedBuilder build()
        {
            return new WorldItemDuplicatedBuilder(this);
        }
    }
}
