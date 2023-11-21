package com.example.duangiaohang.Class;

import android.app.Activity;
import android.app.AlertDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.duangiaohang.R;

public class LoadingDialog {

        private final Activity activity;
        private AlertDialog alertDialog;

        public LoadingDialog(Activity activity) {
            this.activity = activity;

        }

    public void startLoadingDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        LayoutInflater inflater = activity.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.loading_dialog, null);

        // Set background to transparent
        dialogView.setBackgroundColor(Color.TRANSPARENT);

        builder.setView(dialogView);
        builder.setCancelable(false);

        alertDialog = builder.create();

        // Set background to transparent
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        alertDialog.show();
    }

    public void dismissDialog() {
            if (alertDialog != null && alertDialog.isShowing()) {
                alertDialog.dismiss();
            }
        }
    }



