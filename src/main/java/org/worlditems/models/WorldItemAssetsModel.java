package org.worlditems.models;

import org.imageAssets.models.ImageModel;
import org.imageAssets.models.WorldItemsImagesAssets;

public class WorldItemAssetsModel
{
    private final ImageModel imageAssetPath;
    private final String imageTextKey;

    public WorldItemAssetsModel(ImageModel imageAssetPath, String imageTextKey)
    {
        this.imageAssetPath = imageAssetPath;
        this.imageTextKey = imageTextKey;
    }

    public ImageModel getImageAssetPath()
    {
        return this.imageAssetPath;
    }

    public String getImageTextKey()
    {
        return this.imageTextKey;
    }
}
