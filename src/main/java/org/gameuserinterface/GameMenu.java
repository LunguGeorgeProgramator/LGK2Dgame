package org.gameuserinterface;

import org.game.GamePanel;
import org.game.KeyBoardAndMouseHandler;
import org.game.models.GameState;
import org.gameuserinterface.models.PagesType;

import javax.swing.JPanel;
import java.awt.Graphics2D;
import java.awt.FontMetrics;
import java.awt.RenderingHints;
import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;

import static org.game.GamePanel.tileSize;
import static org.game.GamePanel.screenWith;
import static org.game.GamePanel.screenHeight;


public class GameMenu extends JPanel
{
    private final List<String> menuItems = new ArrayList<>();
    private int selectedIndex = 0; // Which button is selected
    private static final int continueGameIndex = 0;
    private static final int startNewGameIndex = 1;
    private static final int controlsMappingIndex = 2;
    private static final int exitGameIndex = 3;
    private static final int centerX = screenWith / 2;
    private static final int startY = 200;
    private static final int btnWidth = 250;
    private static final int btnHeight = 50;
    private static final String btnTextFoundName = "Arial";
    private static final String titleTextFoundName = "Monospaced";
    private static final int btnTextFoundSize = 22;
    private static final int titleTextFoundSize = 25;
    private final GamePanel gamePanel;
    private final KeyBoardAndMouseHandler keyBoardAndMouseHandler;
    private static int upKeyPressedIndex = 0;
    private static int downKeyPressedIndex = 0;
    private static int enterKeyPressedIndex = 0;
    private static int mouseClickedIndex = 0;
    private PagesType loadPanelPage;
    private final String backBtnText;
    private final String gameControlsText;
    private final String mainMenuGameTitleText;

    public GameMenu(GamePanel gamePanel, KeyBoardAndMouseHandler keyBoardAndMouseHandler)
    {
        this.keyBoardAndMouseHandler = keyBoardAndMouseHandler;
        this.gamePanel = gamePanel;
        this.loadPanelPage = PagesType.MAINE_MENU;
        this.menuItems.add(this.gamePanel.gameTextProvider.getGameTextByKey("continue-game-btn"));
        this.menuItems.add(this.gamePanel.gameTextProvider.getGameTextByKey("start-game-btn"));
        this.menuItems.add(this.gamePanel.gameTextProvider.getGameTextByKey("controls-page-btn"));
        this.menuItems.add(this.gamePanel.gameTextProvider.getGameTextByKey("exit-game-btn"));
        this.backBtnText = this.gamePanel.gameTextProvider.getGameTextByKey("back-to-main-menu");
        this.gameControlsText = this.gamePanel.gameTextProvider.getGameTextByKey("game-controls");
        this.mainMenuGameTitleText = this.gamePanel.gameTextProvider.getGameTextByKey("main-menu-game-title");
    }

    public void drawGameMenu(Graphics2D g2D)
    {
        g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        if (loadPanelPage.equals(PagesType.MAINE_MENU))
        {
            drawMainManuPage(g2D);
            mainMenuKeyBoardAndMouseListener();
        }
        else if (this.loadPanelPage.equals(PagesType.CONTROLS_MAPPING))
        {
            drawControlsMappingPage(g2D);
        }

    }

    private void drawMainManuPage(Graphics2D g2D)
    {
        g2D.setColor(new Color(0, 0, 0, 227));
        g2D.fillRect(0, 0, screenWith, screenHeight);

        for (int i = 0; i < menuItems.size(); i++)
        {
            boolean selected = (i == this.selectedIndex);

            // Button rectangle
            int x = centerX - btnWidth / 2;
            int y = startY + i * 80;

            // Background color
            g2D.setColor(selected ? new Color(0, 255, 100) : new Color(0, 200, 255));
            g2D.fillRoundRect(x, y, btnWidth, btnHeight, 20, 20);

            // game title
            g2D.setColor(new Color(0, 255, 100));
            g2D.setFont(new Font(titleTextFoundName, Font.BOLD, titleTextFoundSize));
            g2D.drawString(this.mainMenuGameTitleText, screenWith / 2 - tileSize * 2, screenHeight / 2 - tileSize * 3);

            // Text
            g2D.setColor(Color.BLACK);
            g2D.setFont(new Font(btnTextFoundName, Font.BOLD, btnTextFoundSize));

            FontMetrics fm = g2D.getFontMetrics();
            int textX = centerX - fm.stringWidth(menuItems.get(i)) / 2;
            int textY = y + (btnHeight + fm.getAscent()) / 2 - 5;

            g2D.drawString(menuItems.get(i), textX, textY);
        }
    }

    private void drawControlsMappingPage(Graphics2D g2D)
    {
        int y = tileSize;

        g2D.setColor(new Color(0, 0, 0, 227));
        g2D.fillRect(0, 0, screenWith, screenHeight);
        g2D.setColor(new Color(0, 255, 100));

        // Button rectangle
        g2D.fillRoundRect(tileSize, y, btnWidth, btnHeight, 20, 20);

        // Text
        g2D.setColor(Color.BLACK);
        g2D.setFont(new Font(btnTextFoundName, Font.BOLD, btnTextFoundSize));

        FontMetrics fm = g2D.getFontMetrics();
        int textX = fm.stringWidth(this.backBtnText) / 2 - 5;
        int textY = y + (btnHeight + fm.getAscent()) / 2 - 5;

        g2D.drawString(this.backBtnText, textX, textY);

        this.gamePanel.gameTextProvider.setTextColor(Color.WHITE);
        this.gamePanel.gameTextProvider.setTextPosition(screenWith / 2 - tileSize * 3, screenHeight / 2 - tileSize * 2);
        this.gamePanel.gameTextProvider.showTextInsideGame(g2D, this.gameControlsText);

        controlsPageKeyBoardAndMouseListener();
    }

    private void controlsPageKeyBoardAndMouseListener()
    {
        if (this.keyBoardAndMouseHandler.enterKeyPressed)
        {
            if (enterKeyPressedIndex == 0)
            {
                this.loadPanelPage = PagesType.MAINE_MENU;
                repaint();
            }
            enterKeyPressedIndex++;
        }
        if (!this.keyBoardAndMouseHandler.enterKeyPressed)
        {
            enterKeyPressedIndex = 0;
        }
        if (this.gamePanel.keyBoardAndMouseHandler.mouseClicked)
        {
            if (mouseClickedIndex == 0)
            {
                this.loadPanelPage = PagesType.MAINE_MENU;
                repaint();
            }
            mouseClickedIndex++;
        }
        if (!this.gamePanel.keyBoardAndMouseHandler.mouseClicked)
        {
            mouseClickedIndex = 0;
        }
    }

    private void mainMenuKeyBoardAndMouseListener()
    {

        if (this.keyBoardAndMouseHandler.enterKeyPressed)
        {
            if (enterKeyPressedIndex == 0)
            {
                buttonAction();
            }
            enterKeyPressedIndex++;
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
        if (!this.keyBoardAndMouseHandler.enterKeyPressed)
        {
            enterKeyPressedIndex = 0;
        }

        int mouseX = this.keyBoardAndMouseHandler.mousePositionX;
        int mouseY = this.keyBoardAndMouseHandler.mousePositionY;

        for (int i = 0; i < menuItems.size(); i++)
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
            if (mouseClickedIndex == 0)
            {
                buttonAction();
            }
            mouseClickedIndex++;
        }
        if (!this.gamePanel.keyBoardAndMouseHandler.mouseClicked)
        {
            mouseClickedIndex = 0;
        }
    }

    private void buttonAction()
    {
        if (this.selectedIndex == continueGameIndex)
        {
            this.gamePanel.gameState = GameState.RESUME_GAME;
        }
        else if (this.selectedIndex == startNewGameIndex)
        {
            this.gamePanel.resetGame();
            this.gamePanel.gameState = GameState.RESUME_GAME;
        }
        else if (this.selectedIndex == controlsMappingIndex)
        {
            this.loadPanelPage = PagesType.CONTROLS_MAPPING;
            repaint();
        }
        else if (this.selectedIndex == exitGameIndex)
        {
            System.exit(0);
        }
    }
}
