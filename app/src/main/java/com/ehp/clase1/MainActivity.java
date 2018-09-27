package com.ehp.clase1;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private TextInputLayout til_email;
    private TextInputLayout til_pass;
    private TextInputEditText tiet_email;
    private TextInputEditText tiet_pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        til_email = (TextInputLayout) findViewById(R.id.til_email);
        til_pass = (TextInputLayout) findViewById(R.id.til_pass);
        tiet_email = (TextInputEditText) findViewById(R.id.tiet_email);
        tiet_pass = (TextInputEditText) findViewById(R.id.tiet_pass);

    }
    
    public void login(View view){
        String email = tiet_email.getText().toString();
        String pass = tiet_pass.getText().toString();

        if(email.equals("") || email.isEmpty()){
            til_email.setError("Email requerido");
        }else if(pass.equals("") || pass.isEmpty()){
            til_pass.setError("Introduce contraseña");
        }else{
            if(valida_datos(email, pass)){
                Intent intent = new Intent(this, NavigationActivity.class);
                startActivity(intent);
            }
        }
    }

    public boolean valida_datos(String email, String pass){
        if(email.equals("curso@android.com") || pass.equals("147258")){
            return true;
        }else{
            return false;
        }
    }

    public void muestra_mensaje(String mensaje){
        Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show();
    }

}
