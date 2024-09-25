package edu.hitsz.prop;

import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.application.MusicThread;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.observer.FlyingObjectObserver;

import java.util.ArrayList;
import java.util.List;

/**
 * 炸弹道具
 * @Author 220110504-李乐怡
 */
public class BombProp extends BaseProp {

    public BombProp(int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);
    }

    //观察者列表
    private static List<FlyingObjectObserver> flyingObjectObserverList = new ArrayList<>();

    //增加观察者
    public static void addFlyingObjectObserver(FlyingObjectObserver objectObserver) {
        flyingObjectObserverList.add(objectObserver);
    }
    public static void addFlyingObjectObserver(List<BaseBullet> objectObservers) {
        for(BaseBullet objectObserver:objectObservers){
            flyingObjectObserverList.add((FlyingObjectObserver) objectObserver);
        }
    }

    //删除观察者
    public static void removeFlyingObjectObserver(FlyingObjectObserver objectObserver) {
        flyingObjectObserverList.remove(objectObserver);
    }

    //通知所有观察者
    public void notifyFlyingObjectObserver() {
        for(int i = flyingObjectObserverList.size()-1;i>=0;i--){
            FlyingObjectObserver objectObserver = flyingObjectObserverList.get(i);
            objectObserver.update();
        }
    }

    /**
     * 炸弹道具生效
     */
    @Override
    public void propActive(HeroAircraft heroAircraft) {
        System.out.println("BombSupply active!");
        notifyFlyingObjectObserver();
        new MusicThread("src/videos/bomb_explosion.wav",false).start();
    }
}
