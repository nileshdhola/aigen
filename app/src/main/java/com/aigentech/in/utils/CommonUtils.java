package com.aigentech.in.utils;


import android.app.Activity;
import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.aigentech.in.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.nguyenhoanglam.imagepicker.model.Image;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.channels.FileChannel;
import java.util.ArrayList;

public class CommonUtils {
    private static final String car_rgex = "^[a-zA-z]{2}\\s[0-9]{2}\\s[0-9]{4}$";
    final static String FILENAME = "AIGEN";

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
                //File destinationImage = new File(f1, saveImage.get(i).getName());
                File destinationImage = new File(f1, "aigen" + i + ".jpg");
                copyFileSoureceToDestination(sourceImage, destinationImage);
            }
            return true;
        } catch (Exception e) {
            e.getMessage();
            Toast.makeText(context, " fail to save " + e.getMessage(), Toast.LENGTH_SHORT).show();
            return false;
        }

    }

    private static void copyFileSoureceToDestination(File sourceFile, File destFile) throws IOException {
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

    public static ArrayList<String> getFloderPhoto(String number) {
        ArrayList<String> f = new ArrayList<String>();// list of file paths
        File[] listFile;
        String myfolder = Environment.getExternalStorageDirectory() + "/" + "AIGEN";
        File file = new File(myfolder, number);

        if (file.isDirectory()) {
            listFile = file.listFiles();


            for (int i = 0; i < listFile.length; i++) {

                f.add(listFile[i].getAbsolutePath());

            }
            System.out.println("Total photo" + f.size());

            return f;
        }

        return f;
    }


    public static void writeToFileJson(Context context, String companyName, String carNumber, String carName) {
        try {
            File file = new File(context.getFilesDir(), FILENAME);
            FileReader fileReader = null;
            FileWriter fileWriter = null;
            BufferedReader bufferedReader = null;
            BufferedWriter bufferedWriter = null;
            String response = null;
            if (!file.exists()) {
                file.createNewFile();
                fileWriter = new FileWriter(file.getAbsoluteFile());
                bufferedWriter = new BufferedWriter(fileWriter);
                bufferedWriter.write("{}");
                bufferedWriter.close();
            }

            StringBuffer output = new StringBuffer();
            fileReader = new FileReader(file.getAbsoluteFile());
            bufferedReader = new BufferedReader(fileReader);
            String line = "";
            while ((line = bufferedReader.readLine()) != null) {

                output.append(line + "\n");
                //output.append(line);
            }
            response = output.toString();
            bufferedReader.close();


            JSONObject messageDetails = new JSONObject(response);
            Boolean isUserExit = messageDetails.has("Username");
            if (!isUserExit) {
                //JSONArray newuserMessage = new JSONArray();
                //newuserMessage.put(carNumber);
                messageDetails.put("car_company_name", companyName);
                messageDetails.put("car_name", carName);
                messageDetails.put("car_number", carNumber);

                messageDetails.put(carNumber, response);
            } else {
                JSONArray userMessage = (JSONArray) messageDetails.get("Username");
                userMessage.put(carNumber);
            }

            fileWriter = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fileWriter);
            bw.write(messageDetails.toString());
            bw.close();
        } catch (Exception e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    public static String read(Context context) {
        try {
            // File file = new File(context.getFilesDir(), "AIGEN");
            FileInputStream fis = context.openFileInput(FILENAME);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader bufferedReader = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }
            return sb.toString();
        } catch (FileNotFoundException fileNotFound) {
            return null;
        } catch (IOException ioException) {
            return null;
        }
    }


    public static boolean isFilePresent(Context context) {
        String path = context.getFilesDir().getAbsolutePath() + "/" + FILENAME;
        File file = new File(path);
        return file.exists();
    }


    //write
    public static void WriteJsonData(Context context) {
        //File file = new File(context.getFilesDir(), FILENAME);
        File fileJson = new File(context.getExternalFilesDir("/app"), "app.json");

        if (!fileJson.exists()) {
            fileJson.mkdir();
        }
        try {

            String strFileJson = getStringFromFile(fileJson);
            JSONArray jsonArray = new JSONArray(strFileJson);
            JSONObject jsonObj = new JSONObject();

            jsonObj.put("car_company_name", "Maruti");
            jsonObj.put("car_name", "Appointment Title2");
            jsonObj.put("car_number", "MH18AB1234");
            jsonObj.put("seller_mobile", "01:30");
            jsonObj.put("seller_email_address", "Dhola");
            jsonObj.put("seller_name", "Nilesh");
            jsonObj.put("total_photo_count", "1");

            jsonArray.put(jsonObj);
            writeJsonFile(fileJson, jsonArray.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getStringFromFile(File fl) throws Exception {
        //File fl = new File(filePath);
        FileInputStream fin = new FileInputStream(fl);
        String ret = convertStreamToString(fin);
        //Make sure you close all streams.
        fin.close();
        return ret;
    }

    public static String convertStreamToString(InputStream is) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line = null;
        while ((line = reader.readLine()) != null) {
            sb.append(line).append("\n");
        }
        return sb.toString();
    }

    public static void writeJsonFile(File file, String json) {
        BufferedWriter bufferedWriter = null;
        try {

            if (!file.exists()) {
                Log.e("App", "file not exist");
                file.createNewFile();
            }

            FileWriter fileWriter = new FileWriter(file);
            bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(json);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bufferedWriter != null) {
                    bufferedWriter.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
