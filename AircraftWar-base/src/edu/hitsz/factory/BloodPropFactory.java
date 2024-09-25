package edu.hitsz.factory;

import edu.hitsz.prop.BaseProp;
import edu.hitsz.prop.BloodProp;

public class BloodPropFactory extends PropFactory {
    @Override
    public BaseProp creatProp(int locationX, int locationY) {
        return new BloodProp(locationX,locationY,0,6);
    }
}
