package org.individual;

import org.game.CollisionChecker;
import org.game.GamePanel;
import org.game.models.GameState;
import org.helpers.KeyPressHelper;
import org.imageAssets.ImageLoader;
import org.imageAssets.models.ImageModel;
import org.individual.models.MovingDirection;
import org.individual.models.NonPlayerCharacterType;
import org.inventory.PlayerInventory;
import org.inventory.models.PlayerInventoryModel;
import org.worlditems.models.WorldItemsAssets;

import java.awt.Graphics2D;
import java.awt.BasicStroke;
import java.awt.Rectangle;
import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.game.GamePanel.tileSize;
import static org.game.GamePanel.screenWith;
import static org.game.GamePanel.screenHeight;
import static org.helpers.ToolsHelper.checkIfAssetIsInsideTheBoundary;

public class NonPlayerCharacter extends Individual
{
    private final static String INVENTORY_TITLE = "vendor-inventory-title";
    private final static String INVENTORY_NR_KEYS = "vendor-inventory-keys";
    private final static String INVENTORY_NR_RUBIES = "vendor-inventory-rubies";
    private final static String INVENTORY_SWORD = "vendor-inventory-sword";
    private final static String CLOSE_WINDOW_BUTTON_TEXT = "close-window-button-text";
    final private GamePanel gamePanel;
    final private ImageLoader imageLoader;
    final private Player player;
    final private PlayerInventory playerInventory;
    final private int npcId;
    final private NonPlayerCharacterType npcType;
    final private int maxDistanceAllowedToMove;
    final private List<MovingDirection> npcMovingDirectionList;
    final private Map<MovingDirection, Map<Integer, ImageModel>> npcAssetsMap;
    private BufferedImage npcAsset;
    private int worldNpcAssetPositionX;
    private int worldNpcAssetPositionY;
    private Rectangle collisionArea;
    private final CollisionChecker collisionChecker;
    private boolean closeVendorWindow = false;

    private int selectedIndex = 0;
    private final int[] selectableLines = { 2, 3, 4, 5 };
    private final KeyPressHelper keyDownPressHelper;
    private final KeyPressHelper keyUpPressHelper;
    private final KeyPressHelper keyEnterPressHelper;
    private final Map<Integer, WorldItemsAssets> playerInventoryItemsMap = new HashMap<>();
    {
        playerInventoryItemsMap.put(0, WorldItemsAssets.GOLD_KEY);
        playerInventoryItemsMap.put(1, WorldItemsAssets.RUBY);
        playerInventoryItemsMap.put(2, WorldItemsAssets.SWORD);
        playerInventoryItemsMap.put(3, null);
    }

    public NonPlayerCharacter(
        GamePanel gamePanel,
        int positionX,
        int positionY,
        int speed,
        int npcId,
        NonPlayerCharacterType npcType,
        int maxDistanceAllowedToMove,
        List<MovingDirection> npcMovingDirectionList,
        Map<MovingDirection, Map<Integer, ImageModel>> npcAssetsMap
    )
    {
        super(positionX, positionY, speed);
        this.gamePanel = gamePanel;
        this.imageLoader = gamePanel.imageLoader;
        this.player = gamePanel.player;
        this.playerInventory = gamePanel.playerInventory;
        this.collisionChecker = gamePanel.collisionChecker;
        this.npcId = npcId;
        this.npcType = npcType;
        this.maxDistanceAllowedToMove = maxDistanceAllowedToMove;
        this.npcMovingDirectionList = npcMovingDirectionList;
        this.npcAssetsMap = npcAssetsMap;
        this._buildCollisionArea();
        this.keyDownPressHelper = new KeyPressHelper();
        this.keyUpPressHelper = new KeyPressHelper();
        this.keyEnterPressHelper = new KeyPressHelper();
    }

    private void _buildCollisionArea()
    {
        this.collisionArea = new Rectangle();
        this.collisionArea.x = 0;
        this.collisionArea.y = 0;
        this.collisionArea.height = this.npcType.equals(NonPlayerCharacterType.VENDOR) ? tileSize * 2 : tileSize;
        this.collisionArea.width = tileSize;
    }

    @Override
    public void update()
    {
        if(checkIfAssetIsInsideTheBoundary(this.positionX, this.positionY, this.player, tileSize * 4))
        {
            this.worldNpcAssetPositionX = this.positionX - this.player.positionX + this.player.playerScreenX;
            this.worldNpcAssetPositionY = this.positionY - this.player.positionY + this.player.playerScreenY;
            this.collisionArea.x = this.worldNpcAssetPositionX;
            this.collisionArea.y = this.worldNpcAssetPositionY;

            this.activateCollision = this.collisionChecker.areRectanglesIntersecting(this.player.worldItemCollisionArea, this.collisionArea);

            if (this.npcType.equals(NonPlayerCharacterType.VENDOR))
            {
                if (this.activateCollision && !this.closeVendorWindow)
                {
                    this.gamePanel.setGameState(GameState.OPEN_VENDOR_INVENTORY);
                }

                if (!this.activateCollision)
                {
                    this.closeVendorWindow = false;
                }
            }

        }
    }

    @Override
    public void draw(Graphics2D g2D)
    {
        // draw enemy only if is inside the screen view
        if(checkIfAssetIsInsideTheBoundary(this.positionX, this.positionY, this.player, tileSize))
        {
            ImageModel npcAssetKeyName = this.npcAssetsMap.get(MovingDirection.STATIONARY).get(1);
            this.npcAsset = this.imageLoader.getNonPlayerAssetsImages().get(npcAssetKeyName);
            if (this.npcAsset != null && this.enemyHealth > 0)
            {
                g2D.drawImage(this.npcAsset, this.worldNpcAssetPositionX, this.worldNpcAssetPositionY, null);
            }

//        this.gamePanel.drawTestDynamicRectangle(g2D, this.collisionArea.x, this.collisionArea.y, this.collisionArea.width, this.collisionArea.height);
//        this.gamePanel.drawTestDynamicRectangle(g2D, this.attackArea.x, this.attackArea.y, this.attackArea.width, this.attackArea.height);
//        this.gamePanel.drawTestDynamicRectangle(g2D, this.detectionArea.x, this.detectionArea.y, this.detectionArea.width, this.detectionArea.height);
        }
    }

    @Override
    public void drawLastInsideGamePanel(Graphics2D g2D)
    {
        if(checkIfAssetIsInsideTheBoundary(this.positionX, this.positionY, this.player, tileSize * 4))
        {
            if (this.npcType.equals(NonPlayerCharacterType.VENDOR) && this.activateCollision && !this.closeVendorWindow && this.gamePanel.isGameState(GameState.OPEN_VENDOR_INVENTORY))
            {
                this.drawVendorInventoryWindow(g2D);
                if(this.gamePanel.keyBoardAndMouseHandler.closePressed)
                {
                    this.gamePanel.setGameState(GameState.RESUME_GAME);
                    this.closeVendorWindow = true;
                }
                if (this.keyUpPressHelper.isFirstPress(this.gamePanel.keyBoardAndMouseHandler.upKeyPressed))
                {
                    this.moveSelectionUp();
                }

                if (this.keyDownPressHelper.isFirstPress(this.gamePanel.keyBoardAndMouseHandler.downKeyPressed))
                {
                    this.moveSelectionDown();
                }

                if (this.keyEnterPressHelper.isFirstPress(this.gamePanel.keyBoardAndMouseHandler.enterKeyPressed))
                {
                    WorldItemsAssets worldItemsAssets = this.playerInventoryItemsMap.get(this.selectedIndex);
                    if (worldItemsAssets == null)
                    {
                        this.gamePanel.setGameState(GameState.RESUME_GAME);
                        this.closeVendorWindow = true;
                    }
                    else
                    {
                        this._addToPlayerInventory(worldItemsAssets);
                        if (worldItemsAssets.name().equals(WorldItemsAssets.RUBY.name()))
                        {
                            this.player.playerHealth = this.player.playerMaxHealth;
                        }
                    }
                }
            }
        }

    }

    private void moveSelectionUp()
    {
        selectedIndex--;
        if (selectedIndex < 0)
        {
            selectedIndex = selectableLines.length - 1;
        }
    }

    private void moveSelectionDown()
    {
        selectedIndex++;
        if (selectedIndex >= selectableLines.length)
        {
            selectedIndex = 0;
        }
    }

    private void _addToPlayerInventory(WorldItemsAssets  vendorItem)
    {
        String itemInventoryId = this.playerInventory.getWorldItemInventoryIdByStaticValue(vendorItem.getItemId(), -1, -1);
        PlayerInventoryModel playerInventoryModel = this.playerInventory.getInventoryItemByInventoryId(itemInventoryId);
        if (playerInventoryModel != null)
        {
            playerInventoryModel.setCount(1);
            this.playerInventory.updateInventoryItemByIncrement(playerInventoryModel);
        }
        else
        {
            PlayerInventoryModel playerInventoryModelAdd = getPlayerInventoryModel(vendorItem, itemInventoryId);
            this.playerInventory.addToInventory(playerInventoryModelAdd);
        }
    }

    private PlayerInventoryModel getPlayerInventoryModel(WorldItemsAssets  vendorItem, String itemInventoryId)
    {

        PlayerInventoryModel playerInventoryModelAdd = new PlayerInventoryModel();
        playerInventoryModelAdd.setItemName(vendorItem.name());
        playerInventoryModelAdd.setCount(1);
        playerInventoryModelAdd.setStatus("active");
        playerInventoryModelAdd.setItemType(vendorItem.getItemType());
        playerInventoryModelAdd.setItemId(vendorItem.getItemId());
        playerInventoryModelAdd.setItemWorldMatrixCol(-1);
        playerInventoryModelAdd.setItemWorldMatrixRow(-1);
        playerInventoryModelAdd.setItemInventoryId(itemInventoryId);
        playerInventoryModelAdd.setInInventory(true);
        return playerInventoryModelAdd;
    }


    private void drawVendorInventoryWindow(Graphics2D g2D)
    {
        int positionX = screenWith / 2 + tileSize;
        int positionY = screenHeight / 2 - tileSize;
        int height = tileSize * 3;
        int width = tileSize * 5;
        int lineSpacing = 20;
        int arc = 20;

        // Background
        g2D.setColor(new Color(43, 76, 75, 214));
        g2D.fillRoundRect(positionX, positionY, width, height, arc, arc);

        // Border
        g2D.setColor(new Color(7, 74, 7));
        g2D.setStroke(new BasicStroke(1));
        g2D.drawRoundRect(positionX, positionY, width, height, arc, arc);

        g2D.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 12));

        String[] lines = {
            gamePanel.gameTextProvider.getGameTextByKey(INVENTORY_TITLE), "",
            gamePanel.gameTextProvider.getGameTextByKey(INVENTORY_NR_KEYS),
            gamePanel.gameTextProvider.getGameTextByKey(INVENTORY_NR_RUBIES),
            gamePanel.gameTextProvider.getGameTextByKey(INVENTORY_SWORD),
            gamePanel.gameTextProvider.getGameTextByKey(CLOSE_WINDOW_BUTTON_TEXT)
        };

        for (int i = 0; i < lines.length; i++)
        {

            int textX = positionX + 20;
            int textY = positionY + 30 + i * lineSpacing;

            // Check if this line is selected
            boolean isSelected = false;
            for (int j = 0; j < selectableLines.length; j++)
            {
                if (selectableLines[j] == i && selectedIndex == j)
                {
                    isSelected = true;
                    break;
                }
            }

            // Highlight (CSS-like)
            if (isSelected) {
                g2D.setColor(new Color(255, 255, 255, 80)); // translucent highlight
                g2D.fillRoundRect(positionX + 10, textY - 12, width - 20, lineSpacing, 10, 10);
                g2D.setColor(Color.YELLOW);
            } else {
                g2D.setColor(Color.BLACK);
            }

            g2D.drawString(lines[i], textX, textY);
        }
    }
}
