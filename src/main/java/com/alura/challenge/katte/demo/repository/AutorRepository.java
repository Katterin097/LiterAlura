package com.alura.challenge.katte.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.alura.challenge.katte.demo.model.Autor;

import java.util.Optional;
import java.util.List;

public interface AutorRepository extends JpaRepository<Autor, Long> {

    Optional<Autor> findByNombre(String nombre);

    List<Autor> findByAnioNacimientoLessThanEqualAndAnioMuerteGreaterThanEqual(
            Integer nacimiento,
            Integer muerte
    );
}