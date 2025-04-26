package com.vigilancia.maestria.Bd;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import android.content.Context;

@Database(entities = {Notificacion.class}, version = 1)
@TypeConverters({Converters.class}) // Añadir la anotación para los convertidores
public abstract class NotificacionesDatabase extends RoomDatabase {

    public abstract NotificacionDao notificacionDao();

    private static volatile NotificacionesDatabase INSTANCE;

    public static NotificacionesDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (NotificacionesDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    NotificacionesDatabase.class, "notificaciones_db")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}


