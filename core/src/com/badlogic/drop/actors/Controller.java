package com.badlogic.drop.actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

public class Controller extends Actor {
    private boolean Pressed = false;
    private Texture image;


    public Controller(Texture image) {
        this.image = image;

        addListener(new InputListener());
        this.addListener(new InputListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Pressed = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                Pressed = false;
            }
        });


    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(image, this.getX(), this.getY(), this.getWidth(), this.getHeight());


    }

    public boolean isPressed() {
        return Pressed;
    }
}

