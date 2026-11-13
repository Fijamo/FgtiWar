package com.duartefijamo.Fgti_war.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Ponto de salvamento intermediário no mapa.
 */
public class Checkpoint {

    private final Vector2 position;
    private final Rectangle bounds;
    private final TextureRegion textureOff;
    private final TextureRegion textureOn;
    private boolean activated = false;

    public Checkpoint(float x, float y, TextureRegion off, TextureRegion on) {
        this.position = new Vector2(x, y);
        this.bounds = new Rectangle(x, y, 32, 32);
        this.textureOff = off;
        this.textureOn = on;
    }

    public void draw(SpriteBatch batch) {
        TextureRegion current = activated ? textureOn : textureOff;
        if (current != null) {
            batch.draw(current, bounds.x, bounds.y, bounds.width, bounds.height);
        }
    }

    public boolean isActivated() { return activated; }
    public void setActivated(boolean activated) { this.activated = activated; }
    public Rectangle getBounds() { return bounds; }
    public Vector2 getPosition() { return position; }
}
