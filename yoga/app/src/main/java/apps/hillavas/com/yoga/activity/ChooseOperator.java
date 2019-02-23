package apps.hillavas.com.yoga.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import apps.hillavas.com.yoga.R;
import apps.hillavas.com.yoga.activity.sign.FragmentSubscribe;

public class ChooseOperator extends AppCompatActivity implements View.OnClickListener {
    AppCompatButton btnIrancell, btnHamrahAval;
    public static final String IS_IRANCELL = "IS_IRANCELL";
    public static final String IS_HAMRAHAVAL = "IS_HAMRAHAVAL";
    SharedPreferences sharedPreferencesHome;
    FragmentTransaction transaction;
    FragmentManager fm;
    FrameLayout frameBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_operator);


        sharedPreferencesHome = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        btnHamrahAval = findViewById(R.id.btn_hamrah);
        btnIrancell = findViewById(R.id.btn_irancell);
        frameBase = findViewById(R.id.frameLayout_base);

        transaction = getSupportFragmentManager().beginTransaction().addToBackStack(null);
        //fm = getSupportFragmentManager();

//        transaction = fm.beginTransaction();
//        transaction.addToBackStack(null);
        btnIrancell.setOnClickListener(this);
        btnHamrahAval.setOnClickListener(this);


//        if (LocalStorage.get(this).getBoolean("userIsSubscribe", false)) {
//            if (LocalStorage.get(this).getBoolean("isIrancell", false)) {
//                startActivity(new Intent(this, MtnSubscribeActivity.class));
//
//            } else {
//                startActivity(new Intent(this, MciSubscribeActivity.class));
//            }
//            finish();
//        }
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.btn_hamrah:
                sharedPreferencesHome.edit().putBoolean(IS_IRANCELL, false).apply();
                sharedPreferencesHome.edit().putBoolean(IS_HAMRAHAVAL, true).apply();
//                transaction.replace(R.id.frameLayout_base, new FragmentSubscribe()).commit();

                openFragment(new FragmentSubscribe());

                break;
            case R.id.btn_irancell:
                sharedPreferencesHome.edit().putBoolean(IS_IRANCELL, true).apply();
                sharedPreferencesHome.edit().putBoolean(IS_HAMRAHAVAL, false).apply();
                Intent inten = new Intent(ChooseOperator.this, IntroActivity.class);
                startActivity(inten);
                openFragment(new FragmentSubscribe());
//                transaction.replace(R.id.frameLayout_base, new FragmentSubscribe()).commit();

                break;
        }


        // startActivity(new Intent(ChooseOperator.this, HOME.class));
        //finish();

    }

    private void openFragment(final Fragment fragment) {
         fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.frameLayout_base, fragment);
        transaction.addToBackStack(null);
        transaction.commit();

    }



    @Override
    public void onBackPressed() {

            super.onBackPressed();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        super.onActivityResult(requestCode, resultCode, data);
        for (Fragment fragment : getSupportFragmentManager().getFragments()) {
            fragment.onActivityResult(requestCode, resultCode, data);
        }

    }
}
