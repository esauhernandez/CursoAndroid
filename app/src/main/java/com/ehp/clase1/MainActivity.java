package com.ehp.clase1;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.ehp.clase1.Model.Usuario;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;

import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    private TextInputLayout til_email;
    private TextInputLayout til_pass;
    private TextInputEditText tiet_email;
    private TextInputEditText tiet_pass;
    private TextView registro;

    private String TAG = "MainActivity";
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;

    //https://developers.facebook.com/docs/facebook-login/
    private CallbackManager callbackManager;
    private LoginButton login_facebook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        til_email = (TextInputLayout) findViewById(R.id.til_email);
        til_pass = (TextInputLayout) findViewById(R.id.til_pass);
        tiet_email = (TextInputEditText) findViewById(R.id.tiet_email);
        tiet_pass = (TextInputEditText) findViewById(R.id.tiet_pass);
        registro = (TextView) findViewById(R.id.crea_cuenta);
        login_facebook = (LoginButton) findViewById(R.id.login_button);

        firebaseAuth = FirebaseAuth.getInstance();

        FacebookSdk.sdkInitialize(getApplicationContext());
        login_facebook.setReadPermissions("email");
        callbackManager = CallbackManager.Factory.create();

        login_facebook.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                sigInFacebbokFirebase(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });

    }

    private void sigInFacebbokFirebase(AccessToken accessToken){
        AuthCredential authCredential = FacebookAuthProvider.getCredential(accessToken.getToken());
        firebaseAuth.signInWithCredential(authCredential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    FirebaseUser firebaseUser = task.getResult().getUser();
                    Log.w(TAG, firebaseUser.getUid().toString());
                    Intent intent = new Intent(MainActivity.this, NavigationActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // <- Aquí :)
                    startActivity(intent);
                    finish();
                    Toast.makeText(MainActivity.this, "Acceso correcto", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(MainActivity.this, "No se encontró el usuario", Toast.LENGTH_SHORT).show();
                    task.getException().printStackTrace();
                }
            }
        });
    }
    
    public void login(View view){
        String email = tiet_email.getText().toString();
        String pass = tiet_pass.getText().toString();

        if(email.equals("") || email.isEmpty()){
            til_email.setError("Email requerido");
        }else if(pass.equals("") || pass.isEmpty()){
            til_pass.setError("Introduce contraseña");
        }else{
            loginFirebase(email, pass);
        }
    }

    public void muestra_mensaje(String mensaje){
        Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show();
    }

    public void crearCuenta(View v){
        startActivity(new Intent(this, RegistroActivity.class));
    }

    public void loginFirebase(final String email, final String pass){
        firebaseAuth.signInWithEmailAndPassword(email, pass).
            addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        FirebaseUser firebaseUser = task.getResult().getUser();
                        Log.w(TAG, firebaseUser.getUid().toString());
                        Intent intent = new Intent(MainActivity.this, NavigationActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // <- Aquí :)
                        startActivity(intent);
                        finish();
                        Toast.makeText(MainActivity.this, "Acceso correcto", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(MainActivity.this, "No se encontró el usuario", Toast.LENGTH_SHORT).show();
                        task.getException().printStackTrace();
                        Log.w(TAG, email + " " + pass);
                    }
                }
            });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

}
