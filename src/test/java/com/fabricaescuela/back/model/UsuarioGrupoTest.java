package com.fabricaescuela.back.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class UsuarioGrupoTest {

    @Test
    void testGettersAndSetters() {
        // Arrange
        UsuarioGrupo usuarioGrupo = new UsuarioGrupo();
        Long id = 1L;
        Long idGrupo = 10L;
        Long idUsuario = 20L;
        LocalDateTime fechaIngreso = LocalDateTime.now().minusDays(5);
        LocalDateTime fechaSalida = LocalDateTime.now();
        String estado = "activo";

        // Act
        usuarioGrupo.setId(id);
        usuarioGrupo.setIdGrupo(idGrupo);
        usuarioGrupo.setIdUsuario(idUsuario);
        usuarioGrupo.setFechaIngreso(fechaIngreso);
        usuarioGrupo.setFechaSalida(fechaSalida);
        usuarioGrupo.setEstado(estado);

        // Assert
        assertEquals(id, usuarioGrupo.getId());
        assertEquals(idGrupo, usuarioGrupo.getIdGrupo());
        assertEquals(idUsuario, usuarioGrupo.getIdUsuario());
        assertEquals(fechaIngreso, usuarioGrupo.getFechaIngreso());
        assertEquals(fechaSalida, usuarioGrupo.getFechaSalida());
        assertEquals(estado, usuarioGrupo.getEstado());
    }

    @Test
    void testValoresInicialesPorDefecto() {
        // Arrange
        UsuarioGrupo usuarioGrupo = new UsuarioGrupo();

        // Act & Assert
        assertNull(usuarioGrupo.getId());
        assertNull(usuarioGrupo.getIdGrupo());
        assertNull(usuarioGrupo.getIdUsuario());
        assertNull(usuarioGrupo.getFechaIngreso());
        assertNull(usuarioGrupo.getFechaSalida());
        assertNull(usuarioGrupo.getEstado());
    }
}

