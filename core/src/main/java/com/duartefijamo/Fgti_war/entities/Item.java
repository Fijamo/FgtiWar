package com.duartefijamo.Fgti_war.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Entidade de item coletável (Café/Cura, PenDrive/Chave, ECTS/Moeda).
 */
public class Item {

    public enum Type { COFFEE, KEY, ECTS }

    private final Type type;
    private final Vector2 position;
    private final Rectangle bounds;
    private final TextureRegion texture;
    private boolean collected = false;
    private float floatTimer = 0f;

    public Item(Type type, float x, float y, TextureRegion texture) {
        this.type = type;
        this.position = new Vector2(x, y);
        this.texture = texture;
        this.bounds = new Rectangle(x, y, 24, 24);
    }

    public void update(float delta) {
        if (collected) return;

        // Efeito de flutuação suave para destacar os itens
        floatTimer += delta * 4f;
        float offsetY = (float) Math.sin(floatTimer) * 3f;
        bounds.y = position.y + offsetY;
    }

    public void draw(SpriteBatch batch) {
        if (!collected && texture != null) {
            batch.draw(texture, bounds.x, bounds.y, bounds.width, bounds.height);
        }
    }

    public Type getType() { return type; }
    public Rectangle getBounds() { return bounds; }
    public boolean isCollected() { return collected; }
    public void setCollected(boolean collected) { this.collected = collected; }
}
