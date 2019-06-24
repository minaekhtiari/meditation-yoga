package apps.hillavas.com.yoga.classes;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.webkit.WebView;

/**
 * @author Hoda VMehr
 */
public class CTouchyWebView extends WebView {

        public CTouchyWebView(Context context) {
            super(context);
        }

        public CTouchyWebView(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        public CTouchyWebView(Context context, AttributeSet attrs, int defStyle) {
            super(context, attrs, defStyle);
        }

        @Override
        public boolean onTouchEvent(MotionEvent event){
//            requestDisallowInterceptTouchEvent(true);
            return super.onTouchEvent(event);
        }
    }