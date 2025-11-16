package org.game.models;

public enum GameState
{
    OPEN_GAME_MENU(0),
    RESUME_GAME(1),
    PAUSE_GAME(2),
    OPEN_PLAYER_INVENTORY(3);

    private final int stateId;

    private GameState(int stateId) {
        this.stateId = stateId;
    }

    public int getStateId()
    {
        return this.stateId;
    }
}
