package com.fabricaescuela.back.controller;

import com.fabricaescuela.back.model.PublicacionRequest;
import com.fabricaescuela.back.repository.PublicacionRepositorio;
import com.fabricaescuela.back.repository.UsuarioGrupoRepositorio;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<Object> crearDesdeJson(
            @PathVariable("id") Long idGrupo,
            @RequestBody PublicacionRequest request) {

        int idUsuario = 2; // Simulación de usuario fijo

        if (!usuarioPerteneceAlGrupo(idGrupo, idUsuario)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("El usuario no pertenece al grupo");
        }

        String tipo = request.getTipo() != null ? request.getTipo().toLowerCase() : "";

        if (!esTipoValido(tipo)) {
            return ResponseEntity.badRequest()
                    .body("Tipo de contenido no válido. Usa: texto, multimedia o enlace");
        }

        if (!esContenidoValido(tipo, request)) {
            return ResponseEntity.badRequest()
                    .body("Contenido inválido: para 'texto' se requiere texto; para 'enlace' o 'multimedia' se requiere url "
                            + request.getTexto() + request.getTipo());
        }

        try {
            String urlFinal = tipo.equals("texto") ? "" : request.getUrl();
            String resultado = publicacionRepo.invocarFuncionPublicar(
                    idUsuario,
                    idGrupo,
                    request.getTexto() != null ? request.getTexto() : "",
                    tipo,
                    urlFinal
            );

            return ResponseEntity.status(HttpStatus.CREATED).body(resultado);

        } catch (Exception e) {
            String mensaje = extraerMensajeError(e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(mensaje);
        }
    }

    private boolean usuarioPerteneceAlGrupo(Long idGrupo, int idUsuario) {
        return usuarioGrupoRepo.existsByIdGrupoAndIdUsuario(idGrupo, idUsuario);
    }

    private boolean esTipoValido(String tipo) {
        return tipo.equals("texto") || tipo.equals("multimedia") || tipo.equals("enlace");
    }

    private boolean esContenidoValido(String tipo, PublicacionRequest request) {
        return switch (tipo) {
            case "texto" -> request.getTexto() != null && !request.getTexto().isBlank();
            case "multimedia", "enlace" -> request.getUrl() != null && !request.getUrl().isBlank();
            default -> false;
        };
    }

    private String extraerMensajeError(String mensaje) {
        if (mensaje == null) return "Error desconocido";

        int inicio = mensaje.indexOf("[ERROR:");
        if (inicio != -1) {
            mensaje = mensaje.substring(inicio + 7).trim();
            int saltoLinea = mensaje.indexOf("\n");
            if (saltoLinea != -1) {
                mensaje = mensaje.substring(0, saltoLinea).trim();
            }
        }
        return mensaje;
    }
}
