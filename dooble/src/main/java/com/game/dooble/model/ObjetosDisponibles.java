package com.game.dooble.model;


import lombok.Data;
import org.springframework.stereotype.Repository;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;


public class ObjetosDisponibles {
    private static List objetos=null;
    private static ObjetosDisponibles objetosDisponibles;
    private ObjetosDisponibles() {
        objetos = new ArrayList<Objeto>();

        BufferedReader br = null;

        try {
            String line;
            int i = 0;
            br = new BufferedReader(new FileReader("objetos.csv"));
            while ((line = br.readLine())!=null) {
                objetos.add(new Objeto(Integer.toString(i++), line));
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    static public List getList()
    {
        if (objetos==null)
            new ObjetosDisponibles();
        return objetos;
    }

}
