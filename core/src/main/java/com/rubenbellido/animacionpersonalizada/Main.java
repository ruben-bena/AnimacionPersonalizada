package com.rubenbellido.animacionpersonalizada;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Main implements ApplicationListener {

    // Constant rows and columns of the sprite sheet
    private static final int FRAME_COLS = 12, FRAME_ROWS = 1;

    // Objects used
    Animation<TextureRegion> idleAnimation;
    Animation<TextureRegion> walkAnimation;
    Animation<TextureRegion> backAnimation;
    Texture spritesheet;
    SpriteBatch spriteBatch;
    float x = 0;
    float y = 0;
    float speed = 200;
    float dirX = 0;
    float dirY = 0;

    // A variable for tracking elapsed time for the animation
    float stateTime;

    @Override
    public void create() {

        // Load the sprite sheet as a Texture
        spritesheet = new Texture(Gdx.files.internal("spritesheet_ruben_fixed.png"));

        // Use the split utility method to create a 2D array of TextureRegions. This is
        // possible because this sprite sheet contains frames of equal size and they are
        // all aligned.
        TextureRegion[][] tmp = TextureRegion.split(spritesheet,
            spritesheet.getWidth() / FRAME_COLS,
            spritesheet.getHeight() / FRAME_ROWS);

        TextureRegion[] idleFrames = new TextureRegion[3];
        TextureRegion[] walkFrames = new TextureRegion[6];
        TextureRegion[] backFrames = new TextureRegion[3];
        int index = 0;
        for (int i = 0; i < FRAME_ROWS; i++) {
            for (int j = 0; j < FRAME_COLS; j++) {
                if (j<=2) {
                    idleFrames[index] = tmp[i][j];
                }
                else if (j<=8) {
                    walkFrames[index-3] = tmp[i][j];
                }
                else {
                    backFrames[index-9] = tmp[i][j];
                }
                index++;
            }
        }

        // Initialize the Animation with the frame interval and array of frames
        idleAnimation = new Animation<TextureRegion>(0.1f, idleFrames);
        walkAnimation = new Animation<TextureRegion>(0.1f, walkFrames);
        backAnimation = new Animation<TextureRegion>(0.1f, backFrames);

        // Instantiate a SpriteBatch for drawing and reset the elapsed animation
        // time to 0
        spriteBatch = new SpriteBatch();
        stateTime = 0f;
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void render() {
        input();
        logic();
        draw();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() { // SpriteBatches and Textures must always be disposed
        spriteBatch.dispose();
        spritesheet.dispose();
    }

    public void input() {
        dirX = 0;
        dirY = 0;

        if (Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.LEFT)) {
            dirX = -1;
        }
        if (Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.RIGHT)) {
            dirX = 1;
        }
        if (Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.UP)) {
            dirY = 1;
        }
        if (Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.DOWN)) {
            dirY = -1;
        }
    }

    public void logic() {
        // Obtenemos el tiempo que ha pasado desde el último frame
        float deltaTime = Gdx.graphics.getDeltaTime();

        // Actualizamos la posición: Posición + (Dirección * Velocidad * Tiempo)
        x += dirX * speed * deltaTime;
        y += dirY * speed * deltaTime;
    }

    public void draw() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // Clear screen
        stateTime += Gdx.graphics.getDeltaTime(); // Accumulate elapsed animation time

        // Get current frame of animation for the current stateTime
        TextureRegion currentFrame = backAnimation.getKeyFrame(stateTime, true);
        spriteBatch.begin();
        spriteBatch.draw(currentFrame, x, y);
        spriteBatch.end();
    }
}
