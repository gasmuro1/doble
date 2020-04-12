package com.game.dooble.model;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ObjetosDisponiblesTest {
   ObjetosDisponibles objetosDisponibles;
    @org.junit.jupiter.api.BeforeEach
    void setUp() {

    }
    @Test
    void GetListTest ()
    {

        List objetos=ObjetosDisponibles.getList();
        assertNotNull(objetos);
        assertEquals("0",((Objeto)objetos.get(0)).getId());
        assertEquals("1",((Objeto)objetos.get(1)).getId());

    }

}