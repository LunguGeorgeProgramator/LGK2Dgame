package org.world.dungeons;

import org.game.GamePanel;
import org.individual.models.DungeonEnemyAssets;
import org.individual.models.EnemyAsset;
import org.world.WorldEnemies;


public class DungeonWorldEnemies extends WorldEnemies
{

    public DungeonWorldEnemies(GamePanel gamePanel, String worldEnemiesMapPath)
    {
        super(gamePanel, worldEnemiesMapPath);
    }

    protected EnemyAsset[] _getEnemyAssets()
    {
        return DungeonEnemyAssets.values();
    }

    protected EnemyAsset _getEnemyAssetByIndex(int worldEnemyIndex)
    {
        return DungeonEnemyAssets.getEnemyAssetByIndex(worldEnemyIndex);
    }

}
