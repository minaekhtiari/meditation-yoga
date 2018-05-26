package apps.hillavas.com.yoga;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

public class Yoga_contents_all_for_categoryid extends AppCompatActivity {
    private static final String CATEGORY_ID = "CATEGORY_ID";
    int categoryIdSecelted;
    RecyclerView recyclerView ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yoga_contents_all_for_categoryid);
        if(getIntent().getExtras().containsKey(CATEGORY_ID))
            categoryIdSecelted = getIntent().getIntExtra(CATEGORY_ID , 0);
        if(categoryIdSecelted > 0){

        }
        recyclerView = (RecyclerView) findViewById(R.id.activity_yoga_contents_all_recyclerview);

    }
}
