package org.gamesavedstats.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class EnemyStatsModel
{
    private String mapName;
    private String enemyWorldId;
    private boolean isAlive;

    public EnemyStatsModel()
    {
    }

    @JsonProperty("mapName")
    public String getMapName()
    {
        return mapName;
    }

    @JsonProperty("isAlive")
    public boolean getIsAlive()
    {
        return isAlive;
    }

    @JsonProperty("mapName")
    public void setMapName(String mapName)
    {
        this.mapName = mapName;
    }

    @JsonProperty("enemyWorldId")
    public String getEnemyWorldId()
    {
        return enemyWorldId;
    }

    @JsonProperty("enemyWorldId")
    public void setEnemyWorldId(String enemyWorldId)
    {
        this.enemyWorldId = enemyWorldId;
    }

    @JsonProperty("isAlive")
    public void setAlive(boolean isAlive)
    {
        this.isAlive = isAlive;
    }
}
