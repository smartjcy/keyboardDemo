package com.smartjcy.demo.keyboard;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.StringRes;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentActivity;

import com.safekeyboard.SafeKeyboard;
import com.safekeyboard.SimpleKeyboardView;
import com.smartjcy.demo.R;

/**
 * @description keyboard demo
 * @author jcy
 * @date 2021-11-25 13:42:15
 */

public class MainActivity extends FragmentActivity {
    EditText etName,etPwd;
    Button btnDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initClick();
    }

    private void initView() {
        etName = findViewById(R.id.et_name);
        etPwd = findViewById(R.id.et_pwd);
        btnDialog = findViewById(R.id.btn_dialog);
        bindSafeKeyboard();
    }

    private void initClick(){
        btnDialog.setOnClickListener(v -> showDialog());
    }

    //===============================以bind模式使用================================================//
    private SafeKeyboard safeKeyboard;
    private void bindSafeKeyboard() {
        View rootView = findViewById(R.id.main_root);
        View scrollLayout = findViewById(R.id.scroll_layout);
        LinearLayout keyboardContainer = findViewById(R.id.keyboard_safe);
        safeKeyboard = new SafeKeyboard(this, keyboardContainer,
                R.layout.layout_keyboard_containor, R.id.safeKeyboardLetter, rootView, scrollLayout);
        safeKeyboard.putEditText(etPwd);
        safeKeyboard.setCustomName(getStringRes(R.string.activity_name))
                .setCustomLogo(R.mipmap.ic_launcher)
                .setCustomCloseIcon(R.mipmap.keyboard_stop);
    }

    //===============================以dialog模式使用================================================//
    private SimpleKeyboardView mSimpleKeyboardView;
    private void showDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_demo, null);
        builder.setView(view);
        builder.setCancelable(false);
        AlertDialog dialog = builder.show();
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        final TextView tv_sure = (TextView) view.findViewById(R.id.tv_sure);
        final TextView tv_cancel = (TextView) view.findViewById(R.id.tv_cancel);
        EditText mCetPwd = view.findViewById(R.id.cet_pwd);
        //键盘组件
        mSimpleKeyboardView = view.findViewById(R.id.skv);
        mSimpleKeyboardView.bindEditText(mCetPwd);
        mSimpleKeyboardView.setCustomName(getStringRes(R.string.dialog_keyboard))
                .setCustomLogo(R.mipmap.icon_protect);
//                .setCustomCloseIcon(R.mipmap.keyboard_stop);
        //确认
        tv_sure.setOnClickListener(v -> dialog.dismiss());
        //取消
        tv_cancel.setOnClickListener(v -> dialog.dismiss());

        Window window = dialog.getWindow();// 得到dialog的窗体
        if (window != null) {
            window.setGravity(Gravity.BOTTOM);
            window.setWindowAnimations(R.style.DialogAnimation);
            window.setBackgroundDrawableResource(android.R.color.transparent);
            window.getDecorView().setPadding(0, 0, 0, 0);
            WindowManager.LayoutParams layoutParams = window.getAttributes();
            layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
            layoutParams.horizontalMargin = 0;
            window.setAttributes(layoutParams);
            window.getDecorView().setBackgroundColor(this.getResources().getColor(R.color.transparent));
        }
    }


    @Override
    protected void onDestroy() {
        releaseKeyboard(safeKeyboard);
        super.onDestroy();
    }

    private void releaseKeyboard(SafeKeyboard safeKeyboard) {
        if (safeKeyboard != null) {
            safeKeyboard.release();
            safeKeyboard = null;
        }
    }

    private String getStringRes(@StringRes int stringId){
        return this.getResources().getString(stringId);
    }

}