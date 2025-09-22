package org.game;

import java.awt.Graphics2D;
//import java.awt.RenderingHints;
import java.awt.Font;
import java.awt.Color;

import static org.game.GamePanel.tileSize;

public class GameTextProvider
{
    public String textFont;
    public int textSize;
    public int textStyle;

    public GameTextProvider()
    {
        this.textFont = "Arial";
        this.textSize = 15;
        this.textStyle = Font.BOLD;
    }

    public void showTextInsideGame(Graphics2D g2d, String textToShow)
    {
        // These options cause the game to freeze briefly the first time the text is displayed.
        // g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        // g2d.setFont(new Font(this.textFont, this.textStyle, this.textSize));
        if (textToShow != null)
        {
            g2d.setColor(Color.WHITE);
            g2d.drawString(textToShow, tileSize * 2, tileSize * 2);
        }
    }
}
