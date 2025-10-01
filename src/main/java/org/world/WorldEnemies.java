package org.world;

import org.game.GamePanel;
import org.gamesavedstats.GameSavedStats;
import org.gamesavedstats.models.EnemyStatsModel;
import org.individual.Enemy;
import org.individual.EnemyAssets;

//import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import static org.game.GamePanel.tileSize;

public class WorldEnemies extends GameWorld
{
    EnemyAssets[] enemyAssets;
    List<Enemy> enemyList;
    private final GameSavedStats gameSavedStats;

    public WorldEnemies(GamePanel gamePanel, String worldEnemiesMapPath)
    {
        super(gamePanel, worldEnemiesMapPath);
        this.gameSavedStats = gamePanel.gameSavedStats;
        this.enemyAssets = EnemyAssets.values();
        this.enemyList = new ArrayList<>();
        initializeEnemyObjects();
    }

    private void initializeEnemyObjects()
    {

        for (int i = 0; i < this.worldMap.length; i++)
        {
            for (int j = 0; j < this.worldMap[i].length; j++)
            {
                int worldEnemyIndex = this.worldMap[i][j];
                if (worldEnemyIndex < 0)
                {
                    continue;
                }
                EnemyAssets enemyAsset = EnemyAssets.getEnemyAssetByIndex(worldEnemyIndex);
                if (enemyAsset == null)
                {
                    continue;
                }
                int worldPositionX = tileSize * i;
                int worldPositionY = tileSize * j;
                this.enemyList.add(new Enemy(
                    this.gamePanel,
                    enemyAsset.getEnemyId(),
                    enemyAsset.name(),
                    i,
                    j,
                    worldPositionX,
                    worldPositionY,
                    enemyAsset.getMaxDistanceAllowedToMove(),
                    enemyAsset.getEnamyMovingDirectionList(),
                    enemyAsset.getSpeed(),
                    enemyAsset.getEnemyAssetPath(),
                    this.gamePanel.player,
                    enemyAsset.getEnemyAssetsMap(),
                    enemyAsset.getEnamyUnderAttackAssetsMap()
                ));
            }
        }
    }

    public void update(boolean resetEnemiesHealth)
    {
        for(Enemy enemy : this.enemyList)
        {
            enemy.resetEnemyHealth = resetEnemiesHealth;
            String enemyWorldId = this.gameSavedStats.getEnemyWorldIdFormat(enemy);
            EnemyStatsModel enemyStatsModel = this.gameSavedStats.getEnemyStatsByEnemyWorldId(enemyWorldId);
            if (enemyStatsModel == null)
            {
                EnemyStatsModel enemyStatsToAdd = getEnemyStatsModel(enemy, enemyWorldId);
                this.gameSavedStats.addToEnemyStats(enemyStatsToAdd);
            }
            else if (!enemyStatsModel.getIsAlive())
            {
                // do not check collision or other logic for this item, this is hidden from player inside the inventory
                continue;
            }
            enemy.update();
        }
    }

    public EnemyStatsModel getEnemyStatsModel(Enemy enemy, String enemyWorldId)
    {
        EnemyStatsModel enemyStatsModel = new EnemyStatsModel();
        enemyStatsModel.setMapName("WorldMapEnemies");
        enemyStatsModel.setEnemyWorldId(enemyWorldId);
        enemyStatsModel.setAlive(true);
        return enemyStatsModel;
    }

//    public void draw(Graphics2D g2D)
    public void addEnemiesToDrawList()
    {
//        for(Enemy enemy : this.enemyList)
//        {
//            enemy.draw(g2D);
//        }
        for(Enemy enemy : this.enemyList)
        {
            String enemyWorldId = this.gameSavedStats.getEnemyWorldIdFormat(enemy);
            EnemyStatsModel enemyStatsModel = this.gameSavedStats.getEnemyStatsByEnemyWorldId(enemyWorldId);
            if (enemyStatsModel != null && !enemyStatsModel.getIsAlive())
            {
                continue;
            }
            this.gamePanel.individuals.add(enemy);
        }
//        this.gamePanel.individuals.addAll(this.enemyList);
    }
}
