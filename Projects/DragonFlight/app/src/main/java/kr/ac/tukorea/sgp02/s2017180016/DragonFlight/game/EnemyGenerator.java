package kr.ac.tukorea.sgp02.s2017180016.DragonFlight.game;

import android.graphics.Canvas;

import java.util.Random;

import kr.ac.tukorea.sgp02.s2017180016.DragonFlight.R;
import kr.ac.tukorea.sgp02.s2017180016.DragonFlight.framework.GameObject;
import kr.ac.tukorea.sgp02.s2017180016.DragonFlight.framework.Metrics;

public class EnemyGenerator implements GameObject {
    private static final float INITIAL_SPAWN_INTERVAL = 2.0f;
    private final float spawnInterval;
    private final float fallSpeed;
    private float elapsedTime;
    private int wave;

    public EnemyGenerator() {
        this.spawnInterval = INITIAL_SPAWN_INTERVAL;
        this.fallSpeed = Metrics.size(R.dimen.enemy_initial_speed);

        float enemySize = Metrics.width / 5.0f;
        Enemy.setSize(enemySize);
    }

    @Override
    public void update() {
        float frameTime = MainGame.getInstance().frameTime;
        elapsedTime += frameTime;
        if (elapsedTime > spawnInterval) {
            spawn();
            elapsedTime -= spawnInterval;
        }
    }

    private void spawn() {
        wave++;
        Random rand = new Random();
        float tenth = Metrics.width / 10;
        for (int i = 1; i <= 9; i += 2) {

            float x = i * tenth;
            int level = (wave+15) / 10 - rand.nextInt(3);
            if (level < Enemy.MIN_LEVEL) level = Enemy.MIN_LEVEL;
            if (level > Enemy.MAX_LEVEL) level = Enemy.MAX_LEVEL;

            Enemy enemy = Enemy.get(level, x, fallSpeed);
            MainGame.getInstance().add(MainGame.Layer.enemy, enemy);
        }
    }

    @Override
    public void draw(Canvas canvas) {
    }
}