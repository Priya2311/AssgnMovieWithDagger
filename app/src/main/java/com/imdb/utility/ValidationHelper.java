package com.imdb.utility;

import android.content.Context;
import android.graphics.Color;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.material.snackbar.Snackbar;
import com.imdb.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidationHelper {


    public enum ALERT_TYPE {TOAST, SNACK_BAR}

    /**
     * This method returns true if a edit text is blank ,false otherwise
     *
     * @param targetEditText source edit text
     * @param msg            message to be shown in snackbar
     * @return
     */
    public static boolean isBlank(@NonNull TextView targetEditText, String msg) {
        String source = targetEditText.getText().toString().trim();
        if (source.isEmpty()) {
            showAlert(targetEditText, ALERT_TYPE.SNACK_BAR, msg);
            return true;
        }
        return false;
    }

    /**
     * This method returns true if a edit text contains valid email ,false otherwise
     *
     * @param targetEditText source edit text
     * @param msg            message to be shown in snackbar
     * @return
     */
    public static boolean isEmailValid(@NonNull EditText targetEditText, String msg) {
        String source = targetEditText.getText().toString().trim();
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern p = Pattern.compile(expression, Pattern.CASE_INSENSITIVE); // pattern=/^([a-zA-Z0-9_.-])+@([a-zA-Z0-9_.-])+\.([a-zA-Z])+([a-zA-Z])+/;
        Matcher m = p.matcher(source);
        if (m.matches() && source.trim().length() > 0) {
            return true;
        }
        showAlert(targetEditText, ALERT_TYPE.SNACK_BAR, msg);
        return false;
    }


    public static void showToast(Context context, String msg) {
        Toast.makeText(context.getApplicationContext(), msg, Toast.LENGTH_LONG).show();
    }

    private static void showAlert(TextView targetEditText, ALERT_TYPE alertType, String msg) {
        targetEditText.requestFocus();
        if (alertType == ALERT_TYPE.TOAST) {
            showToast(targetEditText.getContext(), msg);
        } else if (alertType == ALERT_TYPE.SNACK_BAR) {
            showSnackBar(targetEditText, msg);
        }

    }


    public static void showSnackBar(View parentLayout, String msg) {
        final Snackbar snackBar = Snackbar.make(parentLayout, msg, Snackbar.LENGTH_SHORT);
        snackBar.setActionTextColor(Color.WHITE);
        View view = snackBar.getView();
        TextView tv = (TextView)
                view.findViewById(R.id.snackbar_text);
        tv.setTextColor(Color.WHITE);
        snackBar.show();

    }

    public static boolean hasMinimumLength(String source, int length) {
        if (source.trim().length() >= length)
            return true;
        return false;
    }

    public static boolean hasMinimumLength(EditText editText, int length, String message) {
        if (!hasMinimumLength(editText.getText().toString().trim(), length)) {
            showAlert(editText, ALERT_TYPE.TOAST, message);
            return false;
        }
        return true;

    }




}
