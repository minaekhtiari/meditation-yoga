package fragments;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.Toast;

import apps.hillavas.com.yoga.FirstContentActivity;
import apps.hillavas.com.yoga.R;
import classes.tools.ConnectionChecker;
import classes.tools.HideSoftKeyboards;
import factories.FragmentHelper;

/**
 * Created by mohsen.mohammadi on 6/21/2017.
 */

public class Fragment_Password extends Fragment {

    public static final String REGISTERED = "REGISTERED";
    public static final String MOBILE_NUMBER="MOBILE_NUMBER";
    public static final String PASSWORD="PASSWORD";
    SharedPreferences sharedPreferencesHome;

    EditText editPassword;
    Button btnRegister;
    Vibrator vibrator;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_password, container , false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        vibrator = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);

//        Intent intent = new Intent(getActivity() , FirstContentActivity.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//        startActivity(intent);


        if (!ConnectionChecker.check(getActivity()))
            Toast.makeText(getActivity(),R.string.noConnection, Toast.LENGTH_SHORT).show();

        sharedPreferencesHome = PreferenceManager.getDefaultSharedPreferences(getActivity());
        editPassword = (EditText) getActivity().findViewById(R.id.fragment_password_edit_pass);
        btnRegister = (Button) getActivity().findViewById(R.id.fragment_password_btn_register);
        btnRegister.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!ConnectionChecker.check(getActivity())) {
                        Toast.makeText(getActivity(), R.string.noConnection, Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (!sharedPreferencesHome.getBoolean(REGISTERED, false)) {
                        new FragmentHelper(
                                new Fragment_Register(),
                                R.id.frameLayout_base,
                                getActivity().getSupportFragmentManager()
                        ).replace(true);
                    } else {
                        Toast.makeText(getActivity(), R.string.youRegisterBefore, Toast.LENGTH_SHORT).show();
                    }
                }
            });

            editPassword.addTextChangedListener(new AbsListView(getActivity()) {
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
                    if (editPassword.getText().length() == 4) {
                        if (!ConnectionChecker.check(getActivity())) {
                            Toast.makeText(getActivity(), R.string.noConnection, Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if (editPassword.getText().toString().equals(sharedPreferencesHome.getString(PASSWORD, null))) {
                            new HideSoftKeyboards(getActivity());
//                            new FragmentHelper(new Fragment_Home(),
//                                    R.id.frameLayout_base,
//                                    getActivity().getSupportFragmentManager()
//                            ).replace(true);

                            Intent intent = new Intent(getActivity() , FirstContentActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);

//                            Intent intent = new Intent(getActivity() , MenuActivity.class);
//                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                            startActivity(intent);
                        } else {
                            vibrator.vibrate(65);
                            editPassword.setTextColor(getResources().getColor(R.color.red));
                            editPassword.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.shake));
                        }
                    } else {
                        editPassword.setTextColor(getResources().getColor(R.color.black));
                        editPassword.clearAnimation();
                    }
                }
            });

    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onStop() {
        super.onStop();
    }
}
