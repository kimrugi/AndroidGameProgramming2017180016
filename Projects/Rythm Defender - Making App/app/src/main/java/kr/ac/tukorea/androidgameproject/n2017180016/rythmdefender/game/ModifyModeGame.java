package kr.ac.tukorea.androidgameproject.n2017180016.rythmdefender.game;

import android.os.Build;

import java.util.ArrayList;
import java.util.Comparator;

public class ModifyModeGame extends ObjectGenerator{
    ModifyModeGame(String jsonFileName) {
        super(jsonFileName);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            circleInfos.sort(new Comparator<CircleInfo>() {
                @Override
                public int compare(CircleInfo a, CircleInfo b) {
                    if(a.startTime < b.startTime) return -1;
                    if(a.startTime > b.startTime) return 1;
                    return 0;
                }
            });
        }
        ArrayList<CircleInfo> removeList = new ArrayList<>();

        for(CircleInfo info : circleInfos){
            ArrayList<ArrowInfo> arrowInfos = info.arrowInfos;
            if(arrowInfos == null || arrowInfos.isEmpty()){
                removeList.add(info);
            }
        }
        for(CircleInfo toRemove : removeList){
            circleInfos.remove(toRemove);
        }

        for(CircleInfo info : circleInfos){
            float arrowStartTime = info.arrowInfos.get(0).startTime;
            info.startTime = arrowStartTime - 0.5f;
            float arrowEndTime = info.arrowInfos.get(info.arrowInfos.size()-1).endTime;
            info.endTime = arrowEndTime + 0.2f;
        }
    }

    @Override
    public void save() {
        super.save("Result.json");
    }
}
