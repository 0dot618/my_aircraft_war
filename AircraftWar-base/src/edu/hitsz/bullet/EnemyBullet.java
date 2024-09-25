package edu.hitsz.bullet;

import edu.hitsz.prop.BombProp;
import edu.hitsz.observer.FlyingObjectObserver;

/**
 * @Author hitsz
 */
public class EnemyBullet extends BaseBullet implements FlyingObjectObserver {

    public EnemyBullet(int locationX, int locationY, int speedX, int speedY, int power) {
        super(locationX, locationY, speedX, speedY, power);
    }

    @Override
    public void vanish()
    {
        isValid = false;
        BombProp.removeFlyingObjectObserver(this);
    }

    //观察炸弹道具做出对应的响应：销毁
    public void update()
    {
        vanish();
    }

}
