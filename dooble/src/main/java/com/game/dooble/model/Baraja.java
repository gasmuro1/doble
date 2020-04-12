package com.game.dooble.model;

import lombok.Data;

import java.util.*;
import java.util.stream.Collectors;

@Data
public class Baraja {

    List cartas=null;
    public Baraja (int nivel)
    {
        int nObjetosTarjetas = 2*nivel+1;
        int nObjetoTarjeta = nivel+1;
        cartas=new ArrayList <Tarjeta> () ;
        crearTarjetas(nivel-1,nivel-1,nivel);
        Map<Objeto,List>  filas=calcularFilas();
        for (int z=0;z<nObjetoTarjeta;z++)
        {

            Tarjeta carta=new Tarjeta(nivel,z);
            crearTarjetaInfito(carta,filas);
            carta.desordenar();
            cartas.add(carta);
        }
        Random rndm = new Random();
        Collections.shuffle(cartas, rndm);

    }
    private void crearTarjetaInfito(Tarjeta carta,Map filas)
    {
        Iterator i=filas.keySet().iterator();
        Objeto key =(Objeto)i.next();
        carta.objetos.add(key);
        List recta1=(List)filas.get(key);
        filas.remove(key);
        i=filas.keySet().iterator();
        while (i.hasNext()){
            Objeto key2=(Objeto) i.next();
            List recta2=(List)filas.get(key2);
            Set<String> interseccion = (Set <String>) recta1.stream().filter(recta2::contains)
                    .collect(Collectors.toSet());
            if (interseccion.size()==0)
            {
                carta.getObjetos().add(key2);
                filas.remove(key2);
                i=filas.keySet().iterator();

            }
        }

    }
    private void crearTarjetas( int x, int y,int n)
    {
        Tarjeta t=new Tarjeta(n,x,y);
        cartas.add(t);
        if (y>0)
            crearTarjetas(x,--y,n);
        else
            if (x>0)
                crearTarjetas(--x,n-1,n);
         return;

    }
    private Map calcularFilas()
    {
        Map<Objeto,List<String>> ret =new TreeMap<Objeto,List<String>>();
        Iterator i=cartas.iterator();
        while (i.hasNext())
        {
            Tarjeta tarjeta=(Tarjeta)i.next();
            Iterator o = tarjeta.getObjetos().iterator();
            while (o.hasNext())
            {
                Objeto obj=(Objeto)o.next();
                if (!ret.containsKey((Objeto) obj))
                    ret.put(obj,new ArrayList<String>());
                ret.get(obj).add(String.format("%d,%d",tarjeta.getX(),tarjeta.getY()));
            }

        }
        return ret;
    }

}
