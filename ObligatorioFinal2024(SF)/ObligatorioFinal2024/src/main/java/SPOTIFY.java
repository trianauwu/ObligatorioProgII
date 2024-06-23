import Entities.Cancion;
import Entities.Artista;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import uy.edu.um.tad.linkedlist.MyLinkedListImpl;
import uy.edu.um.tad.linkedlist.MyList;
import uy.edu.um.tad.hash.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public final class SPOTIFY {
    private static final String csvFile = "spotify_data.csv";

    public MyHashImpl<String, Cancion> getCancionesPais(String date, String country) {
        MyHashImpl<String, Cancion> canciones = new MyHashImpl<>();
        CSVFormat format = CSVFormat.DEFAULT
                .withFirstRecordAsHeader()
                .withIgnoreSurroundingSpaces() // Ignora espacios en blanco alrededor de los delimitadores
                .withEscape('\\') // Especifica un carácter de escape
                .withQuote('"') // Define el carácter de cita
                .withDelimiter(','); // Especifica el delimitador

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            CSVParser csvParser = format.parse(br);
            for (CSVRecord record : csvParser.getRecords()) {
                String fecha = record.get("snapshot_date");
                String recordCountry = record.get("country");
                // Convierte la fecha a formato "yyyy-MM-dd" y compara con la fecha buscada
                DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                LocalDate localDate = LocalDate.parse(fecha, inputFormatter);
                String formattedDate = localDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                //System.out.println("Fecha: " + formattedDate + ", País: " + recordCountry); // Imprime la fecha y el país de cada registro
                if (formattedDate.equals(date.trim()) && recordCountry.equalsIgnoreCase(country.trim())) {
                    Cancion cancion = new Cancion(
                            record.get("name"),
                            record.get("spotify_id"),
                            record.get("artists"),
                            record.get("duration_ms"),
                            Integer.parseInt(record.get("daily_rank")),
                            record.get("tempo"),
                            record.get("country"),
                            formattedDate
                    );
                    canciones.put(cancion.getId(), cancion);
                }
            }

            System.out.println("Canciones cargadas: " + canciones.size());

        } catch (IOException e) {
            e.printStackTrace();
        }
        return canciones;
    }
    public void top10CancionesPais(String date, String country) {
        // Obtener canciones filtradas por fecha y país
        MyHashImpl<String, Cancion> cancionesMap = getCancionesPais(date, country);

        // Crear un mapa para contar la frecuencia de cada canción en el top 10
        MyHashImpl<String, Integer> frecuenciaCancionesEnTop10 = new MyHashImpl<>();

        // Iterar sobre todas las canciones en el mapa de canciones
        for (Cancion cancion : cancionesMap.values()) {
            // Si la canción está en el top 10, incrementar su conteo en el mapa de frecuencias
            if (cancion.getDailyRank() <= 10) {
                frecuenciaCancionesEnTop10.put(cancion.getId(), frecuenciaCancionesEnTop10.getOrDefault(cancion.getId(), 0) + 1);
            }
        }

        // Crear el arreglo para las top 10 canciones
        Cancion[] topCanciones = new Cancion[10];
        int minIndex = 0;

        // Iterar sobre el mapa de frecuencias e insertar cada canción en el arreglo si su frecuencia es mayor que la más pequeña en el arreglo
        for (String id : frecuenciaCancionesEnTop10.keySet()) {
            Cancion cancion = cancionesMap.get(id);
            int frecuencia = frecuenciaCancionesEnTop10.get(id);
            if (topCanciones[minIndex] == null || frecuencia > (topCanciones[minIndex] != null ? frecuenciaCancionesEnTop10.get(topCanciones[minIndex].getId()) : 0)) {
                topCanciones[minIndex] = cancion;
                // Actualizar el índice de la canción con la menor frecuencia en el arreglo
                for (int i = 0; i < topCanciones.length; i++) {
                    if (topCanciones[i] == null || (topCanciones[i] != null && frecuenciaCancionesEnTop10.get(topCanciones[i].getId()) < (topCanciones[minIndex] != null ? frecuenciaCancionesEnTop10.get(topCanciones[minIndex].getId()) : 0))) {
                        minIndex = i;
                    }
                }
            }
        }

        // Ordenar el arreglo de canciones en orden descendente por su posición
        Arrays.sort(topCanciones, (c1, c2) -> {
            if (c1 == null) {
                return 1;
            } else if (c2 == null) {
                return -1;
            } else {
                return Integer.compare(c2.getDailyRank(), c1.getDailyRank());
            }
        });

        // Imprimir las top 10 canciones
        System.out.println("Imprimiendo top 10 de canciones para el país " + country + " en la fecha " + date);
        for (Cancion cancion : topCanciones) {
            if (cancion != null) {
                System.out.println("Posición: " + cancion.getDailyRank() + " - Nombre: " + cancion.getNombre() + " - Artista: " + cancion.getArtista());
            }
        }
    }

    public MyHashImpl<String, MyList<Cancion>> getCancionesFecha(String fecha) {
        MyHashImpl<String, MyList<Cancion>> canciones = new MyHashImpl<>();
        CSVFormat format = CSVFormat.DEFAULT
                .withFirstRecordAsHeader()
                .withIgnoreSurroundingSpaces() // Ignora espacios en blanco alrededor de los delimitadores
                .withEscape('\\') // Especifica un carácter de escape
                .withQuote('"') // Define el carácter de cita
                .withDelimiter(','); // Especifica el delimitador

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            CSVParser csvParser = format.parse(br);
            for (CSVRecord record : csvParser.getRecords()) {
                String fechaRegistro = record.get("snapshot_date");
                // Convierte la fecha a formato "yyyy-MM-dd" y compara con la fecha buscada
                DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                LocalDate localDate = LocalDate.parse(fechaRegistro, inputFormatter);
                String formattedDate = localDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                //System.out.println("Fecha: " + formattedDate); // Imprime la fecha de cada registro
                if (formattedDate.equals(fecha.trim())) {
                    Cancion cancion = new Cancion(
                            record.get("name"),
                            record.get("spotify_id"),
                            record.get("artists"),
                            record.get("duration_ms"),
                            Integer.parseInt(record.get("daily_rank")),
                            record.get("tempo"),
                            record.get("country"),
                            formattedDate
                    );
                    MyList<Cancion> lista = canciones.get(cancion.getId());
                    if (lista == null) {
                        lista = new MyLinkedListImpl<>();
                    }
                    lista.add(cancion);
                    canciones.put(cancion.getId(), lista);
                }
            }

            System.out.println("Canciones cargadas: " + canciones.size());

        } catch (IOException e) {
            e.printStackTrace();
        }
        return canciones;
    }
    public void top5CancionesEnMasTop50(String fecha){
        // Obtener canciones filtradas por fecha
        MyHashImpl<String, MyList<Cancion>> cancionesMap = new SPOTIFY().getCancionesFecha(fecha);

        // Crear un mapa para contar la frecuencia de cada canción en el top 50
        MyHashImpl<String, Integer> frecuenciaCancionesEnTop50 = new MyHashImpl<>();

        // Iterar sobre todas las canciones en el mapa de canciones
        for (MyList<Cancion> lista : cancionesMap.values()) {
            for (Cancion cancion : lista) {
                // Si la canción está en el top 50, incrementar su conteo en el mapa de frecuencias
                if (cancion.getDailyRank() <= 50) {
                    frecuenciaCancionesEnTop50.put(cancion.getId(), frecuenciaCancionesEnTop50.getOrDefault(cancion.getId(), 0) + 1);
                }
            }
        }

        // Crear el arreglo para las top 5 canciones
        Cancion[] topCanciones = new Cancion[5];
        int minIndex = 0;

        // Iterar sobre el mapa de frecuencias e insertar cada canción en el arreglo si su frecuencia es mayor que la más pequeña en el arreglo
        for (String id : frecuenciaCancionesEnTop50.keySet()) {
            Cancion cancion = cancionesMap.get(id).get(0); // Asumiendo que todas las canciones con el mismo ID son iguales
            int frecuencia = frecuenciaCancionesEnTop50.get(id);
            if (topCanciones[minIndex] == null || frecuencia > (topCanciones[minIndex] != null ? frecuenciaCancionesEnTop50.get(topCanciones[minIndex].getId()) : 0)) {
                topCanciones[minIndex] = cancion;
                // Actualizar el índice de la canción con la menor frecuencia en el arreglo
                for (int i = 0; i < topCanciones.length; i++) {
                    if (topCanciones[i] == null || (topCanciones[i] != null && frecuenciaCancionesEnTop50.get(topCanciones[i].getId()) < (topCanciones[minIndex] != null ? frecuenciaCancionesEnTop50.get(topCanciones[minIndex].getId()) : 0))) {
                        minIndex = i;
                    }
                }
            }
        }

        Arrays.sort(topCanciones, (c1, c2) -> {
            if (c1 == null) {
                return 1;
            } else if (c2 == null) {
                return -1;
            } else {
                return Integer.compare(frecuenciaCancionesEnTop50.get(c2.getId()), frecuenciaCancionesEnTop50.get(c1.getId()));
            }
        });

        // Imprimir las top 5 canciones en el top 50
        System.out.println("Imprimiendo top 5 de canciones en el top 50 para la fecha " + fecha);
        int position = 1;
        for (Cancion cancion : topCanciones) {
            if (cancion != null) {
                System.out.println("Top " + position + ":  Canción: " + cancion.getNombre() + " --- Artista: " + cancion.getArtista() + " --- Apariciones: " + frecuenciaCancionesEnTop50.get(cancion.getId()));
                position++;
            }
        }
    }

    public void top7ArtistasEnRangoDeFechas(String f1, String f2){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate startDate = LocalDate.parse(f1, formatter);
        LocalDate endDate = LocalDate.parse(f2, formatter);

        // Crear un mapa para contar la frecuencia de cada artista en el top 50
        MyHashImpl<String, Integer> frecuenciaArtistasEnTop50 = new MyHashImpl<>();

        // Crear un heap para los top 7 artistas
        PriorityQueue<MyHashImpl.Entry<String, Integer>> topArtistas = new PriorityQueue<>(
                Comparator.comparingInt(MyHashImpl.Entry::getValue)
        );

        CSVFormat format = CSVFormat.DEFAULT
                .withFirstRecordAsHeader()
                .withIgnoreSurroundingSpaces() // Ignora espacios en blanco alrededor de los delimitadores
                .withEscape('\\') // Especifica un carácter de escape
                .withQuote('"') // Define el carácter de cita
                .withDelimiter(','); // Especifica el delimitador
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            CSVParser csvParser = format.parse(br);
            for (CSVRecord record : csvParser.getRecords()) {
                String fechaRegistro = record.get("snapshot_date");
                DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                LocalDate localDate = LocalDate.parse(fechaRegistro, inputFormatter);
                String formattedDate = localDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

                // Comprobar si la fecha del registro está en el rango de fechas proporcionado
                if ((formattedDate.compareTo(f1) >= 0) && (formattedDate.compareTo(f2) <= 0)) {
                    // Comprobar si el artista está en el top 50
                    if (Integer.parseInt(record.get("daily_rank")) <= 50) {
                        String artista = record.get("artists");
                        // Incrementar la cuenta para este artista en el mapa de frecuencias
                        frecuenciaArtistasEnTop50.put(artista, frecuenciaArtistasEnTop50.getOrDefault(artista, 0) + 1);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Iterar sobre el mapa de frecuencias e insertar cada artista en el heap si su frecuencia es mayor que la más pequeña en el heap
        for (Map.Entry<String, Integer> entry : frecuenciaArtistasEnTop50.entrySet()) {
            MyHashImpl.Entry<String, Integer> newEntry = new MyHashImpl.Entry<>(entry.getKey(), entry.getValue());
            if (topArtistas.size() < 7) {
                topArtistas.add(newEntry);
            } else if (entry.getValue() > topArtistas.peek().getValue()) {
                topArtistas.poll();
                topArtistas.add(newEntry);
            }
        }

        // Crear una lista para almacenar las cadenas de salida
        List<String> output = new ArrayList<>();

        // Llenar la lista con las cadenas de salida
        int position = topArtistas.size();
        while (!topArtistas.isEmpty()) {
            MyHashImpl.Entry<String, Integer> entry = topArtistas.poll();
            output.add("Top " + position + ": se encuentra el/la artista " + entry.getKey() + " con " + entry.getValue() + " apariciones");
            position--;
        }

        // Imprimir los top 7 artistas en orden inverso
        System.out.println("Top 7 artistas que más aparecen en los top 50 para el rango de fechas " + f1 + " a " + f2 + ":");
        for (int i = output.size() - 1; i >= 0; i--) {
            System.out.println(output.get(i));
        }
    }
    public MyList<Artista> getArtistasPorNombreYFecha(String nombreArtista, String fecha) {
        MyList<Artista> artistas = new MyLinkedListImpl<>();
        CSVFormat format = CSVFormat.DEFAULT
                .withFirstRecordAsHeader()
                .withIgnoreSurroundingSpaces() // Ignora espacios en blanco alrededor de los delimitadores
                .withEscape('\\') // Especifica un carácter de escape
                .withQuote('"') // Define el carácter de cita
                .withDelimiter(','); // Especifica el delimitador

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            CSVParser csvParser = format.parse(br);
            for (CSVRecord record : csvParser.getRecords()) {
                String fechaRegistro = record.get("snapshot_date");
                // Convierte la fecha a formato "yyyy-MM-dd" y compara con la fecha buscada
                DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                LocalDate localDate = LocalDate.parse(fechaRegistro, inputFormatter);
                String formattedDate = localDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                //System.out.println("Fecha: " + formattedDate); // Imprime la fecha de cada registro
                if (formattedDate.equals(fecha.trim()) && record.get("artists").toLowerCase().contains(nombreArtista.toLowerCase())) {
                    Artista artista = new Artista(
                            record.get("artists"),
                            record.get("daily_rank")
                    );
                    artistas.add(artista);
                }
            }

            //System.out.println("Artistas cargados: " + artistas.size());

        } catch (IOException e) {
            e.printStackTrace();
        }
        return artistas;
    }

    public int aparicionesArtistaEnTop50(String nombreArtista, String fecha){
        // Obtener artistas filtrados por fecha y nombre
        MyList<Artista> artistasList = new SPOTIFY().getArtistasPorNombreYFecha(nombreArtista, fecha);

        // Contar la frecuencia del artista en el top 50
        int frecuenciaArtistaEnTop50 = 0;

        // Iterar sobre todos los artistas en la lista de artistas
        for (Artista artista : artistasList) {
            // Si el artista está en el top 50, incrementar su conteo en la frecuencia
            if (Integer.parseInt(artista.getPuesto()) <= 50) {
                frecuenciaArtistaEnTop50++;
            }
        }

        // Imprimir la frecuencia del artista en el top 50
        System.out.println("El artista " + nombreArtista + " aparece " + frecuenciaArtistaEnTop50 + " veces en el top 50 para la fecha " + fecha);

        return frecuenciaArtistaEnTop50;
    }
    public MyHashImpl<String, Cancion> getCancionesPorFechaYTempo(String fechaInicio, String fechaFin) {
        MyHashImpl<String, Cancion> canciones = new MyHashImpl<>();
        CSVFormat format = CSVFormat.DEFAULT
                .withFirstRecordAsHeader()
                .withIgnoreSurroundingSpaces() // Ignora espacios en blanco alrededor de los delimitadores
                .withEscape('\\') // Especifica un carácter de escape
                .withQuote('"') // Define el carácter de cita
                .withDelimiter(','); // Especifica el delimitador

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            CSVParser csvParser = format.parse(br);
            for (CSVRecord record : csvParser.getRecords()) {
                String fechaRegistro = record.get("snapshot_date");
                // Convierte la fecha a formato "yyyy-MM-dd" y compara con el rango de fechas
                DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                LocalDate localDate = LocalDate.parse(fechaRegistro, inputFormatter);
                String formattedDate = localDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                if ((formattedDate.compareTo(fechaInicio) >= 0) && (formattedDate.compareTo(fechaFin) <= 0)) {
                    Cancion cancion = new Cancion(
                            record.get("name"),
                            record.get("spotify_id"),
                            record.get("artists"),
                            record.get("duration_ms"),
                            Integer.parseInt(record.get("daily_rank")),
                            record.get("tempo"),
                            record.get("country"),
                            formattedDate
                    );
                    canciones.put(cancion.getId(), cancion);
                }
            }

            //System.out.println("Canciones cargadas: " + canciones.size());

        } catch (IOException e) {
            e.printStackTrace();
        }
        return canciones;
    }

    public int getCantidadCancionesConTempoEnRango(String fechaInicio, String fechaFin, double tempoInicio, double tempoFin) {
        // Obtener canciones filtradas por rango de fechas
        MyHashImpl<String, Cancion> cancionesMap = getCancionesPorFechaYTempo(fechaInicio, fechaFin);

        int contador = 0;

        // Iterar sobre todas las canciones en el mapa de canciones
        for (Cancion cancion : cancionesMap.values()) {
            // Si el tempo de la canción está en el rango específico, incrementar el contador
            double tempo = Double.parseDouble(cancion.getTempo());
            if (tempo >= tempoInicio && tempo <= tempoFin) {
                contador++;
            }
        }

        // Imprimir el resultado
        System.out.println("Cantidad de canciones con un tempo en el rango " + tempoInicio + " - " + tempoFin + " para el rango de fechas " + fechaInicio + " - " + fechaFin + ": " + contador);
        return contador;
    }
    public boolean paisExiste(String pais) {
        CSVFormat format = CSVFormat.DEFAULT
                .withFirstRecordAsHeader()
                .withIgnoreSurroundingSpaces() // Ignora espacios en blanco alrededor de los delimitadores
                .withEscape('\\') // Especifica un carácter de escape
                .withQuote('"') // Define el carácter de cita
                .withDelimiter(','); // Especifica el delimitador

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            CSVParser csvParser = format.parse(br);
            for (CSVRecord record : csvParser.getRecords()) {
                String recordCountry = record.get("country");
                if (recordCountry.equalsIgnoreCase(pais.trim())) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}