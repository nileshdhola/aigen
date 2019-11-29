package com.aigentech.in.fragment;


import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aigentech.in.R;
import com.aigentech.in.adapter.CustomViewAdAdapter;
import com.aigentech.in.model.CarDetailsDto;
import com.aigentech.in.utils.CommonUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
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
    private LinearLayoutManager mLayoutManager;
    private CustomViewAdAdapter customViewAdAdapter;

    public ViewAdFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_view_ad, container, false);
        ButterKnife.bind(this, view);

        //check data present
        boolean isFilePresent = CommonUtils.isFilePresent(getActivity());
        if (isFilePresent) {
            String jsonString = CommonUtils.read(getActivity());
            if (TextUtils.isEmpty(jsonString)) {
                adapter("[]");
            } else {
                adapter(jsonString);
            }
        } else {
            adapter("[]");
        }

        return view;
    }

    //region set data in adapter
    private void adapter(String jsondata) {
        Gson gson = new Gson();
        Type listType = new TypeToken<List<CarDetailsDto>>() {
        }.getType();
        List<CarDetailsDto> posts = gson.fromJson(jsondata, listType);

        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        customViewAdAdapter = new CustomViewAdAdapter(posts, getContext());
        mRecyclerView.setAdapter(customViewAdAdapter);

    }
    //endregion

}
