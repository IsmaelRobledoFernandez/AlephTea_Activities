package es.aleph_tea.teabuddy;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import es.aleph_tea.teabuddy.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    FirebaseAuth mAuth;
    TextView textView;
    Button button;
    EditText emailETXT, passwordETXT;
    String email, password;

    CheckBox keepLogin;
    boolean mantenerIniciado;

    String STRING_PREFERENCES = "alephapp.aleph";
    String PREFERENCE_ESTADO_BUTTON_SESION = "estado.button.sesion";

    // OK: La primera vez que hace login OK el usuario se guarda la información de que ha hecho login exitosamente
    // TODO: Poner boton para cerrar sesión dentro de la app
    // TODO: Poner boton para borrar cuenta y para cambiar contraseña
    // TODO: Cambiar contraseña desde el login

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Instanciar firebase
        mAuth = FirebaseAuth.getInstance();
        super.onCreate(savedInstanceState);
        //Mostrar la vista del login (activity_main)
        setContentView(R.layout.activity_login);
        if(!obtenerEstadoBoton()){
            gestionLogin();
            registro_usuario();
        }else{
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        }
    }

    private void registro_usuario(){
        textView = (TextView)findViewById(R.id.activa_registration);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getApplicationContext(), "HolaMundo", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
            }
        });
    }

    private void gestionLogin(){
        //Obtener id de los elementos del login
        emailETXT = (EditText) findViewById(R.id.email);
        passwordETXT = (EditText) findViewById(R.id.password);
        button = (Button) findViewById(R.id.boton_login);
        keepLogin = (CheckBox) findViewById(R.id.checkBox);
        //Poner un clickListener en el boton de login con el que obtener los datos del usuario
        if(keepLogin.isChecked()){
            // Comunicar que el usuario quiere guardar su sesion
            mantenerIniciado = true;
        }
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email = emailETXT.getText().toString().trim();
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
                        if(mantenerIniciado){
                            // Almacenamos que el usuario ya ha hecho login correctamente
                            guardarEstadoBoton();
                        }
                        Toast.makeText(LoginActivity.this, "Bienvenido", Toast.LENGTH_SHORT).show();
                        finish();
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

    private void guardarEstadoBoton(){
        SharedPreferences preferences = getSharedPreferences(STRING_PREFERENCES, MODE_PRIVATE);
        preferences.edit().putBoolean(PREFERENCE_ESTADO_BUTTON_SESION, mantenerIniciado).apply();
    }

    private boolean obtenerEstadoBoton(){
        SharedPreferences preferences = getSharedPreferences(STRING_PREFERENCES, MODE_PRIVATE);
        return preferences.getBoolean(PREFERENCE_ESTADO_BUTTON_SESION, false);
    }

    // https://github.com/firebase/snippets-android/blob/7d6bff60b1f57b13edd34a794619264c14cf0b3d/auth/app/src/main/java/com/google/firebase/quickstart/auth/CustomAuthActivity.java#L54-L60

    // INFO: https://firebase.google.com/docs/auth/android/manage-users?hl=es-419
    // INFO: https://www.youtube.com/watch?v=06YKlMdWyMM
    // INFO: (Para mantener sesion del usuario abierta) https://www.youtube.com/watch?v=NOxoBatLXK0

}