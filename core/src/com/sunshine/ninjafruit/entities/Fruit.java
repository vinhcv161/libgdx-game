package com.sunshine.ninjafruit.entities;

import com.badlogic.gdx.math.Vector2;
import com.sunshine.ninjafruit.NinjaFruitGame;

public class Fruit {
    public static float radius = 60f;

    public enum Type {
        REGULAR, EXTRA, BOMB, COIN
    }

    public Type type;
    Vector2 pos, velocity;
    public boolean living = true;

    public Fruit(Vector2 pos, Vector2 velocity) {
        this.pos = pos;
        this.velocity = velocity;
        type = Type.REGULAR;
    }

    public boolean clicked(Vector2 drag) {
        if (pos.dst2(drag) <= radius * radius + 1) return true;
        return false;
    }

    public final Vector2 getPos() {
        return pos;
    }

    public boolean outOfScreen() {
        return (pos.y < -2f * radius);
    }

    public void update(float dt) {
        velocity.y -= dt * (NinjaFruitGame.HEIGHT * 0.2f);
        velocity.x -= dt * Math.signum(velocity.x) * 5f;
        pos.mulAdd(velocity, dt);

    }

}
