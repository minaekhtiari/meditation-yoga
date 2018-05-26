package apps.hillavas.com.yoga;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import factories.FragmentHelper;
import fragments.Fragment_ArticleTextView;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ArticleTextView extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_text_view);
        getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);

        Bundle bundle = getIntent().getExtras();
        Fragment_ArticleTextView articleTextView = new Fragment_ArticleTextView();
        articleTextView.setArguments(bundle);

        new FragmentHelper(
                        articleTextView,
                        R.id.frameLayout_base,
                        getSupportFragmentManager()
        ).replace(false);
    }

    @Override
    protected void onPause() {
        super.onPause();
        onStop();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

}
