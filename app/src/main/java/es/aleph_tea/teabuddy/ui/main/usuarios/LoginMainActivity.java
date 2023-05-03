package es.aleph_tea.teabuddy.ui.main.usuarios;

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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import es.aleph_tea.teabuddy.ActivitiesListActivity;
import es.aleph_tea.teabuddy.R;
import es.aleph_tea.teabuddy.ui.main.usuarios.admin.MainActivityAdmin;
import es.aleph_tea.teabuddy.models.Sesion;
import es.aleph_tea.teabuddy.ui.main.usuarios.admin.RegisterActivity;
import es.aleph_tea.teabuddy.ui.main.usuarios.monitor.MainActivityMonitor;

public class
LoginMainActivity extends AppCompatActivity {
    FirebaseAuth mAuth;
    DatabaseReference db;
    TextView textView, cambiar_pass;
    Button button;
    EditText emailETXT, passwordETXT;
    String email, password;
    CheckBox keepLogin;
    Sesion sesion = new Sesion(false);
    public static final String STRING_PREFERENCES = "alephapp.aleph";
    public static final String PREFERENCE_ESTADO_BUTTON_SESION = "estado.button.sesion";

    private String rol_str;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Instanciar firebase
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance().getReference();

        super.onCreate(savedInstanceState);
        if(!obtenerEstadoBoton()){
            setContentView(R.layout.activity_login);
            gestionLogin();
            registro_usuario();
            cambiar_password();
        }else{
            finish();
            //OK: Consultar tipo de usuario y ver su rol. En base a esto, mostrar mainActivity de su rol
            if(rol_str.equals("Administrador")) {
                startActivity(new Intent(getApplicationContext(), MainActivityAdmin.class));
            } else if(rol_str.equals("Monitor")) {
                startActivity(new Intent(getApplicationContext(), MainActivityMonitor.class));
            } else if(rol_str.equals("Voluntario")){
                startActivity(new Intent(getApplicationContext(), ActivitiesListActivity.class));
            }
        }
    }

    private void registro_usuario(){
        textView = (TextView)findViewById(R.id.activa_registration);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
            }
        });
    }
    private void cambiar_password(){
        cambiar_pass = findViewById(R.id.cambiar_password);
        cambiar_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), CambiarPassActivity.class));
            }
        });
    }
    private void gestionLogin(){
        //Obtener id de los elementos del login
        emailETXT = (EditText) findViewById(R.id.email);
        passwordETXT = (EditText) findViewById(R.id.password);
        button = (Button) findViewById(R.id.boton_login);
        keepLogin = (CheckBox) findViewById(R.id.checkBox);
        keepLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean checked = ((CheckBox)view).isChecked();
                if(checked){
                    sesion.setMantenerIniciado(true);
                }
                else{
                    sesion.setMantenerIniciado(false);
                }
                Log.d("INFO", "GUARDAR SESION: "+ checked);
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email = emailETXT.getText().toString().trim();
                password = passwordETXT.getText().toString();
                if(email.length()==0||password.length()==0){
                    Toast.makeText(getApplicationContext(), "Completa los campos", Toast.LENGTH_SHORT).show();
                    Log.d("SIGN IN", "No se han completado las credenciales");
                }else if(password.length()<5){
                    Toast.makeText(getApplicationContext(), "Minimo seis caracteres de contraseña", Toast.LENGTH_SHORT).show();
                    Log.d("SIGN IN", "Contraseña demasiado corta");
                }else {
                    loginUser(email, password);
                    guardarEstadoBoton();
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

                        FirebaseDatabase dbRef = FirebaseDatabase.getInstance();

                        String uid = mAuth.getCurrentUser().getUid();

                        DatabaseReference dbReference = dbRef.getReference();
                        dbReference.child("UsuariosAsociacion").addListenerForSingleValueEvent(new ValueEventListener() { //addValueEventListener
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if(snapshot.exists()){
                                    rol_str = snapshot.child(uid).child("rol").getValue().toString();

                                    Log.d("USUARIO", "ROL: " + rol_str);

                                    if(rol_str.equals("Administrador")) {
                                        startActivity(new Intent(getApplicationContext(), MainActivityAdmin.class));
                                    } else if(rol_str.equals("Monitor")) {
                                        startActivity(new Intent(getApplicationContext(), MainActivityMonitor.class));
                                    } else if(rol_str.equals("Voluntario")){
                                        startActivity(new Intent(getApplicationContext(), ActivitiesListActivity.class));
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                Toast.makeText(LoginMainActivity.this, error.getMessage().toString(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else {
                        Toast.makeText(LoginMainActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(LoginMainActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(LoginMainActivity.this, "Fallo en el inicio de sesión", Toast.LENGTH_SHORT);
                Log.d("SIGN IN", "Fallo en el inicio de sesión");
            }
        });
    }

    protected void guardarEstadoBoton(){
        SharedPreferences preferences = getSharedPreferences(STRING_PREFERENCES, MODE_PRIVATE);
        preferences.edit().putBoolean(PREFERENCE_ESTADO_BUTTON_SESION, sesion.isMantenerIniciado()).apply();
    }

    protected boolean obtenerEstadoBoton(){
        SharedPreferences preferences = getSharedPreferences(STRING_PREFERENCES, MODE_PRIVATE);
        return preferences.getBoolean(PREFERENCE_ESTADO_BUTTON_SESION, false);
    }

}