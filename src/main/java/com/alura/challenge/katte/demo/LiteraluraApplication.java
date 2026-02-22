package com.alura.challenge.katte.demo;

import com.alura.challenge.katte.demo.model.Autor;
import com.alura.challenge.katte.demo.model.DatosLibro;
import com.alura.challenge.katte.demo.model.DatosRespuesta;
import com.alura.challenge.katte.demo.model.Libro;
import com.alura.challenge.katte.demo.repository.LibroRepository;
import com.alura.challenge.katte.demo.service.ConsumoAPI;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.alura.challenge.katte.demo.repository.AutorRepository;

import java.util.Scanner;

@SpringBootApplication
public class LiteraluraApplication implements CommandLineRunner {

    @Autowired
    private LibroRepository repository;

    @Autowired
    private AutorRepository autorRepository;

    public static void main(String[] args) {
        SpringApplication.run(LiteraluraApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        Scanner teclado = new Scanner(System.in);
        int opcion = -1;

        while (opcion != 0) {

            System.out.println("""
                
               LITERALURA | Escriba su opción a consultar:
               1 - Buscar libro por título
               2 - Listar libros guardados
               3 - Listar autores
               4 - Listar autores vivos en determinado año
               5 - Listar libros por idioma
               0 - Salir
                """);

            opcion = teclado.nextInt();
            teclado.nextLine(); // limpiar buffer

            switch (opcion) {

                case 1:
                    buscarLibro(teclado);
                    break;

                case 2:
                    listarLibros();
                    break;

                case 3:
                    listarAutores();
                    break;

                case 4:
                    listarAutoresVivos(teclado);
                    break;

                case 5:
                    listarLibrosPorIdioma(teclado);
                    break;

                case 0:
                    System.out.println("La aplicación se cerró correctamente");
                    break;

                default:
                    System.out.println("Opción inválida");
            }
        }
    }

    private void listarLibrosPorIdioma(Scanner teclado) {

        System.out.println("""
    
            Selecciona el idioma del libro:
            es - Español
            en - Inglés
            fr - Francés
            pt - Portugués
            """);

        String idioma = teclado.nextLine();

        var libros = repository.findByIdiomaIgnoreCase(idioma);

        if (libros.isEmpty()) {
            System.out.println("No hay libros en ese idioma.");
        } else {
            System.out.println("\n Libros en idioma " + idioma + ":");
            libros.forEach(libro -> {
                System.out.println("------------");
                System.out.println("Título: " + libro.getTitulo());

                if (libro.getAutor() != null) {
                    System.out.println("Autor: " + libro.getAutor().getNombre());
                } else {
                    System.out.println("Autor: Desconocido");
                }

                System.out.println("Descargas: " + libro.getNumeroDescargas());
            });
        }
    }

    private void listarAutores() {

        System.out.println("\n✍ Autores guardados:");

        var autores = autorRepository.findAll();

        if (autores.isEmpty()) {
            System.out.println("No hay autores registrados.");
        } else {
            autores.forEach(autor -> {
                System.out.println("------------");
                System.out.println("Nombre: " + autor.getNombre());
                System.out.println("Nacimiento: " + autor.getAnioNacimiento());
                System.out.println("Fallecimiento: " + autor.getAnioMuerte());
            });
        }
    }

    private void listarAutoresVivos(Scanner teclado) {

        System.out.println("\n Ingresa el año:");
        int anio = teclado.nextInt();
        teclado.nextLine();

        var autores = autorRepository
                .findByAnioNacimientoLessThanEqualAndAnioMuerteGreaterThanEqual(anio, anio);

        if (autores.isEmpty()) {
            System.out.println("No se encontraron autores vivos en ese año.");
        } else {
            System.out.println("\n✍ Autores vivos en " + anio + ":");
            autores.forEach(autor -> {
                System.out.println("------------");
                System.out.println("Nombre: " + autor.getNombre());
                System.out.println("Nacimiento: " + autor.getAnioNacimiento());
                System.out.println("Fallecimiento: " + autor.getAnioMuerte());
            });
        }
    }

    private void buscarLibro(Scanner teclado) throws Exception {

        System.out.println(" Escribe el título del libro:");
        String tituloBusqueda = teclado.nextLine();

        ConsumoAPI consumo = new ConsumoAPI();
        String json = consumo.obtenerDatos(
                "https://gutendex.com/books/?search=" + tituloBusqueda.replace(" ", "%20")
        );

        ObjectMapper mapper = new ObjectMapper();
        DatosRespuesta datos = mapper.readValue(json, DatosRespuesta.class);

        if (datos.results() == null || datos.results().isEmpty()) {
            System.out.println(" No se encontró el libro en el sistema.");
            return;
        }

        DatosLibro datosLibro = datos.results().get(0);

        // 🔎 Verificar si el libro ya existe
        if (repository.existsByTitulo(datosLibro.titulo())) {
            System.out.println("El libro ya está guardado en la base de datos.");
            System.out.println("Titulo: " + datosLibro.titulo());
            return;
        }

        // 🔎 Verificar si tiene autor
        if (datosLibro.autores() == null || datosLibro.autores().isEmpty()) {
            System.out.println("El libro no tiene información de autor.");
            return;
        }

        var datosAutor = datosLibro.autores().get(0);

        // 🔎 Buscar si el autor ya existe en la base
        var autorExistente = autorRepository.findByNombre(datosAutor.nombre());

        Autor autor;

        if (autorExistente.isPresent()) {
            autor = autorExistente.get();
        } else {
            autor = new Autor(
                    datosAutor.nombre(),
                    datosAutor.anioNacimiento(),
                    datosAutor.anioFallecimiento()
            );
            autorRepository.save(autor);
        }

        // 🔎 Crear libro con autor
        Libro libro = new Libro(
                datosLibro.titulo(),
                datosLibro.idiomas() != null && !datosLibro.idiomas().isEmpty()
                        ? datosLibro.idiomas().get(0)
                        : "Desconocido",
                datosLibro.numeroDescargas(),
                autor
        );

        repository.save(libro);

        System.out.println("\nLibro guardado correctamente:");
        System.out.println("Título: " + libro.getTitulo());
        System.out.println("Autor: " + autor.getNombre());
        System.out.println("Idioma: " + libro.getIdioma());
        System.out.println("Descargas: " + libro.getNumeroDescargas());
    }

    private void listarLibros() {

        System.out.println("Libros guardados:");

        repository.findAll().forEach(libro -> {
            System.out.println("------------");
            System.out.println("Título: " + libro.getTitulo());
            System.out.println("Idioma: " + libro.getIdioma());
            System.out.println("Descargas: " + libro.getNumeroDescargas());
        });
    }
}