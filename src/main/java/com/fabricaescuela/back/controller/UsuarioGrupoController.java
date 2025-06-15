package com.fabricaescuela.back.controller;

import com.fabricaescuela.back.repository.UsuarioGrupoRepositorio;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/grupos")
public class UsuarioGrupoController {

    private static final Logger logger = LoggerFactory.getLogger(UsuarioGrupoController.class);

    private final UsuarioGrupoRepositorio usuarioGrupoRepo;

    @PersistenceContext
    private EntityManager entityManager;

    public UsuarioGrupoController(UsuarioGrupoRepositorio usuarioGrupoRepo) {
        this.usuarioGrupoRepo = usuarioGrupoRepo;
    }

    // ✅ Unirse a un grupo
    @PostMapping("/{id}/unirse")
    public ResponseEntity<?> unirseAGrupo(@PathVariable("id") Long idGrupo) {
        Long idUsuario = 2L; // Usuario fijo simulado

        try {
            Object resultado = entityManager.createNativeQuery(
                    "SELECT public.fn_unirse_a_grupo_publico(:idUsuario, :idGrupo)")
                    .setParameter("idUsuario", idUsuario.intValue())
                    .setParameter("idGrupo", idGrupo.intValue())
                    .getSingleResult();

            return ResponseEntity.ok(resultado);

        } catch (Exception e) {
            String mensaje = e.getMessage();

            if (mensaje != null) {
                // Extraer la frase exacta después de "[ERROR:" si existe
                int inicio = mensaje.indexOf("[ERROR:");
                if (inicio != -1) {
                    mensaje = mensaje.substring(inicio + 7).trim(); // Salta "[ERROR:"
                    int saltoLinea = mensaje.indexOf("\n");
                    if (saltoLinea != -1) {
                        mensaje = mensaje.substring(0, saltoLinea).trim();
                    }
                }
            }

            return ResponseEntity.status(HttpStatus.CONFLICT).body(mensaje);
        }
    }

    // ✅ Salir de un grupo
    @PostMapping("/{id}/salir")
    public ResponseEntity<?> salirDeGrupo(@PathVariable("id") Long idGrupo) {
        Long idUsuario = 2L; // Usuario fijo simulado


        try {
            entityManager.createNativeQuery("SELECT public.fn_salir_de_grupo(:idUsuario, :idGrupo, :motivo)")
                    .setParameter("idUsuario", idUsuario.intValue())
                    .setParameter("idGrupo", idGrupo.intValue())
                    .setParameter("motivo", "")
                    .getSingleResult();

            logger.info("Usuario {} salió del grupo {}", idUsuario, idGrupo);

            return ResponseEntity.ok("Has salido del grupo exitosamente.");

        } catch (Exception e) {
            String mensaje = e.getMessage();

            if (mensaje != null) {
                // Extraer la frase exacta después de "[ERROR:" si existe
                int inicio = mensaje.indexOf("[ERROR:");
                if (inicio != -1) {
                    mensaje = mensaje.substring(inicio + 7).trim(); // Salta "[ERROR:"
                    int saltoLinea = mensaje.indexOf("\n");
                    if (saltoLinea != -1) {
                        mensaje = mensaje.substring(0, saltoLinea).trim();
                    }
                }
            }

            return ResponseEntity.status(HttpStatus.CONFLICT).body(mensaje);
        }
    }
}