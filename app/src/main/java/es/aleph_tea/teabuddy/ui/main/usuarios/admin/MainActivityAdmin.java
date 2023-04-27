package es.aleph_tea.teabuddy.ui.main.usuarios.admin;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import es.aleph_tea.teabuddy.databinding.ActivityMainAdminBinding;
import es.aleph_tea.teabuddy.ui.main.adapters.SectionsPagerAdapterAdmin;

public class MainActivityAdmin extends AppCompatActivity {

    private ActivityMainAdminBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainAdminBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        SectionsPagerAdapterAdmin sectionsPagerAdapter = new SectionsPagerAdapterAdmin(this, getSupportFragmentManager());
        ViewPager viewPager = binding.viewPager;
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = binding.tabs;
        tabs.setupWithViewPager(viewPager);

    }
}