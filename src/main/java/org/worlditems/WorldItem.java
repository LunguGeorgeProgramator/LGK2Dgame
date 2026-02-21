package org.worlditems;

import org.game.CollisionChecker;
import org.game.GamePanel;
import org.imageAssets.models.ImageModel;
import org.individual.Individual;
import org.individual.Player;
import org.inventory.PlayerInventory;
import org.inventory.models.PlayerInventoryModel;
import org.world.models.WorldType;
import org.worlditems.models.WorldItemsAssets;
import org.worlditems.models.WorldItemAssetsModel;
import org.worlditems.models.LoadWorldItemType;
import org.worlditems.models.WorldItemTypes;
import org.worlditems.models.DungeonWorldItemsAssets;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.Map;

import static org.game.GamePanel.tileSize;
import static org.helpers.ToolsHelper.checkIfAssetIsInsideTheBoundary;

public class WorldItem extends Individual
{

    WorldItemsAssets[] worldItemsAssets;
    public final String itemAssetName;
    public final int itemAssetId;
    public final int itemWorldMatrixRowIndex;
    public final int itemWorldMatrixColIndex;
    public final int itemDependencyOnAssetId;
    public final String itemAssetType;
    private final GamePanel gamePanel;
    private final Player player;
    public final int positionX;
    public final int positionY;
    private final Map<Integer, WorldItemAssetsModel> itemsAssetsMap;
    public final PlayerInventory playerInventory;
    private boolean hasPlayerCollidedWithItem = false;
    private String textShownOnInteractionWithItem;
    private final CollisionChecker collisionChecker;
    private Rectangle dungeonsEntryCollisionArea;
    private int worldItemAssetPositionX;
    private int worldItemAssetPositionY;
    private boolean hideWorldItem;
    private final Map<ImageModel, BufferedImage> worldItemsImagesAssetsBufferedImageMap;

    public WorldItem(
            GamePanel gamePanel,
            Player player,
            PlayerInventory playerInventory,
            String itemAssetName,
            int itemAssetId,
            int itemWorldMatrixRowIndex,
            int itemWorldMatrixColIndex,
            int itemDependencyOnAssetId,
            String itemAssetType,
            int worldItemPositionX,
            int worldItemPositionY,
            Map<Integer, WorldItemAssetsModel> itemsAssetsMap,
            LoadWorldItemType loadWorldItemType
    )
    {
        super(worldItemPositionX, worldItemPositionY, gamePanel.player.speed);
        boolean isDungeonType = loadWorldItemType.equals(LoadWorldItemType.DUNGEON_WORLD);
        this.worldItemsImagesAssetsBufferedImageMap =
                isDungeonType ? gamePanel.imageLoader.getDungeonWorldItemsImages()
                : gamePanel.imageLoader.getWorldItemsAssetsImages();
        this.itemAssetId = itemAssetId;
        this.itemWorldMatrixRowIndex = itemWorldMatrixRowIndex;
        this.itemWorldMatrixColIndex = itemWorldMatrixColIndex;
        this.itemDependencyOnAssetId = itemDependencyOnAssetId;
        this.gamePanel = gamePanel;
        this.player = player;
        this.itemAssetName = itemAssetName;
        this.itemAssetType = itemAssetType;
        this.positionX = worldItemPositionX;
        this.positionY = worldItemPositionY;
        this.itemsAssetsMap = itemsAssetsMap;
        this.worldItemsAssets = WorldItemsAssets.values();
        this.playerInventory = playerInventory;
        this.collisionChecker = gamePanel.collisionChecker;
        this.buildEnemyCollisionArea();
    }

    private void buildEnemyCollisionArea()
    {
        this.collisionArea = new Rectangle();
        this.collisionArea.x = 0;
        this.collisionArea.y = 0;
        this.collisionArea.height = tileSize;
        this.collisionArea.width = tileSize;
    }

    private void buildDungeonsEntryCollisionArea()
    {
        this.dungeonsEntryCollisionArea = new Rectangle();
        this.dungeonsEntryCollisionArea.x = this.worldItemAssetPositionX + (tileSize / 3);
        this.dungeonsEntryCollisionArea.y = this.worldItemAssetPositionY + (tileSize / 3);
        this.dungeonsEntryCollisionArea.height = (tileSize / 2) / 2;
        this.dungeonsEntryCollisionArea.width = (tileSize / 2) / 2;
    }

    private boolean hideItemsUntilBossEnemyIsDead()
    {
        if (itemAssetType.equals(WorldItemTypes.CAVE_DUNGEON_ENTRY_WAY.name()))
        {
            return !this.gamePanel.spiderBossEnemy.isBoosEnemyDead;
        }
        if (itemAssetName.equals(DungeonWorldItemsAssets.GOLD_SWORD.name()))
        {
            return !this.gamePanel.grimBoosEnemy.isBoosEnemyDead;
        }
        return false;
    }

    private void updateWorldItemXYPosition()
    {
        this.worldItemAssetPositionX = this.positionX - this.player.positionX + this.player.playerScreenX;
        this.worldItemAssetPositionY = this.positionY - this.player.positionY + this.player.playerScreenY;
        this.collisionArea.x = this.worldItemAssetPositionX;
        this.collisionArea.y = this.worldItemAssetPositionY;
    }

    public void update()
    {
        this.updateWorldItemXYPosition();
        this.hideWorldItem = this.hideItemsUntilBossEnemyIsDead();
        this.setAssetNumber();
        if(checkIfAssetIsInsideTheBoundary(this.positionX, this.positionY, this.player, tileSize * 4) && !this.hideWorldItem)
        {
            this.hasPlayerCollidedWithItem = this.collisionChecker.areRectanglesIntersecting(this.player.worldItemCollisionArea, this.collisionArea);
            if (this.hasPlayerCollidedWithItem)
            {
                String itemInventoryId = this.playerInventory.getWorldItemInventoryId(this);
                PlayerInventoryModel playerInventoryModel = this.playerInventory.getInventoryItemByInventoryId(itemInventoryId);
                if (playerInventoryModel != null)
                {
                    playerInventoryModel.setCount(1);
                    playerInventoryModel.setInInventory(true);
                    this.playerInventory.updateInventoryItem(playerInventoryModel);
                }
                this._playerInteractionWithDungeonEntries();
            }
            this.healingItemsCollisionBehavior();
        }
    }

    private void _playerInteractionWithDungeonEntries()
    {
        if (itemAssetType.equals(WorldItemTypes.CAVE_DUNGEON_ENTRY_WAY.name()))
        {
            this._spawnPlayerInsideWorldByWorldType(WorldType.CAVE_DUNGEON);
        }
        else if (itemAssetType.equals(WorldItemTypes.WATER_DUNGEON_ENTRY_WAY.name()))
        {
            this._spawnPlayerInsideWorldByWorldType(WorldType.WATER_DUNGEON);
        }
        else if (itemAssetType.equals(WorldItemTypes.DUNGEON_EXIT_ENTRY_WAY.name()))
        {
            this._spawnPlayerInsideWorldByWorldType(WorldType.MAIN_GAME);
        }
    }

    private void _spawnPlayerInsideWorldByWorldType(WorldType worldType)
    {
        this.buildDungeonsEntryCollisionArea();
        if (this.collisionChecker.areRectanglesIntersecting(this.player.worldItemCollisionArea, this.dungeonsEntryCollisionArea))
        {
            Map<WorldType, Map<String, Integer>> last = this.gamePanel.LastWorldTypeVisited;
            Map<String, Integer> coordinates = (last == null || last.isEmpty()) ? null : last.values().iterator().next();
            this.player.positionX = coordinates != null ? coordinates.getOrDefault("x", 100) - tileSize * 2 : 100;
            this.player.positionY = coordinates != null ? coordinates.getOrDefault("y", 100) + tileSize * 2 : 100;
            this.gamePanel.worldType = worldType;
            this.gamePanel.LastWorldTypeVisited = worldType.equals(WorldType.MAIN_GAME) ? Map.of() : Map.of(
                worldType, Map.of("x", this.positionX, "y", this.positionY)
            );
        }
    }

    private void setAssetNumber()
    {
        if (this.itemAssetType.equals(WorldItemTypes.CHEST.name()) || this.itemAssetType.equals(WorldItemTypes.DOOR.name()))
        { // TODO this can be rethink a bit, the 1,2 solution is not ok
            String itemAssetNameById = WorldItemsAssets.getWorldItemAssetNameById(this.itemDependencyOnAssetId);
            PlayerInventoryModel playerInventoryModel = playerInventory.getInventoryItemByName(itemAssetNameById);
            boolean isKeyInInventoryForChestAsset = playerInventoryModel != null && playerInventoryModel.getInInventory();
            this.dynamicAssetNumber = this.hasPlayerCollidedWithItem && isKeyInInventoryForChestAsset ? 1 : 2;
        }
        else
        {
            this.changeAssetNumberByFrameCounter(this.itemsAssetsMap.size());
        }
    }

    private void setTextShownOnCollision()
    {
        WorldItemAssetsModel worldItemAssetsModel = this.itemsAssetsMap.get(this.dynamicAssetNumber);
        String gameText = worldItemAssetsModel != null ? worldItemAssetsModel.getImageTextKey() : null;
        this.gamePanel.gameTextProvider.setTextColor(Color.WHITE);
        this.gamePanel.gameTextProvider.setTextPosition(this.player.playerScreenX - 50, this.player.playerScreenY - 20);
        String mapItemId = String.format("%s%s%s", this.itemAssetId, this.itemWorldMatrixRowIndex, this.itemWorldMatrixColIndex);
//        System.out.println(mapItemId);
        String textByMapId = this.gamePanel.gameTextProvider.getGameTextByKey(mapItemId);
        String textByItemKey = this.gamePanel.gameTextProvider.getGameTextByKey(gameText);
        this.textShownOnInteractionWithItem = textByMapId == null || textByMapId.trim().isEmpty() ? textByItemKey : textByMapId;
    }

    public void draw(Graphics2D g2D)
    {
        this.updateWorldItemXYPosition();
        // draw world item only if is inside the screen view
        if(checkIfAssetIsInsideTheBoundary(this.positionX, this.positionY, this.player, tileSize * 2) && !this.hideWorldItem)
        {
            WorldItemAssetsModel worldItemAssetsModel = this.itemsAssetsMap.get(this.dynamicAssetNumber);
            BufferedImage bufferedImage = null;
            if (worldItemAssetsModel != null)
            {
                ImageModel assetImageName = worldItemAssetsModel.getImageAssetPath();
                bufferedImage = worldItemsImagesAssetsBufferedImageMap.get(assetImageName);
            }
            if (bufferedImage != null)
            {
                g2D.drawImage(bufferedImage, this.collisionArea.x, this.collisionArea.y, null);
            }
//            this.gamePanel.drawTestDynamicRectangle(g2D, this.dungeonsEntryCollisionArea.x, this.dungeonsEntryCollisionArea.y, this.dungeonsEntryCollisionArea.width, this.dungeonsEntryCollisionArea.height);
//            this.gamePanel.drawTestDynamicRectangle(g2D, this.collisionArea.x, this.collisionArea.y, this.collisionArea.width, this.collisionArea.height);
        }
    }

    @Override
    public void drawLastInsideGamePanel(Graphics2D g2D)
    {
        this.drawWordItemCollisionText(g2D);
    }

    public void drawWordItemCollisionText(Graphics2D g2D)
    {
        this.setTextShownOnCollision();
        if (this.hasPlayerCollidedWithItem)
        {
            this.gamePanel.gameTextProvider.showTextInsideGame(g2D, this.textShownOnInteractionWithItem);
        }
    }

    private void healingItemsCollisionBehavior()
    {
        if (this.hasPlayerCollidedWithItem && this.itemAssetType.equals(WorldItemTypes.HEALTH_RESTORATION.name()))
        {
            this.player.playerHealth = this.player.playerMaxHealth;
        }
    }
}
