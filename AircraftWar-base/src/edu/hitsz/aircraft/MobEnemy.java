package edu.hitsz.aircraft;

import edu.hitsz.application.Game;
import edu.hitsz.application.ImageManager;
import edu.hitsz.application.Main;
import edu.hitsz.prop.BaseProp;
import edu.hitsz.prop.BombProp;
import edu.hitsz.strategy.ShootStrategy;
import edu.hitsz.observer.FlyingObjectObserver;

import java.util.List;

/**
 * 普通敌机
 * 不可射击
 *
 * @author hitsz
 */
public class MobEnemy extends Enemy implements FlyingObjectObserver {
    public MobEnemy(ShootStrategy shootStrategy,int HP,int speedy) {
        super(shootStrategy);
        locationX = (int) (Math.random() * (Main.WINDOW_WIDTH - ImageManager.MOB_ENEMY_IMAGE.getWidth()));
        locationY = (int) (Math.random() * Main.WINDOW_HEIGHT * 0.05);
        speedX = 0;
        speedY = speedy;
        hp = HP;
        maxHp = HP;
        EXP = 10;
    }

    @Override
    public List<BaseProp> generateProp(int propLocationX, int propLocationY) { return List.of(); }


    @Override
    public void vanish()
    {
        isValid = false;
        BombProp.removeFlyingObjectObserver(this);
    }

    //观察炸弹道具做出对应的响应：坠毁
    public void update()
    {
        vanish();
        Game.increaseScore(getEXP());
    }

}
