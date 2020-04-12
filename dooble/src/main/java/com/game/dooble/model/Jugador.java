package com.game.dooble.model;

import lombok.Data;

@Data
public class Jugador {
    private  int id;
    String nombre;
    int puntos;
    Tarjeta tarjeta;
    Tarjeta tarjetaEnJuego;
    public Jugador()
    {

    }
    public Jugador(String nombre)
    {
        this.nombre=nombre;
    }

}
