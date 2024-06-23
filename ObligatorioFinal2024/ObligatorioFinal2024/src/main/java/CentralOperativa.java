
import java.util.InputMismatchException;
import java.util.Scanner;

public class CentralOperativa {
    public static void main(String[] args) {
        SPOTIFY central = new SPOTIFY();
        Scanner scanner = new Scanner(System.in);
        int opcion = -1;

        do {
            System.out.println("\nSeleccione una opción:");
            System.out.println("1. Top 10 canciones por país");
            System.out.println("2. Top 5 canciones en más Top 50");
            System.out.println("3. Top 7 artistas en rango de fechas");
            System.out.println("4. Cantidad de veces que aparece un artista específico en un top 50");
            System.out.println("5. Cantidad de canciones con tempo en rango");
            System.out.println("0. Salir\n");

            try {
                opcion = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Error: Debe ingresar un número para seleccionar una opción.");
                scanner.nextLine(); // Limpiar el buffer del scanner
                continue;
            }

            try {
                switch (opcion) {
                    case 1:
                        System.out.println("Ingrese la fecha:");
                        String fecha1 = scanner.next();
                        if (!fecha1.matches("\\d{4}-\\d{2}-\\d{2}")) {
                            throw new IllegalArgumentException("Formato de fecha incorrecto. Debe ser YYYY-MM-DD.");
                        }
                        System.out.println("Ingrese el país(Abreviatura de dos dígitos(Ej. (UY))):");
                        String pais = scanner.next();
                        if (!pais.matches("\\b\\w{2}\\b")) {
                            throw new IllegalArgumentException("Formato de país incorrecto. Debe ser CC.");
                        }
                        if (!central.paisExiste(pais)) {
                            throw new IllegalArgumentException("El país ingresado no se encontró en los datos.");
                        }
                        System.out.println("Cargando...");
                        central.top10CancionesPais(fecha1, pais);
                        System.out.println(" ");
                        break;
                    case 2:
                        System.out.println("Ingrese la fecha(YYYY-MM-DD):");
                        String fecha2 = scanner.next();
                        if (!fecha2.matches("\\d{4}-\\d{2}-\\d{2}")) {
                            throw new IllegalArgumentException("Formato de fecha incorrecto. Debe ser YYYY-MM-DD.");
                        }
                        System.out.println("Cargando...");
                        central.top5CancionesEnMasTop50(fecha2);
                        break;
                    case 3:
                        System.out.println("Ingrese la fecha de inicio:");
                        String fechaInicio = scanner.next();
                        if (!fechaInicio.matches("\\d{4}-\\d{2}-\\d{2}")) {
                            throw new IllegalArgumentException("Formato de fecha incorrecto. Debe ser YYYY-MM-DD.");
                        }
                        System.out.println("Ingrese la fecha de fin:");
                        String fechaFin = scanner.next();
                        if (!fechaFin.matches("\\d{4}-\\d{2}-\\d{2}")) {
                            throw new IllegalArgumentException("Formato de fecha incorrecto. Debe ser YYYY-MM-DD.");
                        }
                        System.out.println("Cargando...");
                        central.top7ArtistasEnRangoDeFechas(fechaInicio, fechaFin);
                        System.out.println(" ");
                        break;
                    case 4:
                        System.out.println("Ingrese la fecha:");
                        String fecha3 = scanner.next();
                        if (!fecha3.matches("\\d{4}-\\d{2}-\\d{2}")) {
                            throw new IllegalArgumentException("Formato de fecha incorrecto. Debe ser YYYY-MM-DD.");
                        }
                        System.out.println("Ingrese el nombre del artista:");
                        String artista = scanner.next();
                        System.out.println("Cargando...");
                        central.aparicionesArtistaEnTop50(artista, fecha3);
                        System.out.println(" ");
                        break;
                    case 5:
                        System.out.println("Ingrese la fecha de inicio:");
                        String fechaInicioTempo = scanner.next();
                        if (!fechaInicioTempo.matches("\\d{4}-\\d{2}-\\d{2}")) {
                            throw new IllegalArgumentException("Formato de fecha incorrecto. Debe ser YYYY-MM-DD.\n");
                        }
                        System.out.println("Ingrese la fecha de fin:");
                        String fechaFinTempo = scanner.next();
                        if (!fechaFinTempo.matches("\\d{4}-\\d{2}-\\d{2}")) {
                            throw new IllegalArgumentException("Formato de fecha incorrecto. Debe ser YYYY-MM-DD.");
                        }
                        try {
                            System.out.println("Ingrese el tempo de inicio:");
                            double tempoInicio = scanner.nextDouble();
                            System.out.println("Ingrese el tempo de fin:");
                            double tempoFin = scanner.nextDouble();
                            System.out.println("Cargando...");
                            central.getCantidadCancionesConTempoEnRango(fechaInicioTempo, fechaFinTempo, tempoInicio, tempoFin);
                        } catch (InputMismatchException e) {
                            System.out.println("Error: Debe ingresar un número para el tempo.");
                            scanner.nextLine(); // Limpiar el buffer del scanner
                            continue;
                        }
                        System.out.println(" ");
                        break;
                    case 0:
                        System.out.println("Saliendo...");
                        break;
                    default:
                        System.out.println("Opción no válida. Por favor, intente de nuevo.");
                        break;
                }
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            } catch (Exception e) {
                System.out.println("Error desconocido. Por favor, intente de nuevo.");
            }
        } while (opcion != 0);
        scanner.close();
    }
}


