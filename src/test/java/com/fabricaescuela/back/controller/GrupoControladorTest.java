package com.fabricaescuela.back.controller;

import com.fabricaescuela.back.model.Grupo;
import com.fabricaescuela.back.model.GrupoResumenDTO;
import com.fabricaescuela.back.repository.GrupoRepositorio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GrupoControladorTest {

    private GrupoRepositorio grupoRepositorio;
    private GrupoControlador grupoControlador;

    @BeforeEach
    void setUp() {
        grupoRepositorio = mock(GrupoRepositorio.class);
        grupoControlador = new GrupoControlador(grupoRepositorio);
    }

    @Test
    void obtenerTodosLosGrupos_debeRetornarListaCompleta() {
        // Arrange
        Grupo grupo1 = new Grupo();
        grupo1.setNombre("Grupo 1");

        Grupo grupo2 = new Grupo();
        grupo2.setNombre("Grupo 2");

        List<Grupo> gruposEsperados = List.of(grupo1, grupo2);
        when(grupoRepositorio.obtenerTodosLosGrupos()).thenReturn(gruposEsperados);

        // Act
        List<Grupo> resultado = grupoControlador.obtenerTodosLosGrupos();

        // Assert
        assertEquals(2, resultado.size());
        assertEquals("Grupo 1", resultado.get(0).getNombre());
        assertEquals("Grupo 2", resultado.get(1).getNombre());
        verify(grupoRepositorio, times(1)).obtenerTodosLosGrupos();
    }

    @Test
    void buscarGrupos_conFiltros_debeRetornarResumenesEsperados() {
        // Arrange
        Object[] fila1 = new Object[]{"Grupo A", "Desc A", "Tema A", 5L};
        Object[] fila2 = new Object[]{"Grupo B", "Desc B", "Tema B", 10L};

        when(grupoRepositorio.buscarGruposConFiltro("busqueda", "temaX", 10, 0))
                .thenReturn(Arrays.asList(fila1, fila2));

        // Act
        ResponseEntity<List<GrupoResumenDTO>> respuesta = grupoControlador.buscarGrupos("busqueda", "temaX", 10, 0);
        List<GrupoResumenDTO> resultado = respuesta.getBody();

        // Assert
        assertNotNull(resultado);
        assertEquals(2, resultado.size());

        GrupoResumenDTO dto1 = resultado.get(0);
        assertEquals("Grupo A", dto1.getNombre());
        assertEquals("Desc A", dto1.getDescripcion());
        assertEquals("Tema A", dto1.getTema());
        assertEquals(5L, dto1.getCantidadMiembros());

        GrupoResumenDTO dto2 = resultado.get(1);
        assertEquals("Grupo B", dto2.getNombre());
        assertEquals("Desc B", dto2.getDescripcion());
        assertEquals("Tema B", dto2.getTema());
        assertEquals(10L, dto2.getCantidadMiembros());

        verify(grupoRepositorio, times(1)).buscarGruposConFiltro("busqueda", "temaX", 10, 0);
    }
}
