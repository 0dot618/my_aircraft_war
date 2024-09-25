package edu.hitsz.template;

import edu.hitsz.application.Game;

public class HardGame extends Game {
    public HardGame() {
        super();
        enemySpeedY = 9;
        eliteEnemyProbability = 0.35;
        elitePlusEnemyProbability = 0.15;
        generateEnemyCycleDuration = 600;
        heroShootCycleDuration = 600;
        enemyShootCycleDuration = 550;
        noBoss = false;
        bossEnemyHp = 180;
        increaseBossHP = 60;
        changeCycleDuration = 5000;
    }

    /**
     * 1. 增加游戏界面中出现的敌机数量的最大值
     * 2. 增加敌机横向速度
     * 3. 增加敌机纵向速度
     * 4. 增加精英敌机产生的概率
     * 5. 增加超级精英敌机产生的概率
     * 6. 增加敌机产生的频率
     * 5. 增加敌机射击的频率
     */
    public void changeDifficulty() {
        enemyMaxNumber += 0.15;
        enemySpeedX += 0.15;
        enemySpeedY += 0.3;
        eliteEnemyProbability *= 1.1;
        elitePlusEnemyProbability *= 1.1;
        generateEnemyCycleDuration /= 1.1;
        enemyShootCycleDuration /= 1.04;

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
