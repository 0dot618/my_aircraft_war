package edu.hitsz.aircraft;

import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.HeroBullet;
import edu.hitsz.prop.BaseProp;
import edu.hitsz.factory.BloodPropFactory;
import edu.hitsz.factory.PropFactory;
import org.junit.jupiter.api.*;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HeroAircraftTest {
    private HeroAircraft heroAircraft;

    @BeforeAll
    static void beforeAll() {
        System.out.println("**--- Executed once before all test methods in this class ---**");
    }

    @BeforeEach
    void setUp() {
        heroAircraft = HeroAircraft.getHeroAircraft();
    }

    @AfterEach
    void tearDown() {
        heroAircraft = null;
    }

    @DisplayName("Test increase method")
    @org.junit.jupiter.api.Test
    void increaseHp() {
        int increase = 10;
        int decrease = 15;
        int hp1 = 995;
        int hp2 = 1000;
        heroAircraft.decreaseHp(decrease);
        heroAircraft.increaseHp(increase);
        assertEquals(hp1, heroAircraft.getHp());
        heroAircraft.increaseHp(increase);
        assertEquals(hp2, heroAircraft.getHp());
    }

    @DisplayName("Test crash method")
    @org.junit.jupiter.api.Test
    void crash() {
        heroAircraft.setLocation(100,200);
        PropFactory propFactory = new BloodPropFactory();
        BaseProp prop = propFactory.creatProp(105,205);
        assert(heroAircraft.crash(prop));
    }

    @DisplayName("Test shoot method")
    @org.junit.jupiter.api.Test
    void shoot() {
        heroAircraft.setLocation(100,200);
        BaseBullet bullet = new HeroBullet(100,198,0,-5,30);
        List<BaseBullet> list1 = new LinkedList<>();
        list1.add(bullet);
        List<BaseBullet> list2 = heroAircraft.shoot();
        assert(list2.size() == list1.size());
        for(BaseBullet bullet1 : list2) {
            assert(bullet1.getLocationX() == bullet.getLocationX());
            assert(bullet1.getLocationY() == bullet.getLocationY());
            assert(bullet1.getSpeedX() == bullet.getSpeedX());
            assert(bullet1.getSpeedY() == bullet.getSpeedY());
            assert(bullet1.getPower() == bullet.getPower());
            assert(bullet1.getImage().equals(bullet.getImage()));
            assert(bullet1.getHeight() == bullet.getHeight());
            assert(bullet1.getWidth() == bullet.getWidth());
            assert(bullet.notValid() == bullet1.notValid());
        }
    }

    @DisplayName("Test notValid method")
    @org.junit.jupiter.api.Test
    void notValid() {
        assert(!heroAircraft.notValid());
        heroAircraft.vanish();
        assert(heroAircraft.notValid());
    }
}