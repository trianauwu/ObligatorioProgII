package Entities;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cancion implements Comparable<Cancion> {
    private String nombre;
    private String id;
    private String artista;
    private String duracion;
    @Getter
    private int dailyRank;
    @Getter
    private String tempo; // Añadido campo tempo
    private String pais;
    private String fecha;

    @Override
    public int compareTo(Cancion other) {
        return Integer.compare(this.dailyRank, other.dailyRank);
    }

//    @Override
//    public String toString()
//    {
//        return (new StringBuilder())
//                .append("Cancion: ")
//                .append(nombre)
//                .append(" de ")
//                .append(artista)
//                .append(" se encuentra en el puesto ")
//                .append(dailyRank)
//                .append(" de ")
//                .append(pais)
//                .append(" en el dia ")
//                .append(fecha)
//                .toString();
//    }
}