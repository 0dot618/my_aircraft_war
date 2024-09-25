package edu.hitsz.strategy;

import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.EnemyBullet;
import edu.hitsz.bullet.HeroBullet;

import java.util.LinkedList;
import java.util.List;

public class CircleShootStrategy implements ShootStrategy {
    @Override
    public List<BaseBullet> shoot(int locationX,int locationY,int speedX1,int speedY1,int direction,int power) {
        int shootNum = 20;
        List<BaseBullet> res = new LinkedList<>();
        int x = locationX;
        int y = locationY;
        int speedX = speedX1;
        int speedY = speedY1;
        BaseBullet bullet;
        for(int i=0; i<shootNum; i++){
            // 子弹发射位置相对飞机位置向前偏移
            // 多个子弹环形发射
            if(direction == -1) {
                bullet = new HeroBullet(x, y, speedX + (int) (10 * Math.cos(Math.PI * i * 2 / shootNum)), speedY + (int) (10 * Math.sin(Math.PI * i * 2 / shootNum)), power);
            }
            else {
                bullet = new EnemyBullet(x, y, speedX + (int) (10 * Math.cos(Math.PI * i * 2 / shootNum)), speedY + (int) (10 * Math.sin(Math.PI * i * 2 / shootNum)), power);
            }
            res.add(bullet);
        }
        return res;
    }
}
