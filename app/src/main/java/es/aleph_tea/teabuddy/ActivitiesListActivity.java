package es.aleph_tea.teabuddy;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;


import es.aleph_tea.teabuddy.controllers.FBRTDatabaseController;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;


import es.aleph_tea.teabuddy.ui.main.SectionsPagerAdapter;
import es.aleph_tea.teabuddy.databinding.ActivityActivitiesListBinding;

public class ActivitiesListActivity extends AppCompatActivity {

    ImageButton ajustes;
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

        // Inicializacion metodos de obtencion de datos en tiempo real
        FBRTDatabaseController fbrt = new FBRTDatabaseController(this.getApplicationContext(),
                binding.getRoot());

        fbrt.startService();
    }

    protected void accesoAjustes(){
        ajustes = (ImageButton)findViewById(R.id.settingsButton);
        ajustes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), SettingsActivity.class);
                startActivity(i);
            }
        });

    }


}