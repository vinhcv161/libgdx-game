package com.sunshine.ninjafruit.entities;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class LevelTransporters {
    // giao diện
    public Texture background;
    public BitmapFont textFont;
    public Music music;
    public Sound sound;

    // độ khó
    public Float genSpeed;
    public Float zorlukLimit;

    public LevelTransporters() {
    }

    public LevelTransporters(Texture background, BitmapFont textFont, Music music, Sound sound, Float genSpeed, Float zorlukLimit) {
        this.background = background;
        this.textFont = textFont;
        this.music = music;
        this.sound = sound;
        this.genSpeed = genSpeed;
        this.zorlukLimit = zorlukLimit;
    }

    public LevelTransporters(Texture background, Music music, Float genSpeed, Float zorlukLimit) {
        this.background = background;
        this.music = music;
        this.genSpeed = genSpeed;
        this.zorlukLimit = zorlukLimit;
    }
}
