package org.game;

import org.individual.models.MovingDirection;
import org.individual.Player;
import org.inventory.PlayerInventory;
import org.inventory.models.PlayerInventoryModel;
import org.world.models.WorldAssets;
import org.worlditems.models.WorldItemTypes;
import org.worlditems.models.WorldItemsAssets;

import java.awt.Rectangle;
import java.util.List;
import java.util.Objects;

import static org.game.GamePanel.tileSize;

public class CollisionChecker
{

    GamePanel gamePanel;
    public Rectangle collisionArea;
    private final List<WorldItemTypes> WorldAssetsTypesSkippedOnCollisionCheckList;

     public CollisionChecker(GamePanel gamePanel)
     {
         this.gamePanel = gamePanel;
         this.collisionArea = new Rectangle();
         this.collisionArea.x = 0;
         this.collisionArea.y = 0;
         this.collisionArea.height = tileSize;
         this.collisionArea.width = tileSize;
         this.WorldAssetsTypesSkippedOnCollisionCheckList = List.of(WorldItemTypes.DOOR);
     }

    public Boolean checkPlayerCollisionWithObject(Player player, int objectPositionX, int objectPositionY)
    {
        // player collision rectangle
        int playerLeftX = player.positionX + player.collisionArea.x;
        int playerRightX = player.positionX + player.collisionArea.x + player.collisionArea.width;
        int playerTopY = player.positionY + player.collisionArea.y;
        int playerBottomY = player.positionY + player.collisionArea.y + player.collisionArea.height;

        // object collision rectangle
        int objectLeftX = objectPositionX + this.collisionArea.x;
        int objectRightX = objectPositionX + this.collisionArea.x + player.collisionArea.width;
        int objectTopY = objectPositionY + this.collisionArea.y;
        int objectBottomY = objectPositionY + this.collisionArea.y + player.collisionArea.height;

        // player world matrix index
        int playerLeftCol = playerLeftX / tileSize;
        int playerRightCol = playerRightX / tileSize;
        int playerTopRow = playerTopY / tileSize;
        int playerBottomRow = playerBottomY / tileSize;

        // object world matrix index
        int objectLeftCol = objectLeftX / tileSize;
        int objectRightCol = objectRightX / tileSize;
        int objectTopRow = objectTopY / tileSize;
        int objectBottomRow = objectBottomY / tileSize;

        // default value if none of condition are meet
        boolean hasPlayerCollidedWithObject = false;

        if (playerLeftCol == objectLeftCol && playerTopRow == objectTopRow)
        {
            hasPlayerCollidedWithObject = true;
        }
        else if (playerRightCol == objectRightCol && playerBottomRow == objectBottomRow)
        {
            hasPlayerCollidedWithObject = true;
        }
        return hasPlayerCollidedWithObject;
    }

     public void checkTile(Player player, boolean checkWorldAssetItem)
     {
        int individualLeftX = player.positionX + player.collisionArea.x;
        int individualRightX = player.positionX + player.collisionArea.x + player.collisionArea.width;
        int individualTopY = player.positionY + player.collisionArea.y;
        int individualBottomY = player.positionY + player.collisionArea.y + player.collisionArea.height;

        int individualLeftCol = individualLeftX / tileSize;
        int individualRightCol = individualRightX / tileSize;
        int individualTopRow = individualTopY / tileSize;
        int individualBottomRow = individualBottomY / tileSize;

         int[][] worldMatrix = gamePanel.gameWorld.worldMap;
         int[][] worldItemsMatrix = gamePanel.worldItems.worldMap;

         int[][] sourceWorldMatrix = checkWorldAssetItem ? worldItemsMatrix : worldMatrix;

        switch (player.movementDirection)
        {
            case MovingDirection.UP:
                individualTopRow = (individualTopY - player.speed) / tileSize;
                _checkIfWorldAssetsHaveCollisionOn(individualLeftCol, individualTopRow, individualRightCol, individualTopRow, player, sourceWorldMatrix, checkWorldAssetItem);
                break;
            case MovingDirection.DOWN:
                individualBottomRow = (individualBottomY + player.speed) / tileSize;
                _checkIfWorldAssetsHaveCollisionOn(individualLeftCol, individualBottomRow, individualRightCol, individualBottomRow, player, sourceWorldMatrix, checkWorldAssetItem);
                break;
            case MovingDirection.LEFT:
                individualLeftCol = (individualLeftX - player.speed) / tileSize;
                _checkIfWorldAssetsHaveCollisionOn(individualLeftCol, individualTopRow, individualLeftCol, individualBottomRow, player, sourceWorldMatrix, checkWorldAssetItem);
                break;
            case MovingDirection.RIGHT:
                individualRightCol = (individualRightX + player.speed) / tileSize;
                _checkIfWorldAssetsHaveCollisionOn(individualRightCol, individualTopRow, individualRightCol, individualBottomRow, player, sourceWorldMatrix, checkWorldAssetItem);
                break;
            case null, default:
                break;
        }
     }

    public boolean checkOneWorldItemCollision(Player player, int objectPositionX, int objectPositionY)
    {
        // player collision rectangle
        int playerLeftX = player.positionX + player.collisionArea.x;
        int playerRightX = player.positionX + player.collisionArea.x + player.collisionArea.width;
        int playerTopY = player.positionY + player.collisionArea.y;
        int playerBottomY = player.positionY + player.collisionArea.y + player.collisionArea.height;

        // object collision rectangle
        int objectLeftX = objectPositionX + this.collisionArea.x;
        int objectRightX = objectPositionX + this.collisionArea.x + player.collisionArea.width;
        int objectTopY = objectPositionY + this.collisionArea.y;
        int objectBottomY = objectPositionY + this.collisionArea.y + player.collisionArea.height;

        // player world matrix index
        int playerLeftCol = playerLeftX / tileSize;
        int playerRightCol = playerRightX / tileSize;
        int playerTopRow = playerTopY / tileSize;
        int playerBottomRow = playerBottomY / tileSize;

        // object world matrix index
        int objectLeftCol = objectLeftX / tileSize;
        int objectRightCol = objectRightX / tileSize;
        int objectTopRow = objectTopY / tileSize;
        int objectBottomRow = objectBottomY / tileSize;

        boolean worldItemCollidingWithPlayer = false;

        switch (player.movementDirection)
        {
            case MovingDirection.UP:
                playerTopRow = (playerTopY - player.speed) / tileSize;
                if (playerTopRow == objectBottomRow  && (playerRightCol == objectRightCol || playerLeftCol == objectLeftCol))
                {
                    worldItemCollidingWithPlayer = true;
                }
            break;
            case MovingDirection.DOWN:
                playerBottomRow = (playerBottomY + player.speed) / tileSize;
                if (playerBottomRow == objectTopRow  && (playerRightCol == objectRightCol || playerLeftCol == objectLeftCol))
                {
                    worldItemCollidingWithPlayer = true;
                }
                break;
            case MovingDirection.LEFT:
                playerLeftCol = (playerLeftX - player.speed) / tileSize;
                if (playerLeftCol == objectRightCol && playerBottomRow == objectBottomRow)
                {
                    worldItemCollidingWithPlayer = true;
                }
                break;
            case MovingDirection.RIGHT:
                playerRightCol = (playerRightX + player.speed) / tileSize;
                if (playerRightCol == objectLeftCol  && playerBottomRow == objectBottomRow)
                {
                    worldItemCollidingWithPlayer = true;
                }
                break;
            case null, default:
                break;
        }
        return worldItemCollidingWithPlayer;
    }

     private void _checkIfWorldAssetsHaveCollisionOn(int assetOneCol, int assetOneRow, int assetTwoCol, int assetTwoRow, Player player, int[][] sourceWorldMatrix, boolean checkWorldAssetItem)
     {
         int worldAssetOne = _getWorldAssetIndex(assetOneCol, assetOneRow, sourceWorldMatrix);
         int worldAssetTwo = _getWorldAssetIndex(assetTwoCol, assetTwoRow, sourceWorldMatrix);
         if(!checkWorldAssetItem)
         {
             WorldAssets worldItemsOne = WorldAssets.getWorldAssetByIndex(worldAssetOne);
             WorldAssets worldItemsTwo = WorldAssets.getWorldAssetByIndex(worldAssetTwo);
             player.activateCollision = worldItemsOne != null &&  worldItemsOne.getSolidStopOnCollisionWithPlayer() || worldItemsTwo != null && worldItemsTwo.getSolidStopOnCollisionWithPlayer();
         }
         else
         {
             WorldItemsAssets worldItemsOne = WorldItemsAssets.getWorldItemAssetByIndex(worldAssetOne);
             WorldItemsAssets worldItemsTwo = WorldItemsAssets.getWorldItemAssetByIndex(worldAssetTwo);
             if (worldItemsOne != null && worldItemsOne.getSolidStopOnCollisionWithPlayer() || worldItemsTwo != null && worldItemsTwo.getSolidStopOnCollisionWithPlayer())
             {
                 this._checkWorldItemsCollision(player, Objects.requireNonNull(worldItemsOne != null ? worldItemsOne : worldItemsTwo));
             }
         }
     }

    public void _checkWorldItemsCollision(Player player, WorldItemsAssets worldItemsAssets)
    {
        PlayerInventory playerInventory = player.playerInventory;
        if (this.WorldAssetsTypesSkippedOnCollisionCheckList.contains(WorldItemTypes.valueOf(worldItemsAssets.getItemType())))
        {
            // disable world item solid state if it has getDependsOnAssetId of one of the items present in player inventory
            String itemAssetNameById = WorldItemsAssets.getWorldItemAssetNameById(worldItemsAssets.getDependencyOnAssetId());
            PlayerInventoryModel playerInventoryModel = playerInventory.getInventoryItemByName(itemAssetNameById);
            player.activateCollision = playerInventoryModel == null || !playerInventoryModel.getInInventory();
        }
        else
        {
            player.activateCollision = true;
        }
    }

     private int _getWorldAssetIndex(int assetCol, int assetRow, int[][] worldMatrix)
     {
         if (worldMatrix.length > assetCol && assetCol > -1 && assetRow > -1 && worldMatrix.length > assetRow)
         {
             return worldMatrix[assetCol][assetRow];
         }
         return worldMatrix[0][0];
     }
}
