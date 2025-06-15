package com.fabricaescuela.back.repository;

import com.fabricaescuela.back.model.Publicacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PublicacionRepositorio extends JpaRepository<Publicacion, Long> {
    // List<Publicacion> findByIdGrupoOrderByFechaPublicacionDesc(Long idGrupo);

    @Query(value = "SELECT public.fn_publicar_contenido(CAST(:idUsuario AS integer), CAST(:idGrupo AS integer), :texto, :tipo, :url)", nativeQuery = true)
    String invocarFuncionPublicar(
            @Param("idUsuario") int idUsuario,
            @Param("idGrupo") Long idGrupo,
            @Param("texto") String texto,
            @Param("tipo") String tipo,
            @Param("url") String urlMultimedia);

}