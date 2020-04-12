package com.dooble.game.front.model;

import lombok.Data;

@Data
public class Objeto {
    String id;
    String asset;
    public Objeto(){

    }
    public Objeto (String id, String asset)
    {
        this.id=id;
        this.asset=asset;
    }
}
