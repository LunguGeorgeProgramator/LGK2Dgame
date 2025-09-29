package org.game;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyBoardHandler implements KeyListener
{
    public boolean upPressed;
    public boolean downPressed;
    public boolean leftPressed;
    public boolean rightPressed;
    public boolean pKeyPressed;
    public boolean spaceBarePressed;

    @Override
    public void keyTyped(KeyEvent keyEvent)
    {

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
            case KeyEvent.VK_P:
                pKeyPressed = keyPressedValue;
                break;
            case KeyEvent.VK_SPACE:
                spaceBarePressed = keyPressedValue;
        }
    }
}
