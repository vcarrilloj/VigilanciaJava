package com.vigilancia.maestria.Json;

import androidx.annotation.Nullable;

public class Escenarios {

    private int id;
    private String nombre;
    private String descripcion;

    public Escenarios(int id, String nombre, String descripcion) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public int getId() {
        return id;
    }

    public String getIdString() {
        return Integer.toString(id);
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

    @Override
    public boolean equals(@Nullable Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Escenarios escenario = (Escenarios) obj;
        return getIdString().equals(escenario.getIdString());
    }
}