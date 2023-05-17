package es.aleph_tea.teabuddy.ui.main.usuarios.monitor;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import es.aleph_tea.teabuddy.EnviarCorreo;
import es.aleph_tea.teabuddy.R;
import es.aleph_tea.teabuddy.databinding.ActivityMainMonitorBinding;
import es.aleph_tea.teabuddy.ui.main.adapters.SectionsPagerAdapterMonitor;

public class MainActivityMonitor extends AppCompatActivity {

    private ActivityMainMonitorBinding binding;
    FloatingActionButton fab;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainMonitorBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        SectionsPagerAdapterMonitor sectionsPagerAdapter = new SectionsPagerAdapterMonitor(this, getSupportFragmentManager());
        ViewPager viewPager = binding.viewPager;
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = binding.tabs;
        tabs.setupWithViewPager(viewPager);

    }
    protected void correo(){
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), EnviarCorreo.class);
                startActivity(i);
            }
        });
    }

}