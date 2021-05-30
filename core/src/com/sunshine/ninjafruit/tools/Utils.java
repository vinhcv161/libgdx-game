package com.sunshine.ninjafruit.tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

public class Utils {
    public static BitmapFont createFont(String path, Color color, Integer size, Color borderColor, Float borderWidth) {
        if (path == null) return new BitmapFont(Gdx.files.internal("fonts/score.fnt"));

        FreeTypeFontGenerator fontGen = new FreeTypeFontGenerator(Gdx.files.internal(path));
        FreeTypeFontGenerator.FreeTypeFontParameter params = new FreeTypeFontGenerator.FreeTypeFontParameter();
        if (color != null) params.color = color;
        if (size != null) params.size = size;

        if (borderColor != null) params.borderColor = borderColor;
        if (borderWidth != null) params.borderWidth = borderWidth;

        return fontGen.generateFont(params);
    }
}
