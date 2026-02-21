package org.imageAssets;

import org.helpers.ToolsHelper;
import org.imageAssets.models.*;

import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static org.game.GamePanel.tileSize;

public class ImageLoader
{

    private final Map<WorldImagesAssets, BufferedImage> worldAssetsImages = new HashMap<>();
    private final Map<ImageModel, BufferedImage> worldItemsAssetsImages = new HashMap<>();
    private final Map<ImageModel, BufferedImage> dungeonWorldItemsImages = new HashMap<>();
    private final Map<ImageModel, BufferedImage> enemyAssetsImages = new HashMap<>();

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
    }

    public Map<WorldImagesAssets, BufferedImage> getWorldAssetsImages()
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
