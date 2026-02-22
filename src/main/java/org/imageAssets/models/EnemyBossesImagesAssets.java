package org.imageAssets.models;


public enum EnemyBossesImagesAssets implements ImageModel
{
    GRIM_BOOS_ENEMY("/boosEnemy/grim/grim-boos-enemy.png"),
    GRIM_BOOS_ENEMY_LEFT("/boosEnemy/grim/grim-boos-enemy-left.png"),
    GRIM_WEAPON_ATTACK_UP_UP("/boosEnemy/grim/grim-weapon-attack-up-up.png"),
    GRIM_WEAPON_ATTACK_CENTER_UP("/boosEnemy/grim/grim-weapon-attack-center-up.png"),
    GRIM_WEAPON_ATTACK_UP_RIGHT("/boosEnemy/grim/grim-weapon-attack-up-right.png"),
    GRIM_WEAPON_ATTACK_CENTER_RIGHT("/boosEnemy/grim/grim-weapon-attack-center-right.png"),
    GRIM_WEAPON_ATTACK_UP_LEFT("/boosEnemy/grim/grim-weapon-attack-up-left.png"),
    GRIM_WEAPON_ATTACK_CENTER_LEFT("/boosEnemy/grim/grim-weapon-attack-center-left.png"),
    GRIM_WEAPON_ATTACK_UP_DOWN("/boosEnemy/grim/grim-weapon-attack-up-down.png"),
    GRIM_WEAPON_ATTACK_CENTER_DOWN("/boosEnemy/grim/grim-weapon-attack-center-down.png"),
    GRIM_BOOS_ENEMY_ATTACK_UP("/boosEnemy/grim/grim-boos-enemy-attack-up.png"),
    GRIM_BOOS_ENEMY_ATTACK_DOWN("/boosEnemy/grim/grim-boos-enemy-attack-down.png"),
    GRIM_BOOS_ENEMY_ATTACK_LEFT("/boosEnemy/grim/grim-boos-enemy-attack-left.png"),
    GRIM_BOOS_ENEMY_ATTACK_RIGHT("/boosEnemy/grim/grim-boos-enemy-attack-right.png"),
    BOOS_ENEMY("/boosEnemy/spider/boos-enemy.png"),
    BOOS_ENEMY_ATTACK("/boosEnemy/spider/boos-enemy-attack.png"),
    BOOS_ENEMY_BITES("/boosEnemy/spider/boos-enemy-bites.png"),
    BOOS_ENEMY_MOVING_TWO("/boosEnemy/spider/boos-enemy-moving-two.png"),
    BOOS_ENEMY_MOVING("/boosEnemy/spider/boos-enemy-moving.png");

    private String path;

    EnemyBossesImagesAssets(String path)
    {
        this.path = path;
    }

    public String getPath()
    {
        return this.path;
    }
}
