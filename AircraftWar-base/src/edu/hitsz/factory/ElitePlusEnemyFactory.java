package edu.hitsz.factory;

import edu.hitsz.aircraft.ElitePlusEnemy;
import edu.hitsz.aircraft.Enemy;
import edu.hitsz.strategy.SectorShootStrategy;

public class ElitePlusEnemyFactory extends EnemyFactory {
    @Override
    public Enemy creatEnemy(int HP,int speedY,int speedX) {
        Enemy enemy = new ElitePlusEnemy(new SectorShootStrategy(),HP,speedY,speedX);
        return enemy;
    }
}
