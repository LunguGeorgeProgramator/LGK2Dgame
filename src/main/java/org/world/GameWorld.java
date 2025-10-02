package org.world;

import org.game.GamePanel;
import org.individual.Player;
import org.world.models.WorldAssets;

import java.awt.Graphics2D;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.helpers.ToolsHelper.getTxtFileFromResources;
import static org.game.GamePanel.tileSize;

public class GameWorld
{

    final GamePanel gamePanel;
    final Player player;
    public int[][] worldMap;
    int worldMapCol;
    int worldMapRow;


    public GameWorld(GamePanel gamePanel, String worldMapPath)
    {
        this.gamePanel = gamePanel;
        this.player = this.gamePanel.player;
        List<String> txtMapLines = getTxtFileFromResources(worldMapPath);
        setWorldMapRange(txtMapLines);
        loadMapIntoMatrix(txtMapLines);
    }

    private void setWorldMapRange(List<String> txtMapLines)
    {
        int txtMapRows = txtMapLines.size();
        int txtMapCols = 0;
        Optional<String> cols = txtMapLines.stream().findFirst();
        if (cols.isPresent())
        {
            txtMapCols = cols.get().split(",").length;
        }
        this.worldMap = new int[txtMapCols][txtMapRows];
        this.worldMapCol = txtMapCols;
        this.worldMapRow = txtMapRows;
    }

    private void loadMapIntoMatrix(List<String> txtMapLines)
    {
        int i = 0;
        for(String line : txtMapLines)
        {
            String[] assetsPosition = Arrays.stream(line.split(",")).map(String::trim).toArray(String[]::new);
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
                int worldPositionX = tileSize * i;
                int worldPositionY = tileSize * j;
                int worldAssetPositionX = worldPositionX - this.player.positionX + this.player.playerScreenX;
                int worldAssetPositionY = worldPositionY - this.player.positionY + this.player.playerScreenY;
                int worldAssetIndex = this.worldMap[i][j];
                if (worldAssetIndex < 0)
                { // skip -1 values
                    continue;
                }
                if (checkIfAssetIsInsideTheBoundary(worldPositionX, worldPositionY, this.player, tileSize))
                {
                    // Draw assets only if they are inside the screen (with x height) plus one tile size to remove popup effect on rendering world assets.
                    g2D.drawImage(WorldAssets.getWorldImageAssetByIndex(worldAssetIndex), worldAssetPositionX, worldAssetPositionY, null);
                }
            }
        }
    }

    static public boolean checkIfAssetIsInsideTheBoundary(int worldAssetPositionX, int worldAssetPositionY, Player player, int worldTileSize)
    {
        boolean isAssetBoundaryRightLimitDraw = worldAssetPositionX + worldTileSize > player.positionX - player.playerScreenX;
        boolean isAssetBoundaryLeftLimitDraw = worldAssetPositionX - worldTileSize < player.positionX + player.playerScreenX;
        boolean isAssetBoundaryDownLimitDraw = worldAssetPositionY + worldTileSize > player.positionY - player.playerScreenY;
        boolean isAssetBoundaryUpLimitDraw = worldAssetPositionY - worldTileSize < player.positionY + player.playerScreenY;
        return isAssetBoundaryUpLimitDraw && isAssetBoundaryDownLimitDraw && isAssetBoundaryLeftLimitDraw && isAssetBoundaryRightLimitDraw;
    }
}
