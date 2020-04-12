package com.game.dooble.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
@Data
public class Partida {
    List<Jugador> jugadores=null;

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    Baraja baraja;
    int id;
    int nivel;
    String modo;
    String estado;
    Tarjeta cartaEnJuego=null;
    long timestamptEstado=0;

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    long timestamptGetEstado=0;

    public Partida()
    {

    }
    public Partida(int nivel)
    {
       this.nivel=nivel;
    }

    public void addJugador (Jugador jugador)
    {
        if (jugadores ==null)
            jugadores=new ArrayList<Jugador>();
        jugadores.add(jugador);

    }
    public void crearBaraja()
    {
        this.estado="Creando";
        baraja=new Baraja(nivel);
        setEstado("Listo");
        timestamptEstado=System.currentTimeMillis();

    }

    public Tarjeta recoverFirstTarjeta()
    {
        Tarjeta tarjeta= (Tarjeta) baraja.getCartas().get(0);
        baraja.getCartas().remove(0);
        return tarjeta;
    }
    public Tarjeta recoverTarjeta()
    {
        if (estado=="Listo") {
            int quedan = baraja.getCartas().size();
            int idx = 0;
            if (quedan > 0) {
                setEstado("Sincronizando");
                cartaEnJuego = (Tarjeta) baraja.getCartas().get(idx);
                baraja.getCartas().remove(idx);
            } else {
                setEstado ( "Finalizado");

            }
        }
        return cartaEnJuego;
    }

    public String getEstado()
    {
        timestamptGetEstado=System.currentTimeMillis();
        return estado;
    }
    public void changeEstado()
    {
        if (estado.equals("Sincronizando")) {
            Boolean todos=true;
            for (Jugador j : jugadores) {
                if (j.getTarjetaEnJuego()==null || !j.getTarjetaEnJuego().equals(cartaEnJuego))
                   todos=false;
            }
            if (todos)
            {
                setEstado("Jugando");
            }
        }
    }
    public Jugador getJugador(int id)
    {
        for (Jugador j: jugadores)
        {
            if (id== j.getId())
                return j;
        }
        return null;
    }

    public boolean checkObjeto(Objeto objeto)
    {
        for (Objeto o:cartaEnJuego.getObjetos()) {
            if (o.equals(objeto))
                return true;
        }
        return false;
    }
    public void setEstado(String estado)
    {
        this.estado=estado;
        timestamptEstado=System.currentTimeMillis();
    }

    public long recoverTimestampGetEstado()
    {
        return timestamptGetEstado;
    }

    public void deleteJugador(int id)
    {
        for (Jugador j: jugadores)
        {
            if (id== j.getId())
                jugadores.remove(j);
        }

    }


}
