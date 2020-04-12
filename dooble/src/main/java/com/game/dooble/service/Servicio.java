package com.game.dooble.service;

import com.game.dooble.model.Jugador;
import com.game.dooble.model.Objeto;
import com.game.dooble.model.Partida;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@Component
@Data
public class Servicio  {
    private int secuenciaJugador=0;
    private int secuenciaPartida=0;
    private HashMap<Integer,Jugador> jugadores;
    private HashMap <Integer,Partida> partidas;
    static final  int TIMEOUT_ENTRENAMIENTO=5000;
    public Servicio()
    {
        jugadores=new <Integer,Jugador> HashMap();
        partidas=new <Integer,Partida> HashMap();

    }
    public Jugador addJugador(Jugador jugador) throws IllegalArgumentException
    {
       /* for(Map.Entry <Integer, Jugador> j: jugadores.entrySet()) {
            if (j.getValue().getNombre().equals(jugador.getNombre()))
                throw new IllegalArgumentException("Jugador Duplicado");

        }*/
        jugador.setId(secuenciaJugador);
        jugadores.put(secuenciaJugador++,jugador);
        return (jugador);

    }
    public Partida crearPartida( Partida partida) {
        Partida ret=null;
        if (partida.getModo().equals("Entrenamiento"))
        {
            partida.setId(secuenciaPartida);
            partidas.put(secuenciaPartida++,partida);
            ret=partida;
        }
        else
        {
            for(Map.Entry <Integer, Partida> p: partidas.entrySet()) {
                if (p.getValue().getEstado().equals("Esperando"))
                {
                    if (System.currentTimeMillis()-p.getValue().recoverTimestampGetEstado()<10000) {
                        ret = p.getValue();
                        ret.addJugador(partida.getJugadores().get(0));
                        break;
                    }
                    else
                    {
                      partidas.remove(p.getKey());
                    }
                }
            }

        }
        if (ret!=null)
        {
            ret.crearBaraja();
            for (Jugador j :ret.getJugadores())
                j.setTarjeta(ret.recoverFirstTarjeta());
        }
        else
        {
            partida.setId(secuenciaPartida);
            partida.setEstado("Esperando");
            partidas.put(secuenciaPartida++,partida);
            ret=partida;

        }
        return(ret);


    }

    public Partida getPartida(int id)
    {
        Partida partida=partidas.get(id);
        if (partida.getModo().equals("Entrenamiento") && partida.getEstado().equals("Jugando") &&
            System.currentTimeMillis()-partida.getTimestamptEstado()>TIMEOUT_ENTRENAMIENTO)
        {
            partida.setEstado("Listo");
            partida.recoverTarjeta();
        }
        return partida;
    }

    public Partida checkObjeto(int idPartida,int idJugador,Objeto objeto)
    {
        Partida partida=partidas.get(idPartida);
        if (partida.checkObjeto(objeto))
        {
            Jugador jugador=partida.getJugador(idJugador);
            jugador.setPuntos(jugador.getPuntos()+1);
            jugador.setTarjeta(partida.getCartaEnJuego());
            partida.setEstado("Listo");
            partida.recoverTarjeta();
        }
        return partida;
    }

    public void borrarJugador(int idPartida, int idJugador)
    {
        Partida partida=partidas.get(idPartida);
        partida.deleteJugador(idJugador);
        if (partida.getJugadores().size()==0);
           partidas.remove(partida.getId());
    }



}
