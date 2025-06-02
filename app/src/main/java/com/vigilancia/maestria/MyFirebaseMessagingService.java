package com.vigilancia.maestria;

import static com.vigilancia.maestria.Comun.Utilidades.bitmapToBase64;
import static com.vigilancia.maestria.Globales.App.pendingIntentMap;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.vigilancia.maestria.Bd.Notificacion;
import com.vigilancia.maestria.Bd.NotificacionesDatabase;
import com.vigilancia.maestria.Comun.SecureStorageUtil;

import java.net.URL;
import java.util.Date;
import java.net.HttpURLConnection;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private NotificacionesDatabase db;

    @Override
    public void onNewToken(@NonNull String token) {
        super.onNewToken(token);
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        if (!remoteMessage.getData().isEmpty()) {
            try {
                sendNotification(remoteMessage);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void sendNotification(RemoteMessage remoteMessage) {
        String title = remoteMessage.getData().get("titulo");
        String message = remoteMessage.getData().get("mensaje");
        String nombreFoto = remoteMessage.getData().get("foto");

        if (title != null && title.equals("URL")) {
            SecureStorageUtil storage;
            try {
                storage = new SecureStorageUtil(this);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            storage.saveSecureData("UrlBase", message);

            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(message));

            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "default").setSmallIcon(R.drawable.icononotificacion).setContentTitle("Actualización").setContentText("Se actualizó la información de conexión.").setPriority(NotificationCompat.PRIORITY_HIGH).setContentIntent(pendingIntent).setAutoCancel(true);

            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(0, builder.build());

        } else {
            Bitmap imageBitmap = getBitmapFromUrl(nombreFoto);
            String base64 = imageBitmap != null ? bitmapToBase64(imageBitmap) : "";

            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("irNotificaciones", true);
            long requestCode = new Date().getTime();
            intent.putExtra("idIntent", requestCode);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT | PendingIntent.FLAG_IMMUTABLE);
            pendingIntentMap.put(requestCode, pendingIntent);

            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, "default").setSmallIcon(R.drawable.icononotificacion).setContentTitle(title).setContentText(message).setLargeIcon(imageBitmap) // Mostrar la imagen como ícono grande
                    .setStyle(new NotificationCompat.BigPictureStyle().bigPicture(imageBitmap)) // Mostrar imagen grande en notificación
                    .setPriority(NotificationCompat.PRIORITY_HIGH).setContentIntent(pendingIntent).setAutoCancel(true);

            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(0, notificationBuilder.build());

            db = NotificacionesDatabase.getDatabase(this);

            new Thread(() -> {
                Notificacion notificacion = new Notificacion(new Date(), title, message, base64);
                db.notificacionDao().insertarNotificacion(notificacion);
                Log.d("MiFirebaseService", "Notificación guardada en la base de datos");
            }).start();
        }
    }

    private Bitmap getBitmapFromUrl(String nombreFoto) {
        try {
            SecureStorageUtil storage = new SecureStorageUtil(this);
            URL url = new URL(storage.getSecureData("UrlBase") + "/Capturas/" + nombreFoto);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("GET");
            connection.setRequestProperty("access_token", storage.getSecureData("access_token")); // Si usa Bearer Token
            connection.setRequestProperty("Accept", "application/json");

            return BitmapFactory.decodeStream(connection.getInputStream());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
