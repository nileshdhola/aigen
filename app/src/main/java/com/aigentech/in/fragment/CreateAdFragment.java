package com.aigentech.in.fragment;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.aigentech.in.R;

import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class CreateAdFragment extends Fragment {
    private View view;
    private Context context;
    private static final String car_rgex = "^[A-Z]{2}\\s[0-9]{2}\\s[A-Z]{2}\\s[0-9]{4}$";

    /*
     ^ means start of string
     [A-Z]{2} means 2 characters in the range of A through Z
     \s means white space
     [0-9]{2} means 2 characters in the range of 0 through 9
             \s means white space
     [A-Z]{2} means 2 characters in the range of A through Z
     \s means white space
     [0-9]{4} means 4 characters in the range of 0 through 9
     $ means end of string
     */


    public CreateAdFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_create_ad, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

}
