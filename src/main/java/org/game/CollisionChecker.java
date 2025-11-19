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
    private final List<WorldItemTypes> WorldAssetsTypesSkippedOnCollisionCheckList;

     public CollisionChecker(GamePanel gamePanel)
     {
         this.gamePanel = gamePanel;
         this.WorldAssetsTypesSkippedOnCollisionCheckList = List.of(WorldItemTypes.DOOR);
     }

    public boolean isPlayerCollidingWithIndividual(Player player, Rectangle recTwo)
    {
        Rectangle recOne = new Rectangle();

        recOne.x = player.playerScreenX + player.collisionArea.x - 2;
        recOne.y = player.playerScreenY + player.collisionArea.y;
        recOne.width = player.collisionArea.width;
        recOne.height = player.collisionArea.height;

//        gamePanel.testPositionX = recOne.x;
//        gamePanel.testPositionY = recOne.y;
//        gamePanel.testWith = recOne.width;
//        gamePanel.testHeight = recOne.height;
//        gamePanel.testCollisionArea = true;

        return this.isEnemyUnderAttack(recOne, recTwo);
    }

    public boolean isEnemyUnderAttack(Rectangle recOne, Rectangle recTwo)
    {
        return recTwo != null && recOne != null &&
                recOne.x < recTwo.x + recTwo.width &&
                recOne.x + recOne.width > recTwo.x &&
                recOne.y < recTwo.y + recTwo.height &&
                recOne.y + recOne.height > recTwo.y;
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

    private void _checkWorldItemsCollision(Player player, WorldItemsAssets worldItemsAssets)
    {
        if (this.WorldAssetsTypesSkippedOnCollisionCheckList.contains(WorldItemTypes.valueOf(worldItemsAssets.getItemType())))
        {
            PlayerInventory playerInventory = player.playerInventory;
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
