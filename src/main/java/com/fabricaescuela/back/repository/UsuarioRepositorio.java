package com.fabricaescuela.back.repository;

import com.fabricaescuela.back.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepositorio extends JpaRepository<Usuario, Long> {
}