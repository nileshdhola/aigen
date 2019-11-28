package com.aigentech.in.utils;


import android.app.Activity;
import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.aigentech.in.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.nguyenhoanglam.imagepicker.model.Image;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.FileChannel;
import java.util.ArrayList;

public class CommonUtils {
    private static final String car_rgex = "^[a-zA-z]{2}\\s[0-9]{2}\\s[0-9]{4}$";


    //region validation Email
    public static boolean validateEmail(Context context, TextInputEditText edittextEmail, TextInputLayout inputEmailId) {
        String email = edittextEmail.getText().toString().trim();
        if (email.isEmpty() || !isValidEmail(email)) {
            inputEmailId.setError(context.getResources().getString(R.string.err_msg_email));
            requestFocus((Activity) context, inputEmailId);
            return false;
        } else if (!email.equalsIgnoreCase("test@aigen.tech")) {
            inputEmailId.setError(context.getResources().getString(R.string.err_msg_email_not_match));
            requestFocus((Activity) context, inputEmailId);
            return false;
        } else {
            inputEmailId.setErrorEnabled(false);
        }
        return true;
    }

    //endregion
    //region validation password
    public static boolean validatePassword(Context context, TextInputEditText edittextPassword, TextInputLayout inputEmailId) {
        String pass = edittextPassword.getText().toString().trim();
        if (edittextPassword.getText().toString().trim().isEmpty()) {
            inputEmailId.setError(context.getResources().getString(R.string.err_msg_password));
            requestFocus((Activity) context, inputEmailId);
            return false;
        } else if (!pass.equalsIgnoreCase("AigenTech")) {
            inputEmailId.setError(context.getResources().getString(R.string.err_msg_password_not_match));
            requestFocus((Activity) context, inputEmailId);
            return false;
        } else {
            inputEmailId.setErrorEnabled(false);
        }

        return true;
    }
    //endregion

    public static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public static void requestFocus(Activity activity, View view) {
        if (view.requestFocus()) {
            activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    //region validation Email
    public static boolean validateCarReg(Context context, TextInputEditText edittextEmail, TextInputLayout inputEmailId) {
        String number = edittextEmail.getText().toString().trim();
        if (number.isEmpty() || !number.matches(car_rgex)) {
            inputEmailId.setError(context.getResources().getString(R.string.err_msg_car_reg));
            requestFocus((Activity) context, inputEmailId);
            return false;
        } else {
            inputEmailId.setErrorEnabled(false);
        }
        return true;
    }


    //Method to move the file
    public static void moveFile(File sourceFile, File destFile) throws IOException {
        if (!sourceFile.exists()) {
            return;
        }
        FileChannel source = null;
        FileChannel destination = null;
        source = new FileInputStream(sourceFile).getChannel();
        destination = new FileOutputStream(destFile).getChannel();
        if (destination != null && source != null) {
            destination.transferFrom(source, 0, source.size());
        }
        if (source != null) {
            source.close();
        }
        if (destination != null) {
            destination.close();
        }
    }

    public static void copyFile(Context context, String inputPath, String outputPath) {

        InputStream in = null;
        OutputStream out = null;
        try {
            in = new FileInputStream(inputPath);
            out = new FileOutputStream(outputPath);

            byte[] buffer = new byte[1024];
            int read;
            while ((read = in.read(buffer)) != -1) {
                out.write(buffer, 0, read);
            }
            in.close();
            in = null;

            // write the output file (You have now copied the file)
            out.flush();
            out.close();
            out = null;
            Toast.makeText(context, " success to save ", Toast.LENGTH_SHORT).show();

        } catch (FileNotFoundException fnfe1) {
            Toast.makeText(context, " fail to save " + fnfe1.getMessage(), Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(context, " fail to save " + e.getMessage(), Toast.LENGTH_SHORT).show();

        }
    }

    public static boolean storeImageInFloder(Context context, String floderName, ArrayList<Image> saveImage) {
        try {
            //create parent floder
            String myfolder = Environment.getExternalStorageDirectory() + "/" + "AIGEN";
            File f = new File(myfolder);
            if (!f.exists())
                if (!f.mkdir()) {
                    Toast.makeText(context, myfolder + " can't be created.", Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(context, myfolder + " can be created.", Toast.LENGTH_SHORT).show();

            //create child floder
            File f1 = new File(myfolder, floderName);
            if (!f1.exists()) {
                f1.mkdirs();
            }

            //save imaage to source to destion floder
            for (int i = 0; i <= saveImage.size() - 1; i++) {
                File sourceImage = new File(saveImage.get(i).getPath()); //returns the image File from model class to be moved.
                File destinationImage = new File(f1, saveImage.get(i).getName());
                copyFile1(sourceImage, destinationImage);
            }
            return true;
        } catch (Exception e) {
            e.getMessage();
            Toast.makeText(context, " fail to save " + e.getMessage(), Toast.LENGTH_SHORT).show();
            return false;
        }

    }

    private static void copyFile1(File sourceFile, File destFile) throws IOException {
        if (!sourceFile.exists()) {
            return;
        }

        FileChannel source = null;
        FileChannel destination = null;
        source = new FileInputStream(sourceFile).getChannel();
        destination = new FileOutputStream(destFile).getChannel();
        if (destination != null && source != null) {
            destination.transferFrom(source, 0, source.size());
        }
        if (source != null) {
            source.close();
        }
        if (destination != null) {
            destination.close();
        }

    }


    private static void copySingleFile(File sourceFile, File destFile)
            throws IOException {
        System.out.println("COPY FILE: " + sourceFile.getAbsolutePath()
                + " TO: " + destFile.getAbsolutePath());
        if (!destFile.exists()) {
            destFile.createNewFile();
        }

        FileChannel sourceChannel = null;
        FileChannel destChannel = null;

        try {
            sourceChannel = new FileInputStream(sourceFile).getChannel();
            destChannel = new FileOutputStream(destFile).getChannel();
            sourceChannel.transferTo(0, sourceChannel.size(), destChannel);
        } finally {
            if (sourceChannel != null) {
                sourceChannel.close();
            }
            if (destChannel != null) {
                destChannel.close();
            }
        }
    }


}
