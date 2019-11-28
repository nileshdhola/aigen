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
import org.json.JSONException;
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
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
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

    public static void writeToFileJson(Context context, String data, String carNumber) {
        try {
            File file = new File(context.getFilesDir(), "AIGEN");
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
                JSONArray newuserMessage = new JSONArray();
                newuserMessage.put(carNumber);
                // messageDetails.put("Username", newuserMessage);
                messageDetails.put(carNumber, data);
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

    public static void addEntryToJsonFile(Context ctx, String id, String name) {

        // parse existing/init new JSON

        File jsonFile = new File(ctx.getDir("my_data_dir", 0), "storage.json");
        String previousJson = null;
        if (jsonFile.exists()) {
            try {
                previousJson = read(ctx, "storage.json");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            previousJson = "{}";
        }

        // create new "complex" object
        JSONObject mO = null;
        JSONObject jO = new JSONObject();
        JSONArray jsonArray = new JSONArray();

        try {
            mO = new JSONObject(previousJson);
            jO.put("car", id);
          /*  jO.put("completed", true);
            jO.put("name", name);*/
            //mO.put(id, name); //thanks "retired" answer
            //jO.put("name", name);
            mO.put(id, jO);
            jsonArray.put(name);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // generate string from the object
        String jsonString = null;
        try {
            jsonString = mO.toString(4);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // write back JSON file
        write(jsonFile, jsonString);

    }

    public static void write(File jsonFile, String jsonString) {
        // String path = Environment.getExternalStorageDirectory() + File.separator + "/AppName/App_cache" + File.separator;
        try {

            if (!jsonFile.createNewFile()) {
                jsonFile.delete();
                jsonFile.createNewFile();
            }
            ObjectOutputStream objectOutputStream = null;

            objectOutputStream = new ObjectOutputStream(new FileOutputStream(jsonFile));
            objectOutputStream.writeObject(jsonString);
            objectOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public static boolean createJsonFile(Context context, String fileName, String jsonString) {
        String FILENAME = "storage.json";

        try {
            FileOutputStream fos = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            if (jsonString != null) {
                fos.write(jsonString.getBytes());
            }
            fos.close();
            return true;
        } catch (
                FileNotFoundException fileNotFound) {
            return false;
        } catch (
                IOException ioException) {
            return false;
        }

    }

    public static String read(Context context, String fileName) {
        try {
            FileInputStream fis = context.openFileInput(fileName);
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


    public static boolean isFilePresent(Context context, String fileName) {
        String path = context.getFilesDir().getAbsolutePath() + "/" + fileName;
        File file = new File(path);
        return file.exists();
    }


}
