package com.duartefijamo.Fgti_war.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.duartefijamo.Fgti_war.utils.Constants;

public class Player {
    private Vector2 position;
    private Vector2 velocity;
    private float width = 24;
    private float height = 48;
    private boolean isGrounded = false;

    // Movement flags for mobile controls
    public boolean movingLeft = false;
    public boolean movingRight = false;

    public Player(float x, float y) {
        position = new Vector2(x, y);
        velocity = new Vector2(0, 0);
    }

    public void update(float dt) {
        handleInput();

        velocity.y += Constants.GRAVITY * dt;
        position.add(velocity.x * dt, velocity.y * dt);

        // Simple ground collision
        if (position.y <= 50) {
            position.y = 50;
            velocity.y = 0;
            isGrounded = true;
        } else {
            isGrounded = false;
        }

        // Screen boundaries
        if (position.x < 0) position.x = 0;
        if (position.x > Constants.V_WIDTH - width) position.x = Constants.V_WIDTH - width;
    }

    private void handleInput() {
        velocity.x = 0;

        // Keyboard + Mobile flags
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || movingLeft) {
            velocity.x = -Constants.PLAYER_SPEED;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || movingRight) {
            velocity.x = Constants.PLAYER_SPEED;
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) && isGrounded) {
            jump();
        }
    }

    public void jump() {
        if (isGrounded) {
            velocity.y = Constants.JUMP_FORCE;
        }
    }

    public void draw(ShapeRenderer shapeRenderer) {
        float x = position.x;
        float y = position.y;

        shapeRenderer.setColor(Color.BROWN); // Skin/Head color
        // Head
        shapeRenderer.rect(x + 4, y + 36, 16, 12);

        shapeRenderer.setColor(Color.BLUE); // Shirt/Torso
        // Torso
        shapeRenderer.rect(x + 4, y + 16, 16, 20);

        shapeRenderer.setColor(Color.BLACK); // Pants/Legs
        // Left Leg
        shapeRenderer.rect(x + 4, y, 6, 16);
        // Right Leg
        shapeRenderer.rect(x + 14, y, 6, 16);

        shapeRenderer.setColor(Color.BROWN);
        // Left Arm
        shapeRenderer.rect(x - 2, y + 20, 6, 12);
        // Right Arm
        shapeRenderer.rect(x + 20, y + 20, 6, 12);
    }
}
