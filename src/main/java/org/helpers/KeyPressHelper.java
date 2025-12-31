package org.helpers;

public class KeyPressHelper
{
    public int keyPressCount = 0;
    public boolean isFirstPress(boolean keyPressedActive)
    {
        this.keyPressCount = keyPressedActive ? this.keyPressCount + 1 : 0;
        return this.keyPressCount == 1;
    }
}
