package es.aleph_tea.teabuddy.ui.main.usuarios.monitor;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import es.aleph_tea.teabuddy.R;
import es.aleph_tea.teabuddy.databinding.ActivityMainMonitorBinding;
import es.aleph_tea.teabuddy.ui.main.adapters.SectionsPagerAdapterMonitor;
import es.aleph_tea.teabuddy.ui.main.usuarios.EnviarCorreoActivity;

public class MainActivityMonitor extends AppCompatActivity {

    private ActivityMainMonitorBinding binding;
    FloatingActionButton fab;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainMonitorBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        correo();

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
                Intent i = new Intent(getApplicationContext(), EnviarCorreoActivity.class);
                startActivity(i);
            }
        });
    }

}