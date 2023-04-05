package es.aleph_tea.teabuddy.login;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import es.aleph_tea.teabuddy.R;

public class ModificarPassActivity extends AppCompatActivity {
    EditText password_ETXT;
    Button boton_cambioPass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar_pass);
        password_ETXT = findViewById(R.id.new_password_ETXT);
        boton_cambioPass = findViewById(R.id.new_boton_cambioPass);
        boton_cambioPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String pass;
                pass = password_ETXT.getText().toString().trim();
                updatePassword(pass);
            }
        });
    }
    public void updatePassword(String pass) {
        // [START update_password]
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String newPassword = pass;

        user.updatePassword(newPassword)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Contraseña cambiada", Toast.LENGTH_SHORT).show();
                            Log.d("ListActivity", "User password updated.");
                            finish();
                        }
                        else{
                            Toast.makeText(getApplicationContext(), "Pon una contraseña más larga", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        // [END update_password]
    }
}