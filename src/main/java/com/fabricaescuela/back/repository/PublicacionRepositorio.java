package com.fabricaescuela.back.repository;

import com.fabricaescuela.back.model.Publicacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PublicacionRepositorio extends JpaRepository<Publicacion, Long> {
    List<Publicacion> findByIdGrupoOrderByFechaPublicacionDesc(Long idGrupo);
}