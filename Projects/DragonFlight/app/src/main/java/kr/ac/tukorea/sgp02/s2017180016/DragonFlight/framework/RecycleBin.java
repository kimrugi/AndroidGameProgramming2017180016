package kr.ac.tukorea.sgp02.s2017180016.DragonFlight.framework;

import java.sql.Array;
import java.util.ArrayList;
import java.util.HashMap;

public class RecycleBin {
    public static void clear(){
        recycleBin.clear();
    }

    private static HashMap<Class, ArrayList<Recyclable>> recycleBin = new HashMap<>();

    public static Recyclable get(Class clazz){
        ArrayList<Recyclable> bin = recycleBin.get(clazz);
        if (bin == null) return null; //재활용 된 적이 없음
        if (bin.size() == 0) return null; //재활용 된 적이 있으나 지금 없음
        return bin.remove(0);
    }

    public static void add(Recyclable object){
        object.finish();

        Class clazz = object.getClass();
        ArrayList<Recyclable> bin = recycleBin.get(clazz);
        if(bin == null){
            bin = new ArrayList<>();
            recycleBin.put(clazz, bin);
        }
        if(bin.indexOf(object) >= 0){
            return;
        }
        bin.add(object);
    }
}
