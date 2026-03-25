package com.rubenbellido.animacionpersonalizada;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class PlayerAnimations {

    private static final int FRAME_COLS = 12;
    private static final int FRAME_ROWS = 1;

    Texture sheet;

    Animation<TextureRegion> idleAnimation;
    Animation<TextureRegion> walkAnimation;

    public PlayerAnimations() {

        sheet = new Texture(Gdx.files.internal("spritesheet_ruben.png"));

        TextureRegion[][] tmp = TextureRegion.split(
            sheet,
            sheet.getWidth() / FRAME_COLS,
            sheet.getHeight() / FRAME_ROWS
        );

        TextureRegion[] allFrames = tmp[0];

        TextureRegion[] idleFrames = new TextureRegion[2];
        TextureRegion[] walkFrames = new TextureRegion[7];

        for (int i = 0; i < 2; i++) {
            idleFrames[i] = allFrames[i];
        }

        for (int i = 3; i < 9; i++) {
            walkFrames[i] = allFrames[i];
        }

        idleAnimation = new Animation<>(0.4f, idleFrames);
        walkAnimation = new Animation<>(0.1f, walkFrames);
    }

    public TextureRegion getFrame(PlayerState state, float stateTime) {
        switch (state) {
            case WALKING:
                return walkAnimation.getKeyFrame(stateTime, true);
            case IDLE:
            default:
                return idleAnimation.getKeyFrame(stateTime, true);
        }
    }

    public void dispose() {
        sheet.dispose();
    }
}
