package edu.hitsz.factory;

import edu.hitsz.aircraft.BossEnemy;
import edu.hitsz.aircraft.Enemy;
import edu.hitsz.strategy.CircleShootStrategy;

public class BossEnemyFactory extends EnemyFactory {
    private int bossEnemyHp;
    public BossEnemyFactory(int bossEnemyHp) {
        this.bossEnemyHp = bossEnemyHp;
    }

    @Override
    public Enemy creatEnemy(int HP,int speedY,int speedX) {
        Enemy enemy = new BossEnemy(new CircleShootStrategy(),bossEnemyHp,speedX);
        return enemy;
    }
}
