package com.rubenbellido.animacionpersonalizada;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Main implements ApplicationListener {

    SpriteBatch spriteBatch;
    Player player;

    @Override
    public void create() {
        spriteBatch = new SpriteBatch();
        player = new Player();
    }

    @Override
    public void render() {
        float deltaTime = Gdx.graphics.getDeltaTime();

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        player.update(deltaTime);

        spriteBatch.begin();
        player.render(spriteBatch);
        spriteBatch.end();
    }

    @Override public void resize(int width, int height) {}
    @Override public void pause() {}
    @Override public void resume() {}

    @Override
    public void dispose() {
        spriteBatch.dispose();
        player.dispose();
    }
}
