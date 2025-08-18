package org.world;

import org.game.GamePanel;

import java.awt.Graphics2D;
import java.util.List;

import static org.helpers.ToolsHelper.getTxtFileFromResources;

public class GameWorld
{

    GamePanel gamePanel;
    int[][] worldMap;


    public GameWorld(GamePanel gamePanel)
    {
        this.gamePanel = gamePanel;
        this.worldMap = new int[this.gamePanel.maxScreenColumns][this.gamePanel.maxScreenRows];
        loadMapIntoMatrix();
    }

    private void loadMapIntoMatrix()
    {
        List<String> txtMapLines = getTxtFileFromResources("/worldMaps/CastleRuinMap.txt");
        int i = 0;
        for(String line : txtMapLines)
        {
            String[] assetsPosition = line.split(",");
            for(int j = 0; j < assetsPosition.length; j++)
            {
                this.worldMap[j][i] = Integer.parseInt(assetsPosition[j]);
            }
            i++;
        }
    }

    public void draw(Graphics2D g2D)
    {
        for (int i = 0; i < this.worldMap.length; i++)
        {
            for (int j = 0; j < this.worldMap[i].length; j++)
            {
                int worldAssetPositionX = this.gamePanel.titleSize * i;
                int worldAssetPositionY = this.gamePanel.titleSize * j;
                int worldAssetIndex = this.worldMap[i][j];
                g2D.drawImage(WorldAssets.getWorldAssetByIndex(worldAssetIndex), worldAssetPositionX, worldAssetPositionY, gamePanel.titleSize, gamePanel.titleSize, null);
            }
        }
    }
}
