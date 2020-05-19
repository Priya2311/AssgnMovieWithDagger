package com.imdb.ui.logout;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;

import com.imdb.R;
import com.imdb.databinding.FragmentLogoutDialogBinding;
import com.imdb.utility.AppUtils;


public class LogoutDialogFragment extends DialogFragment implements View.OnClickListener {

    DialogCallBack dialogCallBack;
    FragmentLogoutDialogBinding fragmentLogoutDialogBinding;

    public LogoutDialogFragment() {

    }

    public void setOkClick(DialogCallBack dialogCallBack) {
        this.dialogCallBack = dialogCallBack;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentLogoutDialogBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_logout_dialog, container, false);
        return fragmentLogoutDialogBinding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        AppUtils.modifyDialogBounds(getDialog(), getContext());
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fragmentLogoutDialogBinding.btnOk.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_ok:
                if (dialogCallBack != null)
                    dialogCallBack.okPressed();
                break;
        }
    }



    public interface DialogCallBack {
        void okPressed();
    }
}
