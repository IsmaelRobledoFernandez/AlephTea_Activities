package es.aleph_tea.teabuddy.ui.main.usuarios.voluntario;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import es.aleph_tea.teabuddy.R;
import es.aleph_tea.teabuddy.SettingsActivity;
import es.aleph_tea.teabuddy.controllers.FBRTDBControllerVoluntario;
import es.aleph_tea.teabuddy.controllers.FBRTDatabaseController;
import es.aleph_tea.teabuddy.databinding.ActivityActivitiesListBinding;
import es.aleph_tea.teabuddy.models.Sesion;
import es.aleph_tea.teabuddy.ui.main.adapters.SectionsPagerAdapter;
import es.aleph_tea.teabuddy.ui.main.usuarios.AccountDetailsActivity;
import es.aleph_tea.teabuddy.ui.main.usuarios.EnviarCorreoActivity;

public class MainActivityVoluntario extends AppCompatActivity {

    ImageView ajustes;
    com.google.android.material.floatingactionbutton.FloatingActionButton fab;
    ImageView cuentaUsuario;
    private ActivityActivitiesListBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityActivitiesListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = binding.viewPager;
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = binding.tabs;
        tabs.setupWithViewPager(viewPager);

        // Inicializacion botón de ajustes
        accesoAjustes();
        correo();

        // Inicializacion del boton que hace referencia a la cuenta del usuario
        accesoCuentaUsuario();

        // Inicializacion metodos de obtención de datos en tiempo real (Polimorfismo)
        FBRTDatabaseController fbrt = new FBRTDBControllerVoluntario(this.getApplicationContext(),
                binding.getRoot(), Sesion.SesionUid);

        fbrt.startService();
    }

    protected void correo(){
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), EnviarCorreoActivity.class);
                startActivity(i);
            }
        });
    }

    protected void accesoAjustes(){
        ajustes = (ImageView) findViewById(R.id.settingsButton);
        ajustes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), SettingsActivity.class);
                startActivity(i);
            }
        });

    }

    protected void accesoCuentaUsuario() {
        cuentaUsuario = (ImageView) findViewById(R.id.accountButton);
        cuentaUsuario.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), AccountDetailsActivity.class);
                startActivity(i);
            }
        });
    }

}