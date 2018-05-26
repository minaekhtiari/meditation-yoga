package apps.hillavas.com.yoga;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class AboutUs extends AppCompatActivity {

    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        webView = (WebView) findViewById(R.id.aboutUs_webView);
        webView.loadUrl("http://www.khajenasir.com/درباره-ما.html");

    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

}
