package com.fabricaescuela.back.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GrupoResumenDTOTest {

    @Test
    void testGettersAndSetters() {
        // Arrange
        GrupoResumenDTO dto = new GrupoResumenDTO();
        String nombre = "Grupo A";
        String descripcion = "Descripción del grupo";
        String tema = "Tecnología";
        Long cantidad = 15L;

        // Act
        dto.setNombre(nombre);
        dto.setDescripcion(descripcion);
        dto.setTema(tema);
        dto.setCantidadMiembros(cantidad);

        // Assert
        assertEquals(nombre, dto.getNombre());
        assertEquals(descripcion, dto.getDescripcion());
        assertEquals(tema, dto.getTema());
        assertEquals(cantidad, dto.getCantidadMiembros());
    }
}
