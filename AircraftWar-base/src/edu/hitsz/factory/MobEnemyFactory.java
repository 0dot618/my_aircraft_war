package edu.hitsz.factory;

import edu.hitsz.aircraft.Enemy;
import edu.hitsz.aircraft.MobEnemy;
import edu.hitsz.strategy.NotShootStrategy;

public class MobEnemyFactory extends EnemyFactory {
    @Override
    public Enemy creatEnemy(int HP,int speedY,int speedX) {
        Enemy enemy = new MobEnemy(new NotShootStrategy(),HP,speedY);
        return enemy;
    }
}
