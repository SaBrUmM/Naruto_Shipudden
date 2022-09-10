package com.badlogic.drop.actors;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

public class LevelListener extends InputListener {
    private Level level;

    public LevelListener(Level level) {
        this.level = level;
    }

    @Override
    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
        return true;
    }
    @Override
    public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
    }
    @Override
    public void touchDragged(InputEvent event, float x, float y, int pointer) {
    }
}
