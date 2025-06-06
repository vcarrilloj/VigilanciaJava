package com.vigilancia.maestria.Json;

public class ModoOperacion {

    private int id;
    private String nombre;
    private String descripcion;
    private boolean seleccionado;

    public ModoOperacion(int id, String nombre, String descripcion, boolean seleccionado) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.seleccionado = seleccionado;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public boolean isSeleccionado() {
        return seleccionado;
    }

    public void setSeleccionado(boolean seleccionado) {
        this.seleccionado = seleccionado;
    }
}
