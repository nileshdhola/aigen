package com.aigentech.in.fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aigentech.in.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ViewAdFragment extends Fragment {


    public ViewAdFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_view_ad, container, false);
    }

}
