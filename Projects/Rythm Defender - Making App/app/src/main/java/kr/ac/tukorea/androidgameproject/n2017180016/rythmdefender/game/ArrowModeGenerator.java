package kr.ac.tukorea.androidgameproject.n2017180016.rythmdefender.game;

public class ArrowModeGenerator extends ObjectGenerator{
    BitModeGenerator bitMode;
    ArrowModeGenerator(String jsonFileName, BitModeGenerator bitMode) {
        super(jsonFileName);
        this.bitMode = bitMode;
    }
}
