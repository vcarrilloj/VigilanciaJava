package com.vigilancia.maestria.Fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.vigilancia.maestria.Comun.SecureStorageUtil;
import com.vigilancia.maestria.MainActivity;
import com.vigilancia.maestria.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class LoginFragment extends Fragment {

    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Context context = view.getContext();
        Button button = view.findViewById(R.id.login);
        EditText usuario = view.findViewById(R.id.username);
        EditText clave = view.findViewById(R.id.password);

        // Configura el click listener del botón
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(clave.getText().toString().length()<8){
                    new AlertDialog.Builder(context)
                            .setCancelable(true)
                            .setTitle("Alerta")
                            .setMessage("La clave ingresada es muy corta.")
                            .setPositiveButton("Cerrar", null)
                            .show();
                    return;
                }

                SecureStorageUtil storage;
                try {
                    storage = new SecureStorageUtil(context);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }

                String url = storage.getSecureData("UrlBase") + "/Usuarios/Login"; // URL de tu API

                StringRequest request = new StringRequest(
                        Request.Method.POST,
                        url,
                        response -> {
                            System.out.println("Petición exitosa");
                            storage.saveSecureData("UsuarioAut", usuario.getText().toString());
                            NavController navController = Navigation.findNavController(view);
                            navController.navigate(R.id.action_login_to_p);
                        },
                        error -> {
                            new AlertDialog.Builder(context)
                                    .setCancelable(true)
                                    .setTitle("Alerta")
                                    .setMessage("Usuario o clave incorrectos")
                                    .setPositiveButton("Cerrar", null)
                                    .show();
                            error.printStackTrace();
                        }
                ) {
                    @Override
                    public java.util.Map<String, String> getHeaders() {
                        java.util.Map<String, String> headers = new java.util.HashMap<>();
                        headers.put("access_token", storage.getSecureData("access_token"));
                        headers.put("Content-Type", "application/json");
                        return headers;
                    }

                    @Override
                    public byte[] getBody() {
                        try {
                            JSONObject jsonBody = new JSONObject();
                            jsonBody.put("usuario", usuario.getText().toString());
                            jsonBody.put("clave", clave.getText().toString());
                            return jsonBody.toString().getBytes("utf-8");
                        } catch (Exception e) {
                            return null;
                        }
                    }

                    @Override
                    public String getBodyContentType() {
                        return "application/json; charset=utf-8";
                    }
                };

                RequestQueue queue = Volley.newRequestQueue(context);
                queue.add(request);

            }
        });

    }
}