package com.duartefijamo.Fgti_war.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Entidade de Inimigo Patrulheiro (Bug do Sistema, Guarda ou Reitor).
 */
public class Enemy {

    private final Vector2 position;
    private final Vector2 velocity;
    private final Rectangle bounds;
    private final TextureRegion texture;
    private float patrolDistance = 100f;
    private float startX;
    private boolean movingRight = true;

    public Enemy(float x, float y, TextureRegion texture) {
        this.position = new Vector2(x, y);
        this.startX = x;
        this.velocity = new Vector2(60f, 0f);
        this.texture = texture;
        this.bounds = new Rectangle(x, y, 28, 28);
    }

    public void update(float delta) {
        if (movingRight) {
            position.x += velocity.x * delta;
            if (position.x >= startX + patrolDistance) {
                movingRight = false;
            }
        } else {
            position.x -= velocity.x * delta;
            if (position.x <= startX - patrolDistance) {
                movingRight = true;
            }
        }

        bounds.x = position.x;
        bounds.y = position.y;
    }

    public void draw(SpriteBatch batch) {
        if (texture != null) {
            batch.draw(texture, bounds.x, bounds.y, bounds.width, bounds.height);
        }
    }

    public Rectangle getBounds() { return bounds; }
    public Vector2 getPosition() { return position; }
}
