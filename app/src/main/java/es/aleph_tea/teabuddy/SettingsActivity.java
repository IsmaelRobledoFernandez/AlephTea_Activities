package es.aleph_tea.teabuddy;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import es.aleph_tea.teabuddy.interfaces.SecondaryActivity;

public class SettingsActivity extends AppCompatActivity implements SecondaryActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // Inicializamos la Toolbar
        inicializarToolbar();

    }
    @Override
    public void inicializarToolbar() {
        TextView tituloActividad;

        tituloActividad = (TextView) findViewById(R.id.toolbarTitle);

        tituloActividad.setText("Ajustes");

        accesoBotonAtras();
    }

    @Override
    public void accesoBotonAtras() {

        ImageView botonAtras;

        botonAtras = (ImageView) findViewById(R.id.btn_back);
        botonAtras.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Log.d("SettingsActivity",
                        "Volviendo a la lista de actividades...");
                finish();
            }
        });
    }
}