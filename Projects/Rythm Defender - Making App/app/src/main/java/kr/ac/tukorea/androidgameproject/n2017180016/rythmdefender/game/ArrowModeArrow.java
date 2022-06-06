package kr.ac.tukorea.androidgameproject.n2017180016.rythmdefender.game;

import kr.ac.tukorea.androidgameproject.n2017180016.rythmdefender.framework.Util;

public class ArrowModeArrow extends Arrow{
    public ArrowModeArrow(float x, float y, float cx, float cy, float angle, Circle circle, float startTime, float endTime) {
        super(x, y, cx, cy, angle, circle, startTime, endTime);
    }

    @Override
    public void update() {
        MainGame game = MainGame.getInstance();
        if(game.totalTime >= endTime){
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

    }
}
