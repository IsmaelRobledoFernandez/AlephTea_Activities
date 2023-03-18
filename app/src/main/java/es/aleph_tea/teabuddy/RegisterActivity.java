package es.aleph_tea.teabuddy;

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
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {
    FirebaseAuth mAuth;
    FirebaseFirestore db;

    String email, password;

    String nombre, apellido, n_telefono, fecha_nacimiento;

    Button button_registro;
    EditText emailETXT, passwordETXT;
    EditText nombreETXT, apellidosETXT, n_telefonoETXT, fecha_nacimientoETXT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        gestionSignUp();
    }

    private void gestionSignUp(){
        nombreETXT = (EditText) findViewById(R.id.nombre);
        apellidosETXT = (EditText) findViewById(R.id.apellido);
        n_telefonoETXT = (EditText) findViewById(R.id.n_telefono);
        fecha_nacimientoETXT = (EditText) findViewById(R.id.fecha_nac);
        emailETXT = (EditText) findViewById(R.id.email);
        passwordETXT = (EditText) findViewById(R.id.password);
        // Si el usuario no existe lo crea, si no hace la gestión del login
        button_registro = findViewById(R.id.boton_registro);
        button_registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nombre = nombreETXT.getText().toString().trim();
                apellido = apellidosETXT.getText().toString().trim();
                n_telefono = n_telefonoETXT.getText().toString().trim();
                fecha_nacimiento = fecha_nacimientoETXT.getText().toString().trim();
                email = emailETXT.getText().toString().trim();
                password = passwordETXT.getText().toString();
                if(email.length()==0||password.length()==0||nombre.length()==0||apellido.length()==0||n_telefono.length()==0||fecha_nacimiento.length()==0){
                    Toast.makeText(getApplicationContext(), "Completa los campos", Toast.LENGTH_SHORT).show();
                    Log.d("SIGN IN", "No se han completado las credenciales");
                }else {
                    createAccount(email, password);
                }
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
                                                Map<String, Object> user = new HashMap<>();
                                                user.put("nombre", nombre);
                                                user.put("apellido", apellido);
                                                user.put("fecha nacimiento", fecha_nacimiento);
                                                user.put("email", email);
                                                db.collection("users").add(user).addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        Log.w("FALLO", "Fallo al subir los datos del usuario");
                                                    }
                                                });
                                                Toast.makeText(RegisterActivity.this, "Registro correcto. Por favor, revise su mail", Toast.LENGTH_SHORT).show();
                                                emailETXT.setText("");
                                                passwordETXT.setText("");
                                                nombreETXT.setText("");
                                                apellidosETXT.setText("");
                                                n_telefonoETXT.setText("");
                                                fecha_nacimientoETXT.setText("");
                                            }else{
                                                Toast.makeText(RegisterActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                        }
                        else{
                            Log.w("ERROR", "ERROR AL HACER CREAR LA CUENTA");
                            Toast.makeText(RegisterActivity.this, "Auth failed", Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }
}
