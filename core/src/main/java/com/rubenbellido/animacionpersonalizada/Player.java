package com.rubenbellido.animacionpersonalizada;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Player {

    float x = 50;
    float y = 50;
    float speed = 200;

    boolean mirandoDerecha = true;

    float stateTime = 0f;

    PlayerState currentState = PlayerState.IDLE;

    PlayerAnimations animations;

    public Player() {
        animations = new PlayerAnimations();
    }

    public void update(float deltaTime) {
        stateTime += deltaTime;

        boolean moving = false;

        // Movimiento derecha
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            x += speed * deltaTime;
            mirandoDerecha = true;
            moving = true;
        }

        // Movimiento izquierda
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            x -= speed * deltaTime;
            mirandoDerecha = false;
            moving = true;
        }

        // Estado
        if (moving) {
            currentState = PlayerState.WALKING;
        } else {
            currentState = PlayerState.IDLE;
        }
    }

    public void render(SpriteBatch batch) {
        TextureRegion frame = animations.getFrame(currentState, stateTime);

        // IMPORTANTE: copiamos el frame para no modificar el original
        TextureRegion drawFrame = new TextureRegion(frame);

        if (!mirandoDerecha) {
            drawFrame.flip(true, false);
        }

        batch.draw(drawFrame, x, y);
    }

    public void dispose() {
        animations.dispose();
    }
}
