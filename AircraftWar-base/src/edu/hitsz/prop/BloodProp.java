package edu.hitsz.prop;

import edu.hitsz.aircraft.HeroAircraft;

/**
 * 加血道具
 * @Author 220110504-李乐怡
 */
public class BloodProp extends BaseProp {

    public BloodProp(int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);
    }

    /**
     * 加血道具生效
     */
    @Override
    public void propActive(HeroAircraft heroAircraft) {
        heroAircraft.increaseHp(20);
    }
}
