package com.dooble.game.front.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.dooble.game.front.dooble;

public class Carta extends Actor {
    Texture texture;
    public Carta (Texture texture)
    {
        this.texture=texture;


    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw (texture,this.getX(),this.getY(), Gdx.graphics.getWidth()/2,Gdx.graphics.getHeight()/10*7);


    }
}
