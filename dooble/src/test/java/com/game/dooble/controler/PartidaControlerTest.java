package com.game.dooble.controler;

import com.game.dooble.model.Jugador;
import com.game.dooble.model.Objeto;
import com.game.dooble.model.Partida;
import com.game.dooble.model.Tarjeta;
import com.game.dooble.service.Servicio;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PartidaControlerTest {

    PartidaControler pc;


    @Test
    public void crearUsuario()
    {
        Servicio s=new Servicio();
        pc= new PartidaControler(s);
        Jugador  j=new Jugador("prueba");
        Jugador j2=pc.crearJugador(j);
        assertEquals(0,j2.getId());
 //       assertThrows(org.springframework.web.server.ResponseStatusException.class, () ->{pc.crearJugador(j);});
        Jugador  j3=new Jugador("prueba1");
        pc.crearJugador(j3);
        assertEquals(1,j3.getId());



    }
    @Test
    public void crearPartida()
    {
        Servicio s=new Servicio();
        pc=new PartidaControler(s);
        Jugador  j=new Jugador("prueba");
        pc.crearJugador(j);
        Partida p=new Partida(3);
        p.addJugador(j);
        p.setModo("Entrenamiento");
        p=pc.crearPartida(p);
        assertEquals(0,p.getId());
        assertEquals("Listo",p.getEstado());
        Tarjeta t=pc.getTarjeta(String.valueOf(p.getId()));
        assertNotNull(t);
        pc.setTarjetaEnjuego(p.getId(),j.getId(),t);
        p=pc.getPartida(String.valueOf(p.getId()));
        assertEquals("Jugando",p.getEstado());
        for (Objeto o:j.getTarjeta().getObjetos()) {
            p = pc.setObjeto(p.getId(), j.getId(), o);
            if (p.getEstado().equals("Sincronizando"))
                break;
        }
        assertEquals("Sincronizando",p.getEstado());
        assertEquals(1,j.getPuntos());

    }

    @Test
    public void crearPartidaDoble()
    {
        Servicio s=new Servicio();
        pc=new PartidaControler(s);
        Jugador  j=new Jugador("prueba");
        pc.crearJugador(j);
        Partida p=new Partida(3);
        p.addJugador(j);
        p.setModo("Red");
        p=pc.crearPartida(p);
        assertEquals(0,p.getId());
        assertEquals("Esperando",p.getEstado());
        Jugador  j2=new Jugador("prueba2");
        pc.crearJugador(j2);
        Partida p1=new Partida(3);
        p1.addJugador(j2);
        p1.setModo("Red");
        p1=pc.crearPartida(p1);
        assertEquals(p.getId(),p1.getId());
        assertEquals("Listo",p.getEstado());


        Tarjeta t=pc.getTarjeta(String.valueOf(p.getId()));
        assertNotNull(t);
        pc.setTarjetaEnjuego(p.getId(),j.getId(),t);

        p=pc.getPartida(String.valueOf(p.getId()));
        assertEquals("Sincronizando",p.getEstado());

        Tarjeta t2=pc.getTarjeta(String.valueOf(p.getId()));
        assertNotNull(t);
        pc.setTarjetaEnjuego(p1.getId(),j2.getId(),t2);

        p=pc.getPartida(String.valueOf(p.getId()));
        assertEquals("Jugando",p.getEstado());

        for (Objeto o:j.getTarjeta().getObjetos()) {
            p = pc.setObjeto(p.getId(), j.getId(), o);
            if (p.getEstado().equals("Sincronizando"))
                break;
        }
        assertEquals("Sincronizando",p.getEstado());
        assertEquals(1,j.getPuntos());

    }

}