package org.game;

import org.individual.Individual;
import org.world.WorldAssets;

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

        int individualLeftCol = individualLeftX / this.gamePanel.tileSize;
        int individualRightCol = individualRightX / this.gamePanel.tileSize;
        int individualTopRow = individualTopY / this.gamePanel.tileSize;
        int individualBottomRow = individualBottomY / this.gamePanel.tileSize;

        int worldAssetOne;
        int worldAssetTwo;

        switch (individual.movementDirection)
        {
            case "up":
                individualTopRow = (individualTopY - individual.speed) / gamePanel.tileSize;
                worldAssetOne = gamePanel.gameWorld.worldMap[individualLeftCol][individualTopRow];
                worldAssetTwo = gamePanel.gameWorld.worldMap[individualRightCol][individualTopRow];

                if (WorldAssets.getWorldAssetByIndex(worldAssetOne).getCollision() || WorldAssets.getWorldAssetByIndex(worldAssetTwo).getCollision())
                {
                    individual.activateCollision = true;
                }
                break;
            case "down":
                individualBottomRow = (individualBottomY + individual.speed) / gamePanel.tileSize;
                worldAssetOne = gamePanel.gameWorld.worldMap[individualLeftCol][individualBottomRow];
                worldAssetTwo = gamePanel.gameWorld.worldMap[individualRightCol][individualBottomRow];

                if (WorldAssets.getWorldAssetByIndex(worldAssetOne).getCollision() || WorldAssets.getWorldAssetByIndex(worldAssetTwo).getCollision())
                {
                    individual.activateCollision = true;
                }
                break;
            case "left":
                individualLeftCol = (individualLeftX - individual.speed) / gamePanel.tileSize;
                worldAssetOne = gamePanel.gameWorld.worldMap[individualLeftCol][individualTopRow];
                worldAssetTwo = gamePanel.gameWorld.worldMap[individualLeftCol][individualBottomRow];

                if (WorldAssets.getWorldAssetByIndex(worldAssetOne).getCollision() || WorldAssets.getWorldAssetByIndex(worldAssetTwo).getCollision())
                {
                    individual.activateCollision = true;
                }
                break;
            case "right":
                individualRightCol = (individualRightX + individual.speed) / gamePanel.tileSize;
                worldAssetOne = gamePanel.gameWorld.worldMap[individualRightCol][individualTopRow];
                worldAssetTwo = gamePanel.gameWorld.worldMap[individualRightCol][individualBottomRow];

                if (WorldAssets.getWorldAssetByIndex(worldAssetOne).getCollision() || WorldAssets.getWorldAssetByIndex(worldAssetTwo).getCollision())
                {
                    individual.activateCollision = true;
                }
                break;
            case null, default:
                break;
        }
     }
}
