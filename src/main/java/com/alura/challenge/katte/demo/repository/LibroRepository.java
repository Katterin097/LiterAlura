package com.alura.challenge.katte.demo.repository;

import com.alura.challenge.katte.demo.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface LibroRepository extends JpaRepository<Libro, Long> {
    boolean existsByTitulo(String titulo);
    List<Libro> findByIdiomaIgnoreCase(String idioma);
}
