package apps.hillavas.com.yoga.activity;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import apps.hillavas.com.yoga.R;
import apps.hillavas.com.yoga.adapters.ViewPager_FragmentAdapter_trainingAsli;
import apps.hillavas.com.yoga.adapters.ViewPager_FragmentAdapter_trainingBarnamePishnahadi;

public class TrainingActivity extends AppCompatActivity {
    public static final String TRAINING_CODE="TRAINING_CODE";
    ViewPager viewPagerTraining;

    int code = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training);
        if(getIntent().getExtras().containsKey(TRAINING_CODE))
            if(getIntent().getExtras().getInt(TRAINING_CODE , 0) > 0)
                    code = getIntent().getExtras().getInt(TRAINING_CODE , 0);

        if(code == 1) {
            viewPagerTraining = (ViewPager) findViewById(R.id.training_viewPager);
            viewPagerTraining.setAdapter(new ViewPager_FragmentAdapter_trainingAsli(getSupportFragmentManager()));
        }else if(code == 2){
            viewPagerTraining = (ViewPager) findViewById(R.id.training_viewPager);
            viewPagerTraining.setAdapter(new ViewPager_FragmentAdapter_trainingBarnamePishnahadi(getSupportFragmentManager()));
        }else {
            viewPagerTraining = (ViewPager) findViewById(R.id.training_viewPager);
            viewPagerTraining.setAdapter(new ViewPager_FragmentAdapter_trainingAsli(getSupportFragmentManager()));
        }
    }
}
