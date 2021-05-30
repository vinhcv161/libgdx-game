package com.sunshine.ninjafruit.entities;

import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.sunshine.ninjafruit.NinjaFruitGame;

public class TextPosition {
    public float x, y;

    public GlyphLayout layout;

    public TextPosition() {
    }

    public TextPosition(GlyphLayout layout, float x, float y) {
        this.layout = layout;
        this.x = x;
        this.y = y;
    }

    public boolean isClicked(float touchX, float touchY) {
        float yT = NinjaFruitGame.HEIGHT - touchY;
        return touchX > x && touchX < x + layout.width && yT > y - layout.height && yT < y;
    }

}
