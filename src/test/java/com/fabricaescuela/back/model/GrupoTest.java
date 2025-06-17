package com.fabricaescuela.back.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GrupoTest {

    @Test
    void testGettersAndSetters() {
        // Arrange
        Grupo grupo = new Grupo();
        Long id = 1L;
        String nombre = "Grupo de Prueba";
        String descripcion = "Descripción de prueba";
        Integer idCategoria = 2;
        String reglas = "No spam";
        String visibilidad = "publico";
        Integer administrador = 10;
        LocalDateTime fechaCreacion = LocalDateTime.now();
        String estado = "activo";

        // Act
        grupo.setIdGrupo(id);
        grupo.setNombre(nombre);
        grupo.setDescripcion(descripcion);
        grupo.setIdCategoria(idCategoria);
        grupo.setReglas(reglas);
        grupo.setVisibilidad(visibilidad);
        grupo.setAdministrador(administrador);
        grupo.setFechaCreacion(fechaCreacion);
        grupo.setEstado(estado);

        // Assert
        assertEquals(id, grupo.getIdGrupo());
        assertEquals(nombre, grupo.getNombre());
        assertEquals(descripcion, grupo.getDescripcion());
        assertEquals(idCategoria, grupo.getIdCategoria());
        assertEquals(reglas, grupo.getReglas());
        assertEquals(visibilidad, grupo.getVisibilidad());
        assertEquals(administrador, grupo.getAdministrador());
        assertEquals(fechaCreacion, grupo.getFechaCreacion());
        assertEquals(estado, grupo.getEstado());
    }
}
