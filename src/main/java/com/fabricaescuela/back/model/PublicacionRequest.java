package com.fabricaescuela.back.model;

public class PublicacionRequest {
    private String contenidoTexto;
    private String url;
    private String archivoImagen; // Aquí puedes enviar una cadena base64 si luego vas a manejar imágenes

    // Getters y Setters
    public String getContenidoTexto() { return contenidoTexto; }
    public void setContenidoTexto(String contenidoTexto) { this.contenidoTexto = contenidoTexto; }

    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }

    public String getArchivoImagen() { return archivoImagen; }
    public void setArchivoImagen(String archivoImagen) { this.archivoImagen = archivoImagen; }
}