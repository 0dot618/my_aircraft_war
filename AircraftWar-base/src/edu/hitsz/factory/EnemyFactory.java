package edu.hitsz.factory;

import edu.hitsz.aircraft.Enemy;

public abstract class EnemyFactory {
    public abstract Enemy creatEnemy(int HP,int speedY,int speedX);
}
