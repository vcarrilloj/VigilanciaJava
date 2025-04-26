package com.vigilancia.maestria.Comun;

import android.content.Context;
import android.content.SharedPreferences;
import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKey;

public class SecureStorageUtil {

    private SharedPreferences encryptedSharedPreferences;

    public SecureStorageUtil(Context context) throws Exception {
        // Crear una clave maestra para cifrar los datos
        MasterKey masterKey = new MasterKey.Builder(context)
                .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
                .build();

        // Inicializar EncryptedSharedPreferences
        encryptedSharedPreferences = EncryptedSharedPreferences.create(
                context,
                "secure_prefs", // Nombre del archivo de SharedPreferences
                masterKey,
                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        );
    }

    // Método para guardar un valor seguro
    public void saveSecureData(String key, String value) {
        SharedPreferences.Editor editor = encryptedSharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    // Método para obtener un valor seguro
    public String getSecureData(String key) {
        return encryptedSharedPreferences.getString(key, null);
    }
}

