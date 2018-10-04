package com.ehp.clase1;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.ehp.clase1.ConfigDatabase.ConfigDatabase;
import com.ehp.clase1.ConfigDatabase.UserDB;
import com.ehp.clase1.Model.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.gson.JsonObject;

import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistroActivity extends AppCompatActivity {

    private static final String TAG = "RegistroActivity";
    private TextInputEditText tilEmail, tilName, tilUser, tilPass;
    private Button registro;
    private Usuario usuario;
    private boolean userLogged = false;
    private FirebaseUser firebaseUser;

    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        getSupportActionBar().setTitle("Registro");

        tilEmail = (TextInputEditText) findViewById(R.id.email);
        tilName = (TextInputEditText) findViewById(R.id.name);
        tilUser = (TextInputEditText) findViewById(R.id.user);
        tilPass = (TextInputEditText) findViewById(R.id.password_createaccount);
        registro = (Button) findViewById(R.id.joinUs);
        
        firebaseAuth = FirebaseAuth.getInstance();

    }


    public void registroAuth(View V){
        usuario = new Usuario();
        usuario.setUsername(UUID.randomUUID().toString());
        usuario.setEmail(tilEmail.getText().toString());
        usuario.setName(tilName.getText().toString());
        usuario.setUsername(tilUser.getText().toString());
        usuario.setPass(tilPass.getText().toString());

        userLogged = true;
        
        firebaseAuth.createUserWithEmailAndPassword(usuario.getEmail(), usuario.getPass()).
                addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(RegistroActivity.this, "Usuario registrado exitosamente", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(RegistroActivity.this, NavigationActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // <- Aquí :)
                            finish();
                            startActivity(intent);
                        }
                        else{
                            Toast.makeText(RegistroActivity.this, "Ocurrio un error", Toast.LENGTH_SHORT).show();
                            task.getException().printStackTrace();
                            Log.w(TAG, usuario.getEmail() + " " + usuario.getPass());
                            userLogged = false;
                        }
                    }
                });
        
    }



    public void registroDB(Usuario usuario){
        ConfigDatabase configDatabase = new ConfigDatabase();
        UserDB userDB = (UserDB) configDatabase.getFirebaseService(this);
        Call<JsonObject> call = userDB.createUser(usuario);

        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                Log.w(TAG, "RESPONSE ~ " +  response);
                Log.w(TAG, "RESPONSE ~ " +  response.body());
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.e(TAG, t.toString());
                t.printStackTrace();
            }
        });
    }
}
