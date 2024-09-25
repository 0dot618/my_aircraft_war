package edu.hitsz.aircraft;

import edu.hitsz.application.ImageManager;
import edu.hitsz.application.Main;
import edu.hitsz.factory.*;
import edu.hitsz.prop.*;
import edu.hitsz.strategy.ShootStrategy;
import edu.hitsz.observer.FlyingObjectObserver;

import java.util.LinkedList;
import java.util.List;

/**
 * Boss敌机
 * 可射击，射击方式为环射
 * 随机产生<=3个道具
 *
 * @author 220110504-李乐怡
 */
public class BossEnemy extends Enemy implements FlyingObjectObserver {

    public BossEnemy(ShootStrategy shootStrategy,int HP,int speedx) {
        super(shootStrategy);
        locationX = (int) (Math.random() * (Main.WINDOW_WIDTH - ImageManager.ELITE_ENEMY_IMAGE.getWidth()));
        locationY = (int) (Math.random() * Main.WINDOW_HEIGHT * 0.05);
        speedX = (Math.random()<0.5? -1*speedx : speedx);
        speedY = 0;
        hp = HP;
        maxHp = HP;
        EXP = 50;
    }


    /**
     * BOSS敌机被撞毁后以较低概率产生道具
     * @return 产生的道具BaseProp
     */
    public List<BaseProp> generateProp(int propLocationX,int propLocationY) {
        PropFactory propFactory;
        List<BaseProp> res = new LinkedList<>();
        int propNum = 0;
        double numberRan = Math.random();
        //产生三个道具
        if(numberRan<=0.3) {
            propNum = 3;
        }
        //产生两个道具
        else if(numberRan<=0.6) {
            propNum = 2;
        }
        //产生一个道具
        else if(numberRan<=0.9) {
            propNum = 1;
        }
        //不产生道具
        else return List.of();

        for(int i = 1; i <= propNum; i++) {
            double kindRan = Math.random();
            if(kindRan<=0.33) {
                propFactory = new BloodPropFactory();
            }
            else if(kindRan<=0.67) {
                propFactory = new BombPropFactory();
            }
            else if(kindRan<=0.85) {
                propFactory = new BulletPropFactory();
            }
            else {
                propFactory = new BulletPlusPropFactory();
            }
            res.add(propFactory.creatProp(propLocationX + (i*2 - propNum + 1)*30 , propLocationY));
        }
        return res;
    }

    @Override
    public void vanish()
    {
        isValid = false;
        BombProp.removeFlyingObjectObserver(this);
    }

    public void update(){

    }
}
