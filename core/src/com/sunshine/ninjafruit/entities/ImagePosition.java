package com.sunshine.ninjafruit.entities;

import com.badlogic.gdx.graphics.Texture;
import com.sunshine.ninjafruit.NinjaFruitGame;

public class ImagePosition {
    public float x, y, width, height;
    public Texture text;

    public ImagePosition() {
    }

    public ImagePosition(Texture text, float x, float y, float width, float height) {
        this.text = text;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public boolean isClicked(float screenX, float screenY) {
        return  x < screenX && screenX < x + width && // width
                NinjaFruitGame.HEIGHT - y > screenY && screenY > NinjaFruitGame.HEIGHT - y - height; // height
    }

}
