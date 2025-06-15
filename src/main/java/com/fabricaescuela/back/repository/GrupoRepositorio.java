package com.fabricaescuela.back.repository;

import com.fabricaescuela.back.model.Grupo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GrupoRepositorio extends JpaRepository<Grupo, Long> {

    boolean existsByIdGrupoAndVisibilidadIgnoreCase(Long idGrupo, String visibilidad);

    @Query(value = "SELECT * FROM tbl_grupos", nativeQuery = true)
    List<Grupo> obtenerTodosLosGrupos();

    @Query(value = """
            SELECT g.nombre,
                   LEFT(g.descripcion, 100) AS descripcion,
                   c.nombre AS tema,
                   (SELECT COUNT(*) FROM tbl_usuarios_por_grupos ug
                    WHERE ug.id_grupo = g.id_grupo AND ug.estado = 'activo') AS cantidad_miembros
            FROM tbl_grupos g
            JOIN tbl_categorias c ON g.id_categoria = c.id_categoria
            WHERE
                (:search IS NULL OR (
                    LOWER(g.nombre) LIKE LOWER(CONCAT('%', :search, '%')) OR
                    LOWER(g.descripcion) LIKE LOWER(CONCAT('%', :search, '%'))
                ))
              AND (:tema IS NULL OR LOWER(c.nombre) = LOWER(:tema))
            ORDER BY g.fecha_creacion DESC
            LIMIT :limit OFFSET :offset
            """, nativeQuery = true)
    List<Object[]> buscarGruposConFiltro(
            @Param("search") String search,
            @Param("tema") String tema,
            @Param("limit") int limit,
            @Param("offset") int offset
    );
}