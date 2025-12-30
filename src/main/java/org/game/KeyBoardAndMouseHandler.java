package org.game;

import org.game.models.GameState;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseEvent;

public class KeyBoardAndMouseHandler implements KeyListener, MouseListener, MouseMotionListener
{
    public boolean upPressed;
    public boolean closePressed;
    public boolean downPressed;
    public boolean leftPressed;
    public boolean rightPressed;
    public boolean enterKeyPressed;
    public boolean upKeyPressed;
    public boolean downKeyPressed;
    public boolean spaceBarePressed;
    public boolean fastKeyPressed;
    public boolean playerInventoryKeyPressed;
    public boolean mouseClicked;
    public int mousePositionX;
    public int mousePositionY;
    private final GamePanel gamePanel;

    public KeyBoardAndMouseHandler(GamePanel gamePanel)
    {
        this.gamePanel = gamePanel;
    }

    @Override
    public void keyPressed(KeyEvent keyEvent)
    {
        setKeyPressedValues(keyEvent, true);
    }

    @Override
    public void keyReleased(KeyEvent keyEvent)
    {
        setKeyPressedValues(keyEvent, false);
    }

    private void setKeyPressedValues(KeyEvent keyEvent, boolean keyPressedValue)
    {
        switch (keyEvent.getKeyCode())
        {
            case KeyEvent.VK_W:
                upPressed = keyPressedValue;
                break;
            case KeyEvent.VK_S:
                downPressed = keyPressedValue;
                break;
            case KeyEvent.VK_A:
                leftPressed = keyPressedValue;
                break;
            case KeyEvent.VK_D:
                rightPressed = keyPressedValue;
                break;
            case KeyEvent.VK_SPACE:
                spaceBarePressed = keyPressedValue;
                break;
            case KeyEvent.VK_F:
                fastKeyPressed = keyPressedValue;
                break;
            case KeyEvent.VK_I:
                playerInventoryKeyPressed = keyPressedValue;
//                if (keyPressedValue)
                if (keyPressedValue && !gamePanel.isGameState(GameState.OPEN_VENDOR_INVENTORY))
                {
                    gamePanel.setGameState(GameState.OPEN_PLAYER_INVENTORY);
                }
                break;
            case KeyEvent.VK_ENTER:
                enterKeyPressed = keyPressedValue;
                break;
            case KeyEvent.VK_M:
                if (keyPressedValue)
                {
                    gamePanel.setGameState(GameState.OPEN_GAME_MENU);
                }
                break;
            case KeyEvent.VK_UP:
                upKeyPressed = keyPressedValue;
                break;
            case KeyEvent.VK_DOWN:
                downKeyPressed = keyPressedValue;
                break;
            case KeyEvent.VK_C:
                closePressed = keyPressedValue;
                break;
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        mousePositionX = e.getX();
        mousePositionY = e.getY();
    }

    @Override public void mousePressed(MouseEvent e)
    {
        mouseClicked = true;
    }

    @Override public void mouseReleased(MouseEvent e)
    {
        mouseClicked = false;
    }

    // Unused interface methods
    @Override public void keyTyped(KeyEvent e) {}
    @Override public void mouseClicked(MouseEvent e) {}
    @Override public void mouseEntered(MouseEvent e) {}
    @Override public void mouseExited(MouseEvent e) {}
    @Override public void mouseDragged(MouseEvent e) {}

}
