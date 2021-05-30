package com.sunshine.ninjafruit.screens.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.sunshine.ninjafruit.NinjaFruitGame;
import com.sunshine.ninjafruit.entities.LevelTransporters;
import com.sunshine.ninjafruit.tools.NinjaFruitCommon;

public class FruitHighLevelScreen extends NinjaFruitCommon {
    public FruitHighLevelScreen(NinjaFruitGame game) {
        super(game);
        LevelTransporters trans = new LevelTransporters();
        trans.background = new Texture("fruit-game/background1.jpg");
        trans.music = Gdx.audio.newMusic(Gdx.files.internal("audios/FruitNinjaThemeSong.mp3"));
        trans.zorlukLimit = 0.48f;
        trans.genSpeed = 0.76f;
        super.init(trans);

        System.out.println("FruitHighLevelScreen");
    }
}
