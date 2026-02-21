package org.world;

import org.game.GamePanel;
import org.imageAssets.models.WorldImagesAssets;
import org.individual.Player;
import org.world.models.WorldAssets;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.helpers.ToolsHelper.getTxtFileFromResources;
import static org.game.GamePanel.tileSize;
import static org.helpers.ToolsHelper.checkIfAssetIsInsideTheBoundary;

public class GameWorld
{

    protected final GamePanel gamePanel;
    protected final Player player;
    public int[][] worldMap;
    int worldMapCol;
    int worldMapRow;
    Map<WorldImagesAssets, BufferedImage> worldImagesAssetsBufferedImageMap;


    public GameWorld(GamePanel gamePanel, String worldMapPath)
    {
        this.gamePanel = gamePanel;
        this.worldImagesAssetsBufferedImageMap = gamePanel.imageLoader.getWorldAssetsImages();
        this.player = gamePanel.player;
        List<String> txtMapLines = getTxtFileFromResources(worldMapPath);
        setWorldMapRange(txtMapLines);
        loadMapIntoMatrix(txtMapLines);
    }

    protected void drawWorldAsset(Graphics2D g2D, int worldAssetIndex, int worldAssetPositionX, int worldAssetPositionY)
    {
        WorldImagesAssets worldImagesAssets = WorldAssets.getWorldImageAssetByIndexImgLoad(worldAssetIndex);
        BufferedImage imageAsset = this.worldImagesAssetsBufferedImageMap.get(worldImagesAssets);
        g2D.drawImage(imageAsset, worldAssetPositionX, worldAssetPositionY, null);
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
                if (checkIfAssetIsInsideTheBoundary(worldPositionX, worldPositionY, this.player, tileSize * 2))
                {
                    // Draw assets only if they are inside the screen (with x height) plus one tile size to remove popup effect on rendering world assets.
                    this.drawWorldAsset(g2D, worldAssetIndex, worldAssetPositionX, worldAssetPositionY);
                }
            }
        }
    }
}
