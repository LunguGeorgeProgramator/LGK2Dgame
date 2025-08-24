package org.game;

import org.individual.Individual;
import org.world.WorldAssets;

import static org.game.GamePanel.tileSize;

public class CollisionChecker
{

    GamePanel gamePanel;

     public CollisionChecker(GamePanel gamePanel)
     {
         this.gamePanel = gamePanel;
     }

     public void checkTile(Individual individual)
     {
        int individualLeftX = individual.positionX + individual.collisionArea.x;
        int individualRightX = individual.positionX + individual.collisionArea.x + individual.collisionArea.width;
        int individualTopY = individual.positionY + individual.collisionArea.y;
        int individualBottomY = individual.positionY + individual.collisionArea.y + individual.collisionArea.height;

        int individualLeftCol = individualLeftX / tileSize;
        int individualRightCol = individualRightX / tileSize;
        int individualTopRow = individualTopY / tileSize;
        int individualBottomRow = individualBottomY / tileSize;

        int worldAssetOne;
        int worldAssetTwo;

        switch (individual.movementDirection)
        {
            case "up":
                individualTopRow = (individualTopY - individual.speed) / tileSize;
                worldAssetOne = getWorldAssetIndex(individualLeftCol, individualTopRow);
                worldAssetTwo = getWorldAssetIndex(individualRightCol, individualTopRow);
                checkIfWorldAssetsHaveCollisionOn(worldAssetOne, worldAssetTwo, individual);
                break;
            case "down":
                individualBottomRow = (individualBottomY + individual.speed) / tileSize;
                worldAssetOne = getWorldAssetIndex(individualLeftCol, individualBottomRow);
                worldAssetTwo = getWorldAssetIndex(individualRightCol, individualBottomRow);
                checkIfWorldAssetsHaveCollisionOn(worldAssetOne, worldAssetTwo, individual);
                break;
            case "left":
                individualLeftCol = (individualLeftX - individual.speed) / tileSize;
                worldAssetOne = getWorldAssetIndex(individualLeftCol, individualTopRow);
                worldAssetTwo = getWorldAssetIndex(individualLeftCol, individualBottomRow);
                checkIfWorldAssetsHaveCollisionOn(worldAssetOne, worldAssetTwo, individual);
                break;
            case "right":
                individualRightCol = (individualRightX + individual.speed) / tileSize;
                worldAssetOne = getWorldAssetIndex(individualRightCol, individualTopRow);
                worldAssetTwo = getWorldAssetIndex(individualRightCol, individualBottomRow);
                checkIfWorldAssetsHaveCollisionOn(worldAssetOne, worldAssetTwo, individual);
                break;
            case null, default:
                break;
        }
     }

     private void checkIfWorldAssetsHaveCollisionOn(int worldAssetOne, int worldAssetTwo, Individual individual)
     {
         if(WorldAssets.getWorldAssetByIndex(worldAssetOne).getCollision() || WorldAssets.getWorldAssetByIndex(worldAssetTwo).getCollision())
         {
             individual.activateCollision = true;
         }
     }

     private int getWorldAssetIndex(int assetCol, int assetRow)
     {
         int[][] worldMatrix = gamePanel.gameWorld.worldMap;
         if (worldMatrix.length > assetCol && assetCol > -1 && assetRow > -1 && worldMatrix.length > assetRow)
         {
             return worldMatrix[assetCol][assetRow];
         }
         return worldMatrix[0][0];
     }
}
