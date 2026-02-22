package org.imageAssets;

import org.helpers.ToolsHelper;
import org.imageAssets.models.ImageModel;
import org.imageAssets.models.WorldImagesAssets;
import org.imageAssets.models.WorldItemsImagesAssets;
import org.imageAssets.models.DungeonWorldItemsImagesAssets;
import org.imageAssets.models.EnemyImagesAssets;
import org.imageAssets.models.EnemyBossesImagesAssets;
import org.imageAssets.models.PlayerImagesAssets;
import org.imageAssets.models.NonPlayerImagesAssets;
import org.imageAssets.models.DungeonWorldImagesAssets;

import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static org.game.GamePanel.tileSize;
import static org.individual.Individual.boosTileSizeImage;

public class ImageLoader
{

    private final Map<ImageModel, BufferedImage> worldAssetsImages = new HashMap<>();
    private final Map<ImageModel, BufferedImage> worldItemsAssetsImages = new HashMap<>();
    private final Map<ImageModel, BufferedImage> dungeonWorldItemsImages = new HashMap<>();
    private final Map<ImageModel, BufferedImage> enemyAssetsImages = new HashMap<>();
    private final Map<ImageModel, BufferedImage> playerAssetsImages = new HashMap<>();
    private final Map<ImageModel, BufferedImage> enemyBossAssetsImages = new HashMap<>();
    private final Map<ImageModel, BufferedImage> nonPlayerAssetsImages = new HashMap<>();
    private final Map<ImageModel, BufferedImage> dungeonWorldImagesAssets = new HashMap<>();

    public ImageLoader()
    {
        for(WorldImagesAssets assetsImage : WorldImagesAssets.values())
        {
            this.worldAssetsImages.put(assetsImage, getScaledImageFromAssets(assetsImage.getPath()));
        }
        for(WorldItemsImagesAssets worldItemsImagesAssets : WorldItemsImagesAssets.values())
        {
            this.worldItemsAssetsImages.put(worldItemsImagesAssets, getScaledImageFromAssets(worldItemsImagesAssets.getPath()));
        }
        for(DungeonWorldItemsImagesAssets dungeonWorldItemsImagesAssets : DungeonWorldItemsImagesAssets.values())
        {
            this.dungeonWorldItemsImages.put(dungeonWorldItemsImagesAssets, getScaledImageFromAssets(dungeonWorldItemsImagesAssets.getPath()));
        }
        for(EnemyImagesAssets enemyImagesAssets : EnemyImagesAssets.values())
        {
            this.enemyAssetsImages.put(enemyImagesAssets, getScaledImageFromAssets(enemyImagesAssets.getPath()));
        }
        for(PlayerImagesAssets playerImagesAssets : PlayerImagesAssets.values())
        {
            this.playerAssetsImages.put(playerImagesAssets, getScaledImageFromAssets(playerImagesAssets.getPath()));
        }
        for(EnemyBossesImagesAssets enemyBossesImageAssets : EnemyBossesImagesAssets.values())
        {
            this.enemyBossAssetsImages.put(enemyBossesImageAssets, getScaledImageFromAssets(enemyBossesImageAssets.getPath(), boosTileSizeImage, boosTileSizeImage));
        }
        for(NonPlayerImagesAssets nonPlayerImagesAssets : NonPlayerImagesAssets.values())
        {
            this.nonPlayerAssetsImages.put(nonPlayerImagesAssets, getScaledImageFromAssets(nonPlayerImagesAssets.getPath()));
        }
        for(DungeonWorldImagesAssets dungeonWorldImagesAssets : DungeonWorldImagesAssets.values())
        {
            this.dungeonWorldImagesAssets.put(dungeonWorldImagesAssets, getScaledImageFromAssets(dungeonWorldImagesAssets.getPath()));
        }
    }

    public Map<ImageModel, BufferedImage> getWorldAssetsImages()
    {
        return this.worldAssetsImages;
    }

    public Map<ImageModel, BufferedImage> getWorldItemsAssetsImages()
    {
        return this.worldItemsAssetsImages;
    }

    public Map<ImageModel, BufferedImage> getDungeonWorldItemsImages()
    {
        return this.dungeonWorldItemsImages;
    }

    public Map<ImageModel, BufferedImage> getEnemyAssetsImages()
    {
        return this.enemyAssetsImages;
    }

    public Map<ImageModel, BufferedImage> getNonPlayerAssetsImages()
    {
        return this.nonPlayerAssetsImages;
    }

    public Map<ImageModel, BufferedImage> getDungeonWorldImagesAssets()
    {
        return this.dungeonWorldImagesAssets;
    }

    public BufferedImage getPlayerImageAssetByKey(ImageModel playerImageKeyName)
    {
        try
        {
            return this.playerAssetsImages.get(playerImageKeyName);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        return null;
    }

    public BufferedImage getEnemyBossesImageAssets(ImageModel enemyBossImageKeyName)
    {
        try
        {
            return this.enemyBossAssetsImages.get(enemyBossImageKeyName);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        return null;
    }

    public BufferedImage getScaledImageFromAssets(String imagePath)
    {
        return getScaledImageFromAssets(imagePath, tileSize, tileSize);
    }

    public BufferedImage getScaledImageFromAssets(String imagePath, int with, int height)
    {
        try
        {
            BufferedImage bufferedImage = getImageFromAssets(imagePath);
            if (bufferedImage == null)
            {
                throw new NullPointerException();
            }
            BufferedImage scaledBufferedImage = new BufferedImage(with, height, bufferedImage.getType());
            Graphics2D graphics2D = scaledBufferedImage.createGraphics();
            graphics2D.drawImage(bufferedImage, 0, 0, with, height, null);
            graphics2D.dispose();
            return Objects.requireNonNull(scaledBufferedImage);
        }
        catch (NullPointerException ex)
        {
            ex.printStackTrace();
        }
        return null;
    }

    private BufferedImage getImageFromAssets(String imagePath)
    {
        try
        {
            InputStream imageResource = ToolsHelper.class.getResourceAsStream(imagePath);
            fileNullChecker(imageResource, imagePath);
            return ImageIO.read(imageResource);
        }
        catch (IOException | IllegalArgumentException ex)
        {
            ex.printStackTrace();
        }
        return null;
    }

    private static void fileNullChecker(Object file, String filePath) throws IllegalArgumentException
    {
        if (file == null)
        {
            throw new IllegalArgumentException(String.format("File not found %s !", filePath));
        }
    }
}
