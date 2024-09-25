package edu.hitsz.strategy;

import edu.hitsz.bullet.BaseBullet;

import java.util.List;

public interface ShootStrategy {
    /**
     * 进行某种方式的射击或不射击
     * @return 射击子弹列表
     */
    List<BaseBullet> shoot(int locationX,int locationY,int speedX1,int speedY1,int direction,int power);
}
