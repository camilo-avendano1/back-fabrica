package com.fabricaescuela.back.controller;

import com.fabricaescuela.back.model.PublicacionRequest;
import com.fabricaescuela.back.repository.PublicacionRepositorio;
import com.fabricaescuela.back.repository.UsuarioGrupoRepositorio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PublicacionControllerTest {

    private PublicacionRepositorio publicacionRepo;
    private UsuarioGrupoRepositorio usuarioGrupoRepo;
    private PublicacionController controller;

    @BeforeEach
    void setUp() {
        publicacionRepo = mock(PublicacionRepositorio.class);
        usuarioGrupoRepo = mock(UsuarioGrupoRepositorio.class);
        controller = new PublicacionController(publicacionRepo, usuarioGrupoRepo);
    }

    @Test
    void debeRetornar403CuandoUsuarioNoPerteneceAlGrupo() {
        // Arrange
        Long idGrupo = 1L;
        PublicacionRequest request = new PublicacionRequest();
        request.setTipo("texto");
        request.setTexto("Contenido válido");
        when(usuarioGrupoRepo.existsByIdGrupoAndIdUsuario(idGrupo, 2)).thenReturn(false);

        // Act
        ResponseEntity<?> response = controller.crearDesdeJson(idGrupo, request);

        // Assert
        assertEquals(403, response.getStatusCodeValue());
        assertTrue(response.getBody().toString().contains("no pertenece"));
    }

    @Test
    void debeRetornar400CuandoTipoEsInvalido() {
        // Arrange
        Long idGrupo = 1L;
        PublicacionRequest request = new PublicacionRequest();
        request.setTipo("audio"); // tipo inválido
        when(usuarioGrupoRepo.existsByIdGrupoAndIdUsuario(idGrupo, 2)).thenReturn(true);

        // Act
        ResponseEntity<?> response = controller.crearDesdeJson(idGrupo, request);

        // Assert
        assertEquals(400, response.getStatusCodeValue());
        assertTrue(response.getBody().toString().contains("no válido"));
    }

    @Test
    void debeRetornar400CuandoTextoEsInvalido() {
        // Arrange
        Long idGrupo = 1L;
        PublicacionRequest request = new PublicacionRequest();
        request.setTipo("texto");
        request.setTexto("   "); // texto solo con espacios
        when(usuarioGrupoRepo.existsByIdGrupoAndIdUsuario(idGrupo, 2)).thenReturn(true);

        // Act
        ResponseEntity<?> response = controller.crearDesdeJson(idGrupo, request);

        // Assert
        assertEquals(400, response.getStatusCodeValue());
        assertTrue(response.getBody().toString().contains("Contenido inválido"));
    }

    @Test
    void debeRetornar201CuandoPublicacionEsExitosa() {
        // Arrange
        Long idGrupo = 1L;
        String texto = "Este es un texto válido";
        PublicacionRequest request = new PublicacionRequest();
        request.setTipo("texto");
        request.setTexto(texto);

        when(usuarioGrupoRepo.existsByIdGrupoAndIdUsuario(idGrupo, 2)).thenReturn(true);
        when(publicacionRepo.invocarFuncionPublicar(2, idGrupo, texto, "texto", ""))
                .thenReturn("Publicación creada");

        // Act
        ResponseEntity<?> response = controller.crearDesdeJson(idGrupo, request);

        // Assert
        assertEquals(201, response.getStatusCodeValue());
        assertEquals("Publicación creada", response.getBody());
    }

    @Test
    void debeRetornar409CuandoHayErrorYExtraeMensaje() {
        // Arrange
        Long idGrupo = 1L;
        PublicacionRequest request = new PublicacionRequest();
        request.setTipo("texto");
        request.setTexto("Algo");

        String mensajeError = "[ERROR: Fallo en la base de datos\nDetalles aquí...]";

        when(usuarioGrupoRepo.existsByIdGrupoAndIdUsuario(idGrupo, 2)).thenReturn(true);
        when(publicacionRepo.invocarFuncionPublicar(anyInt(), anyLong(), anyString(), anyString(), anyString()))
                .thenThrow(new RuntimeException(mensajeError));

        // Act
        ResponseEntity<?> response = controller.crearDesdeJson(idGrupo, request);

        // Assert
        assertEquals(409, response.getStatusCodeValue());
        assertEquals("Fallo en la base de datos", response.getBody());
    }
}
