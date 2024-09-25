package edu.hitsz.aircraft;

import edu.hitsz.application.Main;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.prop.BaseProp;
import edu.hitsz.strategy.ShootStrategy;

import java.util.List;

public abstract class Enemy extends AbstractAircraft{

    //此敌机坠毁能获得的经验值
    protected int EXP;

    public Enemy(ShootStrategy shootStrategy) {
        super(shootStrategy);
        direction = 1;
        shootPower = 10;
    }

    @Override
    public void forward() {
        super.forward();
        // 判定 y 轴向下飞行出界
        if (locationY >= Main.WINDOW_HEIGHT ) {
            vanish();
        }
    }

    public int getEXP() {
        return EXP;
    }

    public abstract List<BaseProp> generateProp(int propLocationX, int propLocationY);
}
