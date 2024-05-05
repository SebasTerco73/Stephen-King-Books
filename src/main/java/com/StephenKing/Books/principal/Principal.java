package com.StephenKing.Books.principal;

import com.StephenKing.Books.model.Datos;
import com.StephenKing.Books.model.DatosLibros;
import com.StephenKing.Books.service.ConsumoAPI;
import com.StephenKing.Books.service.ConvierteDatos;
import java.util.*;
import java.util.stream.Collectors;

public class Principal {
    private Scanner leer = new Scanner(System.in);
    private ConsumoAPI consumoApi = new ConsumoAPI();
    private ConvierteDatos conversor = new ConvierteDatos();
    private final String URL_BASE = "https://stephen-king-api.onrender.com/api/books";

    public void muestraElMenu() {
        var json = consumoApi.obtenerDatos(URL_BASE);
        var datos = conversor.obtenerDatos(json, Datos.class);
        int opcion = 0;
        do {

            System.out.println("Libros de Stephen King:" +
                    "\n1) Ver todos" +
                    "\n2) Mas actuales" +
                    "\n3) Mas antiguos" +
                    "\n4) Buscar por nombre" +
                    "\n5) Buscar por año" +
                    "\n6) Buscar por editorial" +
                    "\n7) Estadisticas de paginas por libro" +
                    "\n8) Salir");
            try {
                opcion = leer.nextInt();
                leer.nextLine();
                switch (opcion) {
                    case 1:
                        System.out.println(datos.data());
                        break;

                    case 2:
                        System.out.println("Libros mas actuales!");
                        datos.data().stream()
                                .sorted(Comparator.comparing(DatosLibros::year).reversed())
                                .limit(10)
                                .map(e -> e.year() + ": " + e.title().toUpperCase())
                                .forEach(System.out::println);
                        System.out.println("*******************************");
                        break;

                    case 3:
                        System.out.println("Libros mas antiguos!");
                        datos.data().stream()
                                .sorted(Comparator.comparing(DatosLibros::year))
                                .limit(10)
                                .map(e -> e.year() + ": " + e.title().toUpperCase())
                                .forEach(System.out::println);
                        System.out.println("*******************************");
                        break;

                    case 4:
                        System.out.print("Ingrese el nombre del libro a buscar: ");
                        String nombreBuscado = leer.nextLine();
                        Optional<DatosLibros> nombreEncontrado = datos.data().stream()
                                .filter(l -> l.title().toUpperCase().contains(nombreBuscado.toUpperCase()))
                                .findFirst();
                        if (nombreEncontrado.isPresent()) {
                            System.out.println(nombreEncontrado.get());
                        } else {
                            System.out.println("Ninguna coincidencia con el nombre '" + nombreBuscado + "'");
                        }
                        break;

                    case 5:
                        System.out.print("Ingrese el año a buscar: ");
                        int anioBuscado = leer.nextInt();
                        leer.nextLine();
                        List<DatosLibros> anioEncontrado = datos.data().stream()
                                .filter(l -> l.year() == anioBuscado)
                                .toList();
                        if (!anioEncontrado.isEmpty()) {
                            for (DatosLibros dl : anioEncontrado) {
                                System.out.println(dl.toString());
                            }
                        } else System.out.println("Ninguna coincidencia en el año '" + anioBuscado + "'");
                        break;

                    case 6:
                        System.out.print("Ingrese el nombre de la editorial a buscar: ");
                        nombreBuscado = leer.nextLine();
                        List<DatosLibros> editorialEncontrada = datos.data().stream()
                                .filter(l -> l.publisher().toUpperCase().contains(nombreBuscado.toUpperCase()))
                                .toList();
                        if (!editorialEncontrada.isEmpty()) {
                            for (DatosLibros dl : editorialEncontrada) {
                                System.out.println(dl.toString());
                            }
                        } else {
                            System.out.println("Ninguna coincidencia con la editorial '" + nombreBuscado + "'");
                        }
                        break;
                    case 7:
                        IntSummaryStatistics est = datos.data().stream()
                                .filter(e -> e.pages()>0)
                                .collect(Collectors.summarizingInt(DatosLibros::pages));
                        System.out.println("Cantidad de libros " + est.getCount());
                        System.out.println("El libro mas largo tiene " + est.getMax() + " paginas");
                        System.out.println("El libro mas corto tiene " + est.getMin() + " paginas");
                        System.out.println("La media de paginas es " + est.getAverage());
                        break;

                    case 8:
                        System.exit(0);
                    default:
                        System.out.println("Opcion incorrecta, intente nuevamente");
                }
            }catch (InputMismatchException e){
                opcion = 0;
                leer.nextLine(); // Limpia el búfer del scanner
                System.out.println("Ingrese una opcion valida");
            }
        }while(opcion!=8);
    }
}