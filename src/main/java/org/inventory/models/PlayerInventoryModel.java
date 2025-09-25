package org.inventory.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PlayerInventoryModel
{
    private String itemName;
    private int itemId;
    private int count;
    private String status;
    private String itemType;

    public PlayerInventoryModel()
    {
    }

    @JsonProperty("itemName")
    public String getItemName()
    {
        return itemName;
    }

    @JsonProperty("itemId")
    public int getItemId()
    {
        return itemId;
    }

    @JsonProperty("count")
    public int getCount()
    {
        return Math.abs(count); // only positive numbers
    }

    @JsonProperty("status")
    public String getStatus()
    {
        return status;
    }

    @JsonProperty("itemName")
    public void setItemName(String itemName)
    {
        this.itemName = itemName;
    }

    @JsonProperty("count")
    public void setCount(int count)
    {
        this.count = count;
    }

    @JsonProperty("count")
    public void addToCount(int count)
    {
        this.count = this.count + count;
    }

    @JsonProperty("count")
    public void removeFromCount(int count)
    {
        this.count = this.count - (count > -1 && this.count > 0 ? count : 0);
    }

    @JsonProperty("status")
    public void setStatus(String status)
    {
        this.status = status;
    }

    @JsonProperty("itemType")
    public String getItemType()
    {
        return itemType;
    }

    @JsonProperty("itemType")
    public void setItemType(String itemType)
    {
        this.itemType = itemType;
    }

    @JsonProperty("itemId")
    public void setItemId(int itemId)
    {
        this.itemId = itemId;
    }
}
