package com.aigentech.in.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aigentech.in.CarDetailsDto;
import com.aigentech.in.R;
import com.aigentech.in.adapter.ViewAdAdapter;
import com.aigentech.in.utils.CommonUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class ViewAdFragment extends Fragment {

    private View view;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    ViewAdAdapter mSportAdapter;

    LinearLayoutManager mLayoutManager;

    public ViewAdFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_view_ad, container, false);
        ButterKnife.bind(this, view);
        String jsonData = "[{\"car_company_name\":\"hhdd\",\"car_name\":\"ddcc\",\"car_number\":\"NH77JJ88\",\"seller_mobile\":\"8888888888\",\"seller_email_address\":\"d@outlook.com\",\"seller_name\":\"ccc\",\"total_photo_count\":\"1\"},{\"car_company_name\":\"hhdd\",\"car_name\":\"ddcc\",\"car_number\":\"NH77JJ99\",\"seller_mobile\":\"6565666565\",\"seller_email_address\":\"asad@outlook.com\",\"seller_name\":\"ccc\",\"total_photo_count\":\"1\"}]";
        setAdapterData(view, jsonData);


       /* boolean isFilePresent = CommonUtils.isFilePresent(getActivity(), "storage.json");
          if (isFilePresent) {
            String jsonString = CommonUtils.read(getActivity(), "storage.json");
            System.out.println("JSON Fils : " + jsonString);
            //do the json parsing here and do the rest of functionality of app
        }*/

        return view;
    }

    private void setAdapterData(View view, String jsonData) {

        Gson gson = new Gson();
        Type listType = new TypeToken<List<CarDetailsDto>>() {
        }.getType();
        List<CarDetailsDto> posts = gson.fromJson(jsonData, listType);

        mLayoutManager = new LinearLayoutManager(getActivity());
        mLayoutManager.setOrientation(RecyclerView.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mSportAdapter = new ViewAdAdapter(new ArrayList<>());
        mSportAdapter.addItems(posts);
        mRecyclerView.setAdapter(mSportAdapter);

    }

}
