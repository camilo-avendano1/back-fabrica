package com.fabricaescuela.back.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PublicacionRequestTest {

    @Test
    void testGettersAndSetters() {
        // Arrange
        PublicacionRequest request = new PublicacionRequest();
        String texto = "Este es el texto de la publicación";
        String url = "http://ejemplo.com/contenido.jpg";
        String tipo = "multimedia";

        // Act
        request.setTexto(texto);
        request.setUrl(url);
        request.setTipo(tipo);

        // Assert
        assertEquals(texto, request.getTexto());
        assertEquals(url, request.getUrl());
        assertEquals(tipo, request.getTipo());
    }

    @Test
    void testValoresInicialesPorDefecto() {
        // Arrange
        PublicacionRequest request = new PublicacionRequest();

        // Act & Assert
        assertNull(request.getTexto());
        assertNull(request.getUrl());
        assertNull(request.getTipo());
    }
}
