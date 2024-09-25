package edu.hitsz.application;

import edu.hitsz.Dao.*;
import edu.hitsz.aircraft.*;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.basic.AbstractFlyingObject;
import edu.hitsz.factory.*;
import edu.hitsz.prop.BaseProp;
import edu.hitsz.prop.BombProp;
import edu.hitsz.observer.FlyingObjectObserver;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;
import java.util.concurrent.*;


/**
 * 游戏主面板，游戏启动
 *
 * @author hitsz
 */
public abstract class Game extends JPanel {

    private int backGroundTop = 0;

    /**
     * Scheduled 线程池，用于任务调度
     */
    private final ScheduledExecutorService executorService;

    /**
     * 时间间隔(ms)，控制刷新频率
     */
    private int timeInterval = 40;

    private final HeroAircraft heroAircraft;
    private final List<Enemy> enemyAircrafts;
    private final List<BaseBullet> heroBullets;
    private final List<BaseBullet> enemyBullets;
    private final List<BaseProp> props;
    private EnemyFactory enemyFactory;

    /**
     * 不同难度的不同设置
     */
    //1. 屏幕中出现的敌机最大数量
    protected double enemyMaxNumber = 5;
    //2. 敌机基础信息
    protected int enemyHP = 30;
    protected double enemySpeedX = 1;
    protected double enemySpeedY = 6;
    //3. 精英敌机产生概率
    protected double eliteEnemyProbability = 0.2;
    protected double elitePlusEnemyProbability = 0.08;
    //4. Boss敌机出现时的血量
    protected int bossEnemyHp = 120;
    //5. 产生敌机、英雄机射击、敌机射击的频率
    protected int generateEnemyCycleDuration = 700;
    private int[] generateEnemyCycleTime = {0};
    protected int heroShootCycleDuration = 500;
    private int[] heroShootCycleTime = {0};
    protected int enemyShootCycleDuration = 700;
    private int[] enemyShootCycleTime = {0};

    //是否生成boss敌机
    protected boolean noBoss = true;

    //每次出现boss机的血量提升
    protected int increaseBossHP = 0;

    /**
     * 难度提升的周期
     */
    protected int changeCycleDuration = 7000;
    private int[] changeCycleTime = {0};

    //Boss敌机产生的得分阈值
    private int scoreThreshold = 300;
    private final int initialScoreThreshold = 300;

    /**
     * 当前得分
     */
    private static int score = 0;
    /**
     * 当前时刻
     */
    private int time = 0;

    /**
     * 当前是否有Boss敌机
     */
    private boolean existBossEnemy = false;

    /**
     * bgm线程
     */
    private final MusicThread backgroundMusicThread = new MusicThread("src/videos/bgm.wav",true);

    /**
     * bgm_boss线程
     */
    private MusicThread backgroundBossMusicThread;


    /**
     * 游戏结束标志
     */
    private boolean gameOverFlag = false;

    public Game() {
        heroAircraft = HeroAircraft.getHeroAircraft();

        enemyAircrafts = new LinkedList<>();
        heroBullets = new LinkedList<>();
        enemyBullets = new LinkedList<>();
        props = new LinkedList<>();

        /**
         * Scheduled 线程池，用于定时任务调度
         * 关于alibaba code guide：可命名的 ThreadFactory 一般需要第三方包
         * apache 第三方库： org.apache.commons.lang3.concurrent.BasicThreadFactory
         */
        this.executorService = new ScheduledThreadPoolExecutor(1,
                new BasicThreadFactory.Builder().namingPattern("game-action-%d").daemon(true).build());

        //启动英雄机鼠标监听
        new HeroController(this, heroAircraft);

    }

    /**
     * 游戏启动入口，执行游戏逻辑
     */
    public final void action() {

        backgroundMusicThread.start();

        // 定时任务：绘制、对象产生、碰撞判定、击毁及结束判定
        Runnable task = () -> {

            time += timeInterval;

            // 周期性执行（控制提升难度频率）
            if (timeCountAndNewCycleJudge(changeCycleTime,changeCycleDuration)) {
                changeDifficulty();
            }

            // 周期性执行（控制产生敌机频率）
            if (timeCountAndNewCycleJudge(generateEnemyCycleTime,generateEnemyCycleDuration)) {
                System.out.println(time);
                // 新敌机产生
                if (enemyAircrafts.size() < (int)enemyMaxNumber) {
                    enemyAircraftGenerate();
                }
            }

            // 周期性执行（控制英雄机射击频率）
            if (timeCountAndNewCycleJudge(heroShootCycleTime,heroShootCycleDuration)) {
                heroShootAction();
            }

            // 周期性执行（控制敌机射击频率）
            if (timeCountAndNewCycleJudge(enemyShootCycleTime,enemyShootCycleDuration)) {
                enemyShootAction();
            }

            // 子弹移动
            bulletsMoveAction();

            // 飞机移动
            aircraftsMoveAction();

            // 道具移动
            propsMoveAction();

            // 撞击检测
            crashCheckAction();

            // 后处理
            postProcessAction();

            //每个时刻重绘界面
            repaint();

            // 游戏结束检查英雄机是否存活
            if (heroAircraft.getHp() <= 0) {
                // 游戏结束
                gameOverAction();
            }

        };

        /**
         * 以固定延迟时间进行执行
         * 本次任务执行完成后，需要延迟设定的延迟时间，才会执行新的任务
         */
        executorService.scheduleWithFixedDelay(task, timeInterval, timeInterval, TimeUnit.MILLISECONDS);

    }

    //***********************
    //      Action 各部分
    //***********************

    private boolean timeCountAndNewCycleJudge(int[] cycleTime, int cycleDuration) {
        cycleTime[0] += timeInterval;
        if (cycleTime[0] >= cycleDuration && cycleTime[0] - timeInterval < cycleTime[0]) {
            // 跨越到新的周期
            cycleTime[0] %= cycleDuration;
            return true;
        } else {
            return false;
        }
    }

    public abstract void changeDifficulty();

    private void enemyAircraftGenerate() {
        double ran = Math.random();
        //满足分数达到阈值且当前无boss敌机，产生新的boss敌机
        if(!noBoss && score >= scoreThreshold && !existBossEnemy) {
            backgroundBossMusicThread = new MusicThread("src/videos/bgm_boss.wav",true);
            backgroundBossMusicThread.start();

            enemyFactory = new BossEnemyFactory(bossEnemyHp);
            existBossEnemy = true;
            bossEnemyHp += increaseBossHP;
            scoreThreshold += initialScoreThreshold;
        }
        //否则产生其他非Boss敌机
        else if(ran<=elitePlusEnemyProbability) {
            enemyFactory = new ElitePlusEnemyFactory();
        }
        else if(ran<=eliteEnemyProbability+elitePlusEnemyProbability){
            enemyFactory = new EliteEnemyFactory();
        }
        else {
            enemyFactory = new MobEnemyFactory();
        }
        Enemy enemy = enemyFactory.creatEnemy(enemyHP,(int)enemySpeedY,(int)enemySpeedX);
        enemyAircrafts.add(enemy);
        BombProp.addFlyingObjectObserver((FlyingObjectObserver) enemy);
    }

    private void heroShootAction() {
        // 英雄射击
        heroBullets.addAll(heroAircraft.shoot());
    }

    private void enemyShootAction() {
        // 敌机射击
        for(Enemy enemyAircraft : enemyAircrafts){
            List<BaseBullet> enemyBulletList1 = enemyAircraft.shoot();
            enemyBullets.addAll(enemyBulletList1);
            BombProp.addFlyingObjectObserver(enemyBulletList1);
        }
    }

    private void bulletsMoveAction() {
        for (BaseBullet bullet : heroBullets) {
            bullet.forward();
        }
        for (BaseBullet bullet : enemyBullets) {
            bullet.forward();
        }
    }

    private void aircraftsMoveAction() {
        for (Enemy enemyAircraft : enemyAircrafts) {
            enemyAircraft.forward();
        }
    }

    private void propsMoveAction() {
        for (BaseProp prop : props) {
            prop.forward();
        }
    }

    //获取坠毁敌机的分数
    public static void increaseScore(int increase) {
        score += increase;
    }


    /**
     * 碰撞检测：
     * 1. 敌机攻击英雄
     * 2. 英雄攻击/撞击敌机
     * 3. 英雄获得补给
     */
    private void crashCheckAction() {
        // 敌机子弹攻击英雄
        for (BaseBullet bullet : enemyBullets) {
            if(bullet.notValid()) {
                continue;
            }
            if (heroAircraft.crash(bullet)) {
                //英雄机撞击到精英机子弹
                //英雄机损失一定生命值
                heroAircraft.decreaseHp(bullet.getPower());
                bullet.vanish();
            }
        }

        // 英雄子弹攻击敌机
        for (BaseBullet bullet : heroBullets) {
            if (bullet.notValid()) {
                continue;
            }
            for (Enemy enemyAircraft : enemyAircrafts) {
                if (enemyAircraft.notValid()) {
                    // 若Boss敌机坠毁，标记当前不存在Boss敌机，更改score阈值，boss敌机bgm结束
                    if(enemyAircraft instanceof BossEnemy) {
                        backgroundBossMusicThread.setStop();

                        existBossEnemy = false;
                        scoreThreshold = (score / 300) * 300 + 300;

                    }
                    // 已被其他子弹击毁的敌机，不再检测
                    // 避免多个子弹重复击毁同一敌机的判定
                    continue;
                }
                if (enemyAircraft.crash(bullet)) {
                    // 敌机撞击到英雄机子弹
                    // 敌机损失一定生命值
                    new MusicThread("src/videos/bullet_hit.wav",false).start();
                    int enemyAircraftLocationX = enemyAircraft.getLocationX();
                    int enemyAircraftLocationY = enemyAircraft.getLocationY();
                    enemyAircraft.decreaseHp(bullet.getPower());
                    bullet.vanish();
                    if (enemyAircraft.notValid()) {
                        // 获得分数，产生道具补给
                        List<BaseProp> newProps = enemyAircraft.generateProp(enemyAircraftLocationX,enemyAircraftLocationY);
                        props.addAll(newProps);
                        increaseScore(enemyAircraft.getEXP());
                    }
                }
                // 英雄机 与 敌机 相撞，均损毁
                if (enemyAircraft.crash(heroAircraft) || heroAircraft.crash(enemyAircraft)) {
                    enemyAircraft.vanish();
                    heroAircraft.decreaseHp(Integer.MAX_VALUE);
                }
            }
        }

        // 我方获得道具，道具生效
        for (BaseProp prop : props) {
            if (prop.notValid()) {
                continue;
            }
            //道具与英雄机相撞
            if (prop.crash(heroAircraft) || heroAircraft.crash(prop)) {
                new MusicThread("src/videos/get_supply.wav",false).start();
                prop.propActive(heroAircraft);
                prop.vanish();
            }
        }
    }

    /**
     * 后处理：
     * 1. 删除无效的子弹
     * 2. 删除无效的敌机
     * 3. 删除无效的道具
     * <p>
     * 无效的原因可能是撞击或者飞出边界
     */
    private void postProcessAction() {
        enemyBullets.removeIf(AbstractFlyingObject::notValid);
        heroBullets.removeIf(AbstractFlyingObject::notValid);
        enemyAircrafts.removeIf(AbstractFlyingObject::notValid);
        props.removeIf(AbstractFlyingObject::notValid);
    }

    /**
     * 游戏结束
     */
    private void gameOverAction() {
        backgroundMusicThread.setStop();
        if(existBossEnemy) {
            backgroundBossMusicThread.setStop();
        }

        new MusicThread("src/videos/game_over.wav",false).start();

        System.out.println("Game Over!");
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd HH:mm");
        String format = dateFormat.format(date);
        ScoreTable scoreTable = new ScoreTable(score,format);
        Main.cardPanel.add(scoreTable.getMainPanel());
        Main.cardLayout.last(Main.cardPanel);

        executorService.shutdown();
        gameOverFlag = true;

    }


    //***********************
    //      Paint 各部分
    //***********************

    /**
     * 重写paint方法
     * 通过重复调用paint方法，实现游戏动画
     *
     * @param  g
     */
    @Override
    public void paint(Graphics g) {
        super.paint(g);

        // 绘制背景,图片滚动
        g.drawImage(ImageManager.BACKGROUND_IMAGE, 0, this.backGroundTop - Main.WINDOW_HEIGHT, null);
        g.drawImage(ImageManager.BACKGROUND_IMAGE, 0, this.backGroundTop, null);
        this.backGroundTop += 1;
        if (this.backGroundTop == Main.WINDOW_HEIGHT) {
            this.backGroundTop = 0;
        }

        // 先绘制子弹，后绘制飞机
        // 这样子弹显示在飞机的下层
        paintImageWithPositionRevised(g, enemyBullets);
        paintImageWithPositionRevised(g, heroBullets);

        paintImageWithPositionRevised(g, enemyAircrafts);

        paintImageWithPositionRevised(g, props);

        g.drawImage(ImageManager.HERO_IMAGE, heroAircraft.getLocationX() - ImageManager.HERO_IMAGE.getWidth() / 2,
                heroAircraft.getLocationY() - ImageManager.HERO_IMAGE.getHeight() / 2, null);

        //绘制得分和生命值
        paintScoreAndLife(g);

    }

    private void paintImageWithPositionRevised(Graphics g, List<? extends AbstractFlyingObject> objects) {
        if (objects.size() == 0) {
            return;
        }

        for (AbstractFlyingObject object : objects) {
            BufferedImage image = object.getImage();
            assert image != null : objects.getClass().getName() + " has no image! ";
            g.drawImage(image, object.getLocationX() - image.getWidth() / 2,
                    object.getLocationY() - image.getHeight() / 2, null);
        }
    }

    private void paintScoreAndLife(Graphics g) {
        int x = 10;
        int y = 25;
        g.setColor(new Color(16711680));
        g.setFont(new Font("SansSerif", Font.BOLD, 22));
        g.drawString("SCORE:" + this.score, x, y);
        y = y + 20;
        g.drawString("LIFE:" + this.heroAircraft.getHp(), x, y);
    }


}
