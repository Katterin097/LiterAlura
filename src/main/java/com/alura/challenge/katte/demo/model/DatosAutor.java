package com.alura.challenge.katte.demo.model;

import com.fasterxml.jackson.annotation.JsonAlias;

public record DatosAutor(

        @JsonAlias("name")
        String nombre,

        @JsonAlias("birth_year")
        Integer anioNacimiento,

        @JsonAlias("death_year")
        Integer anioFallecimiento
) {}
