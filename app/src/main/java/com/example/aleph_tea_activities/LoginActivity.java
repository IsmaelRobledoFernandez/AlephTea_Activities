package com.example.aleph_tea_activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    FirebaseAuth mAuth;
    Button button, button_registration;
    EditText emailETXT, passwordETXT;
    String email, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Instanciar firebase
        mAuth = FirebaseAuth.getInstance();
        super.onCreate(savedInstanceState);
        //Mostrar la vista del login (activity_main)
        setContentView(R.layout.activity_login);
        gestionLogin();
        gestionSignUp();
    }
    // TODO: Sign in with google AUTH

    private void gestionSignUp(){
        emailETXT = (EditText) findViewById(R.id.email);
        passwordETXT = (EditText) findViewById(R.id.password);
        button_registration = (Button) findViewById(R.id.boton_registration);
        // Si el usuario no existe lo crea, si no hace la gestión del login
        button_registration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email = emailETXT.getText().toString();
                password = passwordETXT.getText().toString();
                if(email.length()==0||password.length()==0){
                    Toast.makeText(getApplicationContext(), "Completa los campos", Toast.LENGTH_SHORT).show();
                    Log.d("SIGN IN", "No se han completado las credenciales");
                }else {
                    createAccount(email, password);
                }
            }
        });
    }
    private void gestionLogin(){
        //Obtener id de los elementos del login
        emailETXT = (EditText) findViewById(R.id.email);
        passwordETXT = (EditText) findViewById(R.id.password);
        button = (Button) findViewById(R.id.boton_login);
        //Poner un clickListener en el boton de login con el que obtener los datos del usuario
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email = emailETXT.getText().toString();
                password = passwordETXT.getText().toString();
                if(email.length()==0||password.length()==0){
                    Toast.makeText(getApplicationContext(), "Completa los campos", Toast.LENGTH_SHORT).show();
                    Log.d("SIGN IN", "No se han completado las credenciales");
                }else {
                    loginUser(email, password);
                }
            }
        });
    }
    private void loginUser(String emailUser, String passwordUser) {
        // Gestionamos el signIn con Firebase
        mAuth.signInWithEmailAndPassword(emailUser, passwordUser).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    if(mAuth.getCurrentUser().isEmailVerified()){
                        Toast.makeText(LoginActivity.this, "Bienvenido", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        Log.d("SIGN IN", "Usuario ha hecho login correctamente");
                    }
                    else{
                        Toast.makeText(LoginActivity.this, "Revisa tu email", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(LoginActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(LoginActivity.this, "Fallo en el inicio de sesión", Toast.LENGTH_SHORT);
                Log.d("SIGN IN", "Fallo en el inicio de sesión");
            }
        });
    }
    private void createAccount(String email, String password){
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>(){
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            mAuth.getCurrentUser().sendEmailVerification()
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Log.d("EMAIL", "EMAIL ENVIADO.");
                                                Toast.makeText(LoginActivity.this, "Registro correcto. Por favor, revise su mail", Toast.LENGTH_SHORT).show();
                                                emailETXT.setText("");
                                                passwordETXT.setText("");
                                            }else{
                                                Toast.makeText(LoginActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                        }
                        else{
                            Log.w("ERROR", "ERROR AL HACER CREAR LA CUENTA");
                            Toast.makeText(LoginActivity.this, "Auth failed", Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }

    // INFO: https://firebase.google.com/docs/auth/android/manage-users?hl=es-419
    // INFO: https://www.youtube.com/watch?v=06YKlMdWyMM

}