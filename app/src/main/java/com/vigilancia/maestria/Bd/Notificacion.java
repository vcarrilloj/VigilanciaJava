package com.vigilancia.maestria.Bd;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "notificaciones")
public class Notificacion {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private Date fecha; // Cambia el tipo a Date
    private String titulo;
    private String mensaje;
    private String imagen;

    public Notificacion(Date fecha, String titulo, String mensaje, String imagen) {
        this.fecha = fecha;
        this.titulo = titulo;
        this.mensaje = mensaje;
        this.imagen = imagen;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }
}

