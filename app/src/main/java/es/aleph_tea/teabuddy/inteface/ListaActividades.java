package es.aleph_tea.teabuddy.inteface;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import java.util.List;

import es.aleph_tea.teabuddy.database.entity.Actividad;

public interface ListaActividades {

   void onCreate(Bundle savedInstanceState);

   View onCreateView(LayoutInflater inflater, ViewGroup container,
                     Bundle savedInstanceState);

   void onViewCreated(View view, Bundle savedInstanceState);

   void onItemClick(AdapterView<?> adapterView, View view, int position, long l);

}
