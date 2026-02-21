package org.inventory;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.game.GamePanel;
import org.inventory.models.PlayerInventoryModel;
import org.worlditems.WorldItem;

import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Font;
import java.awt.BasicStroke;
import java.io.IOException;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.helpers.ToolsHelper.getJsonFileFromAssets;
import static org.game.GamePanel.tileSize;
import static org.game.GamePanel.screenWith;
import static org.game.GamePanel.screenHeight;

public class PlayerInventory
{
    private final ObjectMapper mapper;
    private final File inventoryJsonFile;
    private List<PlayerInventoryModel> playerInventoryList;
    private final static String INVENTORY_ID_FORMAT = "%d%d%d";
    private static final String INVENTORY_RESOURCE_PATH = "config/inventory.json";
    private static final String INVENTORY_RESOURCE_PATH_NPT_FOUND_ERROR_MESSAGE = "inventory.json not found please check resource.";
    private final GamePanel gamePanel;
    private final static String INVENTORY_TITLE = "player-inventory-title";
    private final static String INVENTORY_NR_KEYS = "player-inventory-number-keys";
    private final static String INVENTORY_NR_RUBIES = "player-inventory-number-rubies";
    private final static String INVENTORY_SWORD = "player-inventory-sword";
    private final static String INVENTORY_GOLD_SWORD = "player-inventory-gold-sword";

    public PlayerInventory(GamePanel gamePanel)
    {
        this.gamePanel = gamePanel;
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

    public String getWorldItemInventoryIdByStaticValue(int itemAssetId, int itemWorldMatrixColIndex, int itemWorldMatrixRowIndex)
    {
        return String.format(INVENTORY_ID_FORMAT, itemAssetId, itemWorldMatrixColIndex, itemWorldMatrixRowIndex);
    }

    private void updateModelDependingOnActionRequested(PlayerInventoryModel itemToUseAsValue, PlayerInventoryModel itemToUpdate, String updateAction)
    {
        switch (updateAction)
        {
            case "addToValue":
                itemToUpdate.addToCount(itemToUseAsValue.getCount());
                break;
            case "toRemoveValue":
                itemToUpdate.removeFromCount(itemToUseAsValue.getCount());
                break;
            case null:
            default:
                itemToUpdate.setCount(itemToUseAsValue.getCount());
                break;
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
        List<PlayerInventoryModel> items = openInventory();
        for (PlayerInventoryModel playerInventoryModel : items)
        {
            if (playerInventoryModel.getItemInventoryId().equals(playerInventoryItem.getItemInventoryId()))
            {
                playerInventoryModel.setStatus(playerInventoryItem.getStatus());
                updateModelDependingOnActionRequested(playerInventoryItem, playerInventoryModel, updateAction);
                playerInventoryModel.setItemType(playerInventoryItem.getItemType());
                playerInventoryModel.setInInventory(playerInventoryItem.getInInventory());
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

    public void drawPlayerInventoryWindow(Graphics2D g2D)
    {
        int positionX = screenWith / 2 + tileSize;
        int positionY = screenHeight / 2 - tileSize;
        int height = tileSize  * 3;
        int wight = tileSize * 5;
        int lineSpacing = 20; // pixels between lines
        int arc = 20; // adjust for more/less roundness

        int numberKeys = 0;
        int numberOfRubies = 0;
        boolean hasSword = false;
        boolean hasGoldSword = false;

        Color inventoryBackGroundColor = new Color(43, 76, 75, 214);
        g2D.setColor(inventoryBackGroundColor);
        g2D.fillRoundRect(positionX, positionY, wight, height, arc, arc);
        // Draw a solid border
        g2D.setColor(new Color(7, 74, 7));  // solid green
        g2D.setStroke(new BasicStroke(3));   // border thickness
        g2D.drawRoundRect(positionX, positionY, wight, height, arc, arc);
        // Text color
        g2D.setColor(Color.BLACK);
        g2D.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 10));

        for (PlayerInventoryModel playerInventoryModel : this.playerInventoryList)
        {
            switch (playerInventoryModel.getItemName())
            {
                case "SWORD":
                    hasSword = playerInventoryModel.getInInventory();
                    break;
                case "GOLD_SWORD":
                    hasGoldSword = playerInventoryModel.getInInventory();
                    break;
                case "RUBY":
                    if (playerInventoryModel.getInInventory())
                    {
                        numberOfRubies = playerInventoryModel.getCount();
                    }
                    break;
                case "GOLD_KEY":
                    if (playerInventoryModel.getInInventory())
                    {
                        numberKeys = playerInventoryModel.getCount();
                    }
                    break;
            }
        }

        String[] lines = {
            this.gamePanel.gameTextProvider.getGameTextByKey(INVENTORY_TITLE), "",
            this.gamePanel.gameTextProvider.getGameTextByKey(INVENTORY_NR_KEYS) + numberKeys,
            this.gamePanel.gameTextProvider.getGameTextByKey(INVENTORY_NR_RUBIES) + numberOfRubies,
            hasSword ? this.gamePanel.gameTextProvider.getGameTextByKey(INVENTORY_SWORD) : "",
            hasGoldSword ? this.gamePanel.gameTextProvider.getGameTextByKey(INVENTORY_GOLD_SWORD) : ""
        };

        for (int i = 0; i < lines.length; i++) {
            g2D.drawString(lines[i], positionX + 20, positionY + 30 + i * lineSpacing);
        }
    }
}
