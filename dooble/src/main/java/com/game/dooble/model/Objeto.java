package com.game.dooble.model;
import java.io.Serializable;
import lombok.Data;
@Data
public class Objeto implements Comparable<Objeto>{
    String id;
    String asset;
    public Objeto(String id,String asset)
    {
        this.id=id;
        this.asset=asset;
    }
    public int compareTo(Objeto objeto)
    {
        return id.compareTo(objeto.id);
    }
    public String getId()
    {
        return(id);
    }

}
