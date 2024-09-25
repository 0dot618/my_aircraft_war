package edu.hitsz.template;

import edu.hitsz.application.Game;

public class MediumGame extends Game {
    public MediumGame() {
        super();
        enemySpeedY = 7;
        eliteEnemyProbability = 0.3;
        elitePlusEnemyProbability = 0.1;
        generateEnemyCycleDuration = 600;
        heroShootCycleDuration = 600;
        enemyShootCycleDuration = 600;
        noBoss = false;
    }

    /**
     * 1. 增加游戏界面中出现的敌机数量的最大值
     * 2. 增加敌机横向速度
     * 3. 增加敌机纵向速度
     * 4. 增加精英敌机产生的概率
     * 5. 增加超级精英敌机产生的概率
     * 6. 增加敌机产生的频率
     * 7. 增加敌机射击的频率
     */
    public void changeDifficulty() {
        enemyMaxNumber += 0.1;
        enemySpeedX += 0.1;
        enemySpeedY += 0.2;
        eliteEnemyProbability *= 1.03;
        elitePlusEnemyProbability *= 1.03;
        generateEnemyCycleDuration /= 1.03;
        enemyShootCycleDuration /= 1.01;

        System.out.println("提高难度!");
        System.out.println("屏幕中出现的敌机最大数量:"+(int)enemyMaxNumber);
        System.out.println("可横向移动敌机的横向速度:"+(int)enemySpeedX);
        System.out.println("敌机纵向速度:"+(int)enemySpeedY);
        System.out.println("精英敌机产生概率:"+String.format("%.2f",eliteEnemyProbability));
        System.out.println("超级精英敌机产生概率:"+String.format("%.2f",elitePlusEnemyProbability));
        System.out.println("敌机产生周期:"+generateEnemyCycleDuration);
        System.out.println("敌机射击周期:"+enemyShootCycleDuration);
    }
}
