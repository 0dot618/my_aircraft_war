package edu.hitsz.strategy;

import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.EnemyBullet;
import edu.hitsz.bullet.HeroBullet;

import java.util.LinkedList;
import java.util.List;

public class SectorShootStrategy implements ShootStrategy {
    @Override
    public List<BaseBullet> shoot(int locationX,int locationY,int speedX1,int speedY1,int direction,int power) {
        int shootNum = 3;
        List<BaseBullet> res = new LinkedList<>();
        int x = locationX;
        int y = locationY + direction*2;
        int speedX = speedX1;
        int speedY = speedY1 + direction*5;
        BaseBullet bullet;
        for(int i=0; i<shootNum; i++){
            // 子弹发射位置相对飞机位置向前偏移
            // 多个子弹横向分散，且进行散射
            if(direction == -1) {
                bullet = new HeroBullet(x + (i * 2 - shootNum + 1) * 10, y, speedX + 2 * (i - shootNum / 2), speedY, power);
            }
            else{
                bullet = new EnemyBullet(x + (i * 2 - shootNum + 1) * 10, y, speedX + 2 * (i - shootNum / 2), speedY, power);
            }
            res.add(bullet);
        }
        return res;
    }
}
