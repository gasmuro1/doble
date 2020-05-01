package com.dooble.game.front;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.net.HttpParametersUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonWriter;
import com.dooble.game.front.model.Jugador;
import com.dooble.game.front.model.Objeto;
import com.dooble.game.front.model.Partida;
import com.dooble.game.front.model.Tarjeta;

import java.util.HashMap;
import java.util.Map;

public class dooble extends Game {
	/*SpriteBatch batch;
	Map<String, Sprite> sprites;
	TextureAtlas textureAtlas;*/
	Jugador jugador;
	Partida partida;
	String netStatus="Inactive";
	String vUrl="https://doble.herokuapp.com/";
    PartidaScreen ps=null;
    NivelScreen ns=null;
    FinalScreen fs=null;
    BitmapFont font12;
    public BitmapFont getfont12()
	{
		return font12;
	}


	public Jugador getJugador() {
		return jugador;
	}
	public Partida getPartida(){
		return partida;
	}

	public void recoverTarjeta(){
		Net.HttpRequest httpGet = new Net.HttpRequest(Net.HttpMethods.GET);
		httpGet.setUrl(vUrl+"carta/"+partida.getId());
		netStatus="pending";
		Gdx.net.sendHttpRequest (httpGet, new Net.HttpResponseListener() {
			public void handleHttpResponse(Net.HttpResponse httpResponse) {
				System.out.print("success");
				Json json1=new Json();
				String result=httpResponse.getResultAsString();
				System.out.println(result);
				Tarjeta t =json1.fromJson(Tarjeta.class,result);
				//do stuff here based on response
				//System.out.println("estado partida"+partida.getEstado());
				putCarta(t);

				netStatus="success";
			}
			@Override
			public void cancelled() {
				System.out.println("CANCELADO");
				netStatus="failed";
			}

			public void failed(Throwable t) {
				t.printStackTrace();
				System.out.println("fallo");
				netStatus= "failed";
				//do stuff here based on the failed attempt
			}
		});
	}

	public void recoverPartida(){
		Net.HttpRequest httpGet = new Net.HttpRequest(Net.HttpMethods.GET);
		httpGet.setUrl(vUrl+"partida/"+partida.getId());
		netStatus="pending";
		Gdx.net.sendHttpRequest (httpGet, new Net.HttpResponseListener() {
			public void handleHttpResponse(Net.HttpResponse httpResponse) {
				System.out.print("success");
				Json json1=new Json();
				String result=httpResponse.getResultAsString();
				System.out.println(result);
				partida =json1.fromJson(Partida.class,result);
				//do stuff here based on response
				System.out.println("estado partida"+partida.getId()+" "+partida.getEstado());
				netStatus="success";
			}
			@Override
			public void cancelled() {
				System.out.println("CANCELADO");
				netStatus="failed";
			}

			public void failed(Throwable t) {
				t.printStackTrace();
				System.out.println("fallo");
				netStatus= "failed";
				//do stuff here based on the failed attempt
			}
		});
	}

	public void putCarta(Tarjeta tarjeta)
	{
		jugador.setTarjetaEnJuego(tarjeta);
		Net.HttpRequest httpPost = new Net.HttpRequest(Net.HttpMethods.PUT);
		httpPost.setUrl(String.format("%scarta/%d/%d",vUrl,partida.getId(),jugador.getId()));
		httpPost.setHeader("Content-Type","application/json");
		Json json=new Json();
		json.setOutputType(JsonWriter.OutputType.json);
		httpPost.setContent(json.toJson(tarjeta));
		System.out.println("tarjeta: "+json.toJson(tarjeta));

		netStatus="pending";
		Gdx.net.sendHttpRequest (httpPost, new Net.HttpResponseListener() {
			public void handleHttpResponse(Net.HttpResponse httpResponse) {
				System.out.print("success");
				Json json1=new Json();
				String result=httpResponse.getResultAsString();
				System.out.println(result);
				partida =json1.fromJson(Partida.class,result);
				for (Jugador j:partida.getJugadores())
					if (jugador.getId()==j.getId())
						jugador=j;
					//do stuff here based on response
				System.out.println("estado partida"+partida.getEstado()+" "+partida.getId());
				System.out.println("Jugador "+json1.toJson(jugador));
				netStatus="success";
			}

			@Override
			public void cancelled() {
				System.out.println("CANCELADO");
				netStatus="failed";
			}

			public void failed(Throwable t) {
				t.printStackTrace();
				System.out.println("fallo");
				netStatus= "failed";
				//do stuff here based on the failed attempt
			}
		});

	}
	public void putObjeto(String Id)
	{
		Objeto objeto=null;
		System.out.println(Id);
		for (Objeto o:partida.getCartaEnJuego().getObjetos())
		{
			System.out.println("jugoado tarjeta objeto"+o.getId());

			if (o.getId().equals(Id))
				objeto=o;
		}

		Net.HttpRequest httpPost = new Net.HttpRequest(Net.HttpMethods.PUT);
		httpPost.setUrl(String.format
				("%sobjeto/%d/%d",vUrl,partida.getId(),jugador.getId()));
		httpPost.setHeader("Content-Type","application/json");
		Json json=new Json();
		json.setOutputType(JsonWriter.OutputType.json);
		httpPost.setContent(json.toJson(objeto));
		System.out.println("objeto: "+json.toJson(objeto));

		netStatus="pending";
		Gdx.net.sendHttpRequest (httpPost, new Net.HttpResponseListener() {
			public void handleHttpResponse(Net.HttpResponse httpResponse) {
				System.out.print("success");
				Json json1=new Json();
				String result=httpResponse.getResultAsString();
				System.out.println(result);
				partida =json1.fromJson(Partida.class,result);
				for (Jugador j:partida.getJugadores())
					if (jugador.getId()==j.getId())
						jugador=j;
				//do stuff here based on response
				System.out.println("estado partidas"+partida.getEstado());
				System.out.println("Jugador "+json1.toJson(jugador));
				netStatus="success";
			}

			@Override
			public void cancelled() {
				System.out.println("CANCELADO");
				netStatus="failed";
			}

			public void failed(Throwable t) {
				t.printStackTrace();
				System.out.println("fallo");
				netStatus= "failed";
				//do stuff here based on the failed attempt
			}
		});

	}



	public void crearPartida(String nivel,String modo,String njugadores)
	{
		partida=new Partida();

		partida.addJugador(jugador);
		partida.setNivel(Integer.parseInt(nivel));
		partida.setModo(modo);
		partida.setEstado("");
		Net.HttpRequest httpPost = new Net.HttpRequest(Net.HttpMethods.POST);
		httpPost.setUrl(vUrl+"partida/");
		httpPost.setHeader("Content-Type","application/json");
		Json json=new Json();
		json.setOutputType(JsonWriter.OutputType.json);
		httpPost.setContent(json.toJson(partida));
		System.out.println(json.toJson(partida));
		netStatus="pending";
		Gdx.net.sendHttpRequest (httpPost, new Net.HttpResponseListener() {
			public void handleHttpResponse(Net.HttpResponse httpResponse) {
				System.out.print("success");
				Json json1=new Json();
				String result=httpResponse.getResultAsString();
				System.out.println(result);
				partida =json1.fromJson(Partida.class,result);
				//do stuff here based on response
				System.out.println("estado partida"+partida.getId()+" "+partida.getEstado());
				netStatus="success";
				//recoverTarjeta();
			}

			@Override
			public void cancelled() {
				System.out.println("CANCELADO");
				netStatus="failed";
			}

			public void failed(Throwable t) {
				t.printStackTrace();
				System.out.println("fallo");
				netStatus= "failed";
				//do stuff here based on the failed attempt
			}
		});

	}

	public void crearJugador(String nombre){
		jugador = new Jugador();
		jugador.setNombre(nombre);
		Net.HttpRequest httpPost = new Net.HttpRequest(Net.HttpMethods.POST);
		httpPost.setUrl(vUrl+"jugador/");
		httpPost.setHeader("Content-Type","application/json");
		Json json=new Json();
		json.setOutputType(JsonWriter.OutputType.json);
		httpPost.setContent(json.toJson(jugador));
		System.out.println(json.toJson(jugador));
		netStatus="pending";
		Gdx.net.sendHttpRequest (httpPost, new Net.HttpResponseListener() {
			public void handleHttpResponse(Net.HttpResponse httpResponse) {
				System.out.print("success");
				Json json1=new Json();
				String result=httpResponse.getResultAsString();
				//System.out.println(result);
				jugador =json1.fromJson(Jugador.class,result);
				//do stuff here based on response
				System.out.println("Id"+jugador.getId());
				netStatus="success";
			}

			@Override
			public void cancelled() {
				System.out.println("CANCELADO");
				netStatus="failed";
			}

			public void failed(Throwable t) {
				t.printStackTrace();
				System.out.println("fallo");
				netStatus= "failed";
				//do stuff here based on the failed attempt
			}
		});

	}

	public void borrarJugador(){
		Net.HttpRequest httpPost = new Net.HttpRequest(Net.HttpMethods.DELETE);
		httpPost.setUrl(vUrl+String.format("%s/%s",partida.getId(),jugador.getId()));
		httpPost.setHeader("Content-Type","application/json");
		Json json=new Json();
		netStatus="pending";
		Gdx.net.sendHttpRequest (httpPost, new Net.HttpResponseListener() {
			public void handleHttpResponse(Net.HttpResponse httpResponse) {
				System.out.print("success");
				netStatus="success";
			}

			@Override
			public void cancelled() {
				System.out.println("CANCELADO");
				netStatus="failed";
			}

			public void failed(Throwable t) {
				t.printStackTrace();
				System.out.println("fallo");
				netStatus= "failed";
				//do stuff here based on the failed attempt
			}
		});

	}



	@Override
	public void create () {
		setScreen(new BienvenidaScreen(this));
		ps = new PartidaScreen(this);
        ns = new NivelScreen(this);
        fs=new FinalScreen(this);

	}


}
