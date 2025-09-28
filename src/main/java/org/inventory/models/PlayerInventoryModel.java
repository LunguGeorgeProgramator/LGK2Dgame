package org.inventory.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PlayerInventoryModel
{
    private String itemName;
    private int itemId;
    private int count;
    private String status;
    private String itemType;
    private int itemWorldMatrixCol;
    private int itemWorldMatrixRow;
    private String itemInventoryId;
    private boolean inInventory;

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

    @JsonProperty("itemWorldMatrixCol")
    public int getItemWorldMatrixCol()
    {
        return itemWorldMatrixCol;
    }

    @JsonProperty("itemWorldMatrixRow")
    public int getItemWorldMatrixRow()
    {
        return itemWorldMatrixRow;
    }

    @JsonProperty("itemInventoryId")
    public String getItemInventoryId()
    {
        return itemInventoryId;
    }

    @JsonProperty("inInventory")
    public boolean getInInventory()
    {
        return inInventory;
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

    @JsonProperty("itemWorldMatrixCol")
    public void setItemWorldMatrixCol(int itemWorldMatrixCol)
    {
        this.itemWorldMatrixCol = itemWorldMatrixCol;
    }

    @JsonProperty("itemWorldMatrixRow")
    public void setItemWorldMatrixRow(int itemWorldMatrixRow)
    {
        this.itemWorldMatrixRow = itemWorldMatrixRow;
    }

    @JsonProperty("itemInventoryId")
    public void setItemInventoryId(String itemInventoryId)
    {
        this.itemInventoryId = itemInventoryId;
    }

    @JsonProperty("inInventory")
    public void setInInventory(boolean inInventory)
    {
        this.inInventory = inInventory;
    }
}
