package com.imdb.utility;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.android.material.snackbar.Snackbar;
import com.imdb.R;


public class DialogUtil {

    private static final String TAG = DialogUtil.class.getSimpleName();

    public static ProgressDialog showProgressDialog(Context mContext) {
        try {
            if (mContext != null && !((Activity) mContext).isFinishing()) {
                ProgressDialog mProgressDialog = ProgressDialog.show(mContext,
                        "", mContext.getString(R.string.please_wait));
                return mProgressDialog;
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
        return new ProgressDialog(mContext);
    }


    public static void hideProgressDialog(@NonNull ProgressDialog progressDialog) {
        try {
            if (progressDialog.isShowing()) progressDialog.dismiss();
        } catch (Exception e) {
            e.printStackTrace();

        }
    }


    public static Snackbar showRetrySnackBar(@NonNull View anyView, String msg, final View.OnClickListener retryListener) {
        //Toast.makeText(context.getApplicationContext(), msg, Toast.LENGTH_LONG).show();
        try {
            final Snackbar snackBar = Snackbar.make(anyView, msg, Snackbar.LENGTH_INDEFINITE);
            snackBar.setActionTextColor(Color.WHITE);
            View view = snackBar.getView();
            TextView tv = (TextView)
                    view.findViewById(R.id.snackbar_text);
            tv.setTextColor(Color.WHITE);
            snackBar.setAction(R.string.retry, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    snackBar.dismiss();
                    retryListener.onClick(v);
                }
            });
            snackBar.show();
            return snackBar;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public static Snackbar showNoNetworkSnackBar(@NonNull View anyView, final View.OnClickListener listener) {
        String msg = anyView.getContext().getResources().getString(R.string.no_network_msg);
        return showRetrySnackBar(anyView, msg, listener);
    }




}
