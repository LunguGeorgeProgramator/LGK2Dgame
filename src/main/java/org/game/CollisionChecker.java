package org.game;

import org.individual.Individual;
import org.individual.Player;
import org.inventory.PlayerInventory;
import org.inventory.PlayerInventoryModel;
import org.world.WorldAssets;
import org.worlditems.WorldItemTypes;
import org.worlditems.WorldItemsAssets;

import java.awt.Rectangle;

import static org.game.GamePanel.tileSize;

public class CollisionChecker
{

    GamePanel gamePanel;
    public Rectangle collisionArea;

     public CollisionChecker(GamePanel gamePanel)
     {
         this.gamePanel = gamePanel;
         buildEnemyCollisionArea();
     }
// TODO: redo this is not ok
    public void checkWorldItems(Player player)
    {
        PlayerInventory playerInventory = player.playerInventory;
        PlayerInventoryModel playerInventoryModel = playerInventory.getInventoryItemByName("GOLD_KEY");
        boolean hasGoldKeyOpenDoor = playerInventoryModel != null && playerInventoryModel.getCount() > 0;


        for (WorldItemsAssets worldItemsAssets : WorldItemsAssets.values()) {
            if (!worldItemsAssets.getSolidStopOnCollisionWithPlayer())
            {
                continue;
            }

            // open door !!!
            if (worldItemsAssets.getItemType().equals(WorldItemTypes.DOOR.name()) && hasGoldKeyOpenDoor)
            {
                continue;
            }
            
            // player collision rectangle
            int playerLeftX = player.positionX + player.collisionArea.x;
            int playerRightX = player.positionX + player.collisionArea.x + player.collisionArea.width;
            int playerTopY = player.positionY + player.collisionArea.y;
            int playerBottomY = player.positionY + player.collisionArea.y + player.collisionArea.height;

            // item collision rectangle
            int itemLeftX = worldItemsAssets.getDefaultPositionX() + this.collisionArea.x;
            int itemRightX = worldItemsAssets.getDefaultPositionX() + this.collisionArea.x + player.collisionArea.width;
            int itemTopY = worldItemsAssets.getDefaultPositionY() + this.collisionArea.y;
            int itemBottomY = worldItemsAssets.getDefaultPositionY() + this.collisionArea.y + player.collisionArea.height;

            // player world matrix index
            int playerLeftCol = playerLeftX / tileSize;
            int playerRightCol = playerRightX / tileSize;
            int playerTopRow = playerTopY / tileSize;
            int playerBottomRow = playerBottomY / tileSize;

            // enemy world matrix index
            int enemyLeftCol = itemLeftX / tileSize;
            int enemyRightCol = itemRightX / tileSize;
            int enemyTopRow = itemTopY / tileSize;
            int enemyBottomRow = itemBottomY / tileSize;

            if (playerLeftCol == enemyRightCol && playerBottomRow == enemyBottomRow && player.movementDirection != null && player.movementDirection.equals("left"))
            {
                player.activateCollision = true;
            }
            else if (playerRightCol == enemyLeftCol  && playerBottomRow == enemyBottomRow && player.movementDirection != null && player.movementDirection.equals("right"))
            {
                player.activateCollision = true;
            }
            else if (playerTopRow == enemyBottomRow  && playerRightCol == enemyRightCol && player.movementDirection != null && player.movementDirection.equals("up"))
            {
                player.activateCollision = true;
            }
            else if (playerBottomRow == enemyTopRow  && playerLeftCol == enemyLeftCol && player.movementDirection != null && player.movementDirection.equals("down"))
            {
                player.activateCollision = true;
            }

        }
    }

    private void buildEnemyCollisionArea()
    { // make the collision area small that the player rectangle so upper corners will not hit solid world assets
        this.collisionArea = new Rectangle();
        this.collisionArea.x = 0;
        this.collisionArea.y = 0;
        this.collisionArea.height = tileSize;
        this.collisionArea.width = tileSize;
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
