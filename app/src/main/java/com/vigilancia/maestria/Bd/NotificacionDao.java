package com.vigilancia.maestria.Bd;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface NotificacionDao {

    @Insert
    void insertarNotificacion(Notificacion notificacion);

    @Query("SELECT * FROM notificaciones order by fecha desc")
    List<Notificacion> obtenerTodasLasNotificaciones();

    @Query("DELETE FROM notificaciones WHERE id = :notificacionId")
    void eliminarNotificacion(int notificacionId);
}

