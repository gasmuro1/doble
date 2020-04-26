package com.dooble.game.front.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.dooble.game.front.dooble;
import com.dooble.game.front.model.Objeto;

public class ActorObjeto extends Actor {
    Sprite objetoSprite;
    dooble game;
    public ActorObjeto(dooble game,String id, Sprite objetoSprite)
    {
        this.game=game;
        this.objetoSprite=objetoSprite;
        this.setName(id);
        System.out.println("constructor"+id);
        setTouchable(Touchable.enabled);
        addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                tocado();
            }
        });
      /*  addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y,
                                int pointer, int button) {

                System.out.println("hola");
                return true;
                //do your stuff
                //it will work when finger is released..

            }});*/


    }

    public void setObjetoSprite( Sprite objetoSprite)
    {
        this.objetoSprite=objetoSprite;
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }

    public void tocado(){

        System.out.println( "Tocado"+getName());
        game.putObjeto(getName());

    }
    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(objetoSprite,getX(),getY(), Gdx.graphics.getWidth()/2/3,Gdx.graphics.getHeight()/10*7/3);
    }
}
