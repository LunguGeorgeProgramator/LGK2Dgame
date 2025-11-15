package org.gameuserinterface;

import org.game.GamePanel;
import org.game.KeyBoardAndMouseHandler;

import javax.swing.*;
import java.awt.Graphics2D;
import java.awt.FontMetrics;
import java.awt.RenderingHints;
import java.awt.Color;
import java.awt.Font;
import static org.game.GamePanel.tileSize;
import static org.game.GamePanel.screenWith;
import static org.game.GamePanel.screenHeight;


public class GameMenu extends JPanel
{
    private final String[] menuItems = {"Continue Game", "Star New Game", "Exit Game"};
    private int selectedIndex = 0; // Which button is selected
    private static final int continueGameIndex = 0;
    private static final int startNewGameIndex = 1;
    private static final int exitGameIndex = 2;
    private static final int centerX = screenWith / 2;
    private static final int startY = 200;
    private static final int btnWidth = 200;
    private static final int btnHeight = 50;
    private final GamePanel gamePanel;
    private final KeyBoardAndMouseHandler keyBoardAndMouseHandler;
    private static int upKeyPressedIndex = 0;
    private static int downKeyPressedIndex = 0;

    public GameMenu(GamePanel gamePanel, KeyBoardAndMouseHandler keyBoardAndMouseHandler)
    {
        this.keyBoardAndMouseHandler = keyBoardAndMouseHandler;
        this.gamePanel = gamePanel;
    }

    public void drawGameMenu(Graphics2D g2D)
    {
        g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2D.setColor(new Color(0, 0, 0, 227));
        g2D.fillRect(0, 0, screenWith, screenHeight);

        for (int i = 0; i < menuItems.length; i++)
        {
            boolean selected = (i == this.selectedIndex);

            // Button rectangle
            int x = centerX - btnWidth / 2;
            int y = startY + i * 80;

            // Background color
            g2D.setColor(selected ? new Color(0, 255, 100) : new Color(0, 200, 255));

            g2D.fillRoundRect(x, y, btnWidth, btnHeight, 20, 20);

            // Text
            g2D.setColor(Color.BLACK);
            g2D.setFont(new Font("Arial", Font.BOLD, 22));

            FontMetrics fm = g2D.getFontMetrics();
            int textX = centerX - fm.stringWidth(menuItems[i]) / 2;
            int textY = y + (btnHeight + fm.getAscent()) / 2 - 5;

            g2D.drawString(menuItems[i], textX, textY);

            keyBoardAndMouseListener();
        }
    }

    private void keyBoardAndMouseListener()
    {

        if (this.keyBoardAndMouseHandler.enterKeyPressed)
        {
            buttonAction();
        }
        else if (this.keyBoardAndMouseHandler.upKeyPressed)
        {

            if (upKeyPressedIndex == 0)
            {
                this.selectedIndex = this.selectedIndex >= startNewGameIndex ? this.selectedIndex - 1 : this.selectedIndex;
                repaint();
            }
            upKeyPressedIndex++;
        }
        else if (this.keyBoardAndMouseHandler.downKeyPressed)
        {
            if (downKeyPressedIndex == 0)
            {
                this.selectedIndex = this.selectedIndex < exitGameIndex ? this.selectedIndex + 1 : this.selectedIndex;
                repaint();
            }
            downKeyPressedIndex++;
        }

        // This workaround functions, but it's not optimal. Since the game loop runs continuously,
        // a single key press is registered multiple times. To prevent repeated triggers, an
        // increment counter is used and the logic runs only when the counter is 0.
        if (!this.keyBoardAndMouseHandler.upKeyPressed)
        {
            upKeyPressedIndex = 0;
        }
        if (!this.keyBoardAndMouseHandler.downKeyPressed)
        {
            downKeyPressedIndex = 0;
        }

        int mouseX = this.keyBoardAndMouseHandler.mousePositionX;
        int mouseY = this.keyBoardAndMouseHandler.mousePositionY;

        for (int i = 0; i < menuItems.length; i++)
        {
            int x = centerX - btnWidth / 2;
            int y = startY + i * 80;

            if (mouseX >= x && mouseX <= x + btnWidth && mouseY >= y && mouseY <= y + btnHeight)
            {
                selectedIndex = i;
                repaint();
            }
        }

        if (this.gamePanel.keyBoardAndMouseHandler.mouseClicked)
        {
            buttonAction();
        }
    }

    private void buttonAction()
    {
        if (this.selectedIndex == continueGameIndex)
        {
            this.gamePanel.gameState = this.gamePanel.runGameState;
        }
        else if (this.selectedIndex == startNewGameIndex)
        {
            this.gamePanel.resetGame();
            this.gamePanel.gameState = this.gamePanel.runGameState;
        }
        else
        {
            System.exit(0);
        }
    }
}
