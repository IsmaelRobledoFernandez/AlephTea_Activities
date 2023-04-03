package es.aleph_tea.teabuddy;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import es.aleph_tea.teabuddy.controllers.FBRTDatabaseController;
import es.aleph_tea.teabuddy.ui.main.SectionsPagerAdapter;
import es.aleph_tea.teabuddy.databinding.ActivityActivitiesListBinding;

public class ActivitiesListActivity extends AppCompatActivity {

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
}