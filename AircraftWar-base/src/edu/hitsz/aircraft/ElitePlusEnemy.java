package edu.hitsz.aircraft;

import edu.hitsz.application.Game;
import edu.hitsz.application.ImageManager;
import edu.hitsz.application.Main;
import edu.hitsz.factory.*;
import edu.hitsz.prop.*;
import edu.hitsz.strategy.ShootStrategy;
import edu.hitsz.observer.FlyingObjectObserver;

import java.util.LinkedList;
import java.util.List;

/**
 * 超级精英敌机
 * 可射击，射击方式为散射
 *
 * @author 220110504-李乐怡
 */
public class ElitePlusEnemy extends Enemy implements FlyingObjectObserver {


    public ElitePlusEnemy(ShootStrategy shootStrategy,int HP,int speedy,int speedx) {
        super(shootStrategy);
        locationX = (int) (Math.random() * (Main.WINDOW_WIDTH - ImageManager.ELITE_ENEMY_IMAGE.getWidth()));
        locationY = (int) (Math.random() * Main.WINDOW_HEIGHT * 0.05);
        speedX = 2*(Math.random()<0.5? -1*speedx : speedx);
        speedY = speedy;
        hp = 3*HP;
        maxHp = 3*HP;
        EXP = 30;
    }

    /**
     * 超级精英敌机被撞毁后以较低概率产生道具
     * @return 产生的道具BaseProp
     */
    public List<BaseProp> generateProp(int propLocationX,int propLocationY) {
        PropFactory propFactory;
        List<BaseProp> res = new LinkedList<>();
        double ran = Math.random();
        if(ran<=0.3) {
            propFactory = new BloodPropFactory();
        }
        else if(ran<=0.6) {
            propFactory = new BombPropFactory();
        }
        else if(ran<=0.75) {
            propFactory = new BulletPropFactory();
        }
        else if(ran<=0.9) {
            propFactory = new BulletPlusPropFactory();
        }
        else return List.of();
        res.add(propFactory.creatProp(propLocationX, propLocationY));
        return res;
    }

    @Override
    public void vanish()
    {
        isValid = false;
        BombProp.removeFlyingObjectObserver(this);
    }

    //观察炸弹道具做出对应的响应：减少血量
    public void update(){
        decreaseHp(30);
        if(notValid()){
            Game.increaseScore(getEXP());
        }
    }
}
