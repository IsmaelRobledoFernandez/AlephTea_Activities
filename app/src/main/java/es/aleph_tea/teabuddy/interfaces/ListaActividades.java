package es.aleph_tea.teabuddy.interfaces;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

public interface ListaActividades {

   void onCreate(Bundle savedInstanceState);

   View onCreateView(LayoutInflater inflater, ViewGroup container,
                     Bundle savedInstanceState);

   void onViewCreated(View view, Bundle savedInstanceState);

   void onItemClick(AdapterView<?> adapterView, View view, int position, long l);

}
