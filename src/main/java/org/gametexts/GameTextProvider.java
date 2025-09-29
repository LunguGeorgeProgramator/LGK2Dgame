package org.gametexts;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Font;
import java.awt.Color;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.helpers.ToolsHelper;

import static org.game.GamePanel.tileSize;

public class GameTextProvider
{

    private Map<String, String> inGameTextsMap;
    private final ObjectMapper mapper;
    private String textFont;
    private int textSize;
    private int textStyle;
    private Color textColor;
    private int textPositionX;
    private int textPositionY;

    public GameTextProvider()
    {
        this.textColor = Color.WHITE;
        this.textFont = "Arial";
        this.textSize = 15;
        this.textStyle = Font.PLAIN;
        this.textPositionX = tileSize * 2;
        this.textPositionY = tileSize * 2;
        this.mapper = new ObjectMapper();
        initializeGameTexts();
    }

    private void initializeGameTexts()
    {
        InputStream jsonFileInputStream = ToolsHelper.loadJsonFileFromResource("/gametexts/game-texts.json");
        if (jsonFileInputStream != null)
        {
            try
            {
                this.inGameTextsMap = mapper.readValue(jsonFileInputStream, new TypeReference<>() {});
            }
            catch (IOException ex)
            {
                ex.printStackTrace();
            }
        }
    }

    public void setTextPosition(int positionX, int positionY)
    {
        this.textPositionX = positionX;
        this.textPositionY = positionY;
    }

    public void setTextColor(Color textColor)
    {
        this.textColor = textColor;
    }

    public void changeTextStyle(int textSize, int textStyle, String textFont, Color textColor)
    {
        this.textColor = textColor;
        this.textFont = textFont;
        this.textSize = textSize;
        this.textStyle = textStyle;
    }

    public void showTextInsideGame(Graphics2D g2d, String textToShow)
    {
        if (textToShow != null)
        {
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            // These options cause the game to freeze briefly the first time the text is displayed.
            g2d.setFont(new Font(this.textFont, this.textStyle, this.textSize));
            g2d.setColor(this.textColor);
            int lineHeight = g2d.getFontMetrics().getHeight();

            String[] lines = textToShow.split("\n");

            for (int i = 0; i < lines.length; i++) {
                g2d.drawString(lines[i], this.textPositionX, this.textPositionY + i * lineHeight);
            }
        }
    }

    public String getGameTextByKey(String textKey)
    {
        if (this.inGameTextsMap.containsKey(textKey))
        {
            return this.inGameTextsMap.get(textKey);
        }
        return null;
    }

}
