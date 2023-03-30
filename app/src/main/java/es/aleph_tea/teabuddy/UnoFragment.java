package es.aleph_tea.teabuddy;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.ListFragment;

import android.text.style.TabStopSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UnoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UnoFragment extends Fragment implements AdapterView.OnItemClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ListView mListView;

    private List<String> actividades;

    private ArrayAdapter<String> mAdapter;

    public UnoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UnoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UnoFragment newInstance(String param1, String param2) {
        UnoFragment fragment = new UnoFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        Toast.makeText(getContext(),"Elemento clickado: " + position, Toast.LENGTH_SHORT).show();
        startActivity(new Intent(getActivity().getApplicationContext(), ActivityDetailsActivity.class));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflar el layout para este fragmento
        return inflater.inflate(R.layout.fragment_uno, container, false);
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Inicializacion de la ListView, la lista de actividades y el adapter
        mListView = (ListView)view.findViewById(R.id.listView);
        actividades = new ArrayList<>();

        actividades.add("Brian");
        actividades.add("Raquel");
        actividades.add("Ismael");

        mAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1,actividades);
        mListView.setAdapter(mAdapter);

        mListView.setOnItemClickListener(this);
    }
}