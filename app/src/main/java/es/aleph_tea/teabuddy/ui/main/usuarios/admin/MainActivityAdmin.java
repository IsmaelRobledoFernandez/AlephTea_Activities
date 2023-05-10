package es.aleph_tea.teabuddy.ui.main.usuarios.admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import es.aleph_tea.teabuddy.R;
import es.aleph_tea.teabuddy.databinding.ActivityMainAdminBinding;
import es.aleph_tea.teabuddy.ui.main.adapters.SectionsPagerAdapterAdmin;

public class MainActivityAdmin extends AppCompatActivity {

    private ActivityMainAdminBinding binding;
    private Button APIActivitiesButton;

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