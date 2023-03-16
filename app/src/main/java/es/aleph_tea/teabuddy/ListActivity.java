package es.aleph_tea.teabuddy;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import es.aleph_tea.teabuddy.R;

public class ListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_actividades);
    }
}