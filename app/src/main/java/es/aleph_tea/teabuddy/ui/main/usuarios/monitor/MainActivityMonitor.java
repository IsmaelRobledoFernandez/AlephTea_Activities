package es.aleph_tea.teabuddy.ui.main.usuarios.monitor;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import es.aleph_tea.teabuddy.databinding.ActivityMainMonitorBinding;
import es.aleph_tea.teabuddy.ui.main.adapters.SectionsPagerAdapterMonitor;

public class MainActivityMonitor extends AppCompatActivity {

    private ActivityMainMonitorBinding binding;

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
}