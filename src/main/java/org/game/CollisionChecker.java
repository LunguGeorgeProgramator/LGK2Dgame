package org.game;

import org.individual.Individual;
import org.individual.MovingDirection;
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

    private void buildEnemyCollisionArea()
    { // make the collision area small that the player rectangle so upper corners will not hit solid world assets
        this.collisionArea = new Rectangle();
        this.collisionArea.x = 0;
        this.collisionArea.y = 0;
        this.collisionArea.height = tileSize;
        this.collisionArea.width = tileSize;
    }

    public void checkPlayerCollisionWithObject(Player player, int objectPositionX, int objectPositionY)
    {
        checkPlayerCollisionWithObject(player, objectPositionX, objectPositionY, false, true);
    }

    public Boolean checkPlayerCollisionWithObject(Player player, int objectPositionX, int objectPositionY, boolean hasPlayerCollidedWithObject, boolean checkedByLookingOnPlayerDirection)
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

        if (!checkedByLookingOnPlayerDirection)
        {
            if (playerLeftCol == objectLeftCol && playerTopRow == objectTopRow)
            {
                hasPlayerCollidedWithObject = true;
            }
            else if (playerRightCol == objectRightCol && playerBottomRow == objectBottomRow)
            {
                hasPlayerCollidedWithObject = true;
            }
            else
            {
                hasPlayerCollidedWithObject = false;
            }
        }
        else
        {
            switch (MovingDirection.getValueByString(player.movementDirection)) {
                case MovingDirection.LEFT:
                    if(playerLeftCol == objectRightCol && playerBottomRow == objectBottomRow)
                    {
                        player.activateCollision = true;
                        hasPlayerCollidedWithObject = true;
                    }
                    break;
                case MovingDirection.RIGHT:
                    if(playerRightCol == objectLeftCol  && playerBottomRow == objectBottomRow)
                    {
                        player.activateCollision = true;
                        hasPlayerCollidedWithObject = true;
                    }
                    break;
                case MovingDirection.UP:
                    if(playerTopRow == objectBottomRow  && playerRightCol == objectRightCol)
                    {
                        player.activateCollision = true;
                        hasPlayerCollidedWithObject = true;
                    }
                    break;
                case MovingDirection.DOWN:
                    if(playerBottomRow == objectTopRow  && playerLeftCol == objectLeftCol)
                    {
                        player.activateCollision = true;
                        hasPlayerCollidedWithObject = true;
                    }
                    break;
                case null:
                default:
                    player.activateCollision = false;
                    hasPlayerCollidedWithObject = false;
                    break;
            }
        }

        return hasPlayerCollidedWithObject;
    }

    // TODO: this almost good, do more to refract this code
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
            checkPlayerCollisionWithObject(player, worldItemsAssets.getDefaultPositionX(), worldItemsAssets.getDefaultPositionY());
        }
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

        switch (MovingDirection.getValueByString(individual.movementDirection))
        {
            case MovingDirection.UP:
                individualTopRow = (individualTopY - individual.speed) / tileSize;
                worldAssetOne = getWorldAssetIndex(individualLeftCol, individualTopRow);
                worldAssetTwo = getWorldAssetIndex(individualRightCol, individualTopRow);
                checkIfWorldAssetsHaveCollisionOn(worldAssetOne, worldAssetTwo, individual);
                break;
            case MovingDirection.DOWN:
                individualBottomRow = (individualBottomY + individual.speed) / tileSize;
                worldAssetOne = getWorldAssetIndex(individualLeftCol, individualBottomRow);
                worldAssetTwo = getWorldAssetIndex(individualRightCol, individualBottomRow);
                checkIfWorldAssetsHaveCollisionOn(worldAssetOne, worldAssetTwo, individual);
                break;
            case MovingDirection.LEFT:
                individualLeftCol = (individualLeftX - individual.speed) / tileSize;
                worldAssetOne = getWorldAssetIndex(individualLeftCol, individualTopRow);
                worldAssetTwo = getWorldAssetIndex(individualLeftCol, individualBottomRow);
                checkIfWorldAssetsHaveCollisionOn(worldAssetOne, worldAssetTwo, individual);
                break;
            case MovingDirection.RIGHT:
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
