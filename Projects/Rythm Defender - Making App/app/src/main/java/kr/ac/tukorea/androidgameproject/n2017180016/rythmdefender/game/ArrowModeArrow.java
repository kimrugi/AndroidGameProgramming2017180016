package kr.ac.tukorea.androidgameproject.n2017180016.rythmdefender.game;

import kr.ac.tukorea.androidgameproject.n2017180016.rythmdefender.framework.Util;

public class ArrowModeArrow extends Arrow{
    boolean isActive = true;
    public ArrowModeArrow(float x, float y, float cx, float cy, float angle, Circle circle, float startTime, float endTime) {
        super(x, y, cx, cy, angle, circle, startTime, endTime);
    }

    @Override
    public void update() {
        if(!isActive) return;
        MainGame game = MainGame.getInstance();
        if(game.totalTime >= endTime){
            isActive = false;
            ((ArrowModeCircle)circle).finishArrow(this);
            circle = null;
            game.remove(this);
            return;
        }

        float factor = (game.totalTime - startTime) / (endTime - startTime);
        factor *= factor * factor;
        x = Util.lerp(headx, originx, factor);
        y = Util.lerp(heady, originy, factor);
    }

    public void onMove(float angle){
        if(!isActive) return;
        angle -= 180;
        this.angle = angle;
        this.circle = circle;

        float radius = circle.getRadius();
        float originDist = radius * 2;
        this.originx  = (float) (circleX + Math.cos(Math.toRadians(angle)) * originDist);
        this.originy  = (float) (circleY + Math.sin(Math.toRadians(angle)) * originDist);

        float circleDist = radius - width/2;
        headx = (float) (circleX + Math.cos(Math.toRadians(angle)) * circleDist);
        heady = (float) (circleY + Math.sin(Math.toRadians(angle)) * circleDist);
    }
}
