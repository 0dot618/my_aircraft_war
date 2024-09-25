package edu.hitsz.prop;

import edu.hitsz.strategy.CircleShootStrategy;
import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.strategy.StraightShootStrategy;

/**
 * 火力道具
 * @Author 220110504-李乐怡
 */
public class BulletPlusProp extends BaseProp {

    public BulletPlusProp(int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);
    }

    /**
     * 超级火力道具生效
     */
    @Override
    public void propActive(HeroAircraft heroAircraft) {
        Runnable r = ()->{
            int thisShootMode = heroAircraft.getShootMode()+1;
            heroAircraft.setShootMode(thisShootMode);
            heroAircraft.setShootStrategy(new CircleShootStrategy());
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            if(thisShootMode == heroAircraft.getShootMode()) {
                heroAircraft.setShootStrategy(new StraightShootStrategy());
            }
        };
        new Thread(r).start();

        System.out.println("FirePlusSupply active!");
    }
}
