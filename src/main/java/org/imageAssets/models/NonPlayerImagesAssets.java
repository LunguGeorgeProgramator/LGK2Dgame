package org.imageAssets.models;

public enum NonPlayerImagesAssets implements ImageModel
{
    VENDOR_SELLER_NPC("/npc/vendor-seller-npc.png");

    private String path;

    NonPlayerImagesAssets(String path)
    {
        this.path = path;
    }

    public String getPath()
    {
        return this.path;
    }
}
