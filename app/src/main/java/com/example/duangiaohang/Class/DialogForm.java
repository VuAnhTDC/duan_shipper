package com.example.duangiaohang.Class;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;

import com.example.duangiaohang.R;

public class DialogForm extends DialogFragment {

    private static final String ARG_FIELD_KEY = "fieldKey";

    private String fieldKey;
    private UpdateDataListener updateDataListener;

    public DialogForm() {
        // Required empty public constructor
    }

    public static DialogForm newInstance(String fieldKey) {
        DialogForm fragment = new DialogForm();
        Bundle args = new Bundle();
        args.putString(ARG_FIELD_KEY, fieldKey);
        fragment.setArguments(args);
        return fragment;

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        if (getArguments() != null) {
            fieldKey = getArguments().getString(ARG_FIELD_KEY);
        }
    }

//    @Override
//    public void onStart() {
//        super.onStart();
//        int with = 3000;
//        int height = 3000;
//        getDialog().getWindow().setLayout(with, height);
//    }

        @Override
        public View onCreateView (LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState){
            // Inflate the layout for this fragment
            return inflater.inflate(R.layout.logdiag, container, false);
        }

        @Override
        public void onViewCreated (@NonNull View view, @Nullable Bundle savedInstanceState){
            super.onViewCreated(view, savedInstanceState);

            EditText editText = view.findViewById(R.id.editText);
            Button btnCancel = view.findViewById(R.id.btnCancel);
            Button btnUpdate = view.findViewById(R.id.btnUpdate);

            btnCancel.setOnClickListener(v -> cancelDialog());
            btnUpdate.setOnClickListener(v -> {
                String newData = editText.getText().toString();
                sendDataToActivity(newData);
                dismiss();
            });
        }

        private void sendDataToActivity (String newData){
            if (updateDataListener != null) {
                updateDataListener.onUpdateData(fieldKey, newData);
            }
        }

        private void cancelDialog () {
            if (updateDataListener != null) {
                updateDataListener.onCancel();
            }
            dismiss();
        }

        @Override
        public void onAttach (@NonNull Context context){
            super.onAttach(context);
            if (context instanceof UpdateDataListener) {
                updateDataListener = (UpdateDataListener) context;
            } else {
                throw new RuntimeException(context.toString()
                        + " must implement UpdateDataListener");
            }
        }

        public interface UpdateDataListener {
            void onUpdateData(String fieldKey, String newData);

            void onCancel();
        }
    }
