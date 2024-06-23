package Entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import uy.edu.um.tad.linkedlist.MyLinkedListImpl;
import uy.edu.um.tad.linkedlist.MyList;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Artista {
    private String nombre;
    private String puesto;
    public String toString()
    {
        return (new StringBuilder())
                .append("Artista: ")
                .append(nombre)
                .toString();
    }
}
