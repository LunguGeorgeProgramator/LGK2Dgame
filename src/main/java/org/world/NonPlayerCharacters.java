package org.world;

import org.game.GamePanel;
import org.individual.Individual;
import org.individual.NonPlayerCharacter;
import org.individual.Player;
import org.individual.models.NonPlayerCharacterAssets;

import java.util.ArrayList;
import java.util.List;

import static org.game.GamePanel.tileSize;
import static org.helpers.ToolsHelper.checkIfAssetIsInsideTheBoundary;

public class NonPlayerCharacters  extends GameWorld
{

    private List<NonPlayerCharacter> npcList;
    private Player player;
    private GamePanel gamePanel;

    public NonPlayerCharacters(GamePanel gamePanel, String worldNpcMapPath)
    {
        super(gamePanel, worldNpcMapPath);
        this.npcList = new ArrayList<>();
        this.gamePanel = gamePanel;
        this.player = gamePanel.player;
        this._initializeNpcList();
    }

    private void _initializeNpcList()
    {
        for (int i = 0; i < this.worldMap.length; i++)
        {
            for (int j = 0; j < this.worldMap[i].length; j++)
            {
                int worldNpcIndex = this.worldMap[i][j];
                if (worldNpcIndex < 0)
                {
                    continue;
                }
                NonPlayerCharacterAssets npcAsset = NonPlayerCharacterAssets.geNpcAssetByIndex(worldNpcIndex);
                if (npcAsset == null)
                {
                    continue;
                }
                int worldPositionX = tileSize * i;
                int worldPositionY = tileSize * j;
                this.npcList.add(new NonPlayerCharacter(
                    this.gamePanel,
                    worldPositionX,
                    worldPositionY,
                    npcAsset.getSpeed(),
                    npcAsset.getNpcId(),
                    npcAsset.getNpcType(),
                    npcAsset.getMaxDistanceAllowedToMove(),
                    npcAsset.getNpcMovingDirectionList(),
                    npcAsset.getNpcAssetsMap()
                ));
            }
        }
    }

    public void update()
    {
        for (NonPlayerCharacter nonPlayerCharacter : this.npcList)
        {
            nonPlayerCharacter.update();
        }
    }

    public List<Individual> addEnemiesToDrawList(List<Individual> individuals)
    {
        for (NonPlayerCharacter nonPlayerCharacter : this.npcList)
        {
            if (checkIfAssetIsInsideTheBoundary(nonPlayerCharacter.positionX, nonPlayerCharacter.positionY, this.player, tileSize * 2))
            {
                individuals.add(nonPlayerCharacter);
            }
        }
        return individuals;
    }
}
