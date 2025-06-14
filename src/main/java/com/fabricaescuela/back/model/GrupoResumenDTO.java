package com.fabricaescuela.back.model;

public class GrupoResumenDTO {
    private String nombre;
    private String descripcion;
    private String tema;
    private Long cantidadMiembros;

    // Getters y Setters
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getTema() {
        return tema;
    }

    public void setTema(String tema) {
        this.tema = tema;
    }

    public Long getCantidadMiembros() {
        return cantidadMiembros;
    }

    public void setCantidadMiembros(Long cantidadMiembros) {
        this.cantidadMiembros = cantidadMiembros;
    }
}