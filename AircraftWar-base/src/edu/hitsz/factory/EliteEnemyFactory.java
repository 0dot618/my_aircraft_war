package edu.hitsz.factory;

import edu.hitsz.aircraft.EliteEnemy;
import edu.hitsz.aircraft.Enemy;
import edu.hitsz.strategy.StraightShootStrategy;

public class EliteEnemyFactory extends EnemyFactory {
    @Override
    public Enemy creatEnemy(int HP,int speedY,int speedX) {
        Enemy enemy = new EliteEnemy(new StraightShootStrategy(),HP,speedY,speedX);
        return enemy;
    }
}
