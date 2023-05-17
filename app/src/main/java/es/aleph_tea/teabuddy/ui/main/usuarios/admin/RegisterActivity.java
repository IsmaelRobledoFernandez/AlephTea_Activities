package es.aleph_tea.teabuddy.ui.main.usuarios.admin;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import es.aleph_tea.teabuddy.R;
import es.aleph_tea.teabuddy.database.entity.Usuario;

public class RegisterActivity extends AppCompatActivity {
    FirebaseAuth mAuth;
    FirebaseFirestore db;
    FirebaseDatabase dbRef;

    String email, password, rol="Voluntario";

    String nombre, apellido, n_telefono, fecha_nacimiento;
    Spinner rol_usuario;
    Button button_registro;
    EditText emailETXT;
    EditText nombreETXT, apellidosETXT, n_telefonoETXT, fecha_nacimientoETXT;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        dbRef = FirebaseDatabase.getInstance();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        gestionSignUp();
    }

    private void gestionSignUp(){
        nombreETXT = (EditText) findViewById(R.id.nombre);
        apellidosETXT = (EditText) findViewById(R.id.apellido);
        n_telefonoETXT = (EditText) findViewById(R.id.n_telefono);
        emailETXT = (EditText) findViewById(R.id.email);
        // Si el usuario no existe lo crea, si no hace la gestión del login
        button_registro = findViewById(R.id.boton_registro);
        rol_usuario = findViewById(R.id.tipo_usuario);

        fecha_nacimientoETXT = (EditText) findViewById(R.id.fecha_nac);

        fecha_nacimientoETXT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(fecha_nacimientoETXT.getWindowToken(), 0);

                final Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(RegisterActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        fecha_nacimientoETXT.setText(day+"/"+month+"/"+year);
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });
        button_registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nombre = nombreETXT.getText().toString().trim();
                apellido = apellidosETXT.getText().toString().trim();
                n_telefono = n_telefonoETXT.getText().toString().trim();
                fecha_nacimiento = fecha_nacimientoETXT.getText().toString().trim();
                //TODO: Cambiar contraseña por defecto por una random imposible de adivinar
                password="123456";
                email = emailETXT.getText().toString().trim();
                rol = rol_usuario.getSelectedItem().toString();
                //if(email.length()==0||password.length()==0||nombre.length()==0||apellido.length()==0||n_telefono.length()==0||fecha_nacimiento.length()==0){
                String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
                if(email.length()==0){
                    Toast.makeText(getApplicationContext(), "Completa los campos", Toast.LENGTH_SHORT).show();
                    Log.d("SIGN IN", "No se han completado las credenciales");
                }if(!email.matches(emailPattern)){
                    Toast.makeText(getApplicationContext(), "El email no es correcto", Toast.LENGTH_SHORT).show();
                    Log.d("SIGN IN", "El email no es correcto");
                }if(n_telefono.length()!=9){
                    Toast.makeText(getApplicationContext(), "El numero de telefono no es correcto", Toast.LENGTH_SHORT).show();
                    Log.d("SIGN IN", "El numero de telefono no es correcto");
                }else {
                    Usuario user = new Usuario();
                    user.setRol(rol);
                    user.setEmail(email);
                    user.setNombre(nombre);
                    user.setApellidos(apellido);
                    user.setNumero_telefono(n_telefono);
                    user.setFecha_nacimiento(fecha_nacimiento);
                    createAccount(user, password);
                }
            }
        });
    }

    private void createAccount(Usuario user, String password){
        mAuth.createUserWithEmailAndPassword(user.getEmail(), password)
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
                                                usuario.put("email", user.getEmail());
                                                usuario.put("nombre", user.getNombre());
                                                usuario.put("apellidos", user.getApellidos());
                                                usuario.put("fecha_nacimiento", user.getFecha_nacimiento());
                                                usuario.put("numero_telefono", user.getNumero_telefono());
                                                usuario.put("rol", user.getRol());
                                                // Ponemos el rol del usuario, por defecto voluntario
                                                try {
                                                    String uid = mAuth.getCurrentUser().getUid();
                                                    dbRef.getReference("UsuariosAsociacion").child(uid).setValue(usuario);

                                                    Toast.makeText(RegisterActivity.this, rol+" creado", Toast.LENGTH_SHORT).show();
                                                    finish();
                                                }catch (Exception e){
                                                    Toast.makeText(RegisterActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                                }
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
