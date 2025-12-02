package org.gamesavedstats;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.gamesavedstats.models.EnemyStatsModel;
import org.individual.Enemy;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.helpers.ToolsHelper.getJsonFileFromAssets;

public class GameSavedStats
{
    private final ObjectMapper mapper;
    private final File enemyStatsJsonFile;
    private List<EnemyStatsModel> enemiesStatsList;
    private static String ENEMY_WORLD_ID_FORMAT = "%d%d%d";
    private static final String ENEMY_STATS_RESOURCE_PATH = "config/enemies_stats.json";
    private static final String ENEMY_STATS_RESOURCE_PATH_NPT_FOUND_ERROR_MESSAGE = "enemies_stats.json not found please check resource.";

    public GameSavedStats()
    {
        this.enemyStatsJsonFile = getJsonFileFromAssets(ENEMY_STATS_RESOURCE_PATH, ENEMY_STATS_RESOURCE_PATH_NPT_FOUND_ERROR_MESSAGE);
        this.mapper = new ObjectMapper();
        this.enemiesStatsList = openEnemyStats();
    }

    public String getEnemyWorldIdFormat(Enemy enemy)
    {
        return String.format(ENEMY_WORLD_ID_FORMAT, enemy.enemyId, enemy.enemyWorldMatrixCol, enemy.enemyWorldMatrixRow);
    }

    public List<EnemyStatsModel> openEnemyStats()
    {
        List<EnemyStatsModel> items = new ArrayList<>();
        try
        {
            items = mapper.readValue(this.enemyStatsJsonFile, new TypeReference<>() {});
            this.enemiesStatsList = items;
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }
        return items;
    }

    public List<EnemyStatsModel> addToEnemyStats(EnemyStatsModel enemyStatsModel)
    {
        List<EnemyStatsModel> items = this.enemiesStatsList;
        EnemyStatsModel EnemyStatsModeItem = getEnemyStatsByEnemyWorldId(enemyStatsModel.getEnemyWorldId());
        if (EnemyStatsModeItem != null)
        { // item already added in inventory
            return items;
        }
        try
        {
            items.add(enemyStatsModel);
            mapper.writerWithDefaultPrettyPrinter().writeValue(this.enemyStatsJsonFile, items);
            this.enemiesStatsList = items;
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        return items;
    }

    public EnemyStatsModel getEnemyStatsByEnemyWorldId(String enemyWorldId)
    {
        if (this.enemiesStatsList == null)
        {
            return null;
        }
        for (EnemyStatsModel enemyStats : this.enemiesStatsList)
        {
            if (enemyStats.getEnemyWorldId().equals(enemyWorldId))
            {
                return enemyStats;
            }
        }
        return null;
    }

    public List<EnemyStatsModel> updateEnemyAliveStatus(String enemyWorldId, boolean isAlive)
    {
        List<EnemyStatsModel> items = this.enemiesStatsList;
        for (EnemyStatsModel enemyStatsModel : items)
        {
            if (enemyStatsModel.getEnemyWorldId().equals(enemyWorldId))
            {
                if (enemyStatsModel.getIsAlive() == isAlive)
                { // do not update if it has same value
                    return items;
                }
                enemyStatsModel.setAlive(isAlive);
                break;
            }
        }
        try
        {
            mapper.writerWithDefaultPrettyPrinter().writeValue(this.enemyStatsJsonFile, items);
            this.enemiesStatsList = items;
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        return items;
    }

    public void resurrectAllDeadEnemies()
    {
        List<EnemyStatsModel> emptyInventoryList = new ArrayList<>();
        try
        {
            mapper.writerWithDefaultPrettyPrinter().writeValue(this.enemyStatsJsonFile, emptyInventoryList);
            this.enemiesStatsList = emptyInventoryList;
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

}
