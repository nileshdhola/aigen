package com.aigentech.in;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;

import com.aigentech.in.model.CarDetailsDto;
import com.aigentech.in.utils.CommonUtils;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import ss.com.bannerslider.ImageLoadingService;
import ss.com.bannerslider.Slider;
import ss.com.bannerslider.adapters.SliderAdapter;
import ss.com.bannerslider.viewholder.ImageSlideViewHolder;

public class ViewInfoActivity extends AppCompatActivity {
    @BindView(R.id.banner_slider1)
    Slider slider;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.btnCall)
    AppCompatButton btnCall;
    @BindView(R.id.btnEmail)
    AppCompatButton btnEmail;
    @BindView(R.id.textCarNumber)
    TextView textCarNumber;
    @BindView(R.id.textCompanyName)
    TextView textCompanyName;
    @BindView(R.id.textCarName)
    TextView textCarName;
    @BindView(R.id.textSellerName)
    TextView textSellerName;

    private CarDetailsDto carDetailsDto;
    private String jsonData;
    private ArrayList<String> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_info);
        ButterKnife.bind(this);

        carDetailsDto = new CarDetailsDto();

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Car Information");
        // add back arrow to toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        //get data
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                jsonData = null;
            } else {
                jsonData = extras.getString("JSON_DATA");
            }
        } else {
            jsonData = (String) savedInstanceState.getSerializable("JSON_DATA");
        }
        Gson gson = new GsonBuilder().create();
        carDetailsDto = gson.fromJson(jsonData, CarDetailsDto.class);
        if (carDetailsDto != null) {
            textCarNumber.setText(carDetailsDto.getCarNumber());
            textCompanyName.setText(carDetailsDto.getCarCompanyName());
            textCarName.setText(carDetailsDto.getCarName());
            textSellerName.setText(carDetailsDto.getSellerName());
        }

        list = CommonUtils.getFloderPhoto("MH12AB9999");
        System.out.println(list.size());

        //set image
        Slider.init(new PicassoImageLoadingService(this));
        slider.setAdapter(new MainSliderAdapter());


        btnCall.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + carDetailsDto.getSelleMobileNo()));
                if (checkSelfPermission(Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                startActivity(callIntent);
            }
        });
        btnEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                        "mailto", carDetailsDto.getSellerEmailAddress(), null));
                intent.putExtra(Intent.EXTRA_SUBJECT, "AIGEN");
                intent.putExtra(Intent.EXTRA_TEXT, "This is testing email from AIGEN.");
                startActivity(Intent.createChooser(intent, "Choose an Email client :"));
            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    public class MainSliderAdapter extends SliderAdapter {

        @Override
        public int getItemCount() {
            return list.size();
        }

        @Override
        public void onBindImageSlide(int position, ImageSlideViewHolder viewHolder) {
            viewHolder.bindImageSlide(list.get(position));
        }
    }

    public class PicassoImageLoadingService implements ImageLoadingService {
        public Context context;

        public PicassoImageLoadingService(Context context) {
            this.context = context;
        }

        @Override
        public void loadImage(String url, ImageView imageView) {
            Glide.with(ViewInfoActivity.this)
                    .load(new File(url))
                    .into(imageView);
        }

        @Override
        public void loadImage(int resource, ImageView imageView) {
            Glide.with(ViewInfoActivity.this)
                    .load(resource)
                    .into(imageView);
        }

        @Override
        public void loadImage(String url, int placeHolder, int errorDrawable, ImageView imageView) {
            Glide.with(ViewInfoActivity.this)
                    .load(url)
                    .placeholder(placeHolder)
                    .error(getResources().getDrawable(R.mipmap.ic_launcher))
                    .into(imageView);
        }
    }
}
