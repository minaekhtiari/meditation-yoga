package fragments;


import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;

import apps.hillavas.com.meditation.R;
import factories.FragmentHelper;


/**
 * Created by mohsen.mohammadi on 6/21/2017.
 */

public class Fragment_Login extends Fragment implements View.OnClickListener{


    EditText edit_mobileNumber;
    EditText edit_password;
    Button btnRegister;
    Button btnLogin;
    ImageView ivPassLock;
    ImageView ivPassEye;
    Vibrator vibrator;

    Typeface tfIranSans;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login , container , false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        vibrator = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
        tfIranSans = Typeface.createFromAsset(getContext().getAssets() , "fonts/iransans.ttf");

        edit_mobileNumber = (EditText) getActivity().findViewById(R.id.fragment_login_EditMobile);
        edit_password = (EditText) getActivity().findViewById(R.id.fragment_login_EditPassword);
        edit_password.setInputType( InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD );
        edit_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
        edit_password.setTypeface(tfIranSans);
        ivPassLock = (ImageView) getActivity().findViewById(R.id.fragment_login_imageLock);
        ivPassEye = (ImageView) getActivity().findViewById(R.id.fragment_login_imageEye);
        btnRegister = (Button) getActivity().findViewById(R.id.fragment_login_btn_register);
        btnLogin = (Button) getActivity().findViewById(R.id.fragment_login_btn_login);
        ivPassEye.setOnClickListener(this);
        btnRegister.setOnClickListener(this);
        btnLogin.setOnClickListener(this);

        edit_mobileNumber.addTextChangedListener(new AbsListView(getActivity()) {
            @Override
            public ListAdapter getAdapter() {
                return null;
            }

            @Override
            public void setSelection(int position) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                super.onTextChanged(s, start, before, count);
                if(edit_mobileNumber.getText().length() > 0)
                    edit_mobileNumber.setGravity(Gravity.LEFT | Gravity.CENTER);
                else
                    edit_mobileNumber.setGravity(Gravity.RIGHT | Gravity.CENTER);
            }
        });

        edit_password.addTextChangedListener(new AbsListView(getActivity()) {
            @Override
            public ListAdapter getAdapter() {
                return null;
            }

            @Override
            public void setSelection(int position) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                super.onTextChanged(s, start, before, count);
                if(edit_password.getText().length() > 0) {
                    edit_password.setGravity(Gravity.LEFT | Gravity.CENTER);
                    ivPassEye.setVisibility(VISIBLE);
                    ivPassLock.setVisibility(INVISIBLE);

                } else {
                    edit_password.setGravity(Gravity.RIGHT | Gravity.CENTER);
                    ivPassEye.setVisibility(INVISIBLE);
                    ivPassLock.setVisibility(VISIBLE);
                }

            }
        });


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.fragment_login_btn_register:{

                new FragmentHelper(
                        new Fragment_Register(),
                        R.id.frameLayout_base,
                        getActivity().getSupportFragmentManager()
                ).replace(true);
                vibrator.vibrate(65);
                break;
            }


            case R.id.fragment_login_imageEye:{
                if(edit_password.getInputType() == 129) {
                    edit_password.setInputType(145);
                    vibrator.vibrate(65);
                    edit_password.setTypeface(tfIranSans);
                }else if(edit_password.getInputType() == 145) {
                    edit_password.setInputType(129);
                    vibrator.vibrate(65);
                    edit_password.setTypeface(tfIranSans);
                }
                break;
            }


        }
    }
}
