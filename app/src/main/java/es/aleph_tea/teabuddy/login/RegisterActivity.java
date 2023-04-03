package es.aleph_tea.teabuddy.login;

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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import es.aleph_tea.teabuddy.LoginMainActivity;
import es.aleph_tea.teabuddy.R;
import es.aleph_tea.teabuddy.models.Usuario;

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
                //if(email.length()==0||password.length()==0||nombre.length()==0||apellido.length()==0||n_telefono.length()==0||fecha_nacimiento.length()==0){
                if(email.length()==0||password.length()==0){
                    Toast.makeText(getApplicationContext(), "Completa los campos", Toast.LENGTH_SHORT).show();
                    Log.d("SIGN IN", "No se han completado las credenciales");
                }else {
                    Usuario user = new Usuario(email, password, n_telefono, fecha_nacimiento, apellido, nombre);
                    createAccount(user);
                }
            }
        });
    }

    private void createAccount(Usuario user){
        mAuth.createUserWithEmailAndPassword(user.getEmail(), user.getPassword())
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

                                                Map<String, Object> usuario = new HashMap<>();
                                                usuario.put("nombre", user.getNombre());
                                                usuario.put("apellidos", user.getApellido());
                                                usuario.put("fecha_nacimiento", user.getFecha_nacimiento());
                                                usuario.put("numero_telefono", user.getN_telefono());

                                                // Add a new document with a generated ID
                                                db.collection("users")
                                                        .add(usuario)
                                                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                                            @Override
                                                            public void onSuccess(DocumentReference documentReference) {
                                                                Log.d("TAG", "DocumentSnapshot added with ID: " + documentReference.getId());
                                                            }
                                                        })
                                                        .addOnFailureListener(new OnFailureListener() {
                                                            @Override
                                                            public void onFailure(@NonNull Exception e) {
                                                                Log.w("TAG", "Error adding document", e);
                                                            }
                                                        });

                                                Toast.makeText(RegisterActivity.this, "Registro correcto. Por favor, revise su mail", Toast.LENGTH_SHORT).show();
                                                finish();
                                                startActivity(new Intent(getApplicationContext(), LoginMainActivity.class));
                                            }else{
                                                Toast.makeText(RegisterActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                        }
                        else{
                            Log.w("ERROR", "ERROR AL CREAR LA CUENTA");
                            Toast.makeText(RegisterActivity.this, "Auth failed", Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }
}
