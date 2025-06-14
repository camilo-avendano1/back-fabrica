package com.fabricaescuela.back.repository;

import com.fabricaescuela.back.model.UsuarioGrupo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioGrupoRepositorio extends JpaRepository<UsuarioGrupo, Long> {
    boolean existsByIdGrupoAndIdUsuario(Long idGrupo, Long idUsuario);
}