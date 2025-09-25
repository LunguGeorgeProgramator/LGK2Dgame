package org.worlditems.models;

import java.awt.image.BufferedImage;

public class WorldItemAssetsModel
{
    private final BufferedImage imageAsset;
    private final String imageTextKey;

    public WorldItemAssetsModel(BufferedImage imageAsset, String imageTextKey)
    {
        this.imageAsset = imageAsset;
        this.imageTextKey = imageTextKey;
    }

    public BufferedImage getImageAsset()
    {
        return this.imageAsset;
    }

    public String getImageTextKey()
    {
        return this.imageTextKey;
    }
}
