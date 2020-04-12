package com.dooble.game.front.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Partida {
    List<Jugador> jugadores=null;
    int id;
    int nivel;
    String modo;
    String estado;
    Tarjeta cartaEnJuego=null;
    long timestamptEstado=0;
    public void addJugador (Jugador jugador)
    {
        if (jugadores ==null)
            jugadores=new ArrayList<Jugador>();
        jugadores.add(jugador);

    }

}
