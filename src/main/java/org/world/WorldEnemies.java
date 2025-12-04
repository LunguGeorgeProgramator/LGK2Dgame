package org.world;

import org.game.GamePanel;
import org.gamesavedstats.GameSavedStats;
import org.gamesavedstats.models.EnemyStatsModel;
import org.individual.Enemy;
import org.individual.models.EnemyAsset;
import org.individual.models.EnemyAssets;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;
import java.nio.file.Paths;

import static org.game.GamePanel.tileSize;

public class WorldEnemies extends GameWorld
{
    EnemyAsset[] enemyAssets;
    List<Enemy> enemyList;
    private final GameSavedStats gameSavedStats;
    private final String mapName;

    public WorldEnemies(GamePanel gamePanel, String worldEnemiesMapPath)
    {
        super(gamePanel, worldEnemiesMapPath);
        this.gameSavedStats = gamePanel.gameSavedStats;
        this.enemyAssets = this._getEnemyAssets();
        this.enemyList = new ArrayList<>();
        String fileName = Paths.get(worldEnemiesMapPath).getFileName().toString();
        int dotIndex = fileName.lastIndexOf('.');
        this.mapName = (dotIndex == -1) ? fileName : fileName.substring(0, dotIndex);
        initializeEnemyObjects();
    }

    protected EnemyAsset[] _getEnemyAssets()
    {
        return EnemyAssets.values();
    }

    protected EnemyAsset _getEnemyAssetByIndex(int worldEnemyIndex)
    {
        return EnemyAssets.getEnemyAssetByIndex(worldEnemyIndex);
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
                EnemyAsset enemyAsset = this._getEnemyAssetByIndex(worldEnemyIndex);
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
                    enemyAsset.getEnemyMovingDirectionList(),
                    enemyAsset.getSpeed(),
                    this.gamePanel.player,
                    enemyAsset.getEnemyAssetsMap(),
                    enemyAsset.getEnemyColisionAssetsMap(),
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
        enemyStatsModel.setMapName(this.mapName);
        enemyStatsModel.setEnemyWorldId(enemyWorldId);
        enemyStatsModel.setAlive(true);
        return enemyStatsModel;
    }

    public void addEnemiesToDrawList()
    {
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
    }

    public void drawEnemyText(Graphics2D g2D, boolean clearPlayerDamageText)
    {
        if (clearPlayerDamageText)
        {
            return;
        }
        for(Enemy enemy : this.enemyList)
        {
            enemy.drawEnemyText(g2D);
        }
    }
}
