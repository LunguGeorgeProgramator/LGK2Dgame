package org.inventory;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.inventory.models.PlayerInventoryModel;

import java.io.IOException;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class PlayerInventory
{
    private final ObjectMapper mapper;
    private final File inventoryJsonFile;
    private List<PlayerInventoryModel> playerInventoryList;
    private static final String INVENTORY_RESOURCE_PATH = "config/inventory.json";
    private static final String INVENTORY_RESOURCE_PATH_NPT_FOUND_ERROR_MESSAGE = "inventory.json not found please check resource.";


    public PlayerInventory()
    {
        this.inventoryJsonFile = getJsonFileFromAssets();
        this.mapper = new ObjectMapper();
        this.playerInventoryList = openInventory();
    }

    public PlayerInventoryModel getInventoryItemByName(String itemName)
    {
        for (PlayerInventoryModel playerInventory : this.playerInventoryList)
        {
            if (playerInventory.getItemName().equals(itemName))
            {
                return playerInventory;
            }
        }
        return null;
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
            if (playerInventoryModel.getItemName().equals(playerInventoryItem.getItemName()))
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
        PlayerInventoryModel foundPlayerInventoryItem = getInventoryItemByName(playerInventoryModel.getItemName());
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

    private File getJsonFileFromAssets()
    {
        try
        {
            File jsonFile = new File(INVENTORY_RESOURCE_PATH);
            if (jsonFile == null)
            {
                throw new RuntimeException(INVENTORY_RESOURCE_PATH_NPT_FOUND_ERROR_MESSAGE);
            }
            return jsonFile;
        }
        catch (RuntimeException ex)
        {
            ex.printStackTrace();
        }
        return null;
    }
}
