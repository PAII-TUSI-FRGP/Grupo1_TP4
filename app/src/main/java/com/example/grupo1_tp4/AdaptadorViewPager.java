package com.example.grupo1_tp4;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class AdaptadorViewPager extends FragmentPagerAdapter {

    private final ArrayList<Fragment> ListaFragmentos = new ArrayList<>();
    private final ArrayList<String> NombreFragmentos = new ArrayList<>();



    public AdaptadorViewPager(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {

        return ListaFragmentos.get(position);
    }

    @Override
    public int getCount() {
        return ListaFragmentos.size();
    }

    public void AgregarFragmento(Fragment fragmento, String titulo){
        ListaFragmentos.add(fragmento);
        NombreFragmentos.add(titulo);
    }
    @Override
    public CharSequence getPageTitle(int position) {
        return NombreFragmentos.get(position);
    }
}
