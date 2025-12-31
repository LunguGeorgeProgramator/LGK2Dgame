package org.world;

import org.game.GamePanel;
import org.gamesavedstats.GameSavedStats;
import org.gamesavedstats.models.EnemyStatsModel;
import org.individual.Enemy;
import org.individual.Individual;
import org.individual.models.EnemyAsset;
import org.individual.models.EnemyAssets;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;
import java.nio.file.Paths;

import static org.game.GamePanel.tileSize;
import static org.helpers.ToolsHelper.checkIfAssetIsInsideTheBoundary;

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
            else if (enemyStatsModel.getIsAlive())
            {
                enemy.update();
            }
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

    public List<Individual> addEnemiesToDrawList(List<Individual> individuals)
    {

        for(Enemy enemy : this.enemyList)
        {
            if (checkIfAssetIsInsideTheBoundary(enemy.positionX, enemy.positionY, this.player, tileSize * 2))
            {
                String enemyWorldId = this.gameSavedStats.getEnemyWorldIdFormat(enemy);
                EnemyStatsModel enemyStatsModel = this.gameSavedStats.getEnemyStatsByEnemyWorldId(enemyWorldId);
                if (enemyStatsModel != null && !enemyStatsModel.getIsAlive())
                {
                    continue;
                }
                individuals.add(enemy);
            }
        }
        return individuals;
    }

//    public void drawEnemyText(Graphics2D g2D, boolean clearPlayerDamageText)
//    {
//        if (clearPlayerDamageText)
//        {
//            return;
//        }
//        for(Enemy enemy : this.enemyList)
//        {
//            enemy.drawEnemyText(g2D);
//        }
//    }
}
