package com.river.comunidad.comunidadriver.View.Fragments.FragmentsViewPager;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.river.comunidad.comunidadriver.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class PreguntasFragment extends Fragment {


    public PreguntasFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_preguntas, container, false);

        return view;
    }

}
