package com.vigilancia.maestria.Comun;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.ByteArrayOutputStream;

public class Utilidades {

    public static String bitmapToBase64(Bitmap bitmap) {
        // Crea un OutputStream en memoria
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        // Comprime el bitmap al formato que desees (JPEG, PNG, etc.) y guarda en el OutputStream
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);

        // Convierte el OutputStream a un arreglo de bytes
        byte[] byteArray = byteArrayOutputStream.toByteArray();

        // Convierte el arreglo de bytes a una cadena Base64
        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }

    public static Bitmap base64ToBitmap(String base64String) {
        // Decodifica la cadena Base64 a un arreglo de bytes
        byte[] decodedBytes = Base64.decode(base64String, Base64.DEFAULT);

        // Convierte el arreglo de bytes en un Bitmap
        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
    }
}
