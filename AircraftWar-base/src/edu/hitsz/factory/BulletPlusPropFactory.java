package edu.hitsz.factory;

import edu.hitsz.prop.BaseProp;
import edu.hitsz.prop.BulletPlusProp;

public class BulletPlusPropFactory extends PropFactory {
    @Override
    public BaseProp creatProp(int locationX, int locationY) {
        return new BulletPlusProp(locationX,locationY,0,6);
    }
}
