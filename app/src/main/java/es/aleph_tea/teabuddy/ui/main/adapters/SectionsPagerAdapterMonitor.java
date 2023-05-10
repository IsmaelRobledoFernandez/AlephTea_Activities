package es.aleph_tea.teabuddy.ui.main.adapters;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import es.aleph_tea.teabuddy.ui.main.usuarios.ListaActividadesApiFragment;
import es.aleph_tea.teabuddy.ui.main.usuarios.monitor.MonitorListaActividadesFragment;
import es.aleph_tea.teabuddy.ui.main.usuarios.monitor.MonitorListaActividadesInscritoFragment;

public class SectionsPagerAdapterMonitor extends FragmentPagerAdapter {

    private static final String[] TAB_TITLES = new String[]{"Mis actividades", "Lista Actividades", "Comunidad Madrid"};
    private final Context mContext;

    public SectionsPagerAdapterMonitor(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new MonitorListaActividadesInscritoFragment();
            case 1:
                return new MonitorListaActividadesFragment();
            case 2:
                return new ListaActividadesApiFragment();
            default:
                return new MonitorListaActividadesInscritoFragment();
        }
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return TAB_TITLES[position];
    }

    @Override
    public int getCount() {
        return 3;
    }
}