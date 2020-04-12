package com.dooble.game.front.model;

import lombok.Data;

@Data
public class Jugador {
    private  int id;
    String nombre;
    int puntos;
    Tarjeta tarjeta;
    Tarjeta tarjetaEnJuego;
}
