package es.aleph_tea.teabuddy.ui.main;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import es.aleph_tea.teabuddy.AdminListaActividadesFragment;
import es.aleph_tea.teabuddy.AllActivitiesFragment;
import es.aleph_tea.teabuddy.EnrolledActivitiesFragment;
import es.aleph_tea.teabuddy.ListaUsuariosFragment;

public class SectionsPagerAdapterAdmin extends FragmentPagerAdapter {

    private static final String[] TAB_TITLES = new String[]{"Lista Actividades", "Lista Usuarios"};
    private final Context mContext;

    public SectionsPagerAdapterAdmin(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new AdminListaActividadesFragment();
            case 1:
                return new ListaUsuariosFragment();
            default:
                return new AdminListaActividadesFragment();
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