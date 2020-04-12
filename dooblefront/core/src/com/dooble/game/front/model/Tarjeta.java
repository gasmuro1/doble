package com.dooble.game.front.model;


import lombok.Data;

import java.util.List;

@Data
public class Tarjeta {

    List<Objeto> objetos;
    int orden;
    int x=0;
    int y=0;
    int z=0;

    int celdax;
    int celday;
    public Tarjeta(){

    }
}
