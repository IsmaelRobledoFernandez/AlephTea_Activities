package es.aleph_tea.teabuddy.ui.main;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import es.aleph_tea.teabuddy.AdminListaActividadesFragment;
import es.aleph_tea.teabuddy.ListaUsuariosFragment;
import es.aleph_tea.teabuddy.MonitorListaActividadesFragment;
import es.aleph_tea.teabuddy.MonitorListaActividadesInscritoFragment;

public class SectionsPagerAdapterMonitor extends FragmentPagerAdapter {

    private static final String[] TAB_TITLES = new String[]{"Mis actividades", "Lista Actividades"};
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
        // Show 2 total pages.
        return 2;
    }
}