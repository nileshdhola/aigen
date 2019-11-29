package com.aigentech.in.fragment;


import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.aigentech.in.R;
import com.aigentech.in.model.CarDetailsDto;
import com.aigentech.in.utils.CommonUtils;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.nguyenhoanglam.imagepicker.model.Image;
import com.nguyenhoanglam.imagepicker.ui.imagepicker.ImagePicker;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class CreateAdFragment extends Fragment {
    private View view;
    private String car_rgex = "^[A-Z]{2}[ -]{0,1}[0-9]{2}[ -]{0,1}[A-Z]{1,2}[ -]{0,1}[0-9]{1,4}$";

    private ArrayList<Image> images = new ArrayList<>();
    @BindView(R.id.btnUploadData)
    Button btnUploadData;
    @BindView(R.id.imgUpload)
    ImageView imgUpload;
    @BindView(R.id.textTotalImage)
    TextView textTotalImage;
    //car info
    @BindView(R.id.edittextCarNumber)
    TextInputEditText edittextCarNumber;
    @BindView(R.id.inputCarNumber)
    TextInputLayout inputCarNumber;
    @BindView(R.id.editextCompanyName)
    TextInputEditText editextCompanyName;
    @BindView(R.id.inputComapnyName)
    TextInputLayout inputComapnyName;
    @BindView(R.id.edittextCar)
    TextInputEditText edittextCar;
    @BindView(R.id.inputCarName)
    TextInputLayout inputCarName;
    //seller info
    @BindView(R.id.edittextsellerName)
    TextInputEditText edittextsellerName;
    @BindView(R.id.inputSellerName)
    TextInputLayout inputSellerName;
    @BindView(R.id.edittextSellerNumber)
    TextInputEditText edittextSellerNumber;
    @BindView(R.id.inputSellerMobileNumber)
    TextInputLayout inputSellerMobileNumber;
    @BindView(R.id.edittextSellerEmailId)
    TextInputEditText edittextSellerEmailId;
    @BindView(R.id.inputSellerEmailId)
    TextInputLayout inputSellerEmailId;

    private CarDetailsDto carDetailsDto;
    //MP 09 AB 1234

    public CreateAdFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_create_ad, container, false);
        ButterKnife.bind(this, view);
        bindData();
        return view;
    }

    private void bindData() {
        //String jsonData = "{\"car_company_name\":\"hhdd\",\"car_name\":\"ddcc\",\"car_number\":\"NH77JJ88\",\"seller_mobile\":\"8888888888\",\"seller_email_address\":\"d@outlook.com\",\"seller_name\":\"ccc\",\"total_photo_count\":\"1\"}";
        CommonUtils.WriteJsonData(getContext());
        //CommonUtils.writeToFileJson(getContext(),"BMW", "MH16MH1222", "S7");

        carDetailsDto = new CarDetailsDto();
        imgUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImagePicker.with(getActivity())              //  Initialize ImagePicker with activity or fragment context
                        .setToolbarColor("#212121")         //  Toolbar color
                        .setStatusBarColor("#000000")       //  StatusBar color (works with SDK >= 21  )
                        .setToolbarTextColor("#FFFFFF")     //  Toolbar text color (Title and Done button)
                        .setToolbarIconColor("#FFFFFF")     //  Toolbar icon color (Back and Camera button)
                        .setProgressBarColor("#4CAF50")     //  ProgressBar color
                        .setBackgroundColor("#212121")      //  Background color
                        .setCameraOnly(false)               //  Camera mode
                        .setMultipleMode(true)              //  Select multiple images or single image
                        .setFolderMode(false)                //  Folder mode
                        .setShowCamera(true)                //  Show camera button
                        .setFolderTitle("AIGEN")           //  Folder title (works with FolderMode = true)
                        .setImageTitle("Image")         //  Image title (works with FolderMode = false)
                        .setDoneTitle("Done")               //  Done button title
                        .setLimitMessage("You have reached selection limit")    // Selection limit message
                        .setMaxSize(10)                     //  Max images can be selected
                        .setSavePath("aigen")         //  Image capture folder name
                        .setSelectedImages(images)          //  Selected images
                        .setAlwaysShowDoneButton(true)      //  Set always show done button in multiple mode
                        .setRequestCode(100)                //  Set request code, default Config.RC_PICK_IMAGES
                        .setKeepScreenOn(true)              //  Keep screen on when selecting images
                        .start();
            }
        });

        btnUploadData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //save file
                saveImage();
            }
        });

        edittextCarNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String number = edittextCarNumber.getText().toString().trim();
                if (number.isEmpty() || !number.matches(car_rgex)) {
                    inputCarNumber.setError(getResources().getString(R.string.err_msg_car_reg));
                    CommonUtils.requestFocus((Activity) getActivity(), inputCarNumber);
                } else {
                    inputCarNumber.setErrorEnabled(false);
                }
                //Context context, TextInputEditText edittextEmail, TextInputLayout inputEmailId
                //  CommonUtils.validateCarReg(getActivity(), edittextCarNumber, inputCarNumber);
            }
        });
    }


    private void saveImage() {
        try {
            int valid = validationData();
            if (valid == 0) {
                if (images == null || images.size() == 0) {
                    Toast.makeText(getActivity(), "Please atleast upload 1 image", Toast.LENGTH_SHORT).show();
                } else {

                    carDetailsDto.setTotalPhotoCount(String.valueOf(images.size()));

                    Gson gson = new Gson();
                    String jsonData = gson.toJson(carDetailsDto, CarDetailsDto.class);
                    System.out.println("JSON DATA" + jsonData);
                    //CommonUtils.addEntryToJsonFile(getContext(), carDetailsDto.getCarNumber(), jsonData);
                    //CommonUtils.writeToFileJson(getContext(), jsonData, carDetailsDto.getCarNumber());
                   /* boolean isFileCreated = CommonUtils.createJsonFile(getActivity(), "storage.json", jsonData);
                    if (isFileCreated) {
                        Toast.makeText(getActivity(), "Successfull save", Toast.LENGTH_SHORT).show();
                    } else {
                        //show error or try again.
                        Toast.makeText(getActivity(), "Fail to save", Toast.LENGTH_SHORT).show();
                    }*/


                    String carNumber = edittextCarNumber.getText().toString();
                    if (TextUtils.isEmpty(carNumber)) {
                        carNumber = "default";
                    }

                    ArrayList<Image> saveImage = images;
                    boolean saveData = CommonUtils.storeImageInFloder(getContext(), carNumber, saveImage);
                    if (saveData) {
                        Toast.makeText(getActivity(), "Save Successfull", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getActivity(), "Failed to save data.", Toast.LENGTH_SHORT).show();
                    }
                }
            }

        } catch (Exception e) {
            e.getMessage();
        }

    }

    private int validationData() {
        String carNumber = edittextCarNumber.getText().toString().trim();
        String carCompanyName = editextCompanyName.getText().toString().trim();
        String carName = edittextCar.getText().toString().trim();
        //seller
        String sellerName = edittextsellerName.getText().toString().trim();
        String sellerMobileNo = edittextSellerNumber.getText().toString().trim();
        String sellerEmail = edittextSellerEmailId.getText().toString().trim();
        //set dto file
        carDetailsDto.setCarNumber(carNumber);
        carDetailsDto.setCarCompanyName(carCompanyName);
        carDetailsDto.setCarName(carName);

        carDetailsDto.setSellerName(sellerName);
        carDetailsDto.setSelleMobileNo(sellerMobileNo);
        carDetailsDto.setSellerEmailAddress(sellerEmail);

        if (TextUtils.isEmpty(carNumber) || !carNumber.matches(car_rgex)) {
            inputCarNumber.setError("Enter valid car number");
            return 1;
        }
        if (TextUtils.isEmpty(carCompanyName)) {
            inputComapnyName.setError("Enter company name");
            return 2;
        }
        if (TextUtils.isEmpty(carName)) {
            inputCarName.setError("Enter car name");
            return 3;
        }
        if (TextUtils.isEmpty(sellerName)) {
            inputSellerName.setError("Enter seller name");
            return 4;
        }
        if (TextUtils.isEmpty(sellerMobileNo) || sellerMobileNo.length() != 10) {
            inputSellerMobileNumber.setError("Enter 10digits seller mobile number");
            return 5;
        }
        if (TextUtils.isEmpty(sellerEmail) || !CommonUtils.isValidEmail(sellerEmail)) {
            inputSellerEmailId.setError("Enter valid seller email address");
            return 6;
        }
        return 0;
    }

    public void getTotalSelectImage(ArrayList<Image> selected) {
        try {
            if (selected == null) {
                textTotalImage.setVisibility(View.GONE);
                return;
            } else {
                textTotalImage.setVisibility(View.GONE);
                textTotalImage.setText("" + selected.size());
                images = selected;
            }
        } catch (Exception e) {
            e.getMessage();
            Toast.makeText(getActivity(), "faile to get file" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

}
