package com.ehp.clase1.fragmentss;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.ehp.clase1.R;

import org.w3c.dom.Text;

/**
 * A simple {@link Fragment} subclass.
 */
public class SlideshowFragment extends Fragment {


    public SlideshowFragment() {
        // Required empty public constructor
    }

    private TextView texto;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_slideshow, container, false);

        texto = (TextView) view.findViewById(R.id.texto);

        Toast.makeText(getContext(), "Mensaje", Toast.LENGTH_SHORT).show();
        return view;
    }

}
