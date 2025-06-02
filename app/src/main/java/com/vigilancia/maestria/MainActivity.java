package com.vigilancia.maestria;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.firebase.messaging.FirebaseMessaging;

import com.vigilancia.maestria.Comun.SecureStorageUtil;
import com.vigilancia.maestria.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SecureStorageUtil storage;
        try {
            storage = new SecureStorageUtil(this);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        storage.saveSecureData("access_token", "[8N(n5eH7rjVEW6kzad]*,yd[f302CtTJYg{BF6nJRzZ}r:fiHfp=5FWUHH&2t/a*D-&QM$d]EM$1VP}.q-JE@RFB/npn#h0WS+B");

        com.vigilancia.maestria.databinding.ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        CrearCanalNotificacines();
        RegistrarDispositivo();

        setSupportActionBar(binding.toolbar);

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
    }

    private void CrearCanalNotificacines() {
        // El nombre del canal visible para el usuario
        CharSequence name = "Notificaciones Generales";
        // La descripción del canal
        String description = "Canal para notificaciones generales";
        // La importancia del canal
        int importance = NotificationManager.IMPORTANCE_HIGH;

        NotificationChannel channel = new NotificationChannel("default", name, importance);
        channel.setDescription(description);

        // Registrar el canal en el sistema
        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void RegistrarDispositivo() {
        FirebaseMessaging.getInstance().subscribeToTopic("CanalVigilancia")
                .addOnCompleteListener(task -> {
                    String msg = "Suscripción exitosa";
                    if (!task.isSuccessful()) {
                        msg = "Fallo en la suscripción";
                    }
                    Log.d("FCM", msg);
                    Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

            SecureStorageUtil storage;
            try {
                storage = new SecureStorageUtil(MainActivity.this);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            storage.saveSecureData("UsuarioAut", null);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }
}