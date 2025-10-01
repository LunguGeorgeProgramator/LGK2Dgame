package org.inventory;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.inventory.models.PlayerInventoryModel;
import org.worlditems.WorldItem;

import java.io.IOException;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.helpers.ToolsHelper.getJsonFileFromAssets;

public class PlayerInventory
{
    private final ObjectMapper mapper;
    private final File inventoryJsonFile;
    private List<PlayerInventoryModel> playerInventoryList;
    private static String INVENTORY_ID_FORMAT = "%d%d%d";
    private static final String INVENTORY_RESOURCE_PATH = "config/inventory.json";
    private static final String INVENTORY_RESOURCE_PATH_NPT_FOUND_ERROR_MESSAGE = "inventory.json not found please check resource.";


    public PlayerInventory()
    {
        this.inventoryJsonFile = getJsonFileFromAssets(INVENTORY_RESOURCE_PATH, INVENTORY_RESOURCE_PATH_NPT_FOUND_ERROR_MESSAGE);
        this.mapper = new ObjectMapper();
        this.playerInventoryList = openInventory();
    }

    public PlayerInventoryModel getInventoryItemByInventoryId(String itemInventoryId)
    {
        for (PlayerInventoryModel playerInventory : this.playerInventoryList)
        {
            if (playerInventory.getItemInventoryId().equals(itemInventoryId))
            {
                return playerInventory;
            }
        }
        return null;
    }

    public PlayerInventoryModel getInventoryItemByName(String itemName)
    {
        for (PlayerInventoryModel playerInventory : this.playerInventoryList)
        {
            if (playerInventory.getItemName().equals(itemName) && playerInventory.getInInventory())
            {
                return playerInventory;
            }
        }
        return null;
    }

    public String getWorldItemInventoryId(WorldItem worldItem)
    {
        return String.format(INVENTORY_ID_FORMAT, worldItem.itemAssetId, worldItem.itemWorldMatrixColIndex, worldItem.itemWorldMatrixRowIndex);
    }

    private void updateModelDependingOnActionRequested(PlayerInventoryModel itemToUseAsValue, PlayerInventoryModel itemToUpdate, String updateAction)
    {
        switch (updateAction)
        {
            case "addToValue":
                itemToUpdate.addToCount(itemToUseAsValue.getCount());
            case "toRemoveValue":
                itemToUpdate.removeFromCount(itemToUseAsValue.getCount());
            case null:
            default:
                itemToUpdate.setCount(itemToUseAsValue.getCount());
        }
    }

    public List<PlayerInventoryModel> updateInventoryItemByIncrement(PlayerInventoryModel playerInventoryItem)
    {
        return updateInventoryItem(playerInventoryItem, "addToValue");
    }

    public List<PlayerInventoryModel> updateInventoryItemByDecrement(PlayerInventoryModel playerInventoryItem)
    {
        return updateInventoryItem(playerInventoryItem, "toRemoveValue");
    }

    public List<PlayerInventoryModel> updateInventoryItem(PlayerInventoryModel playerInventoryItem)
    {
        return updateInventoryItem(playerInventoryItem, null);
    }

    public List<PlayerInventoryModel> updateInventoryItem(PlayerInventoryModel playerInventoryItem, String updateAction)
    {
        List<PlayerInventoryModel> items = this.playerInventoryList;
        for (PlayerInventoryModel playerInventoryModel : items)
        {
            if (playerInventoryModel.getItemInventoryId().equals(playerInventoryItem.getItemInventoryId()))
            {
                playerInventoryModel.setStatus(playerInventoryItem.getStatus());
                updateModelDependingOnActionRequested(playerInventoryItem, playerInventoryModel, updateAction);
                playerInventoryModel.setItemType(playerInventoryItem.getItemType());
                break;
            }
        }
        try
        {
            mapper.writerWithDefaultPrettyPrinter().writeValue(this.inventoryJsonFile, items);
            this.playerInventoryList = items;
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        return items;
    }

    public List<PlayerInventoryModel> openInventory()
    {
        List<PlayerInventoryModel> items = new ArrayList<>();
        try
        {
            items = mapper.readValue(this.inventoryJsonFile, new TypeReference<>() {});
            this.playerInventoryList = items;
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }
        return items;
    }

    public List<PlayerInventoryModel> addToInventory(PlayerInventoryModel playerInventoryModel)
    {
        List<PlayerInventoryModel> items = this.playerInventoryList;
        PlayerInventoryModel foundPlayerInventoryItem = getInventoryItemByName(playerInventoryModel.getItemInventoryId());
        if (foundPlayerInventoryItem != null)
        { // item already added in inventory
            return items;
        }
        try
        {
            items.add(playerInventoryModel);
            mapper.writerWithDefaultPrettyPrinter().writeValue(this.inventoryJsonFile, items);
            this.playerInventoryList = items;
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        return items;
    }

    public void removeAllFromInventory()
    {
        List<PlayerInventoryModel> emptyInventoryList = new ArrayList<>();
        try
        {
            mapper.writerWithDefaultPrettyPrinter().writeValue(this.inventoryJsonFile, emptyInventoryList);
            this.playerInventoryList = emptyInventoryList;
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }
}
