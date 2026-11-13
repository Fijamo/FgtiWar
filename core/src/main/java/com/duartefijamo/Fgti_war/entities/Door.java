package com.duartefijamo.Fgti_war.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Porta de saída da fase. Requer que a chave (PenDrive) tenha sido coletada para abrir.
 */
public class Door {

    private final Vector2 position;
    private final Rectangle bounds;
    private final TextureRegion textureClosed;
    private final TextureRegion textureOpen;
    private boolean locked = true;

    public Door(float x, float y, TextureRegion closed, TextureRegion open) {
        this.position = new Vector2(x, y);
        this.bounds = new Rectangle(x, y, 32, 64);
        this.textureClosed = closed;
        this.textureOpen = open;
    }

    public void draw(SpriteBatch batch) {
        TextureRegion current = locked ? textureClosed : textureOpen;
        if (current != null) {
            batch.draw(current, bounds.x, bounds.y, bounds.width, bounds.height);
        }
    }

    public boolean isLocked() { return locked; }
    public void setLocked(boolean locked) { this.locked = locked; }
    public Rectangle getBounds() { return bounds; }
    public Vector2 getPosition() { return position; }
}
