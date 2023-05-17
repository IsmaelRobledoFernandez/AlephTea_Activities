package es.aleph_tea.teabuddy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class EnviarCorreo extends AppCompatActivity {

    EditText editText;
    Button EnviarCorreo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enviar_correo);
        EnviarCorreo = findViewById(R.id.button);
        editText = findViewById(R.id.editTextTextMultiLine);
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        EnviarCorreo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(editText!=null){
                    sendEmail("raqueldiazchavez9@gmail.com", user.getEmail(), editText.getText().toString());
                }
            }
        });
    }


    public void sendEmail(String recipient, String subject, String message) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("message/rfc822");
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{recipient});
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, message);

        try {
            startActivity(Intent.createChooser(intent, "Enviar correo electrónico"));
        } catch (ActivityNotFoundException e) {
            // Manejo de excepciones si no hay aplicaciones de correo electrónico instaladas
            Toast.makeText(this, "No hay aplicaciones de correo electrónico instaladas.", Toast.LENGTH_SHORT).show();
        }
    }
}