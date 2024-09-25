package edu.hitsz.strategy;

import edu.hitsz.bullet.BaseBullet;

import java.util.List;

public class NotShootStrategy implements ShootStrategy {
    @Override
    public List<BaseBullet> shoot(int locationX,int locationY,int speedX1,int speedY1,int direction,int power) {
        return List.of();
    }
}
