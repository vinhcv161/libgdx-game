package com.fruitslicer;

import com.badlogic.gdx.graphics.Texture;

public class Button {
    private float x, y, width, height;
    private Texture text;

    public Button() {
    }

    public Button(Texture text, float x, float y, float width, float height) {
        this.text = text;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public Texture getText() {
        return text;
    }

    public void setText(Texture text) {
        this.text = text;
    }
}
