package apps.hillavas.com.yoga.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import apps.hillavas.com.yoga.R;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class BMIActivity extends AppCompatActivity {
EditText weight,height;
Button resultBmi;
TextView txtResult,bmiStatus;
RelativeLayout relativeLayoutBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmi);

        weight=findViewById(R.id.edit_weight);
        height=findViewById(R.id.edit_height);
        resultBmi=findViewById(R.id.btn_bmi);
        txtResult=findViewById(R.id.txt_result_bmi);
        relativeLayoutBack=findViewById(R.id.layout_Back);
        bmiStatus=findViewById(R.id.bmi_status);

        resultBmi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String heightStr = height.getText().toString();
                String weightStr = weight.getText().toString();

                if (heightStr != null && !"".equals(heightStr)
                        && weightStr != null && !"".equals(weightStr)) {

                    float heightValue = Float.parseFloat(heightStr) ;
                    float weightValue = Float.parseFloat(weightStr);

                    float bmi = weightValue / (heightValue * heightValue);
                    txtResult.setText(String.valueOf(bmi));
                    displayBMI(bmi);
                }else {
                    Toast.makeText(BMIActivity.this,"وزن و قد را وارد کنید",Toast.LENGTH_LONG).show();
                }
            }


        });

relativeLayoutBack.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        onBackPressed();
    }
});

    }

//
//    public void calculateBMI(BMIActivity v) {
//
//    }

        private void displayBMI ( float bmi){
            String bmiLabel = "";

            if (Float.compare(bmi, 35f) >= 0) {
              bmiLabel = "خیلی چاق";

            } else if (Float.compare(bmi, 30f) > 0 && Float.compare(bmi, 34.9f) <= 0) {
              bmiLabel = "چاق";
            } else if (Float.compare(bmi, 25f) > 0 && Float.compare(bmi, 29.9f) <= 0) {
              bmiLabel = "اضافه وزن";
            } else if (Float.compare(bmi, 18.5f) > 0 && Float.compare(bmi, 24.9f) <= 0) {
              bmiLabel = "نرمال";
            } else if (Float.compare(bmi, 18.5f) < 0 && Float.compare(bmi, 35f) <= 0) {
                bmiLabel = "کمبود وزن";
            }
//            bmiLabel = bmi + "\n\n" + bmiLabel;
            bmiStatus.setText(bmiLabel);
        }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

}
