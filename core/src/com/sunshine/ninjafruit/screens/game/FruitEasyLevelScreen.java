package com.sunshine.ninjafruit.screens.game;

import com.sunshine.ninjafruit.NinjaFruitGame;
import com.sunshine.ninjafruit.entities.LevelTransporters;
import com.sunshine.ninjafruit.tools.NinjaFruitCommon;

public class FruitEasyLevelScreen extends NinjaFruitCommon {
    public FruitEasyLevelScreen(NinjaFruitGame game) {
        super(game);
        LevelTransporters trans = new LevelTransporters();
        super.init(trans);

        System.out.println("FruitEasyLevelScreen");
    }
}
