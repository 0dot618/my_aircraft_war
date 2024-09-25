package edu.hitsz.factory;

import edu.hitsz.prop.BaseProp;
import edu.hitsz.prop.BombProp;

public class BombPropFactory extends PropFactory {
    @Override
    public BaseProp creatProp(int locationX, int locationY) {
        return new BombProp(locationX,locationY,0,6);
    }
}
