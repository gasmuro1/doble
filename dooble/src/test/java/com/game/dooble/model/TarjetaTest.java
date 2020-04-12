package com.game.dooble.model;

//import junit.framework.TestCase;
//import junit.framework.TestSuite;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TarjetaTest  {

    ObjetosDisponibles objetosDisponibles;

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
    }

    @org.junit.jupiter.api.AfterEach
    void tearDown() {
    }

    @Test
    public void testCreateTarjeta()
    {
       Tarjeta t=new Tarjeta (2,1,1);
        assertNotNull(t);
        List o=t.getObjetos();
        List od=objetosDisponibles.getList();
        assertEquals(3,o.size());

        assertTrue(o.contains(od.get(2)));
        assertTrue(o.contains(od.get(3)));
        assertTrue(o.contains(od.get(4)));



    }
}