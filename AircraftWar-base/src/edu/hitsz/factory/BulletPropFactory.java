package edu.hitsz.factory;

import edu.hitsz.prop.BaseProp;
import edu.hitsz.prop.BulletProp;

public class BulletPropFactory extends PropFactory {
    @Override
    public BaseProp creatProp(int locationX, int locationY) {
        return new BulletProp(locationX,locationY,0,6);
    }
}
