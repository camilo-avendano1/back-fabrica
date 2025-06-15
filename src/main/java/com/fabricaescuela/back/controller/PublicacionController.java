package com.fabricaescuela.back.controller;

import com.fabricaescuela.back.model.PublicacionRequest;
import com.fabricaescuela.back.repository.PublicacionRepositorio;
import com.fabricaescuela.back.repository.UsuarioGrupoRepositorio;

import java.util.Random;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

        // int idUsuario = new Random().nextInt(14) + 1; // genera de 1 a 14 inclusive
        // // Simulación de usuario fijo
        int idUsuario = 2;  // Simulación de usuario fijo

        if (!usuarioGrupoRepo.existsByIdGrupoAndIdUsuario(idGrupo, idUsuario)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("El usuario no pertenece al grupo");
        }

        String tipo = request.getTipo() != null ? request.getTipo().toLowerCase() : "";

        boolean contenidoValido = true;

        switch (tipo) {
            case "texto":
                contenidoValido = request.getTexto() != null && !request.getTexto().isBlank();
                break;
            case "multimedia":
                contenidoValido = request.getUrl() != null && !request.getUrl().isBlank();
                break;
            case "enlace":
                contenidoValido = request.getUrl() != null && !request.getUrl().isBlank();
                break;
            default:
                return ResponseEntity.badRequest()
                        .body("Tipo de contenido no válido. Usa: texto, multimedia o enlace");
        }

        if (!contenidoValido) {
            return ResponseEntity.badRequest()
                    .body("Contenido inválido: para 'texto' se requiere texto; para 'enlace' o 'multimedia' se requiere url " + request.getTexto() + request.getTipo());
        }

        try {
            String urlFinal = tipo.equals("texto") ? "" : request.getUrl();

            String resultado = publicacionRepo.invocarFuncionPublicar(
                    idUsuario,
                    idGrupo,
                    request.getTexto() != null ? request.getTexto() : "",
                    tipo,
                    urlFinal);

            return ResponseEntity.status(HttpStatus.CREATED).body(resultado);

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