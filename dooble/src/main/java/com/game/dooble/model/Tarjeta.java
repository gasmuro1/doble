package com.game.dooble.model;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

@Data
public class Tarjeta {


    List <Objeto> objetos=null;
    int orden;
    int x=0;
    int y=0;
    int z=0;
    public Tarjeta()
    {

    }

    public Tarjeta ( int orden,int x, int y)
    {
        objetos = new ArrayList <Objeto>();
        this.orden=orden;
        this.x=x;
        this.y=y;
        this.z=0;
        calcularObjetos();
        desordenar();
    }
    public void desordenar()
    {
        Random rndm = new Random();
        Collections.shuffle(objetos, rndm);
    }
    public Tarjeta (int orden,int z)
    {
        this.z=z+1;
        this.orden=orden;
        objetos = new ArrayList <Objeto>();
        calcularObjetos();
    }

    private void calcularObjetos()
    {
        if (z>0)
        {
            objetos.add((Objeto)ObjetosDisponibles.getList().get(orden*orden+orden+1));
            return;
        }
        int xt=x;
        int yt=y;
        List listaObjetos=ObjetosDisponibles.getList();
        int idxObjeto=0;
        for (int a=0;a<orden;a++)
        {
            for (int c=0;c<orden;c++)
            {

                    int y=xt*a+c;
                    while (y>=orden)
                        y=y-orden;
                    if (yt==y) {
                        Objeto aux = (Objeto) listaObjetos.get(idxObjeto);
                        objetos.add(aux);
                    }
                    idxObjeto++;
                    if (a==0) {
                        if (y == xt)
                            objetos.add((Objeto) listaObjetos.get(idxObjeto));
                        idxObjeto++;
                    }


            }
        }


    }


 }
