package org.helpers;


import org.individual.Player;

import java.io.InputStream;
import java.io.FileNotFoundException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.File;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class ToolsHelper
{
    public static List<String> getTxtFileFromResources(String txtFilePath)
    {
        try (InputStream is = ToolsHelper.class.getResourceAsStream(txtFilePath))
        {
            if (is == null)
            {
                throw new FileNotFoundException("Resource not found: " + txtFilePath);
            }
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(is)))
            {
                return reader.lines().toList();
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    public static InputStream loadJsonFileFromResource(String jsonResourcePath)
    {
        try
        {
            InputStream inputStream = ToolsHelper.class.getResourceAsStream(jsonResourcePath);
            if (inputStream == null)
            {
                throw new RuntimeException("Json file not found in resources");
            }
            return inputStream;
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            return null;
        }
    }

    public static File getJsonFileFromAssets(String jsonFilePath, String fileNotFoundErrorMessage)
    {
        try
        {
            File jsonFile = new File(jsonFilePath);
            if (jsonFile == null)
            {
                throw new RuntimeException(fileNotFoundErrorMessage);
            }
            return jsonFile;
        }
        catch (RuntimeException ex)
        {
            ex.printStackTrace();
        }
        return null;
    }

    public static int _randomXYMultiplier()
    {
        Random random = new Random();
        return random.nextInt(10) + 1;
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
