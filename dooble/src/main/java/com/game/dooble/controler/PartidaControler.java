package com.game.dooble.controler;

import com.game.dooble.model.*;
import com.game.dooble.service.Servicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@RestController
public class PartidaControler {
    @Autowired
    Servicio serv;

    public PartidaControler(Servicio serv)
    {
        this.serv=serv;
    }
    public PartidaControler()
    {

    }

    @GetMapping("/")
    public String index() {
        return "Hola a la api de dooble";
    }

    @PostMapping(value="jugador",consumes = "application/json",produces = "application/json")
    public Jugador crearJugador(@RequestBody Jugador jugador)
    {
       try {
           return serv.addJugador(jugador);
       }
       catch (IllegalArgumentException e)
       {
                throw new ResponseStatusException(HttpStatus.CONFLICT,"Ya existe el Jugador");
       }
    }

    @PostMapping(value="partida",consumes = "application/json",produces = "application/json")
    public Partida crearPartida(@RequestBody Partida partida) {

        return(serv.crearPartida(partida));

    }

    @GetMapping (value="partida/{idPartida}",produces ="application/json")
    public Partida getPartida(@PathVariable String idPartida)

    {
        return serv.getPartida(Integer.parseInt(idPartida));
    }
    @GetMapping(value="carta/{idPartida}",produces = "application/json")
    public Tarjeta getTarjeta(@PathVariable String idPartida)
    {
        Partida p=serv.getPartida(Integer.parseInt(idPartida));
        return (p.recoverTarjeta());
    }

    @PutMapping(value="carta/{idPartida}/{idJugador}",consumes = "application/json",produces = "application/json")
    public Partida setTarjetaEnjuego(@PathVariable int idPartida,@PathVariable int idJugador,@RequestBody Tarjeta t )
    {
        Partida p=serv.getPartida(idPartida);
        Jugador j=p.getJugador(idJugador);
        j.setTarjetaEnJuego(t);
        p.changeEstado();
        return p;
    }

    @PutMapping(value="objeto/{idPartida}/{idJugador}",consumes = "application/json",produces = "application/json")
    public Partida setObjeto(@PathVariable int idPartida,@PathVariable int idJugador,@RequestBody Objeto objeto ) {
        return serv.checkObjeto(idPartida,idJugador,objeto);
    }

    @DeleteMapping(value="{idPartida}/{idJugador}",consumes = "application/json",produces = "application/json")
    public void borrarJugador(@PathVariable int idPartida,@PathVariable int idJugador) {
        serv.borrarJugador(idPartida,idJugador);
    }


}
