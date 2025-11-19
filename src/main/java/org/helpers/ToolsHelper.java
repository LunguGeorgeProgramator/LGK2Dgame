package org.helpers;


import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.io.FileNotFoundException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.File;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Random;

import static org.game.GamePanel.tileSize;

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

    public static BufferedImage getScaledImageFromAssets(String imagePath)
    {
        return getScaledImageFromAssets(imagePath, tileSize, tileSize);
    }

    public static BufferedImage getScaledImageFromAssets(String imagePath, int with, int height)
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

    // tool used to load game images assets
    public static BufferedImage getImageFromAssets(String imagePath)
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

}
