package org.world;

import org.game.GamePanel;
import org.individual.Enemy;
import org.individual.EnemyAssets;
import org.individual.Player;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

public class Enemies
{
    GamePanel gamePanel;
    Player player;
    EnemyAssets[] enemyAssets;
    List<Enemy> enemyList;

    public Enemies(GamePanel gamePanel, Player player)
    {
        this.enemyAssets = EnemyAssets.values();
        this.enemyList = new ArrayList<>();
        this.gamePanel = gamePanel;
        this.player = player;
        initializeEnemyObjects();
    }

    private void initializeEnemyObjects()
    {
        for(EnemyAssets enemyAsset : this.enemyAssets)
        {
            this.enemyList.add(new Enemy(
                this.gamePanel,
                enemyAsset.getDefaultPositionX(),
                enemyAsset.getDefaultPositionY(),
                enemyAsset.getMaxDistanceAllowedToMove(),
                enemyAsset.getDirection(),
                enemyAsset.getSpeed(),
                enemyAsset.getEnemyAssetPath(),
                this.player,
                enemyAsset.getEnemyAssetsMap()
            ));
        }
    }

    public void update()
    {
        for(Enemy enemy : this.enemyList)
        {
            enemy.update();
        }
    }

    public void draw(Graphics2D g2D)
    {
        for(Enemy enemy : this.enemyList)
        {
            enemy.draw(g2D);
        }
    }
}
