package com.aigentech.in.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.aigentech.in.R;
import com.aigentech.in.utils.CommonUtils;

/**
 * A simple {@link Fragment} subclass.
 */
public class ViewAdFragment extends Fragment {

    private View view;


    public ViewAdFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_view_ad, container, false);

        boolean isFilePresent = CommonUtils.isFilePresent(getActivity(), "storage.json");
        if (isFilePresent) {
            String jsonString = CommonUtils.read(getActivity(), "storage.json");
            System.out.println("JSON Fils : " + jsonString);
            //do the json parsing here and do the rest of functionality of app
        }

        return view;
    }

}
