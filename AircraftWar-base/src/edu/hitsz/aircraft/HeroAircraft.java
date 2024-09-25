package edu.hitsz.aircraft;

import edu.hitsz.application.ImageManager;
import edu.hitsz.application.Main;
import edu.hitsz.strategy.ShootStrategy;
import edu.hitsz.strategy.StraightShootStrategy;

/**
 * 英雄飞机，游戏玩家操控
 * @author hitsz
 */
public class HeroAircraft extends AbstractAircraft {

    /**
     * 单例模式储存·实例
     */
    private static HeroAircraft heroAircraft;

    /**
     * 英雄机当前的射击编码，每碰撞到一个新的火力道具会得到新的射击编码
     */
    private int shootMode = 0;

    /**
     * @param locationX 英雄机位置x坐标
     * @param locationY 英雄机位置y坐标
     * @param speedX 英雄机射出的子弹的基准速度（英雄机无特定速度）
     * @param speedY 英雄机射出的子弹的基准速度（英雄机无特定速度）
     * @param hp    初始生命值
     */
    private HeroAircraft(int locationX, int locationY, int speedX, int speedY, int hp, ShootStrategy shootStrategy) {
        super(locationX, locationY, speedX, speedY, hp, shootStrategy);
        this.shootPower = 30;
        this.direction = -1;
    }

    /**
     * 允许访问唯一实例
     */
    public static HeroAircraft getHeroAircraft() {
        if (heroAircraft == null) {
            heroAircraft = new HeroAircraft(
                    Main.WINDOW_WIDTH / 2,
                    Main.WINDOW_HEIGHT - ImageManager.HERO_IMAGE.getHeight() ,
                    0, 0, 1000, new StraightShootStrategy());
        }
        return heroAircraft;
    }


    public int getShootMode(){
        return this.shootMode;
    }

    public void setShootMode(int shootMode){
        this.shootMode = shootMode;
    }


    @Override
    public void forward() {
        // 英雄机由鼠标控制，不通过forward函数移动
    }


}
