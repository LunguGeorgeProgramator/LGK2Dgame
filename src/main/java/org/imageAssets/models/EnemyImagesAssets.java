package org.imageAssets.models;


public enum EnemyImagesAssets implements ImageModel
{
    COLOR_MONSTER_FRONT("/enemy/colormonster/color-monster-front.png"),
    COLOR_MONSTER_BACK("/enemy/colormonster/color-monster-back.png"),
    COLOR_MONSTER_LEFT("/enemy/colormonster/color-monster-left.png"),
    COLOR_MONSTER_RIGHT("/enemy/colormonster/color-monster-right.png"),
    GHOST_MONSTER_LEFT("/enemy/ghost/ghost-fly-left.png"),
    GHOST_MONSTER_RIGHT("/enemy/ghost/ghost-fly-right.png"),
    GHOST_MONSTER_UP("/enemy/ghost/ghost-fly-left.png"),
    GHOST_MONSTER_DOWN("/enemy/ghost/ghost-fly-right.png"),
    GHOST_MONSTER_PANIC_RIGHT("/enemy/ghost/ghost-panic-right.png"),
    GHOST_MONSTER_PANIC_LEFT("/enemy/ghost/ghost-panic-left.png"),
    GHOST_MONSTER_PANIC_UNDER_ATTACK_RIGHT_ONE("/enemy/ghost/ghost-panic-right.png"),
    GHOST_MONSTER_PANIC_UNDER_ATTACK_RIGHT_TWO("/enemy/ghost/ghost-panic-right-under-attack.png"),
    GHOST_MONSTER_PANIC_UNDER_ATTACK_LEFT_ONE("/enemy/ghost/ghost-panic-left.png"),
    GHOST_MONSTER_PANIC_UNDER_ATTACK_LEFT_TWO("/enemy/ghost/ghost-panic-left-under-attack.png"),
    SPIDER_FRONT_MOVE_ONE("/enemy/spider/spider-front-view-moving-one.png"),
    SPIDER_FRONT_MOVE_TWO("/enemy/spider/spider-front-view-moving-two.png"),
    SPIDER_BACK_MOVE_ONE("/enemy/spider/spider-back-view-moving-one.png"),
    SPIDER_BACK_MOVE_TWO("/enemy/spider/spider-back-view-moving-two.png"),
    SPIDER_LEFT_MOVE_ONE("/enemy/spider/spider-left-view-moving-one.png"),
    SPIDER_LEFT_MOVE_TWO("/enemy/spider/spider-left-view-moving-two.png"),
    SPIDER_RIGHT_MOVE_ONE("/enemy/spider/spider-right-view-moving-one.png"),
    SPIDER_RIGHT_MOVE_TWO("/enemy/spider/spider-right-view-moving-two.png"),
    SPIDER_BACK_VIEW("/enemy/spider/spider-back-view.png"),
    SPIDER_FRONT_VIEW("/enemy/spider/spider-front-view.png"),
    SPIDER_RIGHT_VIEW("/enemy/spider/spider-right-view.png"),
    SPIDER_LEFT_VIEW("/enemy/spider/spider-left-view.png"),
    SPIDER_BACK_VIEW_UNDER_ATTACK("/enemy/spider/spider-back-view-under-attack.png"),
    SPIDER_FRONT_VIEW_UNDER_ATTACK("/enemy/spider/spider-front-view-under-attack.png"),
    SPIDER_RIGHT_VIEW_UNDER_ATTACK("/enemy/spider/spider-right-view-under-attack.png"),
    SPIDER_LEFT_VIEW_UNDER_ATTACK("/enemy/spider/spider-left-view-under-attack.png");

    private String path;

    EnemyImagesAssets(String path)
    {
        this.path = path;
    }

    public String getPath()
    {
        return this.path;
    }
}
