package org.helpers;


import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Collections;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class ToolsHelper
{
    // tool used to load txt files
    public static List<String> getTxtFileFromResources(String txtFilePath)
    {
        try
        {
            URL resource = ToolsHelper.class.getResource(txtFilePath);
            fileNullChecker(resource, txtFilePath);
            return Files.readAllLines(Paths.get(resource.toURI()));
        }
        catch (IOException | URISyntaxException | IllegalArgumentException e)
        {
//            throw new RuntimeException(String.format("Failed to load txt file %s error %s ", txtFilePath, e.getMessage()));
            e.printStackTrace();
        }
        return Collections.emptyList();
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
}
