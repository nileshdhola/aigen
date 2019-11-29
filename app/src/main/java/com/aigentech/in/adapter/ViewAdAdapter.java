package com.aigentech.in.adapter;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.aigentech.in.CarDetailsDto;
import com.aigentech.in.R;
import com.aigentech.in.ViewInfoActivity;
import com.google.gson.Gson;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ViewAdAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private static final String TAG = "ViewAdAdapter";
    public static final int VIEW_TYPE_EMPTY = 0;
    public static final int VIEW_TYPE_NORMAL = 1;

    private Callback mCallback;
    private List<CarDetailsDto> mSportList;

    public ViewAdAdapter(List<CarDetailsDto> sportList) {
        mSportList = sportList;
    }

    public void setCallback(Callback callback) {
        mCallback = callback;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        holder.onBind(position);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        switch (viewType) {
            case VIEW_TYPE_NORMAL:
                return new ViewHolder(
                        LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false));
            case VIEW_TYPE_EMPTY:
            default:
                return new EmptyViewHolder(
                        LayoutInflater.from(parent.getContext()).inflate(R.layout.item_empty_view, parent, false));
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (mSportList != null &&
                mSportList.size() > 0) {
            return VIEW_TYPE_NORMAL;
        } else {
            return VIEW_TYPE_EMPTY;
        }
    }

    @Override
    public int getItemCount() {
        if (mSportList != null &&
                mSportList.size() > 0) {
            return mSportList.size();
        } else {
            return 1;
        }
    }

    public void addItems(List<CarDetailsDto> sportList) {
        mSportList.addAll(sportList);
        notifyDataSetChanged();
    }

    public interface Callback {
        void onEmptyViewRetryClick();
    }

    public class ViewHolder extends BaseViewHolder {

       /* @BindView(R.id.thumbnail)
        ImageView coverImageView;*/

        @BindView(R.id.textCarNumber)
        TextView textCarNumber;

        @BindView(R.id.textCarName)
        TextView textCarName;

        @BindView(R.id.textViewCompnayName)
        TextView textViewCompnayName;

        @BindView(R.id.textSellName)
        TextView textSellName;

        @BindView(R.id.textSellMobileNumber)
        TextView textSellMobileNumber;

        @BindView(R.id.textSellEmail)
        TextView textSellEmail;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        protected void clear() {
            // coverImageView.setImageDrawable(null);
            textCarNumber.setText("");
            textCarName.setText("");
            textViewCompnayName.setText("");

            textSellName.setText("");
            textSellMobileNumber.setText("");
            textSellEmail.setText("");
        }

        public void onBind(int position) {
            super.onBind(position);

            final CarDetailsDto carDetailsDto = mSportList.get(position);

           /* if (mSport.getImageUrl() != null) {
                Glide.with(itemView.getContext())
                        .load(mSport.getImageUrl())
                        .into(coverImageView);
            }*/

            //car info
            if (carDetailsDto.getCarCompanyName() != null) {
                textCarNumber.setText(carDetailsDto.getCarCompanyName());
            }

            if (carDetailsDto.getCarName() != null) {
                textCarName.setText(carDetailsDto.getCarName());
            }

            if (carDetailsDto.getCarNumber() != null) {
                textViewCompnayName.setText(carDetailsDto.getCarNumber());
            }

            //seller
            if (carDetailsDto.getSellerName() != null) {
                textSellName.setText(carDetailsDto.getSellerName());
            }

            if (carDetailsDto.getSelleMobileNo() != null) {
                textSellMobileNumber.setText(carDetailsDto.getSelleMobileNo());
            }

            if (carDetailsDto.getSellerEmailAddress() != null) {
                textSellEmail.setText(carDetailsDto.getSellerEmailAddress());
            }


            itemView.setOnClickListener(v -> {
                if (carDetailsDto.getCarNumber() != null) {
                    try {

                        Gson gson = new Gson();
                        String jsonData = gson.toJson(carDetailsDto, CarDetailsDto.class);
                        Intent intent = new Intent(itemView.getContext(), ViewInfoActivity.class);
                        intent.putExtra("JSON_DATA", jsonData);
                        itemView.getContext().startActivity(intent);
                    } catch (Exception e) {
                        Log.e(TAG, "onClick: not correct");
                    }
                }
            });
        }
    }

    public class EmptyViewHolder extends BaseViewHolder {

        @BindView(R.id.tv_message)
        TextView messageTextView;
        @BindView(R.id.buttonRetry)
        TextView buttonRetry;

        EmptyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            buttonRetry.setOnClickListener(v -> mCallback.onEmptyViewRetryClick());
        }

        @Override
        protected void clear() {

        }

    }


}
