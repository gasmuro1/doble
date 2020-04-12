package com.dooble.game.front;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;

public class NivelScreen extends BaseScreen {
    private Stage stage;
    private Skin skin;
    private SelectBox nivelSelectBox;
    private SelectBox modoSelectBox;
    private SelectBox njugadoresBox;
    private Label estadoLabel;
    private float retryEstado=0;
    public NivelScreen(dooble game) {
        super(game);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.4f, 0.5f, 0.8f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        stage.draw();

        if (game.getPartida() != null && game.getPartida().getEstado() != null && game.getPartida().getEstado().equals("Jugando")) {
                    game.setScreen(game.ps);
        }
        else
        {
            if (game.getPartida()!=null)
            {
                if (game.getPartida().getEstado().equals("Listo")||game.getPartida().getEstado().equals("Sincronizando")) {
                    game.recoverTarjeta();
                }
                else {
                    estadoLabel.setText(game.getPartida().getEstado());
                    if (retryEstado>2)
                    {
                        retryEstado=0;
                        game.recoverPartida();
                    }
                    retryEstado+=delta;
                }

            }

        }
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
        stage=new Stage();
        Gdx.input.setInputProcessor(stage);
        skin= new Skin(Gdx.files.internal("uiskin.json"));
        Label linea1=new Label("Elige",skin);
        linea1.setSize(Gdx.graphics.getWidth(),40);
        linea1.setFontScale(2.5f);
        linea1.setPosition(0,Gdx.graphics.getHeight()-100);
        linea1.setAlignment(Align.center);
        stage.addActor(linea1);

        Label linea2 =new Label("Nivel:",skin);
        linea2.setSize(Gdx.graphics.getWidth()/2,40);
        linea2.setFontScale(1.5f);
        linea2.setPosition(0,Gdx.graphics.getHeight()-150);
        linea2.setAlignment(Align.right);
        stage.addActor(linea2);

        estadoLabel =new Label("ESTADO",skin);
        estadoLabel.setSize(Gdx.graphics.getWidth(),40);
        estadoLabel.setFontScale(1.5f);
        estadoLabel.setPosition(0,10);
        estadoLabel.setAlignment(Align.center);
        stage.addActor(estadoLabel);


        nivelSelectBox = new SelectBox<String>(skin);
        nivelSelectBox.setPosition(Gdx.graphics.getWidth()/2+10,Gdx.graphics.getHeight()-150);
        nivelSelectBox.setSize(Gdx.graphics.getWidth()/2-20, 40);
        Array<String> niveles=new Array<String>();
        niveles.add("2","3","7","9");
        nivelSelectBox.setItems(niveles);
        stage.addActor(nivelSelectBox);            // <-- Actor now on stage

        Label linea3 =new Label("Modo:",skin);
        linea3.setSize(Gdx.graphics.getWidth()/2,40);
        linea3.setFontScale(1.5f);
        linea3.setPosition(0,Gdx.graphics.getHeight()-200);
        linea3.setAlignment(Align.right);
        stage.addActor(linea3);

        modoSelectBox = new SelectBox<String>(skin);
        modoSelectBox.setPosition(Gdx.graphics.getWidth()/2+10,Gdx.graphics.getHeight()-200);
        modoSelectBox.setSize(Gdx.graphics.getWidth()/2-20, 40);
        Array<String> modos=new Array<String>();
        modos.add("Entrenamiento","Red");
        modoSelectBox.setItems(modos);
        stage.addActor(modoSelectBox);            // <-- Actor now on stage


        TextButton btnLogin =new TextButton("Continuar",skin);
        btnLogin.setPosition(Gdx.graphics.getWidth()/2-150,200);
        btnLogin.setSize(300,60);
        btnLogin.addListener(new ClickListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if (game.partida==null)
                   game.crearPartida((String)nivelSelectBox.getSelected(),(String) modoSelectBox.getSelected(),"2");
            }
        });
        stage.addActor(btnLogin);



    }
}
