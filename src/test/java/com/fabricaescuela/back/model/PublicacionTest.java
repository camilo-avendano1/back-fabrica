package com.fabricaescuela.back.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class PublicacionTest {

    @Test
    void testGettersAndSetters() {
        // Arrange
        Publicacion publicacion = new Publicacion();
        Long idPublicacion = 1L;
        Long idGrupo = 10L;
        Long idUsuario = 5L;
        String texto = "Contenido de prueba";
        String tipoContenido = "texto";
        String urlMultimedia = "http://ejemplo.com/img.jpg";
        LocalDateTime fecha = LocalDateTime.of(2024, 6, 1, 12, 0);
        String estado = "inactivo";

        // Act
        publicacion.setIdPublicacion(idPublicacion);
        publicacion.setIdGrupo(idGrupo);
        publicacion.setIdUsuario(idUsuario);
        publicacion.setTexto(texto);
        publicacion.setTipoContenido(tipoContenido);
        publicacion.setUrlMultimedia(urlMultimedia);
        publicacion.setFechaPublicacion(fecha);
        publicacion.setEstado(estado);

        // Assert
        assertEquals(idPublicacion, publicacion.getIdPublicacion());
        assertEquals(idGrupo, publicacion.getIdGrupo());
        assertEquals(idUsuario, publicacion.getIdUsuario());
        assertEquals(texto, publicacion.getTexto());
        assertEquals(tipoContenido, publicacion.getTipoContenido());
        assertEquals(urlMultimedia, publicacion.getUrlMultimedia());
        assertEquals(fecha, publicacion.getFechaPublicacion());
        assertEquals(estado, publicacion.getEstado());
    }

    @Test
    void testValoresPorDefecto() {
        // Arrange
        Publicacion publicacion = new Publicacion();

        // Act
        String estado = publicacion.getEstado();
        LocalDateTime fecha = publicacion.getFechaPublicacion();

        // Assert
        assertEquals("activo", estado);
        assertNotNull(fecha);
    }
}

