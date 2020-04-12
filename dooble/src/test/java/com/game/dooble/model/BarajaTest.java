package com.game.dooble.model;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BarajaTest {

    @Test
    void CreacionBaraja()
    {
       int nivel=2;
        Baraja b=new Baraja(nivel);
        List cartas=b.getCartas();
        assertEquals(Math.pow(nivel,2)+nivel+1,cartas.size());
        assertEquals(nivel+1,((Tarjeta)cartas.get(0)).getObjetos().size());

    }

}