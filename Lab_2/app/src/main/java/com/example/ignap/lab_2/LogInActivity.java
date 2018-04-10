package com.example.ignap.lab_2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class LogInActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);


    }

    public void Click_Log_in(View view){
        EditText user = (EditText) findViewById(R.id.userEditText);
        EditText pass = (EditText) findViewById(R.id.passEditText);
        String usuario = user.getText().toString();
        if (usuario.contains("@") && usuario.contains(".")){
            Toast tostada = Toast.makeText(getApplicationContext(), "Aprovado",Toast.LENGTH_LONG);
            tostada.show();
            Intent i = new Intent(LogInActivity.this, MainActivity.class);
            i.putExtra("usuario_c", usuario);
            startActivity(i);
        }
        else {
            Toast tostada = Toast.makeText(getApplicationContext(), "Email Erroneo", Toast.LENGTH_LONG);
            tostada.show();
        }


    }
}
