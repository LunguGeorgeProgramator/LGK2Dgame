package org.game;

import org.individual.Individual;
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

    public boolean areRectanglesIntersecting(Rectangle recOne, Rectangle recTwo)
    {
        return recTwo != null && recOne != null &&
            recOne.x < recTwo.x + recTwo.width &&
            recOne.x + recOne.width > recTwo.x &&
            recOne.y < recTwo.y + recTwo.height &&
            recOne.y + recOne.height > recTwo.y;
    }

    public MovingDirection rectanglesIntersectingOnDirection(Rectangle recOne, Rectangle recTwo)
    {
        if (this.areRectanglesIntersecting(recOne, recTwo))
        {
            float overlapLeft   = (recOne.x + recOne.width) - recTwo.x;
            float overlapRight  = (recTwo.x + recTwo.width) - recOne.x;
            float overlapTop    = (recOne.y + recOne.height) - recTwo.y;
            float overlapBottom = (recTwo.y + recTwo.height) - recOne.y;

            float minOverlap = Math.min(
                Math.min(overlapLeft, overlapRight),
                Math.min(overlapTop, overlapBottom)
            );

            if (minOverlap == overlapLeft)
            {
                return MovingDirection.LEFT;
            }
            else if (minOverlap == overlapRight)
            {
                return MovingDirection.RIGHT;
            }
            else if (minOverlap == overlapTop)
            {
                return MovingDirection.UP;
            }
            else
            {
                return MovingDirection.DOWN;
            }
        }
        return null;
    }

    public void checkTile(Individual individual, boolean checkWorldAssetItem)
     {
//         gamePanel.testPositionX = individual.collisionArea.x;
//         gamePanel.testPositionY = individual.collisionArea.y;
//         gamePanel.testWith = individual.collisionArea.width;
//         gamePanel.testHeight = individual.collisionArea.height;
//         gamePanel.testCollisionArea = true;

         int errorMargin = (individual instanceof  Player ? 0 : 1);
         int individualLeftX = individual.positionX + individual.collisionArea.x + errorMargin;
         int individualRightX = individual.positionX + individual.collisionArea.x + individual.collisionArea.width - errorMargin;
         int individualTopY = individual.positionY + individual.collisionArea.y + errorMargin;
         int individualBottomY = individual.positionY + individual.collisionArea.y + individual.collisionArea.height - errorMargin;

         int individualLeftCol = individualLeftX / tileSize;
         int individualRightCol = individualRightX / tileSize;
         int individualTopRow = individualTopY / tileSize;
         int individualBottomRow = individualBottomY / tileSize;

         int[][] worldMatrix = gamePanel.gameWorld.worldMap;
         int[][] worldItemsMatrix = gamePanel.worldItems.worldMap;
         int[][] sourceWorldMatrix = checkWorldAssetItem ? worldItemsMatrix : worldMatrix;

         switch (individual.movementDirection)
         {
             case MovingDirection.UP:
                individualTopRow = (individualTopY - individual.speed) / tileSize;
                _checkIfWorldAssetsHaveCollisionOn(individualLeftCol, individualTopRow, individualRightCol, individualTopRow, individual, sourceWorldMatrix, checkWorldAssetItem);
                break;
             case MovingDirection.DOWN:
                individualBottomRow = (individualBottomY + individual.speed) / tileSize;
                _checkIfWorldAssetsHaveCollisionOn(individualLeftCol, individualBottomRow, individualRightCol, individualBottomRow, individual, sourceWorldMatrix, checkWorldAssetItem);
                break;
             case MovingDirection.LEFT:
                individualLeftCol = (individualLeftX - individual.speed) / tileSize;
                _checkIfWorldAssetsHaveCollisionOn(individualLeftCol, individualTopRow, individualLeftCol, individualBottomRow, individual, sourceWorldMatrix, checkWorldAssetItem);
                break;
             case MovingDirection.RIGHT:
                individualRightCol = (individualRightX + individual.speed) / tileSize;
                _checkIfWorldAssetsHaveCollisionOn(individualRightCol, individualTopRow, individualRightCol, individualBottomRow, individual, sourceWorldMatrix, checkWorldAssetItem);
                break;
             case null, default:
                break;
         }
     }

     private void _checkIfWorldAssetsHaveCollisionOn(int assetOneCol, int assetOneRow, int assetTwoCol, int assetTwoRow, Individual individual, int[][] sourceWorldMatrix, boolean checkWorldAssetItem)
     {
         int worldAssetOne = _getWorldAssetIndex(assetOneCol, assetOneRow, sourceWorldMatrix);
         int worldAssetTwo = _getWorldAssetIndex(assetTwoCol, assetTwoRow, sourceWorldMatrix);
         if(!checkWorldAssetItem)
         {
             WorldAssets worldItemsOne = WorldAssets.getWorldAssetByIndex(worldAssetOne);
             WorldAssets worldItemsTwo = WorldAssets.getWorldAssetByIndex(worldAssetTwo);
             individual.activateCollision = worldItemsOne != null && worldItemsOne.getSolidStopOnCollisionWithPlayer() || worldItemsTwo != null && worldItemsTwo.getSolidStopOnCollisionWithPlayer();
         }
         else
         {
             WorldItemsAssets worldItemsOne = WorldItemsAssets.getWorldItemAssetByIndex(worldAssetOne);
             WorldItemsAssets worldItemsTwo = WorldItemsAssets.getWorldItemAssetByIndex(worldAssetTwo);
             if (worldItemsOne != null && worldItemsOne.getSolidStopOnCollisionWithPlayer() || worldItemsTwo != null && worldItemsTwo.getSolidStopOnCollisionWithPlayer())
             {
                  this._checkWorldItemsCollision(individual, Objects.requireNonNull(worldItemsOne != null ? worldItemsOne : worldItemsTwo));
             }
         }
     }

    private void _checkWorldItemsCollision(Individual individual, WorldItemsAssets worldItemsAssets)
    {
        if (this.WorldAssetsTypesSkippedOnCollisionCheckList.contains(WorldItemTypes.valueOf(worldItemsAssets.getItemType())))
        {
            if (individual instanceof Player player)
            {
                PlayerInventory playerInventory = player.playerInventory;
                // disable world item solid state if it has getDependsOnAssetId of one of the items present in player inventory
                String itemAssetNameById = WorldItemsAssets.getWorldItemAssetNameById(worldItemsAssets.getDependencyOnAssetId());
                PlayerInventoryModel playerInventoryModel = playerInventory.getInventoryItemByName(itemAssetNameById);
                individual.activateCollision = playerInventoryModel == null || !playerInventoryModel.getInInventory();
            }
            else
            {
                individual.activateCollision = true;
            }
        }
        else
        {
            individual.activateCollision = true;
        }
    }

     private int _getWorldAssetIndex(int assetCol, int assetRow, int[][] worldMatrix)
     {
         if (worldMatrix.length > assetCol && assetCol > -1 && assetRow > -1 && worldMatrix[assetCol].length > assetRow)
         {
             return worldMatrix[assetCol][assetRow];
         }
         return worldMatrix[0][0];
     }
}
