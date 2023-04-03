package es.aleph_tea.teabuddy.login;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import es.aleph_tea.teabuddy.LoginMainActivity;
import es.aleph_tea.teabuddy.R;

public class CambiarPassActivity extends AppCompatActivity {
    private Button cambio_pass;
    private EditText emailAddress;

    private String str_email;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cambiar_pass);
        cambiarPass();
    }

    private void cambiarPass(){
        mAuth = FirebaseAuth.getInstance();
        emailAddress = findViewById(R.id.email_ETXT);
        cambio_pass = findViewById(R.id.boton_cambioPass);
        cambio_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                str_email = emailAddress.getText().toString().trim();
                if(str_email.length()>0){
                    mAuth.sendPasswordResetEmail(str_email)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Log.d("CambiarPass", "Email sent.");
                                        finish();
                                        startActivity(new Intent(getApplicationContext(), LoginMainActivity.class));
                                    }
                                }
                            });
                }
            }
        });
    }
}