package com.fabricaescuela.back.controller;

import com.fabricaescuela.back.repository.UsuarioGrupoRepositorio;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UsuarioGrupoControllerTest {

    private UsuarioGrupoRepositorio usuarioGrupoRepo;
    private EntityManager entityManager;
    private UsuarioGrupoController controller;

    @BeforeEach
    void setUp() {
        usuarioGrupoRepo = mock(UsuarioGrupoRepositorio.class);
        entityManager = mock(EntityManager.class);
        controller = new UsuarioGrupoController(usuarioGrupoRepo);
        controller.entityManager = entityManager; // Inyectamos manualmente
    }

    @Test
    void unirseAGrupo_debeRetornarOkConResultado() {
        // Arrange
        Long idGrupo = 5L;
        Query query = mock(Query.class);
        when(entityManager.createNativeQuery(anyString())).thenReturn(query);
        when(query.setParameter(eq("idUsuario"), anyInt())).thenReturn(query);
        when(query.setParameter(eq("idGrupo"), anyInt())).thenReturn(query);
        when(query.getSingleResult()).thenReturn("OK");

        // Act
        ResponseEntity<?> response = controller.unirseAGrupo(idGrupo);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("OK", response.getBody());
        verify(entityManager).createNativeQuery(contains("fn_unirse_a_grupo_publico"));
    }

    @Test
    void unirseAGrupo_conErrorDebeRetornarConflict() {
        // Arrange
        Long idGrupo = 5L;
        when(entityManager.createNativeQuery(anyString()))
                .thenThrow(new RuntimeException("[ERROR: No puedes unirte]"));

        // Act
        ResponseEntity<?> response = controller.unirseAGrupo(idGrupo);

        // Assert
        assertEquals(409, response.getStatusCodeValue());
        assertTrue(response.getBody().toString().contains("No puedes unirte"));
    }

    @Test
    void salirDeGrupo_debeRetornarOkConMensaje() {
        // Arrange
        Long idGrupo = 7L;
        Query query = mock(Query.class);
        when(entityManager.createNativeQuery(anyString())).thenReturn(query);
        when(query.setParameter(eq("idUsuario"), anyInt())).thenReturn(query);
        when(query.setParameter(eq("idGrupo"), anyInt())).thenReturn(query);
        when(query.setParameter(eq("motivo"), anyString())).thenReturn(query);
        when(query.getSingleResult()).thenReturn(null); // Ignorado en este caso

        // Act
        ResponseEntity<?> response = controller.salirDeGrupo(idGrupo);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Has salido del grupo exitosamente.", response.getBody());
        verify(entityManager).createNativeQuery(contains("fn_salir_de_grupo"));
    }

    @Test
    void salirDeGrupo_conErrorDebeRetornarConflict() {
        // Arrange
        Long idGrupo = 10L;
        when(entityManager.createNativeQuery(anyString()))
                .thenThrow(new RuntimeException("[ERROR: Ya no perteneces al grupo]"));

        // Act
        ResponseEntity<?> response = controller.salirDeGrupo(idGrupo);

        // Assert
        assertEquals(409, response.getStatusCodeValue());
        assertTrue(response.getBody().toString().contains("Ya no perteneces al grupo"));
    }
}
