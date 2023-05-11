package es.aleph_tea.teabuddy.ui.main.adapters;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import es.aleph_tea.teabuddy.EnrolledActivitiesFragment;
import es.aleph_tea.teabuddy.AllActivitiesFragment;
import es.aleph_tea.teabuddy.ui.main.usuarios.ListaActividadesApiFragment;

public class SectionsPagerAdapter extends FragmentPagerAdapter {

    private static final String[] TAB_TITLES = new String[]{"Lista Actividades", "Actividades Inscritas", "Comunidad Madrid"};
    private final Context mContext;

    public SectionsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new AllActivitiesFragment();
            case 1:
                return new EnrolledActivitiesFragment();
            case 2:
                return new ListaActividadesApiFragment();
            default:
                return new AllActivitiesFragment();
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