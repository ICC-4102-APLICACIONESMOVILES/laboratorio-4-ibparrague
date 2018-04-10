package com.example.ignap.lab_2;

import android.app.Fragment;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import static android.os.Build.VERSION_CODES.M;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;

    private static final String DATABASE_NAME = "form_db";
    private FormDatabase formDatabase;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        formDatabase = Room.databaseBuilder(getApplicationContext(),FormDatabase.class, DATABASE_NAME).build();

        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        TextView conectadoTV = (TextView) findViewById(R.id.conectadoTextView);
        String usuario_conectado = sharedPref.getString("usuario", null);

        conectadoTV.setText(usuario_conectado);


        Button buttonLogInMain = (Button) findViewById(R.id.LogInToLogInButton);
        Button buttonLogOut = (Button) findViewById(R.id.logOutButton);

        if (usuario_conectado == null){
            conectadoTV.setText("No hay usuario");
        }
        else{
            buttonLogInMain.setVisibility(View.INVISIBLE);
            buttonLogOut.setVisibility(View.VISIBLE);
        }



        Bundle extras = getIntent().getExtras();
        if (extras != null){
            usuario_conectado = extras.getString("usuario_c");
            conectadoTV.setText(usuario_conectado);

            buttonLogInMain.setVisibility(View.INVISIBLE);
            buttonLogOut.setVisibility(View.VISIBLE);

        }

        editor.putString("usuario",usuario_conectado);
        editor.commit();

        getSupportFragmentManager();


        mDrawerLayout = findViewById(R.id.drawer_layout);


        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {

                        final FragmentManager fragmentManager = getSupportFragmentManager();
                        final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();


                        TextView conectado = (TextView) findViewById(R.id.conectadoTextView);
                        Button buttonLogInMain = (Button) findViewById(R.id.LogInToLogInButton);
                        Button buttonLogOut = (Button) findViewById(R.id.logOutButton);

                        conectado.setVisibility(View.INVISIBLE);
                        buttonLogInMain.setVisibility(View.INVISIBLE);
                        buttonLogOut.setVisibility(View.INVISIBLE);

                        int id = menuItem.getItemId();
                        // set item as selected to persist highlight
                        menuItem.setChecked(true);


                        // close drawer when item is tapped
                        mDrawerLayout.closeDrawers();
                        // Add code here to update the UI based on the item selected
                        // For example, swap UI fragments here
                        if (id == R.id.nav_first){
                            fragmentTransaction.replace(R.id.content_frame, new FormularioFragment());
                            fragmentTransaction.commit();
                        }
                        else if (id == R.id.nav_second){
                            fragmentTransaction.replace(R.id.content_frame, new ResumenFragment());
                            fragmentTransaction.commit();
                        }
                        else if (id == R.id.nav_third) {
                            fragmentTransaction.replace(R.id.content_frame, new NewFormularioFragment());
                            fragmentTransaction.commit();
                        }
                        else if (id == R.id.nav_pre_first){
                            fragmentTransaction.replace(R.id.content_frame, new EmptyFragment());
                            fragmentTransaction.commit();
                            if (conectado.getText() == "No hay usuario" || conectado.getText() ==  "desconectado, ingrese otro usuario"){
                                conectado.setVisibility(View.VISIBLE);
                                buttonLogInMain.setVisibility(View.VISIBLE);
                                buttonLogOut.setVisibility(View.INVISIBLE);
                            }
                            else{
                                conectado.setVisibility(View.VISIBLE);
                                buttonLogInMain.setVisibility(View.INVISIBLE);
                                buttonLogOut.setVisibility(View.VISIBLE);
                            }
                        }


                        return true;
                    }
                });
    }

    public void cambiar_actividad(View view){
        Intent intent1 = new Intent(this, LogInActivity.class);
        startActivity(intent1);
    }

    public void ClickLogOut(View view){
        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("usuario", null);
        editor.commit();

        TextView conectado = (TextView) findViewById(R.id.conectadoTextView);
        conectado.setText("desconectado, ingrese otro usuario");

        Button buttonLogInMain = (Button) findViewById(R.id.LogInToLogInButton);
        Button buttonLogOut = (Button) findViewById(R.id.logOutButton);


        buttonLogInMain.setVisibility(View.VISIBLE);
        buttonLogOut.setVisibility(View.INVISIBLE);


    }

    public void AceptarNewForm(View view){

    //    Form forma = formDatabase.daoAccess().fetchHighestId();
        Toast.makeText(MainActivity.this, "ok", Toast.LENGTH_SHORT).show();

        new Thread(new Runnable() {
            @Override
            public void run() {
                TextView nombretv = (TextView) findViewById(R.id.editTextNombreForm);
                TextView datetv = (TextView) findViewById(R.id.editTextDateNuevoForm);
                TextView comentariotv = (TextView) findViewById(R.id.editTextComentarioForm);



                Form form =new Form();
                form.setFormId(nombretv.getText().toString());
                form.setFormName(nombretv.getText().toString());
                form.setFormDate(datetv.getText().toString());
                form.setFormCategory("default");
                form.setFormComment(comentariotv.getText().toString());

                formDatabase.daoAccess().insertOnlySingleForm (form);
            }
        }).start();


    }
}

