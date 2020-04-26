package com.dooble.game.front;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.dooble.game.front.actors.ActorObjeto;
import com.dooble.game.front.actors.Carta;
import com.dooble.game.front.model.Jugador;
import com.dooble.game.front.model.Objeto;


import java.util.ArrayList;
import java.util.List;

public class PartidaScreen extends BaseScreen {
    private List<ActorObjeto> tarjetaJugador;
    private List<ActorObjeto> tarjetaBaraja;
    long timestamp=0;
    TextureAtlas textureAtlas;
    Texture textureCarta;
    Carta [] cartas;
    boolean recargarTarjetas=false;
    private List <Label> scores;
    private Label texto;
    BitmapFont myFont;
    int lineHeight=Gdx.graphics.getHeight()/10;
    public PartidaScreen(dooble game) {
        super(game);
        textureAtlas=new TextureAtlas("things1.txt");

    }
    private Stage stage;
    private void reloadTarjetas() {
        int i=0;
        for (Objeto objeto : game.getJugador().getTarjeta().getObjetos()) {
            tarjetaJugador.get(i).setName (objeto.getId()); textureAtlas.createSprite(objeto.getAsset());
            tarjetaJugador.get(i).setObjetoSprite(textureAtlas.createSprite(objeto.getAsset()));
            i++;
        }
        i=0;
        for (Objeto objeto : game.getJugador().getTarjetaEnJuego().getObjetos()) {
            System.out.println("tarjeta en juego");
            tarjetaBaraja.get(i).setName( objeto.getId());
            tarjetaBaraja.get(i).setObjetoSprite(textureAtlas.createSprite(objeto.getAsset()));
            i++;
        }
    }

    private void loadObjetos(){
        tarjetaJugador=new ArrayList<ActorObjeto>();
        for (Objeto objeto:game.getJugador().getTarjeta().getObjetos())
        {
            tarjetaJugador.add(new ActorObjeto(game,objeto.getId(), textureAtlas.createSprite(objeto.getAsset())));
        }
        tarjetaBaraja=new ArrayList<ActorObjeto>();
        for (Objeto objeto:game.getJugador().getTarjetaEnJuego().getObjetos())
        {   System.out.println("tarjeta en juego");
            tarjetaBaraja.add(new ActorObjeto(game,objeto.getId(), textureAtlas.createSprite(objeto.getAsset())));
        }
        textureCarta=new Texture(Gdx.files.internal("carta.png"));
        cartas=new Carta[2];
        cartas[0]=new Carta(textureCarta);
        cartas[1]=new Carta(textureCarta);


    }

    public void loadScore()
    {
        Label.LabelStyle label1Style = new Label.LabelStyle();
        myFont = new BitmapFont();
        label1Style.font = myFont;
        label1Style.fontColor = Color.RED;

        int x=0;
        int y=1;
        scores= new ArrayList<Label>();
        for (Jugador j:game.getPartida().getJugadores())
        {  Label label1=new Label(String.format("%s:%d",j.getNombre(),j.getPuntos()),label1Style);
          label1.setSize(Gdx.graphics.getWidth()/2,lineHeight);
           label1.setFontScale(3);
          label1.setPosition(x+10,Gdx.graphics.getHeight()-y*lineHeight);
          if (x==0)
          {
              x=Gdx.graphics.getWidth()/2;
          }
          else
          {
              x=0;
              y++;
          }
          label1.setAlignment(Align.left);
          stage.addActor(label1);
          scores.add(label1);
        }
    }

    @Override
    public void show() {

        stage=new Stage();
        loadObjetos();
        loadScore();
        Label.LabelStyle label1Style = new Label.LabelStyle();
        label1Style.font = myFont;
        label1Style.fontColor = Color.BLACK;
        texto=new Label("Seleciona aqui el objeto repetido",label1Style);
        texto.setSize(Gdx.graphics.getWidth()/2,lineHeight);
        texto.setFontScale(3);
        texto.setPosition(Gdx.graphics.getWidth()/2,Gdx.graphics.getHeight()-lineHeight*2);
        texto.setAlignment(Align.center);

        stage.addActor(texto);

        Gdx.input.setInputProcessor(stage);
        stage.addActor(cartas[0]);
        stage.addActor(cartas[1]);
        int x=0;
        int y=0;
       for (int i=0;i<tarjetaJugador.size();i++)
        {
            stage.addActor(tarjetaJugador.get(i));
            tarjetaJugador.get(i).setPosition(x,y);
            x=x+Gdx.graphics.getWidth()/2/3;
            if (x+50>=Gdx.graphics.getWidth()/2)
            {
                y=y+Gdx.graphics.getWidth()/2/3;
                x=0;
            }
        }
        x=Gdx.graphics.getWidth()/2;
        y=0;
        for (int i=0;i<tarjetaBaraja.size();i++)
        {
            stage.addActor(tarjetaBaraja.get(i));
            tarjetaBaraja.get(i).setPosition(x,y);
            tarjetaBaraja.get(i).setBounds(x,y,Gdx.graphics.getWidth()/2/3,Gdx.graphics.getWidth()/2/3);
            x=x+Gdx.graphics.getWidth()/2/3;
            if (x+50>=Gdx.graphics.getWidth())
            {
                y=y+Gdx.graphics.getWidth()/2/3;
                x=Gdx.graphics.getWidth()/2;
            }
        }

        cartas[0].setPosition(0,0);
        cartas[1].setPosition(Gdx.graphics.getWidth()/2,0);

    }


    @Override
    public void hide() {

       stage.dispose();
    }


    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.4f, 0.5f, 0.8f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        if (game.getPartida().getEstado().equals("Jugando"))
        {       if (recargarTarjetas) {
                    reloadTarjetas();
                    recargarTarjetas=false;
                }
                if (System.currentTimeMillis()-timestamp>1000) {
                    timestamp=System.currentTimeMillis();
                    game.recoverPartida();
                }
                stage.act(); // actuacin del cliente
                if (scores!=null) {
                    int o=0;
                    for (Jugador j:game.getPartida().getJugadores())
                        scores.get(o++).setText(String.

                                format("%s:%d", j.getNombre(), j.getPuntos()));
                }
                stage.draw();
        }
        else if (game.getPartida().getEstado().equals("Finalizado")){
            game.setScreen(game.fs);

        }
        else{
/*            if(stage!=null) {
                stage.dispose();
                stage = null;
            }*/

            if (game.getPartida().getEstado().equals("Sincronizando") && !game.netStatus.equals("pending")) {
                recargarTarjetas = true;
                game.recoverTarjeta();
            }
        }
    }

    @Override
    public void dispose() {
        super.dispose();
        textureAtlas.dispose();
        textureCarta.dispose();
        myFont.dispose();

    }
}
