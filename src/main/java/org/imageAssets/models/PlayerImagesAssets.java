package org.imageAssets.models;


public enum PlayerImagesAssets implements ImageModel
{

    PLAYER_WALK_UP("/player/walking/player-walk-up.png"),
    PLAYER_WALK_UP_TWO("/player/walking/player-walk-up-two.png"),
    PLAYER_WALK_DOWN("/player/walking/player-walk-down.png"),
    PLAYER_WALK_DOWN_TWO("/player/walking/player-walk-down-two.png"),
    PLAYER_WALK_LEFT("/player/walking/player-walk-left.png"),
    PLAYER_WALK_LEFT_TWO("/player/walking/player-walk-left-two.png"),
    PLAYER_WALK_RIGHT("/player/walking/player-walk-right.png"),
    PLAYER_WALK_RIGHT_TWO("/player/walking/player-walk-right-two.png"),
    PLAYER_STAND_STILL_UP("/player/player-stand-still-up.png"),
    PLAYER_STAND_STILL_DOWN("/player/player-stand-still-down.png"),
    PLAYER_STAND_STILL_LEFT("/player/player-stand-still-left.png"),
    PLAYER_STAND_STILL_RIGHT("/player/player-stand-still-right.png"),
    SWORD_SWING_UP("/player/attack/sword-swing-up.png"),
    SWORD_SWING_CENTER("/player/attack/sword-swing-center.png"),
    SWORD_SWING_DOWN("/player/attack/sword-swing-down.png"),
    SWORD_SWING_UP_LEFT("/player/attack/sword-swing-up-left.png"),
    SWORD_SWING_CENTER_LEFT("/player/attack/sword-swing-center-left.png"),
    SWORD_SWING_DOWN_LEFT("/player/attack/sword-swing-down-left.png"),
    SWORD_SWING_UP_UP("/player/attack/sword-swing-up-up.png"),
    SWORD_SWING_CENTER_UP("/player/attack/sword-swing-center-up.png"),
    SWORD_SWING_DOWN_UP("/player/attack/sword-swing-down-up.png"),
    SWORD_SWING_UP_DOWN("/player/attack/sword-swing-up-down.png"),
    SWORD_SWING_CENTER_DOWN("/player/attack/sword-swing-center-down.png"),
    SWORD_SWING_DOWN_DOWN("/player/attack/sword-swing-down-down.png"),
    GOLD_SWORD_SWING_UP("/player/attack/gold-sword-swing-up.png"),
    GOLD_SWORD_SWING_CENTER("/player/attack/gold-sword-swing-center.png"),
    GOLD_SWORD_SWING_DOWN("/player/attack/gold-sword-swing-down.png"),
    GOLD_SWORD_SWING_UP_LEFT("/player/attack/gold-sword-swing-up-left.png"),
    GOLD_SWORD_SWING_CENTER_LEFT("/player/attack/gold-sword-swing-center-left.png"),
    GOLD_SWORD_SWING_DOWN_LEFT("/player/attack/gold-sword-swing-down-left.png"),
    GOLD_SWORD_SWING_UP_UP("/player/attack/gold-sword-swing-up-up.png"),
    GOLD_SWORD_SWING_CENTER_UP("/player/attack/gold-sword-swing-center-up.png"),
    GOLD_SWORD_SWING_DOWN_UP("/player/attack/gold-sword-swing-down-up.png"),
    GOLD_SWORD_SWING_UP_DOWN("/player/attack/gold-sword-swing-up-down.png"),
    GOLD_SWORD_SWING_CENTER_DOWN("/player/attack/gold-sword-swing-center-down.png"),
    GOLD_SWORD_SWING_DOWN_DOWN("/player/attack/gold-sword-swing-down-down.png"),
    PLAYER_SWORD_START_SWING_ARM("/player/swingingSword/player-sword-start-swing-arm.png"),
    PLAYER_SWORD_START_SWING_ARM_TWO("/player/swingingSword/player-sword-start-swing-arm-two.png"),
    PLAYER_SWORD_START_SWING_ARM_LEFT("/player/swingingSword/player-sword-start-swing-arm-left.png"),
    PLAYER_SWORD_START_SWING_ARM_TWO_LEFT("/player/swingingSword/player-sword-start-swing-arm-two-left.png"),
    PLAYER_SWORD_START_SWING_ARM_STAND_STILL_LEFT("/player/swingingSword/player-sword-start-swing-arm-stand-still-left.png"),
    PLAYER_SWORD_START_SWING_ARM_STAND_STILL("/player/swingingSword/player-sword-start-swing-arm-stand-still.png");

    private String path;

    PlayerImagesAssets(String path)
    {
        this.path = path;
    }

    public String getPath()
    {
        return this.path;
    }
}
