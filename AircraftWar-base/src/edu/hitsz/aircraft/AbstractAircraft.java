package edu.hitsz.aircraft;

import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.basic.AbstractFlyingObject;
import edu.hitsz.strategy.ShootStrategy;

import java.util.List;

/**
 * 所有种类飞机的抽象父类：
 * 敌机（BOSS, ELITE, MOB），英雄飞机
 *
 * @author hitsz
 */
public abstract class AbstractAircraft extends AbstractFlyingObject {
    /**
     * 射击策略
     */
    private ShootStrategy shootStrategy;

    /**攻击方式 */

    /**
     * 子弹伤害
     */
    protected int shootPower;

    /**
     * 子弹射击方向 (向下发射：1，向上发射：-1)
     */
    protected int direction;


    /**
     * 生命值
     */
    protected int maxHp;
    protected int hp;

    public AbstractAircraft(ShootStrategy shootStrategy) {
        this.shootStrategy = shootStrategy;
    }

    public AbstractAircraft(int locationX, int locationY, int speedX, int speedY, int hp, ShootStrategy shootStrategy) {
        super(locationX, locationY, speedX, speedY);
        this.hp = hp;
        this.maxHp = hp;
        this.shootStrategy = shootStrategy;
    }

    public void decreaseHp(int decrease){
        hp -= decrease;
        if(hp <= 0){
            hp=0;
            vanish();
        }
    }

    public void increaseHp(int increase){
        if(hp + increase >= maxHp){
            hp=maxHp;
        }
        else {
            hp += increase;
        }
    }
    public int getHp() {
        return hp;
    }

    public int getDirection() {
        return direction;
    }

    public int getShootPower() {
        return shootPower;
    }


    public void setShootStrategy (ShootStrategy shootStrategy) {
        this.shootStrategy = shootStrategy;
    }

    public ShootStrategy getShootStrategy () {
        return this.shootStrategy;
    }

    /**
     * 飞机射击方法，可射击对象必须实现
     * @return
     *  可射击对象需实现，返回子弹
     *  非可射击对象空实现，返回List.of()
     */
    public List<BaseBullet> shoot(){
        return shootStrategy.shoot(getLocationX() ,getLocationY() , getSpeedX() , getSpeedY() , getDirection() , getShootPower());
    }

}


