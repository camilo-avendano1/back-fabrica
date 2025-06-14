package com.fabricaescuela.back.controller;

import com.fabricaescuela.back.model.Grupo;
import com.fabricaescuela.back.model.GrupoResumenDTO;
import com.fabricaescuela.back.repository.GrupoRepositorio;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/grupos")
public class GrupoControlador {

    private final GrupoRepositorio grupoRepositorio;

    public GrupoControlador(GrupoRepositorio grupoRepositorio) {
        this.grupoRepositorio = grupoRepositorio;
    }

    // 📌 Endpoint base: devuelve todos los grupos sin filtros
    @GetMapping("/todos")
    public List<Grupo> obtenerTodosLosGrupos() {
        return grupoRepositorio.obtenerTodosLosGrupos();
    }

    // 🔍 Endpoint filtrado: búsqueda por nombre, descripción, tema y paginación
    @GetMapping
    public ResponseEntity<List<GrupoResumenDTO>> buscarGrupos(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String tema,
            @RequestParam(defaultValue = "10") int limit,
            @RequestParam(defaultValue = "0") int offset) {

        List<Object[]> resultados = grupoRepositorio.buscarGruposConFiltro(search, tema, limit, offset);

        List<GrupoResumenDTO> grupos = resultados.stream().map(obj -> {
            GrupoResumenDTO dto = new GrupoResumenDTO();
            dto.setNombre((String) obj[0]);
            dto.setDescripcion((String) obj[1]);
            dto.setTema((String) obj[2]);
            dto.setCantidadMiembros(((Number) obj[3]).longValue());
            return dto;
        }).toList();

        return ResponseEntity.ok(grupos);
    }
}