package com.aigentech.in.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.aigentech.in.R;
import com.aigentech.in.ViewInfoActivity;
import com.aigentech.in.model.CarDetailsDto;
import com.google.gson.Gson;

import java.util.List;

public class CustomViewAdAdapter extends RecyclerView.Adapter<CustomViewAdAdapter.MyViewHolder> {

    private List<CarDetailsDto> dataSet;
    private Context context;

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView textCarNumber;
        private TextView textCarName;
        private TextView textViewCompnayName;
        private TextView textSellName;
        private TextView textSellMobileNumber;
        private TextView textTotalImage;
        private TextView textSellEmail;
        private CardView cardView;


        public MyViewHolder(View itemView) {
            super(itemView);
            this.textCarNumber = (TextView) itemView.findViewById(R.id.textCarNumber);
            this.textCarName = (TextView) itemView.findViewById(R.id.textCarName);
            this.textViewCompnayName = (TextView) itemView.findViewById(R.id.textViewCompnayName);
            this.textSellName = (TextView) itemView.findViewById(R.id.textSellName);
            this.textSellMobileNumber = (TextView) itemView.findViewById(R.id.textSellMobileNumber);
            this.textTotalImage = (TextView) itemView.findViewById(R.id.textTotalImage);
            this.textSellEmail = (TextView) itemView.findViewById(R.id.textSellEmail);
            this.cardView = (CardView) itemView.findViewById(R.id.card_view);
        }
    }

    public CustomViewAdAdapter(List<CarDetailsDto> data, Context context) {
        this.dataSet = data;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent,
                                           int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int listPosition) {

        final CarDetailsDto carDetailsDto = dataSet.get(listPosition);

        holder.textCarNumber.setText(carDetailsDto.getCarNumber());
        holder.textCarName.setText(carDetailsDto.getCarName());
        holder.textViewCompnayName.setText(carDetailsDto.getCarCompanyName());

        holder.textSellName.setText(carDetailsDto.getSellerName());
        holder.textSellMobileNumber.setText(carDetailsDto.getSelleMobileNo());
        holder.textSellEmail.setText(carDetailsDto.getSellerEmailAddress());

        holder.textTotalImage.setText(carDetailsDto.getTotalPhotoCount());

        try {

            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Gson gson = new Gson();
                    String jsonData = gson.toJson(carDetailsDto, CarDetailsDto.class);
                    Intent intent = new Intent(context, ViewInfoActivity.class);
                    intent.putExtra("JSON_DATA", jsonData);
                    context.startActivity(intent);
                }
            });
        } catch (Exception e) {
            e.getMessage();
        }


    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

}
