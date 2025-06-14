package com.fabricaescuela.back.controller;

import com.fabricaescuela.back.model.Publicacion;
import com.fabricaescuela.back.model.PublicacionRequest;
import com.fabricaescuela.back.repository.PublicacionRepositorio;
import com.fabricaescuela.back.repository.UsuarioGrupoRepositorio;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/grupos")
public class PublicacionController {

    private final PublicacionRepositorio publicacionRepo;
    private final UsuarioGrupoRepositorio usuarioGrupoRepo;

    public PublicacionController(PublicacionRepositorio publicacionRepo, UsuarioGrupoRepositorio usuarioGrupoRepo) {
        this.publicacionRepo = publicacionRepo;
        this.usuarioGrupoRepo = usuarioGrupoRepo;
    }

    @PostMapping("/{id}/publicaciones")
    public ResponseEntity<?> crearDesdeJson(
            @PathVariable("id") Long idGrupo,
            @RequestBody PublicacionRequest request) {
        // 👇 Aquí simulas el usuario autenticado
        Long idUsuario = 1L; // Cambia este valor por un id_usuario que ya tengas en tu tabla

        if (!usuarioGrupoRepo.existsByIdGrupoAndIdUsuario(idGrupo, idUsuario)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("El usuario no pertenece al grupo");
        }

        boolean hayContenido = (request.getContenidoTexto() != null && !request.getContenidoTexto().isBlank()) ||
                (request.getUrl() != null && !request.getUrl().isBlank()) ||
                (request.getArchivoImagen() != null && !request.getArchivoImagen().isBlank());

        if (!hayContenido) {
            return ResponseEntity.badRequest()
                    .body("Debes enviar al menos texto, enlace o imagen");
        }

        String tipo = "texto";
        if (request.getArchivoImagen() != null && !request.getArchivoImagen().isBlank())
            tipo = "multimedia";
        else if (request.getUrl() != null && !request.getUrl().isBlank())
            tipo = "enlace";

        Publicacion nueva = new Publicacion();
        nueva.setIdGrupo(idGrupo);
        nueva.setIdUsuario(idUsuario);
        nueva.setTexto(request.getContenidoTexto() != null ? request.getContenidoTexto() : "");
        nueva.setTipoContenido(tipo);
        nueva.setUrlMultimedia(request.getUrl() != null ? request.getUrl() : "");
        nueva.setFechaPublicacion(LocalDateTime.now());
        nueva.setEstado("activo");

        return ResponseEntity.status(HttpStatus.CREATED).body(publicacionRepo.save(nueva));
    }

}