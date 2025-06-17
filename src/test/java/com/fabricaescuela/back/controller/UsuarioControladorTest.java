package com.fabricaescuela.back.controller;

import com.fabricaescuela.back.model.Usuario;
import com.fabricaescuela.back.repository.UsuarioRepositorio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UsuarioControladorTest {

    private UsuarioRepositorio usuarioRepositorio;
    private UsuarioControlador controlador;

    @BeforeEach
    void setUp() {
        usuarioRepositorio = mock(UsuarioRepositorio.class);
        controlador = new UsuarioControlador(usuarioRepositorio);
    }

    @Test
    void listar_debeRetornarListaDeUsuarios() {
        // Arrange
        Usuario u1 = new Usuario();
        u1.setNombre("Juan");

        Usuario u2 = new Usuario();
        u2.setNombre("Ana");

        when(usuarioRepositorio.findAll()).thenReturn(List.of(u1, u2));

        // Act
        List<Usuario> resultado = controlador.listar();

        // Assert
        assertEquals(2, resultado.size());
        assertEquals("Juan", resultado.get(0).getNombre());
        assertEquals("Ana", resultado.get(1).getNombre());
        verify(usuarioRepositorio, times(1)).findAll();
    }

    @Test
    void guardar_debeGuardarYRetornarUsuario() {
        // Arrange
        Usuario entrada = new Usuario();
        entrada.setNombre("Camilo");

        Usuario esperado = new Usuario();
        esperado.setId(1L);
        esperado.setNombre("Camilo");

        when(usuarioRepositorio.save(entrada)).thenReturn(esperado);

        // Act
        Usuario resultado = controlador.guardar(entrada);

        // Assert
        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("Camilo", resultado.getNombre());
        verify(usuarioRepositorio, times(1)).save(entrada);
    }
}
