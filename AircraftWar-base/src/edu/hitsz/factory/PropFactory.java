package edu.hitsz.factory;

import edu.hitsz.prop.BaseProp;

public abstract class PropFactory {
    public abstract BaseProp creatProp(int locationX, int locationY);
}
