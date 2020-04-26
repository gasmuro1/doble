package com.dooble.game.front;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.loaders.AssetLoader;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.dooble.game.front.model.Jugador;

import java.util.ArrayList;

public class BienvenidaScreen extends BaseScreen {

    private Stage stage;
    private Skin skin;
    private TextField usernameTextField;
    public BienvenidaScreen(dooble game) {
        super(game);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.4f, 0.5f, 0.8f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        stage.draw();
    }

    @Override
    public void hide() {
        stage.dispose();
    }

    @Override
    public void dispose() {
        skin.dispose();
    }

    @Override
    public void show() {
        int lineHeight=Gdx.graphics.getHeight()/10;
        stage=new Stage();
        Gdx.input.setInputProcessor(stage);
        skin= new Skin(Gdx.files.internal("uiskin.json"));
        Label linea1=new Label("Hola bienvenido al DOOBLE",skin);
        linea1.setSize(Gdx.graphics.getWidth(),lineHeight);
        linea1.setFontScale(5f);
        linea1.setPosition(0,Gdx.graphics.getHeight()-2*lineHeight);
        linea1.setAlignment(Align.center);
        stage.addActor(linea1);

        Label linea2 =new Label("Como te quieres llamar:",skin);
        linea2.setSize(Gdx.graphics.getWidth()/2,lineHeight);
        linea2.setFontScale(3f);
        linea2.setPosition(0,Gdx.graphics.getHeight()-4*lineHeight);
        linea2.setAlignment(Align.right);
        stage.addActor(linea2);

        skin.get(TextField.TextFieldStyle.class).font.getData().setScale(3f);
        usernameTextField = new TextField("",skin);
        usernameTextField.setPosition(Gdx.graphics.getWidth()/2+10,Gdx.graphics.getHeight()-4*lineHeight);
        usernameTextField.setSize(Gdx.graphics.getWidth()/2-20, lineHeight);


        stage.addActor(usernameTextField);            // <-- Actor now on stage

        TextButton btnLogin =new TextButton("Continuar",skin);
        btnLogin.setPosition(Gdx.graphics.getWidth()/2-150,200);
        btnLogin.setSize(600,lineHeight);
        btnLogin.getLabel().setFontScale(5f);
        btnLogin.addListener(new ClickListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                game.crearJugador(usernameTextField.getText());
                try {
                    while (1 == 1) {
                        Thread.sleep(500);
                        if (game.netStatus.equals("success")) {
                            game.setScreen(game.ns);
                            break;
                        }
                    }

                } catch (Exception e) {

                }
            }
        });
        stage.addActor(btnLogin);



    }



}
